package j8.lambda.use;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Niki on 2018/12/11 18:24
 */
public class Test {

    public static void main(String[] args) {
        Map<String, Map<String, String>> test = new HashMap<>();

        final String key = "";
        Stream.of(test.get(key)).filter(e -> e != null).map(e1-> (List<Map<String,Object>>) e1).findAny();
    }
}
