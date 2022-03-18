package com.fesine.mall.as;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/27
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/27
 */
@Slf4j
public abstract class AbstractService {

    private String name;

    public AbstractService(String name){
        this.name = name;
    }

    public void invoke() throws Exception {
        if (name == null) {
            throw new Exception("name不可为空");
        }
        log.info("----->name:{}",name);
        say();
    }

    protected abstract void say();

    protected String getName(){
        return this.name;
    }

}
