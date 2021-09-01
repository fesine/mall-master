package com.fesine.mall.config;

import com.fesine.mall.service.impl.EsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/1
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/1
 */
@Component
@Slf4j
public class EsClientBeanProcessor implements BeanDefinitionRegistryPostProcessor,
        EnvironmentAware, ApplicationContextAware {

    private Environment environment;

    private ApplicationContext applicationContext;


    /**
     * 配置文件前缀
     */
    private static final String ES_URL_PREFIX = "es";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 注册Bean定义，容器根据定义返回bean
        log.info(">>>>>>>>>>>>>>>>register IEsService");
        BindResult<EsClientConfig> bindResult = Binder.get(environment).bind(ES_URL_PREFIX, EsClientConfig.class);
        if (!bindResult.isBound()){
            log.warn(">>>>>>>>>>>>>>>>[EsClientConfig not set properties value]");
            return;
        }
        EsClientConfig config = bindResult.get();
        if (StringUtils.isEmpty(config.getPrimary())) {
            throw new BeanCreationException("[EsClientConfig not set primary value]");
        }

        //构造bean定义
        for (Map.Entry<String, EsClusterConfig> entry :
                config.getEsConfigMap().entrySet()) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                    .genericBeanDefinition(EsServiceImpl.class);
            //设置依赖
            beanDefinitionBuilder.addPropertyValue("clusterConfig", entry.getValue());
            BeanDefinition beanDefinition = beanDefinitionBuilder
                    .getRawBeanDefinition();
            if(entry.getKey().equals(config.getPrimary())){
                beanDefinition.setPrimary(true);
            }
            //注册bean定义
            registry.registerBeanDefinition(entry.getKey(), beanDefinition);
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
