package data_structure.json;

/**
 * Created by Niki on 2019/6/10 10:00
 */
public interface JsonUtil {
    String bean2Json(Object object);

    <T> T json2Bean(String JsonStr, Class<T> objClass);

}
