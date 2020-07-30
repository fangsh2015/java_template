package j8.lambda.stream.map_reduce;

import j8.lambda.stream.list.AgentData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 利用MapReduce并行处理Demo，
 * 同样分为两种类型，一种基础数据和对象
 * Created by Niki on 2018/10/23 9:24
 */
public class MapReduceDemo {
    static List<Integer> intList = Arrays.asList(1, 9, 8, 4, 2);

    static List<AgentData> dataList = Arrays.asList(new AgentData("1", 10, 1f), new AgentData("2", 11, 2f),
            new AgentData("3", 12, 3f), new AgentData("1", 9, 4f),
            new AgentData("6", 26, 12f));

    /**
     * 基础List求和
     */
    static void sum() {
        int sum = intList.stream().map(Integer::intValue).reduce(0, (i1, i2) -> {
            return i1 + i2;
        });
        System.out.println("并行计算List的和：" + sum);

        sum = intList.stream().map(Integer::intValue).filter(value -> value > 10).reduce(100, (i1, i2) -> {
            return i1 + i2;
        });

        System.out.println("并行计算List中大于10的参数的和，并且从100开始自加：" + sum);
    }

    /**
     * 处理人数大于10人,小于20人的最多人数。不关系渠道类型
     *
     * @param datas
     */
    static void sum_obj(List<AgentData> datas) {
        Optional<Integer> maxNumOptional = datas.stream().map(AgentData::getUserNumber)
                .filter(userNumber -> userNumber > 10)
                .filter(userNumber -> userNumber < 20)
                .reduce((n1, n2) -> {
                    return n1 > n2 ? n1 : n2;
                });

        if (maxNumOptional.isPresent()) {
            int maxNum = maxNumOptional.get();
            System.out.println("人数大于10人，小于20人的最大值：" + maxNum);
        }
    }

    /**
     * 处理人数大于10人,金额最多。不关系渠道类型
     *
     * @param datas
     */
    static void sum_obj2(List<AgentData> datas) {
        // 没有简化的方式
        Optional<AgentData> dataOptional = datas.stream().map(data -> data)
                .filter(data -> data.getUserNumber() > 10)
                .reduce((d1, d2) -> {
                    return d1.getAmount() > d2.getAmount() ? d1 : d2;
                });

        if (dataOptional.isPresent()) {
            float maxAmount = dataOptional.get().getAmount();
            System.out.println("人数大于10人，最大金额的数据为：" + dataOptional.get() + ", 最大金额为：" + maxAmount);
        }

        // 简化版
        dataOptional = datas.stream()
                .filter(data -> data.getUserNumber() > 10)
                .reduce((d1, d2) -> d1.getAmount() > d2.getAmount() ? d1 : d2);


        if (dataOptional.isPresent()) {
            float maxAmount = dataOptional.get().getAmount();
            System.out.println("人数大于10人，最大金额的数据为：" + dataOptional.get() + ", 最大金额为：" + maxAmount);
        }
    }


    public static void main(String[] args) {
        sum_obj2(dataList);
    }
}
