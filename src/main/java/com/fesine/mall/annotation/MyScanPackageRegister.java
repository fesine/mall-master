package com.fesine.mall.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/3
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/3
 */
public class MyScanPackageRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(EnableMyService.class.getName()));
        if (mapperScanAttrs != null) {
            String[] value = (String[]) mapperScanAttrs.get("basePackages");
            MyBeanDefinitionScanner scanner = new MyBeanDefinitionScanner(registry,false);
            scanner.doScan(value);
        }
    }

}
