package data_structure.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by Niki on 2019/6/10 10:03
 */
public class GsonUtil  {
    private static Gson gson = new GsonBuilder().create();

    public static String bean2Json(Object object) {
        return gson.toJson(object);
    }

    public static <T> T json2Bean(String JsonStr, Class<T> objClass) {
        return gson.fromJson(JsonStr, objClass);
    }

    public static String jsonFormatter(String uglyJsonStr) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        return gson.toJson(je);
    }
}
