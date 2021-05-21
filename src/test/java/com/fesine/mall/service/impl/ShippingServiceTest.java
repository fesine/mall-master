package com.fesine.mall.service.impl;

import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.form.ShippingForm;
import com.fesine.mall.service.IShippingService;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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

    @Test
    public void add() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("fesine");
        form.setReceiverPhone("0571-12341234");
        form.setReceiverMobile("13812341234");
        form.setReceiverProvince("浙江省");
        form.setReceiverCity("杭州市");
        form.setReceiverDistrict("萧山区");
        form.setReceiverZip("313000");
        form.setReceiverAddress("钱江世纪城");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(2, form);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void delete() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.delete(2, 5);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
    }

    @Test
    public void list() {
    }
}