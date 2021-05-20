package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Slf4j
public class CartServiceTest extends MallApplicationTest {
    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        CartAddForm addForm = new CartAddForm();
        addForm.setProductId(29);
        addForm.setSelected(true);
        cartService.add(1,addForm);
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info(JSON.toJSONString(list));
    }
}