package design_pattern.strategy.annotation.universal.test;

import design_pattern.strategy.annotation.universal.Stragety;

/**
 * Created by Niki on 2020/12/17 11:39
 */
@LKFQStragetyAnnotation(types = {"1","2"})
public class LKFQStragety implements AgentStrategy {
    @Override
    public String apply(String v) {
        return "乐卡分期";
    }
}
