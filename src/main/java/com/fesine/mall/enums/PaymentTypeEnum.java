package com.fesine.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/29
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/29
 */
@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    ;
    Integer code;

}
