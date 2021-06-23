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
@Rule(name = "被3整除" ,description = "number如果被3整除，打印：number is three")
public class ThreeRule {

    /**
     * Condition:条件判断注解：如果return true， 执行Action
     * @param number
     * @return
     */
    @Condition
    public boolean isThree(@Fact("number") Integer number){
        return number % 3 == 0;
    }

    @Action
    public void threeAction(@Fact("number") Integer number){
        System.out.println(number + " is three");
    }

    @Priority
    public int getPriority(){
        return 1;
    }
}
