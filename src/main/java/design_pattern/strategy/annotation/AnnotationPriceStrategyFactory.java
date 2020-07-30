package design_pattern.strategy.annotation;

import design_pattern.strategy.Customer;
import design_pattern.strategy.PriceStrategy;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于注解的策略工厂
 * Created by Niki on 2018/4/2 9:29
 */
public class AnnotationPriceStrategyFactory {
    private ClassLoader classLoader = getClass().getClassLoader();



    //策略模式的包路径
    private static final String STRATEGY_PACKATE = "design_pattern.strategy.annotation";
    private static List<Class<PriceStrategy>> strategyList = new ArrayList<>();

    public  double pay(Customer customer, double price) {
        int integral = customer.getIntegral() ;
        PriceStrategy strategy = getPriceStrategy(integral);
        return strategy.price(price);
    }

    //初始化策略列表
    private void init() {
        File[] resources = getResource();
        Class<PriceStrategy> priceStrategyClass = null;
        try {
            priceStrategyClass = (Class<PriceStrategy>) getClass().getClassLoader().loadClass(PriceStrategy.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("未找到策略接口");
        }
        for (File file : resources){
            try {
                Class<?> clazz = classLoader.loadClass(STRATEGY_PACKATE + "." + file.getName().replace(".class",""));
                if(PriceStrategy.class.isAssignableFrom(clazz) && clazz != priceStrategyClass){
                    strategyList.add((Class<PriceStrategy>) clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private File[] getResource(){
        try {
            File file = new File(classLoader.getResource(STRATEGY_PACKATE.replace(".","/")).toURI());
            return file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".class");
                }
            }) ;
        } catch (URISyntaxException e) {
            throw new RuntimeException("未找到策略资源");
        }
    }

    private static PriceStrategy getPriceStrategy(int integral) {
        for (Class<PriceStrategy> clazz : strategyList) {

            IntegralRegion integralRegion = clazz.getAnnotation(IntegralRegion.class);
            if (integralRegion == null) {
                throw new RuntimeException("没有发现策略模式注解");
            }
            int min = integralRegion.min();
            int max = integralRegion.max();

            if (integral > min && integral < max) {
                try {
                    return clazz.newInstance();
                } catch (Exception e) {
                    continue;
                }
            }
        }
        throw new RuntimeException("没有找到对应的策略模式！");
    }
    /**
     * 单利模式
     */
    private AnnotationPriceStrategyFactory() {
        init();
    }

    private static class FactoryInstance{
        private static AnnotationPriceStrategyFactory instance = new AnnotationPriceStrategyFactory();
    }

    public static AnnotationPriceStrategyFactory getInstance(){
        return FactoryInstance.instance;
    }

}
