package nio.buffer;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * Created by Niki on 2018/5/9 9:31
 */
public class DirectByteBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(100 * 1024 * 1024);
        System.out.println("start");
        sleep(80000);
        clear(buffer);
        System.out.println("end");
        sleep(5000);
    }

    /**
     * 清楚direct Memory
     * @param buffer
     */
    private static void clear(final ByteBuffer buffer) {
        if (buffer.isDirect()) {
            ((DirectBuffer) buffer).cleaner().clean();
        }
    }

    private static void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
