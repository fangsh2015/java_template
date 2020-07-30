package design_pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Niki on 2018/4/24 8:42
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target ;
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /* 目标对象 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy before");
        Object ret = method.invoke(target, args);
        System.out.println("proxy after");
        return ret;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}
