package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.form.CartUpdateForm;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    private static final Integer uid = 2;

    private static final Integer pid = 29;
    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        CartAddForm addForm = new CartAddForm();
        addForm.setProductId(pid);
        addForm.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.add(uid, addForm);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list(){
        ResponseVo<CartVo> responseVo = cartService.list(uid);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(3);
        cartUpdateForm.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.update(uid, pid, cartUpdateForm);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(uid, pid);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void sum(){
        ResponseVo<Integer> responseVo = cartService.sum(uid);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}