package com.fesine.mall.annotation;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/3
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/3
 */
public class ScanPackageRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(ScanPackage.class.getName()));
        if (mapperScanAttrs != null) {
            String[] value = (String[]) mapperScanAttrs.get("value");
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                    .genericBeanDefinition(String[].class, () -> value);
            registry.registerBeanDefinition(ScanPackage.class.getName(),
                    beanDefinitionBuilder.getRawBeanDefinition());

        }
    }
}
