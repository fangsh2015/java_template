package j8.lambda.stream;


import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class OptionalTest {
    public static void main(String[] args) {
        Optional optional = Optional.empty() ;

    }

    /**
     * 通过Optional来封装返回的值，避免key不存在时的空指针
     * @param key
     * @return
     */
    public Optional<String> getMapValue(String key){
        Map<String,String> test  = Maps.newHashMap() ;

        Optional<String> res = Optional.ofNullable(test.get(key)) ;
        return res ;
    }

    public void test( ){
        Properties properties = new Properties() ;
        properties.setProperty("a", String.valueOf(5)) ;
        properties.setProperty("b","true") ;
        properties.setProperty("c","-3") ;

        String res = Optional.ofNullable(properties.getProperty("a")).filter(pro -> Integer.valueOf(pro) > 5).get()  ;
    }

    public static Optional<Integer> stringToInt(String  value){
        try {
            return Optional.of(Integer.valueOf(value)) ;
        }catch(NumberFormatException e){
            return Optional.empty() ;
        }
    }


    public int get(String key, Properties properties){
        int res = Optional.ofNullable(properties.getProperty(key)).flatMap(OptionalTest::stringToInt).filter(i -> i > 0).orElse(0) ;
        res = Optional.ofNullable(properties.getProperty(key)).flatMap(x -> Optional.ofNullable(Integer.valueOf(x))).filter(i -> i>0).orElse(0) ;
        return res ;
    }

}
