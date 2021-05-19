package com.fesine.mall.interceptor;

import com.fesine.mall.exception.UserLoginException;
import com.fesine.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fesine.mall.constants.MallConstants.CURRENT_USER;

/**
 * @description: 用户登录拦截器
 * true表示继续流程，false中断
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(CURRENT_USER);

        if (user == null) {
            log.info("user == null");
            throw new UserLoginException();
        }
        return true;
    }
}
