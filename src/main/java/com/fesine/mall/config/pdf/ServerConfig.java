package com.fesine.mall.config.pdf;

import lombok.Data;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/25
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/25
 */
@Data
public class ServerConfig {

    /**
     * 模板请求url
     */
    private String template;

    /**
     * phantomjs服务地址
     */
    private String phantomjs;
}
