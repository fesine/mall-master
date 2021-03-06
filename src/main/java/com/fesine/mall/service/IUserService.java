package com.fesine.mall.service;

import com.fesine.mall.pojo.User;
import com.fesine.mall.vo.ResponseVo;

/**
 * @description: 用户业务接口
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
public interface IUserService {

    /**
     * 注册
     * @param user
     */
    ResponseVo register(User user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ResponseVo login(String username, String password);


}
