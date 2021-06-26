package com.fesine.mall.annotation;

import java.util.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/24
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/24
 */
public class OptionalTest {

    public static void main(String[] args) {
        Map<String, List<String>> map = null;
        //map.put("test", new ArrayList<>());
        List<String> test =
                Optional.ofNullable(map).map(e -> e.get("test")).orElse(null);
        System.out.println(test);
    }
}
