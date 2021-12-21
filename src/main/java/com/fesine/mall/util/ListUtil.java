package com.fesine.mall.util;

import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/19
 */
public class ListUtil {
    /**
     * Discription: 笛卡尔乘积算法
     * 把一个List{[1,2],[A,B],[a,b]} 转化成
     * List{[1,A,a],[1,A,b],[1,B,a],[1,B,b],[2,A,a],[2,A,b],[2,B,a],[2,B,b]} 数组输出
     *
     * @param dimensionValue 原List
     * @param result         通过乘积转化后的数组
     * @param layer          中间参数
     * @param currentList    中间参数
     */
    public static <T> void descartes(List<List<T>> dimensionValue, List<List<T>> result, int layer, List<T> currentList) {
        if (layer < dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                descartes(dimensionValue, result, layer + 1, currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    descartes(dimensionValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                result.add(currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    /**
     * 将m对象中所有的子list拉平，元素复制到t中，进行迪卡尔积合并
     *
     * @param t
     * @param m
     * @param <T>
     * @param <M>
     * @return
     */
    public static <T, M> List<T> merge(T t, M m) throws Exception {
        List<T> result = new ArrayList<>();
        List<Field> fieldList = getAllField(m.getClass(), List.class);
        //复制第一层
        BeanUtils.copyProperties(m, t);
        if (fieldList.size() == 0) {
            T o = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, o);
            result.add(o);
            return result;
        }
        //递归处理
        for (Field field : fieldList) {
            T subObj = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, subObj);
            List subList = (List) getFieldValue(m, field);
            for (Object sub : subList) {
                List<T> merge = merge(subObj, sub);
                result.addAll(merge);
            }

        }
        return result;
    }

    public static <T, M> List<T> dp(T t, M m) throws Exception {
        List<T> result = new ArrayList<>();
        //获取m对象所有的为List的属性列表
        List<Field> fieldList = getAllField(m.getClass(), List.class);
        //复制当前m对象的普通属性给t
        BeanUtils.copyProperties(m, t);
        //当没有list属性时，说明已经递归到底了。
        if (fieldList.size() == 0) {
            T o = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, o);
            result.add(o);
            return result;
        }
        //遍历处理
//        for (int i = 0; i < fieldList.size(); i++) {
//            T o = (T) t.getClass().newInstance();
//            BeanUtils.copyProperties(t, o);
//            List subList = (List) getFieldValue(m, fieldList.get(i));
//            for (Object sub : subList) {
//                result.addAll(dp(o, sub));
//            }
//        }
        result.addAll(_dp(t, m, m, 0, fieldList));
        return result;
    }

    /**
     * @param t
     * @param pm   父层对象
     * @param cur  当前层对象
     * @param x
     * @param list
     * @param <T>
     * @param <M>
     * @return
     * @throws Exception
     */
    public static <T, M> List<T> _dp(T t, M pm, M cur, int x, List<Field> list) throws Exception {
        List<T> result = new ArrayList<>();
        List<Field> fieldList = getAllField(cur.getClass(), List.class);
        //忽略为空的数据
        Iterator<Field> iterator = fieldList.iterator();
        while (iterator.hasNext()){
            Field next = iterator.next();
            Object value = getFieldValue(cur, next);
            if (value == null) {
                iterator.remove();
            }
        }
        //复制当前m对象的普通属性给t
        BeanUtils.copyProperties(cur, t);
        //当没有list属性时，说明已经递归到底了。
        if (fieldList.size() == 0) {
            T t1 = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, t1);
            if (x == list.size() - 1) {
                result.add(t1);
            }
            for (int i = x + 1; i < list.size(); i++) {
                List subList = (List) getFieldValue(pm, list.get(i));
                for (int j = 0; j < subList.size(); j++) {
                    result.addAll(_dp(t1, pm, subList.get(j), x + 1, list));
                }
            }
            return result;
        }
        //遍历处理
        int y = fieldList.size() > 1 ? fieldList.size() - 1 : 1;
        for (int i = 0; i < y; i++) {
            T o = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, o);
            List subList = (List) getFieldValue(cur, fieldList.get(i));
            for (int j = 0; j < subList.size(); j++) {
                result.addAll(_dp(o, cur, subList.get(j), i, fieldList));
            }
        }
        return result;
    }


    /**
     * 遍历所有list
     *
     * @param t         待拉平对象
     * @param valueList 当前层级中第index属性的list值
     * @param index     list下标
     * @param list      属性list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> dp(T t, List valueList, int index, List<Field> list) throws Exception {
        List<T> result = new ArrayList<>();
        T t1 = (T) t.getClass().newInstance();
        BeanUtils.copyProperties(t, t1);
        if (index == list.size()) {
            for (Object o : valueList) {
                //到达最后一层，复制对象添加到list
                T t2 = (T) t.getClass().newInstance();
                BeanUtils.copyProperties(t1, t2);
                BeanUtils.copyProperties(o, t2);
                result.add(t2);
            }
            return result;
        }
        for (Object o : valueList) {
            //到达最后一层，复制对象添加到list
            T t2 = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t1, t2);
            BeanUtils.copyProperties(o, t2);
            result.addAll(dp(t2, (List) getFieldValue(o, list.get(index)), 0, getAllField(o.getClass(), List.class)));
            index++;
        }
        return result;
    }

    public static <T, M> List<T> dp1(T t, M m, int index, List<Field> list) throws Exception {
        List<T> result = new ArrayList<>();
        //获取当前属性中可能包含的list属性列表
        List<Field> fieldList = getAllField(m.getClass(), List.class);
        //复制第一层
        BeanUtils.copyProperties(m, t);
        //遍历到最后一组属性list
        if (fieldList.size() == 0) {
            T o = (T) t.getClass().newInstance();
            BeanUtils.copyProperties(t, o);
            if (index == list.size() - 1) {
                result.add(o);
                return result;
            }
//            result.addAll(dp(o,m,index++,list));
        } else {
            //递归处理
            for (Field field : fieldList) {
                Object subObj = t.getClass().newInstance();
                BeanUtils.copyProperties(t, subObj);
                List subList = (List) getFieldValue(m, field);
                for (Object sub : subList) {
                    List<T> merge = dp1((T) subObj, sub, 0, list);
                    result.addAll(merge);
                }

            }
        }

        return result;
    }

    private static <M> Object getFieldValue(M m, Field field) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(),
                m.getClass());
        Method readMethod = descriptor.getReadMethod();
        //取出数据列表
        return readMethod.invoke(m);
    }


    /**
     * 获取所有的属性，包括父类
     *
     * @param clazz
     * @return
     */
    private static List<Field> getAllField(Class clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取是parent类型所有的属性，包括父类
     *
     * @param clazz
     * @return
     */
    private static List<Field> getAllField(Class clazz, Class parent) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (parent.isAssignableFrom(field.getType())) {
                    fields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
