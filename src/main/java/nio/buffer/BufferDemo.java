package nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * 缓冲区的demo
 * Created by Niki on 2018/5/7 8:59
 */
public class BufferDemo {
    public static void main(String[] args) {

        CharBuffer buffer = CharBuffer.allocate(10);
        print(buffer, "还没开始插入数据之前的位置");

        String demo = "1234567890";
        for (int i = 0; i < 10; i++) {
            buffer.put(demo.charAt(i));

        }

        buffer.flip();
        print(buffer, "flip之后的位置");
        boolean hasRemaining = buffer.hasRemaining();
        print(buffer,"还没开始读之前的位置");
        System.out.println(buffer.get());
        System.out.println(buffer.get());

        print(buffer,"读取两次后的位置");

        buffer.rewind();
        print(buffer, "rewind之后的位置");
    }


    private static void print(Buffer buffer, String msg) {
        System.out.println("********************"+msg+"********************");
        System.out.println("capacity is :" + buffer.capacity());
        System.out.println("limit is :" + buffer.limit());
        System.out.println("position is :" + buffer.position());
        System.out.println("mark is :" + buffer.mark());
        System.out.println("******************************************");
    }

    private static void testStringBuffer() {

    }
}
