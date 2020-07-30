package object_initialization_order;

public class B {
    public B() {
        System.out.println(((A) this).a);
    }
}
