package test;

/**
 * Created by Niki on 2018/5/10 14:50
 */
public class EscapeAnalysis {
    private static class Foo {
        private int x;
        private static int counter;
        private static byte[] buffer = new byte[1024];

        public Foo() {
            x = (++counter);
        }
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("***starg***");
        long start = System.nanoTime();
        for (int i = 0; i < 1000 * 1000 * 10; ++i) {
            Foo foo = new Foo();
        }
        long end = System.nanoTime();

        //no escapeAnalysis : Time cost is 6130081
        //escapeAnalysis : Time cost is 6127904
        System.out.println("Time cost is " + (end - start));
    }

}
