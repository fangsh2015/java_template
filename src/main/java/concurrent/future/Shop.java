package concurrent.future;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by Niki on 2019/2/16 16:39
 */
@Data
public class Shop {
    Random random = new Random();
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getPice(String product) {
        delay();
        return random.nextDouble() * 100;
    }

    public Quote getPrice2(String product) {
        delay();
        double price = random.nextDouble() * 100;
        Quote quote = new Quote();
        quote.setPrice(price);
        quote.setShop(this.name);
        quote.setProduct(product);
        return quote;
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> {
            return getPice(product);
        });
    }


}
