package design_pattern.proxy.jdk;

/**
 * Created by Niki on 2018/4/24 8:45
 */
public class ProxyMain {
    public static void main(String[] args) {
        IHelloService hello = new HelloServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        IHelloService service = (IHelloService) handler.getProxy();
        service.say();
        /**
         * proxy before
         * Hello World !
         * proxy after
         */

    }
}
