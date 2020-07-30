package nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * Created by Niki on 2018/5/10 8:32
 */
public class FileChannelLockDemo {
    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
    private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
    //创建此字节缓冲区的视图，作为 int 缓冲区。
    private IntBuffer indexBuffer = buffer.asIntBuffer();

    private Random random = new Random();

    public static void main(String[] args) throws Exception {
        boolean writer = false;
        String fileName;
        if (args.length != 2) {
            System.out.println("Usage: [ -r | -w ] filename");
            return;
        }

        writer = args[0].equals("-w");
        fileName = args[1];
        RandomAccessFile raf = new RandomAccessFile(fileName, writer ? "rw" : "r");

        FileChannel fileChannel = raf.getChannel();

        FileChannelLockDemo lockDemo = new FileChannelLockDemo();

        if (writer) {
            lockDemo.doUpdates(fileChannel);
        } else {
            lockDemo.doQuery(fileChannel);
        }

    }

    void doQuery(FileChannel fileChannel) {
        while (true) {
            System.out.println("trying for shared lock...");
            FileLock lock = null;
            try {
                lock = fileChannel.lock(INDEX_START, INDEX_SIZE, true);

                int reps = random.nextInt(60) + 20;
                for (int i = 0; i < reps; i++) {
                    int n = random.nextInt(INDEX_COUNT);
                    int position = INDEX_START + (n * SIZEOF_INT);
                    buffer.clear();

                    //从给定的文件位置开始，从此通道读取字节序列,并写入给定的缓冲区
                    fileChannel.read(buffer, position);
                    int value = indexBuffer.get(n);
                    System.out.println("Index entry " + n + "=" + value);
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock != null) {
                    try {
                        lock.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    void doUpdates(FileChannel fc)
            throws Exception {
        while (true) {
            System.out.println(("trying for exclusive lock..."));
            FileLock lock = fc.lock(INDEX_START,
                    INDEX_SIZE, false);
            updateIndex(fc);
            lock.release();
            System.out.println(("<sleeping>"));
            Thread.sleep(random.nextInt(2000) + 500);
        }
    }

    private int idxval = -1;

    private void updateIndex(FileChannel fc)
            throws Exception {
        // "indexBuffer" is an int view of "buffer"
        indexBuffer.clear();
        for (int i = 0; i < INDEX_COUNT; i++) {
            idxval++;
            System.out.println(("Updating index " + i + "=" + idxval));
            indexBuffer.put(idxval);
            // Pretend that this is really hard work
            Thread.sleep(500);
        }
        // leaves position and limit correct for whole buffer
        buffer.clear();
        fc.write(buffer, INDEX_START);
    }

}
