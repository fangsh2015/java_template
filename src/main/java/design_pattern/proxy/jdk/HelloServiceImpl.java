package design_pattern.proxy.jdk;

/**
 * Created by Niki on 2018/4/24 8:41
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public void say() {
        System.out.println("Hello World !");
    }
}
