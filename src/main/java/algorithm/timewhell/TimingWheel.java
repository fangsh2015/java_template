package algorithm.timewhell;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Niki on 2020/8/14 16:42
 * @see{@link https://blog.csdn.net/mindfloating/article/details/8033340}
 */
public class TimingWheel<E> {

    private final long tickDuration;
    private final int ticksPerWheel;
    private volatile int currentTickIndex = 0;

    private final CopyOnWriteArrayList<ExpirationListener<E>> expirationListeners = new CopyOnWriteArrayList<>();
    private final ArrayList<Slot<E>> wheel;
    private final Map<E, Slot<E>> indicator = new ConcurrentHashMap<>();

    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private Thread workThread;

    public TimingWheel(int tickDuration, int ticksPerWheel, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }

        if (tickDuration <= 0) {
            throw new IllegalArgumentException("tickDuration must be greater than 0:" + tickDuration);
        }

        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }

        this.wheel = new ArrayList<>();
        this.tickDuration = TimeUnit.MILLISECONDS.convert(tickDuration, timeUnit);
        this.ticksPerWheel = ticksPerWheel + 1;

        for (int i = 0; i < this.ticksPerWheel; i++) {
            wheel.add(new Slot<>(i));
        }
        wheel.trimToSize();

        workThread = new Thread(new TickWorker(), "Timing-Wheel");

    }

    public void start() {
        if (shutdown.get()) {
            throw new IllegalStateException("Cannot be started once stopped");
        }
        if (!workThread.isAlive()) {
            workThread.start();
        }
    }

    public boolean stop() {
        if (!shutdown.compareAndSet(false, true)) {
            return false;
        }
        boolean interrupted = false;
        while (workThread.isAlive()) {
            workThread.interrupt();
            try {
                workThread.join(100);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    public void addExpirationListener(ExpirationListener<E> listener) {
        expirationListeners.add(listener);
    }

    public void removeExpirationListener(ExpirationListener<E> listener) {
        expirationListeners.remove(listener);
    }

    public long add(E e) {
        synchronized (e) {
            checkAdd(e);

            int previousTickIndex = getPreviousTickIndex();
            Slot<E> slot = wheel.get(previousTickIndex);
            slot.add(e);
            indicator.put(e, slot);

            return (ticksPerWheel - 1) * tickDuration;
        }
    }

    private int getPreviousTickIndex() {
        lock.readLock().lock();
        try {
            int cti = currentTickIndex;
            if (cti == 0) {
                return ticksPerWheel - 1;
            }
            return cti - 1;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void checkAdd(E e) {
        Slot<E> slot = indicator.get(e);
        if (slot != null) {
            slot.remove(e);
        }
    }

    public boolean remove(E e) {
        synchronized (e) {
            Slot<E> slot = indicator.get(e);
            if (slot == null) {
                return false;
            }
            indicator.remove(e);
            return slot.remove(e) != null;
        }
    }

    private void notifyExpired(int idx) {
        Slot<E> slot = wheel.get(idx);
        Set<E> elements = slot.elements();
        for (E element : elements) {
            slot.remove(element);
            synchronized (element) {
                Slot<E> latestSlot = indicator.get(element);
                if (latestSlot.equals(slot)) {
                    indicator.remove(element);
                }
            }

            for (ExpirationListener<E> expirationListener : expirationListeners) {
                expirationListener.expired(element);
            }
        }
    }

    @Data
    private class Slot<E> {
        private int id;
        private Map<E, E> elements = new ConcurrentHashMap<>();

        public Slot(int id) {
            this.id = id;
        }

        public void add(E e) {
            elements.put(e, e);
        }

        public E remove(E e) {
            return elements.remove(e);
        }

        public Set<E> elements() {
            return elements.keySet();
        }
    }



    private class TickWorker implements Runnable {
        private long startTime;
        private long tick;

        @Override
        public void run() {
            startTime = System.currentTimeMillis();
            tick = 1;

            for (int i = 0; !shutdown.get(); i++) {
                if (i == wheel.size()) {
                    i = 0;
                }
                lock.writeLock().lock();
                try {
                    currentTickIndex = i;
                } finally {
                    lock.writeLock().unlock();
                }
                notifyExpired(currentTickIndex);
                waitForNextTick();
            }
        }

        private void waitForNextTick() {
            for (; ; ) {
                long currentTime = System.currentTimeMillis();
                long sleepTime = tickDuration * tick - (currentTime - startTime);

                if (sleepTime <= 0) {
                    break;
                }

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            tick++;
        }
    }


}

