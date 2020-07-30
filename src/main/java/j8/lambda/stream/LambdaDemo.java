package j8.lambda.stream;

/**
 * Created by Niki on 2018/10/25 17:44
 */
public class LambdaDemo {

    public static void main(String[] args) {
        Model m = new Model();
        Action<Model> a1 = s -> System.out.println("hello");
        a1.run(m);

        Action<Model> a2 = Model::test3;
        a2.run(m);

        Action<Model> a3 = m::test2;
        a3.run(m);
    }

    static class Model {
        public void test1() {
            System.out.println("test1");
        }

        public void test2(Model a) {
            System.out.println("test2");
        }

        public int test3() {
            System.out.println("test3");
            return 1;
        }
    }

    @FunctionalInterface
    interface Action<T> {
        void run(T t);
    }

}
