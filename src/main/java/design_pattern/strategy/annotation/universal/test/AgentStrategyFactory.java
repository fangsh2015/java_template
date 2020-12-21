package design_pattern.strategy.annotation.universal.test;

import com.google.common.collect.Lists;
import design_pattern.strategy.annotation.universal.Stragety;
import design_pattern.strategy.annotation.universal.UniversalStrategyAbstractFactory;
import design_pattern.strategy.annotation.universal.UniversalStrategyAnnotation;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Niki on 2020/12/17 12:16
 */
public class  AgentStrategyFactory  extends UniversalStrategyAbstractFactory {

    @Override
    public String getStrategyPackage() {
        return "design_pattern.strategy.annotation.universal.test";
    }

    @Override
    public Stragety getStrategy(Object o) {
        String type = (String) o;
        for (Class<Stragety> clazz : strategyList) {

            LKFQStragetyAnnotation strategyAnnotation = clazz.getAnnotation(LKFQStragetyAnnotation.class);
            if (strategyAnnotation == null) {
                continue;
//                throw new RuntimeException("没有发现策略模式注解");
            }
            String[] types = strategyAnnotation.types();
            List<String> typeList = Lists.newArrayList(types);
            if (typeList.contains(type)) {
                try {
                    return clazz.newInstance();
                } catch (Exception e) {
                    continue;
                }
            }
        }
        throw new RuntimeException("没有找到对应的策略模式！");
    }


//    @Override
//    public String apply(String type) {
//        Stragety strategy = getStrategy(type);
//        String res = (String) strategy.apply(type);
//        System.out.println(res);
//        return res;
//    }

    public static void main(String[] args) {
        AgentStrategyFactory factory = new AgentStrategyFactory();
        factory.init();
        final Stragety strategy = factory.getStrategy("1");
        System.out.println(strategy.apply("1"));
    }
}
