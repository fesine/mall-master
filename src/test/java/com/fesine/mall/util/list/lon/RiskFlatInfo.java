package com.fesine.mall.util.list.lon;

import lombok.Data;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/3/17
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/3/17
 */
@Data
public class RiskFlatInfo {

    private String signalTypeDesc;

    private String riskLevelCode;

    private Integer custNum;

    private String effect;

    private Integer signalNum;
}
