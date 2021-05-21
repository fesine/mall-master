package com.fesine.mall.controller;

import com.fesine.mall.constants.MallConstants;
import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.form.CartUpdateForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

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
