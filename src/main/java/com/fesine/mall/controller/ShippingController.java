package com.fesine.mall.controller;

import com.fesine.mall.constants.MallConstants;
import com.fesine.mall.form.ShippingForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IShippingService;
import com.fesine.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/25
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/25
 */
@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm form,
                          HttpSession session) {
        User user = (User)session.getAttribute(MallConstants.CURRENT_USER);
        return shippingService.add(user.getId(), form);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@Valid @RequestBody ShippingForm form,
                          @PathVariable Integer shippingId,
                          HttpSession session) {
        User user = (User)session.getAttribute(MallConstants.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId, form);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                          HttpSession session) {
        User user = (User)session.getAttribute(MallConstants.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                          HttpSession session) {
        User user = (User)session.getAttribute(MallConstants.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }


}
