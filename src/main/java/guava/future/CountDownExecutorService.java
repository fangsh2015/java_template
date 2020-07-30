package guava.future;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.*;

/**
 * 对LintenableFuture的封装，用来一次获取多个Future的值，避免每个Future都阻塞等待
 * Created by Niki on 2019/2/22 14:32
 */
public class CountDownExecutorService<T> {
    private ListeningExecutorService executorService;

    private List<ListenableFuture<T>> futureList;

    /**
     * 构造方法，初始化用于执行异步任务的线程池
     *
     * @param service
     */
    private CountDownExecutorService(ExecutorService service) {
        executorService = MoreExecutors.listeningDecorator(service);
        futureList = Lists.newArrayList();
    }

    public CountDownExecutorService getInstance(ExecutorService service) {
        if (service == null) {
            throw new IllegalArgumentException("executor service is not null");
        }
        return new CountDownExecutorService(service);
    }

    public CountDownExecutorService getInstance(int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                TimeUnit unit,
                                                BlockingQueue<Runnable> workQueue) {
        ExecutorService service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        return new CountDownExecutorService(service);
    }

    /**
     * 添加异步任务,有返回值
     *
     * @param task
     */
    public ListenableFuture<T> addTask(Callable<T> task) {
        ListenableFuture<T> future = executorService.submit(task);
        futureList.add(future);
        return future;
    }

    /**
     * 添加异步任务，无返回值
     * @param runnable
     * @return
     */
    public ListenableFuture addTask(Runnable runnable) {
        ListenableFuture future = executorService.submit(runnable);
        futureList.add(future);
        return future;
    }

    /**
     * 清除所有的异步任务
     */
    public void clearAll() {
        futureList.forEach(futureList -> {
            futureList = null;
        });
        futureList.clear();
    }

    public void clearAll(boolean destory) {
        if (!destory) {
            clearAll();
            return;
        } else {
            futureList.forEach(future -> {
                future.cancel(true);
                futureList.remove(future);
            });
        }
    }

    public List<T> getAllResult() throws ExecutionException, InterruptedException {
        ListenableFuture<List<T>> future = Futures.allAsList(futureList);
        return future.get();
    }

    public List<T> getAllResult(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<List<T>> future = Futures.allAsList(futureList);
        return future.get(timeout, unit);
    }


}
