package com.fesine.mall.controller;

import com.fesine.mall.enums.RoleEnum;
import com.fesine.mall.form.UserForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IUserService;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.fesine.mall.enums.ResponseEnum.PARAM_ERROR;

/**
 * @description: 用户控制层
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("注册提交参数有误,{} {}", bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        log.info("username={}", userForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());

        return userService.register(user);
    }
}
