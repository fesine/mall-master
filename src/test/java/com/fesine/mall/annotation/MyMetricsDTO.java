package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.MetricsDTO;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
@MetricsData(groupBy = "s_ip")
public class MyMetricsDTO extends MetricsDTO<MyItemDTO> {

}
