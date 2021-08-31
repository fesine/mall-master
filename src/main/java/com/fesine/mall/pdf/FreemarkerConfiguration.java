package com.fesine.mall.pdf;

import freemarker.template.Configuration;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
public class FreemarkerConfiguration {
    private static Configuration config = null;

    static {
        config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/report/");
    }

    public static Configuration getConfiguration() {
        return config;
    }
}
