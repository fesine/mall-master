package com.fesine.mall.service.impl;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.dao.ShippingMapper;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.form.ShippingForm;
import com.fesine.mall.pojo.Shipping;
import com.fesine.mall.service.IEsService;
import com.fesine.mall.service.IShippingService;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/21
 */
@Service
@Slf4j
@ServiceGroup("iis")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private IEsService esService;
    /**
     * 添加地址
     *
     * @param uid
     * @param form
     * @return
     */
    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        shipping.setUserId(uid);
        int i = shippingMapper.insertSelective(shipping);
        if (i == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());
        return ResponseVo.success(map);
    }

    /**
     * 删除地址
     *
     * @param uid
     * @param shippingId
     * @return
     */
    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        Shipping shipping = new Shipping();
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int i = shippingMapper.deleteByIdAndUserId(uid, shippingId);
        if (i == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAILED);
        }
        return ResponseVo.success();
    }

    /**
     * 更新地址
     *
     * @param uid
     * @param form
     * @return
     */
    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form,shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int i = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (i == 0) {
            return ResponseVo.error(ResponseEnum.UPDATE_SHIPPING_FAILED);
        }
        return ResponseVo.success();
    }

    /**
     * 分页查询地址
     *
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo<Shipping>> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> list = shippingMapper.selectByUserId(uid);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ResponseVo.success(pageInfo);
    }
}
