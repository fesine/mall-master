package com.fesine.mall.rule;

import org.jeasy.rules.annotation.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/16
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/16
 */
@Rule(name = "被8整除" ,description = "number如果被8整除，打印：number is eight")
public class EightRule {

    /**
     * Condition:条件判断注解：如果return true， 执行Action
     * @param number
     * @return
     */
    @Condition
    public boolean isEight(@Fact("number") Integer number){
        return number % 8 == 0;
    }

    @Action
    public void eightAction(@Fact("number") Integer number){
        System.out.println(number + " is eight");
    }

    @Priority
    public int getPriority(){
        return 2;
    }
}
