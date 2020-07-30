package test;

/**
 * 对象逃逸
 * Created by Niki on 2018/5/10 14:04
 */
public class ObjectEscape {
    private Object a ;

    private void test1(){
        //局部变量逃逸
        a = new Object();
    }

    public Object test2(){
        Object obj = new Object();
        //对象返回逃逸
        return obj ;
    }

    public void test3(){
        //实例对象引用发生逃逸
        Object ojb = test2();
    }
}
