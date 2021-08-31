package com.fesine.mall.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/7/30
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/7/30
 */
@Component
@ConfigurationProperties(prefix = "es")
@Data
@ToString
public class EsClientConfig {

    Map<String, EsClusterConfig> esConfigMap = new HashMap<>();

    private String primary;



}
