package com.fesine.mall;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/7/9
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/7/9
 */
public class ListSortTest {

    public static void main(String[] args) {
        //List<String> list = new ArrayList<>();
        //list.add("a");
        //list.add("aa");
        //list.add("aaaa");
        //list.add("aa/bb");
        //list.add("bb");
        //list.add("bbbb");
        //list.add("ccc");
        //list.sort((o1, o2) -> o1.length() <= o2.length() ? 0 : -1);
        //for (String s : list) {
        //    System.out.println(s);
        //}
        String str = "风险门户系统|LU26|LV81|LZ08";
        String[] arr = str.split("\\|");
        List<String> list = Arrays.asList(arr);
        boolean lu26 = list.contains("LU26");
        System.out.println(lu26);
    }
}
