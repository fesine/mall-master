package com.fesine.mall.annotation.util;

import com.fesine.mall.annotation.ItemData;
import com.fesine.mall.annotation.MetricsData;
import com.fesine.mall.annotation.entity.DynamicItemDTO;
import com.fesine.mall.annotation.entity.ItemDTO;
import com.fesine.mall.annotation.entity.MetricsDTO;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: 处理注解工具类
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
public class MetricsAnnotationParseUtil {

    public static<T extends MetricsDTO> T parseToDTO(T t,Map<String, List<Map<String, Object>>> map) throws Exception {
        Class cls = t.getClass();
        //获取注解
        Annotation[] annotations = cls.getAnnotations();
        if(annotations == null || annotations.length == 0){
           throw new IllegalArgumentException(t.getClass().getSimpleName()+"没有配置注解。");
        }
        boolean hasMetricsDataAnnotation = false;
        String groupBy = null;
        for (Annotation annotation : annotations) {
            //判断是否包含有MetricsData注解
            if (annotation.annotationType().equals(MetricsData.class)){
                hasMetricsDataAnnotation = true;
                //获取groupBy
                groupBy = ((MetricsData) annotation).groupBy();
                break;
            }
        }
        if (!hasMetricsDataAnnotation) {
            throw new IllegalArgumentException("请为["+t.getClass().getSimpleName() + "]配置@MetricsData注解。");
        }
        if (StringUtils.isEmpty(groupBy)) {
            throw new IllegalArgumentException("请为[" +t.getClass().getSimpleName() +
                    "]@MetricsData注解填写groupBy值。");
        }
        //获取列表属性
        Type type = cls.getGenericSuperclass();
        Class<?> itemClass = null;
        if (type instanceof ParameterizedType) {
            // 当前集合的泛型类型
            ParameterizedType pt = (ParameterizedType) type;
            // 得到泛型里的class类型对象
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            itemClass = (Class<?>) actualTypeArguments[0];

        }
        if (itemClass.equals(ItemDTO.class)) {
            throw new IllegalArgumentException("请为[" +t.getClass().getSimpleName() + "]添加继承ItemDTO类的属性类。");
        }
        List<Map<String, Object>> mapList = map.get(groupBy);
        //继续处理item
        List<ItemDTO> objectList = new ArrayList<>();
        if(itemClass.equals(DynamicItemDTO.class)){
            for (Map<String, Object> itemMap : mapList) {
                //添加动态对象，map只有一个元素
                DynamicItemDTO object = (DynamicItemDTO)itemClass.newInstance();
                Iterator<Map.Entry<String, Object>> iterator = itemMap.entrySet().iterator();
                Map.Entry<String, Object> next = iterator.next();
                object.setKey(next.getKey());
                object.setValue(next.getValue());
                objectList.add(object);
            }
        }else{
            for (Map<String, Object> itemMap : mapList) {
                Object object = itemClass.newInstance();
                //获取所有的属性
                Field[] itemField = itemClass.getDeclaredFields();
                for (Field field : itemField) {
                    ItemData fieldAnnotation = field.getAnnotation(ItemData.class);
                    if (fieldAnnotation != null) {
                        String itemKey = fieldAnnotation.itemKey();
                        Object value = itemMap.get(itemKey);
                        setFieldValue(object, field, value);
                    }
                }
                objectList.add((ItemDTO) object);
            }
        }
        t.setItemList(objectList);

        return t;
    }

    /**
     * 目前只支持String类型，后续再扩展其他类型
     * @param object
     * @param field
     * @param value
     * @throws IllegalAccessException
     */
    private static void setFieldValue(Object object, Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, value);
    }
}
