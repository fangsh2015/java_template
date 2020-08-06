package pool.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sftp连接池帮助类
 * Created by Niki on 2020/7/23 11:44
 */
@Slf4j
public class ChannelSftpPoolHelper {
    /**
     * sftp连接池集合（不同商户不同的sftp连接池）
     */
    private static Map<String, ChannelSftpPool> customerSFTPPools = new ConcurrentHashMap<>();

    /**
     * 连接池中允许最大的链接数， 超过该链接数则不再继续创建
     */
    private static final Integer DEFAULT_MAX_TOTAL = 2;

    /**
     * 最大空闲连接数
     */
    private static final Integer DEFAULT_MAX_IDLE = 2;

    /**
     * 最小空闲连接数
     */
    private static final Integer DEFAULT_MIN_IDLE = 1;

    /**
     * pooledObject保持了idle状态的时长超过了该值，会被evict
     */
    private static final Long IDLE_EVICT_TIME = 20 * 1000L;

    /**
     * objectPool"借"出pooledObject时,当active数量为max时,等待的时长单位毫秒
     */
    private static final Integer MAX_WAIT_TIME = 3 * 1000;


//    /**
//     * 连接成为空闲连接后，销毁的时间
//     */
//    private static final Integer DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1000 * 10;

    /**
     * 添加连接池到连接池集合
     *
     * @param userName
     * @param sftpPool
     */
    public static void addPool(String userName, ChannelSftpPool sftpPool) {
        customerSFTPPools.put(userName, sftpPool);
    }

    /**
     * 获取商户对应的连接池
     *
     * @param userName
     * @return
     */
    public static ChannelSftpPool getPool(String userName) {
        return customerSFTPPools.get(userName);
    }


    /**
     * 获取userName对应的连接池，如果没有则新建一个连接池
     *
     * @param properties
     * @return
     */
    public static ChannelSftpPool getAndCreatePool(SFTPProperties properties) {
        String userName = properties.getUsername();
        ChannelSftpPool pool = getPool(userName);
        // 当前环境不包括该连接池
        if (pool == null) {
            pool = createPool(properties);
            if (pool != null) {
                customerSFTPPools.put(userName, pool);
            }
        }
        return pool;
    }

    /**
     * 从连接池中获取一个sftp连接对象
     *
     * @param pool
     * @return
     */
    public static ChannelSftp borrowObject(ChannelSftpPool pool) {
        try {
            if (pool != null) {
                ChannelSftp channelSftp = pool.borrowObject();
                if (channelSftp.isClosed()) {
                    // 如果连接已经关闭，则在连接池中销毁该连接
                    pool.invalidateObject(channelSftp);
                    // 重新创建一个连接（避免再次从池子里拿到的连接还是关闭了的）
                    channelSftp = pool.borrowObject();
                }
                return channelSftp;
            }
        } catch (Exception e) {
            log.error("从连接池获取={}sftp连接异常", pool.getName(), e);
        }
        return null;
    }

    /**
     * 从连接池中获取一个sftp连接对象
     *
     * @param userName 商户sftp名称
     * @return sftp连接对象
     */
    public static ChannelSftp borrowObject(String userName) {
        ChannelSftpPool pool = getPool(userName);
        return borrowObject(pool);
    }

    /**
     * 从连接池中获取sftp连接对象
     *
     * @param properties
     * @return
     */
    public static ChannelSftp borrowObject(SFTPProperties properties) {
        final ChannelSftpPool pool = getAndCreatePool(properties);
        return borrowObject(pool);
    }

    /**
     * 归还一个sftp连接对象
     *
     * @param channelSftp sftp连接对象
     */
    public static void returnObject(String userName, ChannelSftp channelSftp) {
        if (channelSftp != null) {
            final ChannelSftpPool pool = getPool(userName);
            pool.returnObject(channelSftp);
        }
    }

    /**
     * 创建sftp连接池
     *
     * @param properties
     * @return
     */
    public static ChannelSftpPool createPool(SFTPProperties properties) {
        ChannelSftpFactory factory = new ChannelSftpFactory(properties);
        return createPool(properties, factory);
    }

    /**
     * 创建sftp连接池对象
     *
     * @param sftpProperties
     * @param channelSftpFactory
     * @return
     */
    public static ChannelSftpPool createPool(SFTPProperties sftpProperties, ChannelSftpFactory channelSftpFactory) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        final Integer maxIdle = Optional.ofNullable(sftpProperties.getMaxIdle()).orElse(DEFAULT_MAX_IDLE);
        poolConfig.setMaxIdle(maxIdle);
        final Integer minIdle = Optional.ofNullable(sftpProperties.getMinIdle()).orElse(DEFAULT_MIN_IDLE);
        poolConfig.setMinIdle(minIdle);
        final Integer maxTotal = Optional.ofNullable(sftpProperties.getMaxTotal()).orElse(DEFAULT_MAX_TOTAL);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setBlockWhenExhausted(true);
        // 	从pooledObjectFactory创建对象添加到objectPool时，是否进行有效性验证
        poolConfig.setTestOnCreate(true);
        // 	objectPool"借"出pooledObject时,是否进行有效性验证
        poolConfig.setTestOnBorrow(false);
        // pooledObject用完了"还"给objectPool时，是否进行有效性验证
        poolConfig.setTestOnReturn(true);
        // 返回evictor是否需要对处于idle状态下的pooledObject进行validate操作
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRunsMillis(1000 * 60 * 30);
        poolConfig.setMaxWaitMillis(MAX_WAIT_TIME);
//        poolConfig.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        //一定要关闭jmx，不然springboot启动会报已经注册了某个jmx的错误
        poolConfig.setJmxEnabled(false);
        ChannelSftpPool pool = new ChannelSftpPool(channelSftpFactory, poolConfig);
        pool.setName(sftpProperties.getUsername());
        addPool(sftpProperties.getUsername(), pool);
        return pool;
    }

    /**
     * 获取当前商户
     *
     * @return
     */
    public static Map<String, ChannelSftpPool> getPools() {
        return customerSFTPPools;
    }


}
