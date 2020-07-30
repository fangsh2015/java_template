package j8.lambda.stream.list;

import java.util.*;
import java.util.stream.Collectors;

/**
 * List相关的Lambda实例
 * Created by Niki on 2018/10/22 17:44
 */
public class ListLambdaDemo {

// ----------------- 基本数据对象 ---------------------------------

    static List<Integer> intList = Arrays.asList(1, 9, 8, 4, 2);

    static List<AgentData> dataList = Arrays.asList(new AgentData("1", 10, 1f, null), new AgentData("2", 11, 2f, null),
            new AgentData("3", 12, 3f, null), new AgentData("1", 9, 4f, null));

    /**
     * 对基本对象列表的求和
     *
     * @return
     */
    static void sum() {

        int sum = intList.stream().mapToInt(Integer::intValue).sum();

        System.out.println("基本对象求和：" + sum);

        // 并行的方式
        sum = intList.stream().map(Integer::intValue).reduce((i1, i2) -> {
            return i1 + i2;
        }).get();

        System.out.println("基本对象列表求和：" + sum);
    }

    /**
     * 基本对象列表求平均值
     */
    static void avg() {
        double avg = intList.stream().mapToDouble(Integer::intValue).average().getAsDouble();
        System.out.println("基本对象列表求平均值" + avg);
    }

    /**
     * 基本对象列表求最大值
     */
    static void max() {
        int max = intList.stream().mapToInt(Integer::intValue).max().getAsInt();
        max = intList.stream().max((i1, i2) -> i1.compareTo(i2)).get();
        System.out.println("基本对象列表求最大值" + max);

        int min = intList.stream().mapToInt(Integer::intValue).min().getAsInt();
        min = intList.stream().min((i1, i2) -> i2.compareTo(i1)).get();

        System.out.println("基本对象列表求最大值" + min);
    }

    /**
     * 使用Lambda的统Statistics对象，利用一个对象，求出List的和，平均值，最大值，最小值
     */
    static void statistics() {
        DoubleSummaryStatistics statistics = intList.stream().mapToDouble(Integer::intValue).summaryStatistics();
        double sum = statistics.getSum();
        double avg = statistics.getAverage();
        double max = statistics.getMax();
        double min = statistics.getMin();

        System.out.println(String.format("基本对象列表中，和：%d,平均值：%d，最大值：%d，最小值：%d", sum, avg, max, min));
    }

    /**
     * 基本对象列表排序
     */
    static void sort() {
        // 根据i1 和 i2 的顺序进行升序，降序
        List<Integer> sortAsc = intList.stream().sorted((i1, i2) -> i1.compareTo(i2)).collect(Collectors.toList());
        System.out.println("基本对象列表排序后的升序列表" + sortAsc);

        List<Integer> sortDesc = intList.stream().sorted((i1, i2) -> i2.compareTo(i1)).collect(Collectors.toList());
        System.out.println("基本对象列表排序后的降序列表" + sortDesc);
    }

// ----------------- 对象 ---------------------------------
    /*
    背景：渠道数据对象AgentData,记录了每个渠道的用户数，以及消费金额
     */


    /**
     * 对List对象中的某个参数值求和，平均值，最大，最小
     *
     * @param datas
     */
    static void analysis_obj(List<AgentData> datas) {
        int sum = datas.stream().mapToInt(AgentData::getUserNumber).sum();

        double avg = datas.stream().mapToDouble(AgentData::getUserNumber).average().getAsDouble();

        int max = datas.stream().mapToInt(AgentData::getUserNumber).max().getAsInt();

        int min = datas.stream().mapToInt(AgentData::getUserNumber).min().getAsInt();
    }

    /**
     * List中对象进行分组处理
     *
     * @param datas
     */
    static void analysis_group(List<AgentData> datas) {
        // 列表中的数据进行分组
        Map<String, List<AgentData>> groupRes = datas.stream().collect(Collectors.groupingBy(AgentData::getAgentType));

        // 对每组的数据进行分析，求和，平均值，最大值，最小值
        Map<String, Double> sumAmount = datas.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.summingDouble(AgentData::getAmount)));
        Map<String, Double> avgAmount = datas.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.averagingDouble(AgentData::getAmount)));
        Map<String, Optional<AgentData>> maxAmount = datas.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.maxBy(Comparator.comparing(AgentData::getAmount))));
        Map<String, Optional<AgentData>> minAmount = datas.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.minBy(Comparator.comparing(AgentData::getAmount))));

        System.out.println("渠道金额和" + sumAmount);
        System.out.println("渠道金额平均值" + avgAmount);
        System.out.println("渠道金额最大值" + maxAmount);
        System.out.println("渠道金额最小值" + minAmount);
    }

    /**
     * 列表对象转换为Map
     *
     * @param datas
     */
    static void list_to_map(List<AgentData> datas) {
        // 可以将List转换为Map，K-V都在List的对象中寻找。

        /*
        调用了toMap方法将List转换为Map，四个参数：第一个参数表示选取对象中那个参数作为Map的Key
        第二个参数表示选取对象中那个参数作为Value
        第三个参数为一个方法，当Key重复时，如何处理Value。我们这里进行添加
        第四个参数为生成的Map类型。
         */
        // agentType对应的金额总数的Map
        datas.stream().collect(Collectors.toMap(AgentData::getAgentType, AgentData::getAmount, (oldAmount, newAmount) -> {
            return oldAmount + newAmount;
        }, HashMap::new));

        // agentType对应的人员总数Map
        datas.stream().collect(Collectors.toMap(AgentData::getAgentType, AgentData::getUserNumber, (oldNum, newNum) -> {
            return oldNum + newNum;
        }, LinkedHashMap::new));
    }


    public static void main(String[] args) {
        analysis_group(dataList);
    }
}
