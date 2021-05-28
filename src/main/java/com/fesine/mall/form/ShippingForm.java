package com.fesine.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/21
 */
@Data
public class ShippingForm {
    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}
