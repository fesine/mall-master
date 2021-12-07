package com.fesine.mall.util;

import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 将map<trend, dto>中数据转换
 * 为多组map<dto.param.name,List<dto.param.value>>数据
 * @author: fesine
 * @createTime:2021/12/7
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/7
 */
@Slf4j
public class TrendUtil {


    /**
     * @param trendKey          趋势字段
     * @param trendNum          统计周期+1
     * @param tempMap           源数据
     * @param defaultValue      初始化对象
     * @param <T>               泛型
     * @param strategy          区间处理策略
     * @return
     */
    public static <T extends Object> Map<Object, List> fillMonthTrendMap(String trendKey, Integer trendNum, Object trendObject,  Map<Object, T> tempMap, T defaultValue, TrendStrategy strategy) throws Exception {
        Class<?> clazz = defaultValue.getClass();
        //获取trendKey，校验trendKey属性是否存在
        clazz.getDeclaredField(trendKey);
        Field[] fields = clazz.getDeclaredFields();
        Map<Object, List> map = new HashMap<>(fields.length);
        // 1.初始化map，将所有字段转换成map key
        if (tempMap == null) {
            tempMap = new HashMap<>();
        }
        //遍历初始化元素
        for (Field field : fields) {
            map.put(field.getName(), new ArrayList<>());
        }
        for (int i = trendNum; i >= 0; i--) {
            Object key = strategy.getKey(trendObject,i);
            //没有就添加默认值
            tempMap.putIfAbsent(key, defaultValue);
            for (Field field : fields) {
                T t1 = tempMap.get(key);
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                Method readMethod = descriptor.getReadMethod();
                if (field.getName().equals(trendKey)) {
                    map.get(trendKey).add(key);
                } else {
                    map.get(field.getName()).add(readMethod.invoke(t1));
                }
            }
        }
        return map;
    }

}
