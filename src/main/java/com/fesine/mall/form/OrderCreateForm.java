package com.fesine.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/1
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/1
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
