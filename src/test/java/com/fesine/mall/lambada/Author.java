package com.fesine.mall.lambada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/4/11
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/4/11
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;

    private String name;

    private int age;

    private String intro;

    private List<Book> books;
}
