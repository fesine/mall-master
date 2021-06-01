package com.fesine.mall.controller;

import com.fesine.mall.constants.MallConstants;
import com.fesine.mall.form.OrderCreateForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IOrderService;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/31
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/31
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVo create(@Valid  @RequestBody OrderCreateForm form, HttpSession session){
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVo list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                           HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo detail(@PathVariable Long orderNo,HttpSession session){
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,HttpSession session){
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
