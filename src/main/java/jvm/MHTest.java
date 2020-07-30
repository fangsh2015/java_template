package jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MHTest {
    static MethodHandles.Lookup lookup = MethodHandles.lookup();

    public static void main(String[] args) {
        while (true){
            MethodType type = MethodType.methodType(double.class, double.class);
            try {
                MethodHandle mh = lookup.findStatic(Math.class, "log", type);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
