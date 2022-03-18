package com.fesine.mall.util.list.lon;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/3/17
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/3/17
 */
@Data
@AllArgsConstructor
public class RiskInfo {

    private String signalTypeDesc;

    private List<CustInfo> riskLevels;

}
