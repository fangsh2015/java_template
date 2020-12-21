package design_pattern.strategy.annotation.universal.test;

import design_pattern.strategy.annotation.universal.UniversalStrategyAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Niki on 2020/12/17 11:41
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LKFQStragetyAnnotation {
    String[] types();
}
