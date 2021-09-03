package com.fesine.mall.config;

import com.fesine.mall.annotation.ScanPackage;
import com.fesine.mall.annotation.ServiceGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/2
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/2
 */
@Component
@Slf4j
public class EsClientBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private String[] scanPackage;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //1、获取类上面的ServiceGroup注解
        ServiceGroup serviceGroup = bean.getClass().getAnnotation(ServiceGroup.class);
        if (serviceGroup != null) {
            //1.1、获取所有包含注解的属性
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                //1.2 获取ServiceGroup注解
                ServiceGroup s = field.getAnnotation(ServiceGroup.class);
                if (s != null) {
                    //1.3 重新注入
                    setBeanFieldValue(bean, field, s.value());
                    continue;
                }
                //1.4 获取Resource和Autowired注解
                Resource r = field.getAnnotation(Resource.class);
                Autowired a = field.getAnnotation(Autowired.class);
                if (r != null || a != null) {
                    setBeanFieldValue(bean, field, serviceGroup.value());
                }
            }
        }else if(scanPackage != null && scanPackage.length != 0){
            for (String sp : scanPackage) {
                if (StringUtils.isEmpty(sp)) {
                    continue;
                }
                //在此包中
                if (bean.getClass().getName().startsWith(sp)) {
                    setBeanFieldValue(bean);
                }
            }
        }else{
            //3、没有配置scanPackage，全部扫描处理，建议配置scanPackage
            setBeanFieldValue(bean);
        }
        return bean;
    }

    private void setBeanFieldValue(Object bean) {
        //2 类上面没有ServiceGroup注解
        //2.1、获取所有包含注解的属性
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            //2.2 获取ServiceGroup注解
            ServiceGroup s = field.getAnnotation(ServiceGroup.class);
            if (s != null) {
                //2.3 重新注入
                setBeanFieldValue(bean, field, s.value());
            }
        }
    }

    /**
     * 重新设值
     * @param bean
     * @param field
     * @param group
     */
    private void setBeanFieldValue(Object bean, Field field, String group) {
        try {
            String beanName = group + field.getType().getName();
            Object value =
                    applicationContext.getBean(beanName);
            field.setAccessible(true);
            field.set(bean, value);
            log.info(">>>>>>>>>>>>>>>>>{}#{} set service group [{}] use reference [{}] success.",
                    bean.getClass().getName(), field.getName(), group,beanName);
        } catch (Exception e) {
            //忽略异常
        }
    }

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        try{
            scanPackage = applicationContext.getBean(ScanPackage.class.getName(), String[].class);
            log.info(">>>>>>>>>>>>>>>>>scanPackage={}", Arrays.toString(scanPackage));
        }catch (BeansException e){
            log.info(">>>>>>>>>>>>>>>>>scanPackage not configured，will scan all package.");

        }
    }
}
