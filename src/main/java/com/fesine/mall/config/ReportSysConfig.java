package com.fesine.mall.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/7/30
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/7/30
 */
@Component
@ConfigurationProperties(prefix = "report.analyse")
@Data
@ToString
public class ReportSysConfig {

    List<SysConfig> sysConfigList = new ArrayList<>();


    @Data
    public static class SysConfig{
        private String sysName;
        private List<String> sysCodeList;
    }
}
