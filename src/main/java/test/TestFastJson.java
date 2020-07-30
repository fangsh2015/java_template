package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Niki on 2019/3/11 10:02
 */
public class TestFastJson {
    public static void main(String[] args) {
        SerializeConfig config = SerializeConfig.getGlobalInstance();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        JSONObject object1 = new JSONObject();
        object1.put("stageId", 12D);
        object1.put("stageType", 14d);
        object1.put("profiteName", "优惠文案");
        object1.put("rationsDes", "test");
        A a = new A("fsh", "胡同",22);
        String tmp = JSON.toJSONString(a, config);
        System.out.println(tmp);
        Map<String, Object> map = JSON.parseObject(tmp, Map.class);
        System.out.println(map);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class A{
        private String studentName;
        private String studentAddress;
        private int studentAge;
    }
}
