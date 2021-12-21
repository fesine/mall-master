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
public class Sub2Dto {
    private String s2Id;

    private List<Sub2Sub1Dto> sub2Sub1DtoList;

    private List<Sub2Sub2Dto> sub2Sub2DtoList;
}
