package com.fesine.mall.annotation;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/4/6
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/4/6
 */
public class MyBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public MyBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(MyService.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        return beanDefinitionHolders;
    }
}
