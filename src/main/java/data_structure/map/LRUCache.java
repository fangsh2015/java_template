package data_structure.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 继承LinkedHashMap实现LRU Cache。重写方法removeEldestEntry方法
 * Created by Niki on 2018/5/16 11:10
 */
public class LRUCache<K, V> extends LinkedHashMap<K,V> {
    /* 默认缓存容量的大小 */
    private static final int DEFAULT_MAX_SIZE = 50;

    /* 最大缓存数设定后无法更改 */
    private int max_size = 0;

    public LRUCache(int initialCapacity, float loadFactor, int max_size) {
        super(initialCapacity, loadFactor);
        this.max_size = max_size;
    }

    public LRUCache(int initialCapacity, int max_size) {
        super(initialCapacity);
        this.max_size = max_size;
    }

    public LRUCache(int max_size) {
        this.max_size = max_size;
    }

    public LRUCache(Map<? extends K, ? extends V> m, int max_size) {
        super(m);
        this.max_size = max_size;
    }

    public LRUCache(int initialCapacity, float loadFactor, boolean accessOrder, int max_size) {
        super(initialCapacity, loadFactor, accessOrder);
        this.max_size = max_size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        int size = super.size();
        return size > max_size;
    }

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache(5);
        for (int i = 1; i <= 5; i++) {
            cache.put("" + i, "" + i);
        }
        for (Map.Entry<String,String> entry : cache.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        System.out.println("********************************");
        cache.put("6", "6");

        for (Map.Entry<String,String> entry : cache.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }
}
