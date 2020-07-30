package j8.lambda.stream.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 关于Map一些相关的Lambda操作
 * Created by Niki on 2018/10/25 16:19
 */
public class MapLambdaDemo {

    /**
     * 通过Map的value进行升序排序
     *
     * @param map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue_ASC(Map<K, V> map) {
        return map.entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).map(entry -> {
            Map<K, V> result = new LinkedHashMap<>();
            result.put(entry.getKey(), entry.getValue());
            return result;
        }).reduce((map1, map2) -> {
            map1.putAll(map2);
            return map1;
        }).get();
    }

    /**
     * 根据Map的value进行倒叙排序
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue_DESC(Map<K, V> map) {
        return map.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).map(entry -> {
            Map<K, V> temp = new LinkedHashMap<>();
            temp.put(entry.getKey(), entry.getValue());
            return temp;
        }).reduce((m1, m2) -> {
            m1.putAll(m2);
            return m1;
        }).get();
    }
}
