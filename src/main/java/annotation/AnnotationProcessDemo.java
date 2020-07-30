package annotation;

import com.totoro.annotation.compile.MyAnnotation;

/**
 * Created by Niki on 2018/9/29 11:06
 */
@MyAnnotation("编译期注解")
public class AnnotationProcessDemo {
    public static void main(String[] args) {
        System.out.println("test");
//        System.out.println(new HelloWorld().word);
    }


}
