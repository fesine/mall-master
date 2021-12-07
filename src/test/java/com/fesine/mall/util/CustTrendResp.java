package com.fesine.mall.util;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/7
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/7
 */
@Data
public class CustTrendResp {

    private String month;

    private Integer custNum = 0;

    private BigDecimal completePercent;

    private BigDecimal notCompletePercent;
}
