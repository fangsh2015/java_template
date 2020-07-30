package concurrent.future;


import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Niki on 2018/12/25 19:57
 */
class CompletableFutureDemoTest {
    @Test
    public void test() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        System.out.println("sync");
        Assert.assertTrue("Result was empty", result.length() > 0);
    }

}