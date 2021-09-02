package com.fesine.mall.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fesine.mall.annotation.entity.ItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description: 类描述
 * 包装类：Integer、Long、Short、Boolean、Byte、Character、Double、Float
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
@Data
public class MyItemDTO extends ItemDTO {

    @EsItemField(itemKey = "s_name")
    private String name;

    @EsItemField(itemKey = "s_age", expect = "NaN", fill = "0")
    private Integer age;

    @EsItemField(itemKey = "s_married" , expect="null", fill = "1")
    private Byte married;

    //@EsItemField(itemKey = "s_character")
    //private Character character;

    @EsItemField(itemKey = "s_height")
    private Long height;

    @EsItemField(itemKey = "s_heavy")
    private Double heavy;

    @EsItemField(itemKey = "s_age", expect = "NaN", fill = "0")
    private Short sage;

    @EsItemField(itemKey = "s_money")
    private BigDecimal money;

    @EsItemField(itemKey = "s_money")
    private Float moneyf;

    @EsItemField(itemKey = "s_sex")
    private Boolean sex;

    @EsItemField(itemKey = "s_birthday",dateFormat = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyyMMdd")
    private LocalDate birthday;

    @EsItemField(itemKey = "s_createTime",dateFormat = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日 HH:mm:ss.SSS")
    private LocalDateTime createTime;


    /**
     * address是由name的值作为key再取出的值
     */
    @EsItemField(itemKey = "s_name->value")
    private String  address;

    @EsItemField(itemKey = "s_name->value->value", expect = "null",fill = "000000")
    private String zip;

}
