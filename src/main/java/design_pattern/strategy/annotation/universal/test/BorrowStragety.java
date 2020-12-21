package design_pattern.strategy.annotation.universal.test;

import design_pattern.strategy.annotation.universal.Stragety;
import design_pattern.strategy.annotation.universal.UniversalStrategyAnnotation;

/**
 * Created by Niki on 2020/12/17 11:40
 */
@UniversalStrategyAnnotation(types = {"3","4"})
public class BorrowStragety implements AgentStrategy {
    @Override
    public String apply(String v) {
        return "借钱";
    }
}
