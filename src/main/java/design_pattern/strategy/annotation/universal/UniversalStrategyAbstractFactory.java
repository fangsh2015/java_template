package design_pattern.strategy.annotation.universal;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2020/12/17 11:15
 */
public abstract class UniversalStrategyAbstractFactory <T>  {
    ClassLoader classLoader = getClass().getClassLoader();

    protected UniversalStrategyAbstractFactory() {
        init();
    }

    public abstract String getStrategyPackage();


    protected static List<Class<Stragety>> strategyList = new ArrayList<>();

    public abstract Stragety getStrategy(T t);
//    {
//        for (Class<Stragety> clazz : strategyList) {
//
//            UniversalStrategyAnnotation strategyAnnotation = clazz.getAnnotation(UniversalStrategyAnnotation.class);
//            if (strategyAnnotation == null) {
//                continue;
////                throw new RuntimeException("没有发现策略模式注解");
//            }
//            String[] types = strategyAnnotation.types();
//            List<String> typeList = Lists.newArrayList(types);
//            if (typeList.contains(type)) {
//                try {
//                    return clazz.newInstance();
//                } catch (Exception e) {
//                    continue;
//                }
//            }
//        }
//        throw new RuntimeException("没有找到对应的策略模式！");
//    }

    //初始化策略列表
    protected void init() {
        File[] resources = getResource();
        Class<Stragety> priceStrategyClass = null;
        try {
            // 查找具体策略类
            priceStrategyClass = (Class<Stragety>) getClass().getClassLoader().loadClass(Stragety.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("未找到策略接口");
        }
        for (File file : resources){
            try {
                Class<?> clazz = classLoader.loadClass(getStrategyPackage() + "." + file.getName().replace(".class",""));
                if(Stragety.class.isAssignableFrom(clazz) && clazz != priceStrategyClass){
                    strategyList.add((Class<Stragety>) clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private File[] getResource(){
        try {
            File file = new File(classLoader.getResource(getStrategyPackage().replace(".","/")).toURI());
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
}
