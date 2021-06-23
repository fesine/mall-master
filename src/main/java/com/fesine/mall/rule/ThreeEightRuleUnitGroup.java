package com.fesine.mall.rule;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.support.composite.UnitRuleGroup;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/16
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/16
 */
@Rule(name = "被3和8同时整除", description = "这是一个组合规则")
public class ThreeEightRuleUnitGroup extends UnitRuleGroup {

    public ThreeEightRuleUnitGroup(Object... rules){
        for (Object rule : rules) {
            addRule(rule);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }


}
