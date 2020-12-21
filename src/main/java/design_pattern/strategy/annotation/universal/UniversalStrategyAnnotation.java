package design_pattern.strategy.annotation.universal;

import java.lang.annotation.*;

/**
 * 通用的等值范围策略
 * Created by Niki on 2020/12/17 11:11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniversalStrategyAnnotation {
    String[] types() default {};
}
