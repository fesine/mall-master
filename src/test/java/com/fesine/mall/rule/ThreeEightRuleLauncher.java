package com.fesine.mall.rule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.Test;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/16
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/16
 */
public class ThreeEightRuleLauncher {

    @Test
    public void test() {
        /**
         * 创建规则执行引擎
         * 注意: skipOnFirstAppliedRule意思是，只要匹配到第一条规则就跳过后面规则匹配
         */
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine engine = new DefaultRulesEngine(parameters);
        Rules rules = new Rules();
        rules.register(new ThreeEightRuleUnitGroup(new EightRule(), new ThreeRule()));
        rules.register(new EightRule());
        rules.register(new ThreeRule());
        rules.register(new OtherRule());
        Facts facts = new Facts();
        for (int i = 1; i <= 50; i++) {
            //规则因素，对应的name，要和规则里面的@Fact 一致
            facts.put("number",i);
            //触发规则
            engine.fire(rules, facts);
            System.out.println();
        }
    }
}
