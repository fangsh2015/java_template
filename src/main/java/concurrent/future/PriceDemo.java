package concurrent.future;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 *
 * Created by Niki on 2019/2/16 16:42
 */
public class PriceDemo {
    private List<Shop> shops = Arrays.asList(new Shop("shop1"),
            new Shop("shop2"),
            new Shop("shop3"),
            new Shop("shop4"),
            new Shop("shop5"),
            new Shop("shop6"),
            new Shop("shop7"),
            new Shop("shop8")
    );

    /**
     * 在不同的商店查找商品的价格 - 并行流的方式
     * @param product 商品
     * @return
     */
    public List<String> findPricesByParallel(String product) {
        return shops.stream().map(shop -> {
            return String.format("商品：%s在%s商店的价格为：%d", product, shop.getName(), shop.getPice(product));
        }).collect(Collectors.toList());
    }

    public List<String> findPrice(String product) {
        List<CompletableFuture<String>> futures = shops.stream().map(shop ->{
            return CompletableFuture.supplyAsync(()->{return String.format("商品：%s在%s商店的价格为：%d", product, shop.getName(), shop.getPice(product));});
        }).collect(Collectors.toList());

//        return futures.stream().map(future -> {return future.join();}).collect(Collectors.toList());
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 查找商品经过打折，和费率换算后的价格
     * 采用CompletableFuture的方式
     * @param product
     */
    public void findPrice3(String product, DiscountDemo.Discount discount) {
       CompletableFuture<String>[]  futures = shops.stream().map(shop -> {
            return
                    CompletableFuture.supplyAsync(() -> {
                        return shop.getPice(product);
                    }) // 获取价格
                            .thenCombine(CompletableFuture.supplyAsync(() -> { // 获取价格输出对象
                                return ExchangeDemo.getRate("USA", "CNY");
                            }), (p, r) -> {
                                return new Quote(p, r, discount, shop.getName(), product);
                            });
        }).map(future -> future.thenApply(quote -> {
            return quote.parse();
        })).toArray(size -> new CompletableFuture[size]);

        List<String> res = Arrays.asList(futures).stream().map(CompletableFuture::join).collect(Collectors.toList());
        CompletableFuture.anyOf(futures).thenAcceptAsync((obj) -> System.out.println(obj));
    }

    public void findPrice4(String product, DiscountDemo.Discount discount) {
        Executor executor = Executors.newCachedThreadPool();
        CompletableFuture<Void>[] priceFuture = shops.stream().map(
                // 查询商品价格
                shop -> CompletableFuture.supplyAsync(() -> shop.getPrice2(product), executor)
                        // 查询商品费率
                        .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeDemo.getRate("USD", "CNY"), executor), (quote, rate) -> new Quote(quote.getPrice(), rate, discount, quote.getShop(), product))
        )
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> DiscountDemo.applyDiscount(quote))))
                .map(future -> future.thenAccept(content -> System.out.println(content + "done")))
                .toArray(size -> new CompletableFuture[size]);

        // 全部future完成
        CompletableFuture.allOf(priceFuture).thenAccept((obj) -> {System.out.println("all done");((ExecutorService) executor).shutdown();});

        CompletableFuture.anyOf(priceFuture).thenAccept((obj) -> {System.out.println("finish anyOf done" + obj);});
    }

    public static void main(String[] args) {
        new PriceDemo().findPrice4("mac pro", DiscountDemo.Discount.GOLD);
    }
}
