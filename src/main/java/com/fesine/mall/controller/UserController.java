package com.fesine.mall.controller;

import com.fesine.mall.enums.RoleEnum;
import com.fesine.mall.form.UserLoginForm;
import com.fesine.mall.form.UserRegisterForm;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IUserService;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.fesine.mall.constants.MallConstants.CURRENT_USER;
import static com.fesine.mall.enums.ResponseEnum.NEED_LOGIN;
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
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm,
                               BindingResult bindingResult) {
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

    @PostMapping("/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userForm
            , BindingResult bindingResult
            , HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> responseVo = userService.login(userForm.getUsername(),
                userForm.getPassword());
        session.setAttribute(CURRENT_USER, responseVo.getData());
        return responseVo;
    }

    /**
     * session保存在内存内，重启后失效，改进版token+redis
     * @param session
     * @return
     */
    @GetMapping("")
    public ResponseVo<User> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(NEED_LOGIN);
        }
        return ResponseVo.success(user);
    }

    @PostMapping("/logout")
    public ResponseVo<User> logout(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(NEED_LOGIN);
        }
        session.removeAttribute(CURRENT_USER);
        return ResponseVo.success();
    }
}
