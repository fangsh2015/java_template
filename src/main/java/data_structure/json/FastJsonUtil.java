package data_structure.json;

import com.alibaba.fastjson.JSON;

/**
 * Created by Niki on 2019/6/10 9:59
 */
public class FastJsonUtil  {
    public static String bean2Json(Object object) {
        return JSON.toJSONString(object);
    }

    public static  <T> T json2Bean(String JsonStr, Class<T> objClass) {
        return JSON.parseObject(JsonStr, objClass);
    }
}
