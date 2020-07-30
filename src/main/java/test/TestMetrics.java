package test;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试通过日志输出metrics的值
 * 应用监控：https://www.jianshu.com/p/effe8e259d25
 * Created by Niki on 2018/8/28 18:03
 */
@Slf4j
public class TestMetrics {
    private static final MetricRegistry metrics = new MetricRegistry();

    private static Counter counter = metrics.counter(MetricRegistry.name(TestMetrics.class, "request"));

    private static Slf4jReporter reporter = Slf4jReporter.forRegistry(metrics).outputTo(log)
            .build();

    private static void response() {
        counter.inc();
    }

    public static void main(String[] args) throws InterruptedException {
        reporter.start(2, TimeUnit.SECONDS);

        int i = 0;
        while (i < 200) {
            i++;
            response();
            Thread.sleep(800);
        }
    }

}
