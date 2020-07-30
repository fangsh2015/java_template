package reactor;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Flux使用demo
 * Created by Niki on 2020/6/10 16:26
 */
public class FluxDemo {

    private static void createFlux() {
        // 通过jsut指定序列中包含的全部元素创建Flux对象，Flux在发布这些元素后会自动结束
        Flux.just("Hello", "world", "!").subscribe(System.out::println);

        // fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9}).subscribe(e -> System.out.println(e));

        Flux.fromStream(Lists.newArrayList(1, 2, 3, 4, 5).stream()).doOnComplete(() -> System.out.println("完成")).subscribe(System.out::println);

        // 创建一个不包含任何元素，只发布结束消息的序列。
        // 没有元素，订阅没有消息
        Flux.empty().doOnComplete(() -> System.out.println("创建没有任何元素的flux完成")).subscribe();

        // 创建一个只包含错误消息的序列。因为创建过程中出现了异常， flux没有完成的消息，也没有任何元素
        Flux.error(new RuntimeException("flux创建异常")).doOnError(throwable -> System.out.println(throwable.getMessage())).
                doOnComplete(() -> System.out.println("创建一个只包含错误消息的flux完成")).
                subscribe(System.out::println);

        // 创建一个不包含任何消息通知的序列。
        Flux.never().doOnComplete(() -> System.out.println("创建不包含任何消息通知flux完成")).subscribe(System.out::println);

        // 创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
        Flux.range(1, 10).doOnComplete(() -> System.out.println("创建区间flux完成")).subscribe(System.out::println);

        // 创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
    }

    private static void buffer() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);

        //收集所有的元素为数组，达到要求后停止收集，开始下一次收集
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);

        // 达到要求后收集元素为数组，不满足不收集
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void filter() {
        // 对元素进行过滤，与bufferWhile的区别是，该方法不收集元素
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void window() {
        Flux.range(1, 100).window(20).subscribe(System.out::println);

        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).window(Duration.of(1001, ChronoUnit.MILLIS)).
                take(2).toStream().forEach(System.out::println);

    }

    public static void main(String[] args) {
//        createFlux();

//        buffer();

//        filter();

//        window();

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .switchOnError(Mono.just(0))
                .subscribe(System.out::println);

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }

}
