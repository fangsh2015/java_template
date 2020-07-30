package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by Niki on 2019/6/19 10:07
 */
public class ShoutClient {
    public static void main(String[] args) {
        ServiceLoader<Shout> serviceLoader = ServiceLoader.load(Shout.class);
        Iterator<Shout> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            iterator.next().shout();
        }
    }
}
