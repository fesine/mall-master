package com.fesine.mall.service;

import com.fesine.mall.pojo.User;

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
    void register(User user);


}
