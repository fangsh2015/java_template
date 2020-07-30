package concurrent.future.completable_future;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Niki on 2019/6/13 17:39
 */

public class Shop {
    Random random = new Random();

    @Getter
    @Setter
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    /**
     * 模拟接口耗时
     */
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询商品价钱
     * @param product
     * @return
     */
    public double getPice(String product) {
        delay();
        return random.nextDouble() * 100;
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> getPice(product));
    }




}
