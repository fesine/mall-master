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
public class Sub1Sub2Dto {

    private String sub1Sub2Id;

    private List<Sub1Sub2Sub1Dto> sub1Sub2Sub1DtoList;
}
