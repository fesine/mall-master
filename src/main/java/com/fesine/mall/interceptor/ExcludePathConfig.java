package com.fesine.mall.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@Component
@ConfigurationProperties(prefix = "interceptorconfig.path")
@Data
public class ExcludePathConfig {
    private List<String> exclude;
}
