package com.fesine.mall.as;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/27
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/27
 */
@Service("cclService")
@Slf4j
public class CclServiceImpl extends AbstractService {


    public CclServiceImpl() {
        super("ccl");
    }

    @Override
    protected void say() {
        log.info("----->{} say!",super.getName());
    }
}
