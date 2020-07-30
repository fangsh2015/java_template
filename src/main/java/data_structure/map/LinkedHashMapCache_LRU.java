package data_structure.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 通过LinkedHashMap实现LRU 算法
 * 通过重写方法removeEldestEntry实现
 * Created by Niki on 2018/5/16 9:31
 */
public class LinkedHashMapCache_LRU<K, V> {
    /* 默认缓存容量的大小 */
    private static final int DEFAULT_MAX_SIZE = 50;

    /* 最大缓存数设定后无法更改 */
    private int max_size = 0;

    private LinkedHashMap<K, V> cache;

    public LinkedHashMapCache_LRU(int max_size) {
        if (max_size == 0 || max_size < 0) {
            throw new IllegalArgumentException("max_size mast greater than 0");
        }
        this.max_size = max_size;
        cache = cacheMap(max_size);
    }

    public LinkedHashMapCache_LRU() {
        max_size = DEFAULT_MAX_SIZE;
        cache = cacheMap(DEFAULT_MAX_SIZE);
    }

    private LinkedHashMap<K, V> cacheMap(final int size) {
        return new LinkedHashMap<K, V>(max_size) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                int size = this.size();
                if (size > max_size) {
                    return true;
                }
                return false;
            }
        };
    }

    public int getMax_size() {
        return max_size;
    }

    public V put(K k, V v) {
        return cache.put(k, v);
    }

    public V get(K k) {
        return cache.get(k);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return cache.entrySet();
    }

    public static void main(String[] args) {
        LinkedHashMapCache_LRU<String, String> cache = new LinkedHashMapCache_LRU(5);
        for (int i = 1; i <= 5; i++) {
            cache.put("" + i, "" + i);
        }
        for (Map.Entry entry : cache.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        System.out.println("********************************");
        cache.put("6", "6");

        for (Map.Entry entry : cache.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }

}
