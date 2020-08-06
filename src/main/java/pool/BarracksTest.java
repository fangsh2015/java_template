package pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Optional;

/**
 * 军营测试类
 * Created by Niki on 2020/8/6 17:36
 */
public class BarracksTest {
    public static void main(String[] args) throws Exception {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 线程池中允许的最大连接数 军营能够容纳的最大士兵数
        poolConfig.setMaxTotal(5);
        // 线程池中允许最大空闲连接数 超过该数量的连接回被回收，士兵会退伍
        poolConfig.setMaxIdle(3);
        // 线程池中个允许的最小空闲数
        poolConfig.setMinIdle(1);
        //  从空闲队列中查询时是否阻塞，如果阻塞,则超时时间为maxWaitMillis, 如果不阻塞，则一直等待连接获取
        poolConfig.setBlockWhenExhausted(true);
        // 创建连接的超时时间，找一个士兵最大的等待时间
        poolConfig.setMaxWaitMillis(5000);
        // 	从pooledObjectFactory创建对象添加到objectPool时，是否进行有效性验证
        poolConfig.setTestOnCreate(true);
        // 	objectPool"借"出pooledObject时,是否进行有效性验证
        poolConfig.setTestOnBorrow(false);
        // pooledObject用完了"还"给objectPool时，是否进行有效性验证
        poolConfig.setTestOnReturn(true);
        // 返回evictor是否需要对处于idle状态下的pooledObject进行validate操作
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRunsMillis(1000 * 60 * 30);

//        poolConfig.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        //一定要关闭jmx，不然springboot启动会报已经注册了某个jmx的错误
        poolConfig.setJmxEnabled(false);

        Barracks barracks = Barracks.createBarracks("第三山地师", poolConfig);

        // 派兵执行任务
        final Solider solider = barracks.borrowObject();

        // 士兵结束任务回到军营
        barracks.returnObject(solider);
    }
}
