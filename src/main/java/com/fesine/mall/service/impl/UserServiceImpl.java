package com.fesine.mall.service.impl;

import com.fesine.mall.dao.UserMapper;
import com.fesine.mall.pojo.User;
import com.fesine.mall.service.IUserService;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.fesine.mall.enums.ResponseEnum.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     *
     * @param user
     */
    @Override
    public ResponseVo register(User user) {
        //username不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }
        //email不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(EMAIL_EXIST);
        }
        //MD5摘要算法（Spring自带）
        String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(s);
        //写入数据库
        int i = userMapper.insertSelective(user);
        if (i == 0) {
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.successByMsg("注册成功");
    }

    @Override
    public ResponseVo login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            //用户名不存在
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        String s = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equalsIgnoreCase(s)) {
            //密码错误
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        //取消返回密码
        user.setPassword("");
        return ResponseVo.success(user);
    }


}
