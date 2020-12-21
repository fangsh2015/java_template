package tools.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Created by Niki on 2020/10/21 18:06
 */
public class CaffeineCacheUtil {
    public static void main(String[] args) {
        @NonNull final Cache<String, String> build = Caffeine.newBuilder().maximumSize(100).build();
        build.put("1", "test1");
        build.get("2", key -> {
            return "test" + key;
        });

        @Nullable final String s = build.getIfPresent("2");
        System.out.println(s);
    }
    public void test() {

    }
}
