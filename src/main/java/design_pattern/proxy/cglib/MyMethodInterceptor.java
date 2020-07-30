package design_pattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Niki on 2018/6/15 9:01
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before:" + method);
        Object obj = methodProxy.invokeSuper(object, args);
        System.out.println("After:" + method);
        return obj ;
    }
}
