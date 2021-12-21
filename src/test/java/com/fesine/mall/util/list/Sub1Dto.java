package com.fesine.mall.util.list;

import lombok.Data;

import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/19
 */
@Data
public class Sub1Dto {
    private String s1Id;

    private List<Sub1Sub1Dto> sub1Sub1DtoList;

    private List<Sub1Sub2Dto> sub1Sub2DtoList;
}
