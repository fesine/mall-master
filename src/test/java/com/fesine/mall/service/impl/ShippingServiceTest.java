package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.form.ShippingForm;
import com.fesine.mall.pojo.Shipping;
import com.fesine.mall.service.IShippingService;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/21
 */
@Transactional
@Slf4j
public class ShippingServiceTest extends MallApplicationTest {

    @Autowired
    private IShippingService shippingService;

    private ShippingForm form;

    @Before
    public void init(){
        ShippingForm form = new ShippingForm();
        form.setReceiverName("fesine");
        form.setReceiverPhone("0571-12341234");
        form.setReceiverMobile("13812341234");
        form.setReceiverProvince("浙江省");
        form.setReceiverCity("杭州市");
        form.setReceiverDistrict("萧山区");
        form.setReceiverZip("313000");
        form.setReceiverAddress("钱江世纪城");
        this.form = form;
    }

    @Test
    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(2, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void delete() {
        ResponseVo responseVo = shippingService.delete(2, 5);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        this.form.setReceiverCity("宁波");
        ResponseVo responseVo = shippingService.update(2, 6,form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo<PageInfo<Shipping>> responseVo = shippingService.list(2, 1, 1);
        log.info("list={}", JSON.toJSONString(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}