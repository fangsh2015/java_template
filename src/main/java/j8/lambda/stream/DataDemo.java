package j8.lambda.stream;

import j8.lambda.stream.list.AgentData;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Niki on 2018/10/23 9:52
 */
public class DataDemo {
    static List<AgentData> dataList = Arrays.asList(new AgentData("1", 7, 1f, new Date()), new AgentData("2", 17, 2f, new Date()),
            new AgentData("3", 21, 3f, new Date()), new AgentData("1", 5, 4f, new Date()),
            new AgentData("6", 60, 12f, new Date()));

    /**
     * 根据渠道类型，求出人数和，平均值，最大，最小
     */
    static void sumUserNumber(List<AgentData> dataList) {
        Map<String, Integer> sumUserNumber = dataList.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.summingInt(AgentData::getUserNumber)));

        Map<String, Double> avgUserNumber = dataList.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.averagingDouble(AgentData::getUserNumber)));

        Map<String, Optional<AgentData>> maxUserNumber = dataList.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.maxBy(Comparator.comparing(AgentData::getUserNumber))));

        Map<String, Optional<AgentData>> minUserNumber = dataList.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.minBy(Comparator.comparing(AgentData::getUserNumber))));

        System.out.println(sumUserNumber);
        System.out.println(avgUserNumber);
        System.out.println(maxUserNumber);
        System.out.println(minUserNumber);
    }

    /**
     * 比较两天的人数增长， 假设oldData中，已经是处理好的数据
     *
     * @param oldData
     * @param newData
     */
    static void compare(List<AgentData> oldData, List<AgentData> newData) {
        // 处理newData
        Map<String, Integer> sumUserNumber = dataList.stream().collect(Collectors.groupingBy(AgentData::getAgentType, Collectors.summingInt(AgentData::getUserNumber)));
        System.out.println(sumUserNumber);
        // 处理后的数据，没有日期数据
        List<AgentData> newData2 = sumUserNumber.entrySet().stream().map(entry -> new AgentData(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        System.out.println(newData2);

        oldData.addAll(newData2);
        Map<String, AgentData> compareResult = oldData.stream().collect(Collectors.toMap(AgentData::getAgentType, AgentData -> AgentData, (d1, d2) -> {
            AgentData compareData = new AgentData();
            compareData.setAgentType(d1.getAgentType());
            if (d1.getDate() == null) {
                compareData.setUserNumber(d1.getUserNumber() - d2.getUserNumber());
            }else {
                compareData.setUserNumber(d2.getUserNumber() - d1.getUserNumber());
            }
            return compareData;
        }));

        System.out.println(compareResult);
    }


    public static void main(String[] args) {
        List<AgentData> oldData = Arrays.asList(new AgentData("1", 10, 1f, new Date()),
                new AgentData("2", 11, 2f, new Date()),
                new AgentData("3", 12, 3f, new Date()),
                new AgentData("6", 26, 12f, new Date()));
        List<AgentData> o1 = new ArrayList<>();
        o1.addAll(oldData);

        List<AgentData> d1 = new ArrayList<>();
        d1.addAll(dataList);
        compare(o1, d1);

//        sumUserNumber(dataList);
    }

}
