package concurrent.future.completable_future;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 并行获取各个商店同一商品价格
 * Created by Niki on 2019/6/13 17:47
 */
public class PriceDemo {

    // 循环查询
    public List<String> getPrices(String product, List<Shop> shops) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product)))
                .collect(Collectors.toList());
    }

    // 并行查询
    public List<String> getPricesParallel(String product, List<Shop> shops) {
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product)))
                .collect(Collectors.toList());
    }

    /**
     * 通过CompletableFuture 并行查询
     * @param product
     * @param shops
     * @return
     */
    public List<String> getPriceFuture(String product, List<Shop> shops) {
        List<CompletableFuture<String>> allFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product))))
                .collect(Collectors.toList());
        return allFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> getPriceFuture2(String product, List<Shop> shops) {
        Executor service = Executors.newCachedThreadPool();

        List<CompletableFuture<String>> allFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product)), service))
                .collect(Collectors.toList());
        return allFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

//    public List<String> getComplexPrice(){
//        CompletableFuture future = new CompletableFuture();
//    }


    public static void main(String[] args) {
        List<Shop> shops = Lists.newArrayList(new Shop("shop1"),
                new Shop("shop2"),
                new Shop("shop3"),
                new Shop("shop4"),
                new Shop("shop5"),
                new Shop("shop6"),
                new Shop("shop7"),
                new Shop("shop8")
        );
        PriceDemo priceDemo = new PriceDemo();
        long begin = System.currentTimeMillis();
        List<String> allPrice = priceDemo.getPrices("iphone8", shops);
        long end = System.currentTimeMillis();

        System.out.println(String.format("查询结果%s, 耗时%d", allPrice.toString(), end-begin));
        begin = end;
        allPrice = priceDemo.getPricesParallel("iphon7", shops);
        end = System.currentTimeMillis();
        System.out.println(String.format("并行查询结果%s, 耗时%d", allPrice.toString(), end-begin));

        begin = end;
        allPrice = priceDemo.getPriceFuture("iphon7", shops);
        end = System.currentTimeMillis();
        System.out.println(String.format("Future查询结果%s, 耗时%d", allPrice.toString(), end-begin));


        begin = end;
        allPrice = priceDemo.getPriceFuture2("iphon7", shops);
        end = System.currentTimeMillis();
        System.out.println(String.format("多线程Future查询结果%s, 耗时%d", allPrice.toString(), end-begin));
    }
}
