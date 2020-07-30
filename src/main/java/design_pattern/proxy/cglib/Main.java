package design_pattern.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by Niki on 2018/6/15 9:04
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("cglib代理实现！");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloServiceImpl service = (HelloServiceImpl) enhancer.create();
        service.say();
    }
}
