package com.fesine.mall.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fesine.mall.annotation.entity.ItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @EsItemField(itemKey = "s_name",hasType = true)
    private String name;

    @EsItemField(itemKey = "s_age", expect = "NaN", fill = "0", hasType = true)
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

    @EsItemField(itemKey = "s_money",numberScale = 2)
    private BigDecimal money;

    @EsItemField(itemKey = "s_money", numberScale = 0)
    private Float moneyf;

    @EsItemField(itemKey = "s_sex")
    private Boolean sex;

    @EsItemField(itemKey = "s_birthday", pattern = "yyyy-MM-dd",locale ="GMT+0",timezone = "GMT+8")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyyMMdd")
    private LocalDate birthday;

    @EsItemField(itemKey = "s_createTime", pattern = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'")
    private LocalDateTime createTime;

    @EsItemField(itemKey = "s_createDate", pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @EsItemField(itemKey = "s_createTime",
            pattern = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'",
            locale = "GMT+0",timezone = "GMT+8"
    )
    private LocalDateTime createTimeZone;

    @EsItemField(itemKey = "s_createTime",
            pattern = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'",
            locale = "GMT+1",timezone = "GMT+8"
    )
    private LocalDateTime createTimeZone1;

    @EsItemField(itemKey = "s_createTime",
            pattern = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'",
            dateFormat = "yyyy-MM-dd HH:mm:ss"
    )
    private String createTimeString;

    @EsItemField(itemKey = "s_createTime",
            pattern = "yyyy-MM-dd'T'HH:mm+08:00'[Asia/Shanghai]'",
            locale = "GMT+0", timezone = "GMT+8",
            dateFormat = "yyyy-MM-dd HH:mm:ss"
    )
    private String createTimeZoneString;

    @EsItemField(itemKey = "s_createDate", pattern = "yyyy-MM-dd",
            locale = "GMT+0", timezone = "GMT+8")
    private LocalDate createDateZone;

    /**
     * address是由name的值作为key再取出的值
     */
    @EsItemField(itemKey = "s_name->value", hasType = true)
    private String  address;

    @EsItemField(itemKey = "s_name->value->value", expect = "null",fill = "000000", hasType = true)
    private String zip;

    /**
     * 处理对象引用
     */
    @EsItemField(itemKey = "sub",instance = true,hasType = true)
    private MySubItemDTO sub;

    /**
     * 不加注解处理对象引用
     */
    private MySubItemDTO subItemDTO;

    /**
     * 处理List
     */
    @EsItemField(itemKey = "subItemList", reference = MySubItemDTO.class)
    private List<MySubItemDTO> subItemList;

    private String s_name;

    private Double s_money;

    /**
     * 子对象属性拉平处理
     */

    @EsItemField(itemKey = "sub=>ss_name", hasType = true)
    private String subName;

    @EsItemField(itemKey = "sub=>ss_age", expect = "NaN", fill = "0", hasType = true)
    private Integer subAge;

    /**
     * 子对象属性拉平处理
     */

    @EsItemField(itemKey = "sub=>ssub=>ss_name", hasType = true)
    private String ssubName;

    @EsItemField(itemKey = "sub=>ssub=>ss_age", expect = "NaN", fill = "0", hasType = true)
    private Integer ssubAge;



}
