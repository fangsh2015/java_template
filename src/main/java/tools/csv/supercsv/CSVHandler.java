package tools.csv.supercsv;

/**
 * csv处理器
 * Created by Niki on 2020/1/5 14:43
 */
public interface CSVHandler {
    /**
     * csv每行记录处理器， 没有返回值
     *
     * @param param 入参
     * @param <T> 入参类型
     */
    <T> void handler(T param);

    /**
     * csv每行记录处理器， 有返回值
     * @param param 入参
     * @param <T> 入参类型
     * @param <R> 返回类型
     * @return 返回值
     */
    <T, R> R deal(T param);

}
