package test;

import java.lang.invoke.MethodType;

public class TestMethodType {
    public static void main(String[] args) {
        MethodType methodType1 = MethodType.methodType(String.class, String.class);
        MethodType m2 = MethodType.methodType(int.class, char[].class,  float.class);

        methodType1.appendParameterTypes(String.class);
        methodType1.insertParameterTypes(2, int.class);
//        methodType1.dropParameterTypes(1,int.class);

    }
}
