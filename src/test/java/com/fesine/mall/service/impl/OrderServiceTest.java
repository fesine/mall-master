package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.service.IOrderService;
import com.fesine.mall.vo.OrderVo;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
@Slf4j
public class OrderServiceTest extends MallApplicationTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(2, 6);
        log.info("list = {}", JSON.toJSONString(responseVo.getData()));
    }
}