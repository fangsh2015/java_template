package guava;



import com.google.common.collect.Lists;

import java.util.concurrent.TimeUnit;

/**
 * guava 缓存的demo
 */
public class CacheDemo {

//    LoadingCache<String, String> oidToPersonIdCache = CacheBuilder.newBuilder()
//            .weakKeys() //缓存的值允许GC回收，并且回收的策略和弱引用类似
//            .weakValues()
//            .maximumSize(8000) //最大的缓存数量
//            .expireAfterWrite(10, TimeUnit.HOURS) //没有任何写操作时缓存过期时间
//            .expireAfterWrite(8, TimeUnit.HOURS) //没有任何读/写操作，缓存内容过期时间
//            .build(new CacheLoader<String, String>() {
//                @Override
//                public String load(String s) throws Exception {
//                    return null;
//                }
//            });
            //.removalListener()



}
