package com.fesine.mall.controller;

import com.fesine.mall.annotation.ServiceGroup;
import com.fesine.mall.constants.MallConstants;
import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.form.CartUpdateForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.service.IEsService;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Api(value = "cart测试接口", tags = "cart测试")
@RestController
public class CartController {


    @Resource
    @ServiceGroup("iis")
    private IEsService iisService;

    @Autowired
    @ServiceGroup("jwlog")
    private IEsService jwlogService;

    @Autowired
    private ICartService cartService;

    @ApiOperation(value = "iis正常，field注解，包扫描", tags = "cart测试")
    @GetMapping("/cart/iis")
    public String iis() {
        return iisService.getMetrics();
    }

    @ApiOperation(value = "jwlog正常，field注解，包扫描", tags = "cart测试")
    @GetMapping("/cart/jwlog")
    public String jwlog() {
        return jwlogService.getMetrics();
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);
    }

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@Valid @RequestBody CartUpdateForm updateForm,
                                     @PathVariable Integer productId,
                                  HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.update(user.getId(),productId ,updateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                  HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(MallConstants.CURRENT_USER);
        return cartService.sum(user.getId());
    }

}
