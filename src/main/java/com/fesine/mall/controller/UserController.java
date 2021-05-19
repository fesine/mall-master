package com.fesine.mall.controller;

import com.fesine.mall.pojo.User;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fesine.mall.enums.ResponseEnum.NEED_LOGIN;

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

    @PostMapping("/register")
    public ResponseVo register(@RequestBody User user){
        log.info("username={}",user.getUsername());
        return ResponseVo.error(NEED_LOGIN);
    }
}
