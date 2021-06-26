package com.fesine.mall;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/24
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/24
 */
public class StreamTest {

    public static void main(String[] args) {
        StreamTest.createByStaticMethod();
    }

    //Stream可以通过集合数组创建
    /**
     * 1、通过 java.util.Collection.stream() 方法用集合创建流
     */
    public static void createByCollection(){
        String str = "a-b-c";
        String[] split = str.split("-");

        List<String> list = Arrays.asList(split);
        //创建一个顺序流
        Stream<String> stream = list.stream();
        //创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
    }

    /**
     * 2、使用java.util.Arrays.stream(T[] array)方法用数组创建流
     */
    public static void createByArray(){
        int[] array = {1, 3, 5, 6, 8};
        IntStream stream = Arrays.stream(array);
    }

    /**
     * 3、使用Stream的静态方法：of()、iterate()、generate()
     */

    public static void createByStaticMethod(){
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
        stream2.forEach(System.out::println);

        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);
    }
}
