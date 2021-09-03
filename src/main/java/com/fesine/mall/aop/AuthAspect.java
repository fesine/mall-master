package com.fesine.mall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/3
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/3
 */
@Aspect
public class AuthAspect {
    /**
     * 处理带@Auth注解的方法
     *
     * @param point 切点
     * @return Object
     * @throws Throwable
     */
    @Around("@annotation(com.fesine.mall.aop.Auth)")
    public Object preAuth(ProceedingJoinPoint point) throws Throwable {
        System.out.println("权限处理前：切面逻辑");

        Object o = point.proceed();

        System.out.println("权限处理后：切面逻辑");
        return o;

    }
}
