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
public class StaticConfig {

    /**
     * 是否使用临时目录
     */
    private Boolean useTemp;

    /**
     * 模板属性
     */
    private TemplateConfig template;

    /**
     * 模板属性
     */
    private EchartsConfig echarts;

    /**
     * 字体属性
     */
    private FontConfig font;

    /**
     * 图片属性
     */
    private ImageConfig image;

    /**
     * 临时目录
     */
    private TempConfig tmp;

    @Data
    public class TemplateConfig {

        /**
         * 模板目录
         */
        private String dir;

        /**
         * pdf模板名称
         */
        private String name;

        /**
         * 邮件正文模板名称
         */
        private String emailName;
    }

    @Data
    public class EchartsConfig {

        /**
         * echarts模板目录
         */
        private String dir;
    }

    @Data
    public class FontConfig {

        /**
         * 字体目录
         */
        private String dir;
    }

    @Data
    public class ImageConfig {

        /**
         * 图片目录
         */
        private String dir;
    }

    @Data
    public class TempConfig {

        /**
         * 临时目录
         */
        private String dir;
    }
}


