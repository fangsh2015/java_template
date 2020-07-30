package design_pattern.strategy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 策略模式的区间注解，给所有的策略实现类上加上这个注解，利用反射来确定具体的策略模式。对修改关闭；对扩展开放
 * Created by Niki on 2018/4/2 9:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegralRegion {
    int max() default Integer.MAX_VALUE;

    int min() default 0;

}
