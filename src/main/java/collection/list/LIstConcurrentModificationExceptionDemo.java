package collection.list;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Niki on 2020/12/21 9:50
 * 对list进行并发读取，以及修改时，在读取的时候发现有修改的动作，读取的方法会抛异常：ConcurrentModificationException
 */
public class LIstConcurrentModificationExceptionDemo {

    private List<Integer> list = Lists.newArrayList(1, 2, 3);

    private void readList() {
        list.stream().forEach(l->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(l);
        });
    }

    private void modifyList() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                list.add(4);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(5);
            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }

    public static void main(String[] args) {
        LIstConcurrentModificationExceptionDemo test = new LIstConcurrentModificationExceptionDemo();

        test.modifyList();
        test.readList();
    }
}
