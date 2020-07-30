package concurrent.thread.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Niki on 2018/5/17 15:28
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {


    }

    private void executorDemo() {
        Executor executor = Executors.newFixedThreadPool(1);
    }

    private void genericObjectPoolDemo() {
        JedisFactory factory = new JedisFactory();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(10);
        config.setMaxTotal(10);
        config.setMaxWaitMillis(30000);

        GenericObjectPool<Jedis> jedisPool = new GenericObjectPool<Jedis>(factory, config);

        Jedis jedis = null;

        try {
            jedis = jedisPool.borrowObject();
            System.out.println(jedisPool.getNumActive());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedisPool.returnObject(jedis);
            System.out.println(jedisPool.getNumActive());
        }
    }

    static class JedisFactory extends BasePooledObjectFactory<Jedis> {

        @Override
        public PooledObject<Jedis> makeObject() throws Exception {
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.connect();
            return new DefaultPooledObject<>(jedis);
        }

        @Override
        public boolean validateObject(PooledObject<Jedis> p) {
            Jedis jedis = p.getObject();
            if (jedis.isConnected()) {
                return true;
            }
            return false;
        }

        @Override
        public Jedis create() throws Exception {
            return null;
        }

        @Override
        public PooledObject<Jedis> wrap(Jedis obj) {
            return null;
        }

        @Override
        public void destroyObject(PooledObject<Jedis> p) throws Exception {
            p.getObject().close();
        }
    }
}
