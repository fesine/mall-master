package com.fesine.mall.service.impl;

import com.fesine.mall.MallApplicationTest;
import com.fesine.mall.enums.RoleEnum;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @description: user测试类
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
//@Transactional,保证测试不污染数据库
@Transactional
public class UserServiceImplTest extends MallApplicationTest {

    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User("fesine1", "123456", "fesine1@qq.com ", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }
}