package com.fesine.mall.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/19
 */
@Data
@AllArgsConstructor
@ToString
public class Model {

    private Integer id;

    private String name;

    private String author;

    private Integer type;
}
