package com.fesine.mall.annotation.util;

import com.fesine.mall.annotation.EsItemField;
import com.fesine.mall.annotation.EsMetricsField;
import com.fesine.mall.annotation.entity.DynamicItemDTO;
import com.fesine.mall.annotation.entity.ItemDTO;
import com.fesine.mall.annotation.entity.MetricsDTO;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @description: 处理注解工具类
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
public class MetricsAnnotationParseUtil {

    private static final String nullValue = "null";

    /**
     * 转换map到dto
     * @param t
     * @param map
     * @param <T>
     * @return
     * @throws Exception
     */
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
            if (annotation.annotationType().equals(EsMetricsField.class)){
                hasMetricsDataAnnotation = true;
                //获取groupBy
                groupBy = ((EsMetricsField) annotation).groupBy();
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
                objectList.add((ItemDTO) fillPropertyValue(itemClass, itemMap));
            }
        }
        t.setItemList(objectList);

        return t;
    }

    /**
     * 转换mapList to itemList
     * @param t
     * @param list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends ItemDTO>  List<T> parseToItemList(T t,List<Map<String, Object>> list) throws Exception {
        Class itemClass = t.getClass();
        //获取列表属性
        if (itemClass.equals(ItemDTO.class)) {
            throw new IllegalArgumentException("请为[" +t.getClass().getSimpleName() + "]添加继承ItemDTO类的属性类。");
        }
        List<T> result = new ArrayList<>();
        //继续处理item
        if(itemClass.equals(DynamicItemDTO.class)){
            for (Map<String, Object> itemMap : list) {
                //添加动态对象，map只有一个元素
                DynamicItemDTO object = (DynamicItemDTO)itemClass.newInstance();
                Iterator<Map.Entry<String, Object>> iterator = itemMap.entrySet().iterator();
                Map.Entry<String, Object> next = iterator.next();
                object.setKey(next.getKey());
                object.setValue(next.getValue());
                result.add((T)object);
            }
            return result;
        }else{
            for (Map<String, Object> itemMap : list) {
                result.add((T) fillPropertyValue(itemClass, itemMap));
            }
        }
        return result;
    }

    private static Object fillPropertyValue(Class itemClass, Map<String, Object> itemMap) throws Exception {
        Object object = itemClass.newInstance();
        //获取所有的属性
        List<Field> itemField = getAllField(itemClass);
        for (Field field : itemField) {
            EsItemField fieldAnnotation = field.getAnnotation(EsItemField.class);
            if (fieldAnnotation != null) {
                String itemKey = fieldAnnotation.itemKey();
                //处理传递赋值
                if (itemKey.endsWith("->value")) {
                    String[] itemArr = itemKey.split("->");
                    itemKey = itemArr[0];
                    for (int i = 1; i < itemArr.length; i++) {
                        itemKey = String.valueOf(itemMap.get(itemKey));
                    }
                }
                //处理属性拉平赋值
                if(itemKey.contains(".")){
                    String[] itemArr = itemKey.split("\\.");
                    itemKey = itemArr[0];
                    Object o = itemMap.get(itemKey);
                    for (int i = 1; i < itemArr.length; i++) {
                        itemKey = itemArr[i];
                        if(o instanceof Map){
                            o = ((Map<String, Object>) o).get(itemKey);
                            continue;
                        }
                        break;
                    }

                    if (o != null) {
                        setFieldValue(object, field, o, fieldAnnotation);
                    }
                    continue;
                }
                Object value = itemMap.get(itemKey);
                //处理对象属性
                if (fillItemDtoValue(object, field, value)) {
                    continue;
                }
                //处理List对象，满足四个条件
                if (fillListValue(object, field, fieldAnnotation, value)) {
                    continue;
                }
                setFieldValue(object, field, value, fieldAnnotation);
            }else{
                String itemKey = field.getName();
                Object value = itemMap.get(itemKey);
                //处理对象属性
                if (fillItemDtoValue(object, field, value)) {
                    continue;
                }
                setFieldValue(object, field, value);
            }
        }
        return object;
    }

    /**
     * 处理List对象，满足四个条件
     * @param object
     * @param field
     * @param fieldAnnotation
     * @param value
     * @return
     * @throws Exception
     */
    private static boolean fillListValue(Object object, Field field, EsItemField fieldAnnotation,
                                         Object value) throws Exception {
        if (value instanceof List
                && List.class.isAssignableFrom(field.getType())
                && !StringUtils.isEmpty(fieldAnnotation.reference())
                && ItemDTO.class.isAssignableFrom(fieldAnnotation.reference())) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) value;
            ItemDTO itemDTO = fieldAnnotation.reference().newInstance();
            List<ItemDTO> dtoList = parseToItemList(itemDTO, mapList);
            field.setAccessible(true);
            field.set(object, dtoList);
            return true;
        }
        return false;
    }

    /**
     * 处理对象属性赋值
     * @param object
     * @param field
     * @param value
     * @return
     * @throws Exception
     */
    private static boolean fillItemDtoValue(Object object, Field field, Object value) throws Exception {
        //需要满足两个条件，一是value为map，二是属性继承自ItemDTO
        if (value instanceof Map && ItemDTO.class.isAssignableFrom(field.getType())) {
            ItemDTO itemDTO = (ItemDTO) field.getType().newInstance();
            itemDTO = parseToItemDTO(itemDTO, (Map<String, Object>) value);
            field.setAccessible(true);
            field.set(object, itemDTO);
            return true;
        }
        return false;
    }

    /**
     * 转换map to itemDTO
     * @param t
     * @param map
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T extends ItemDTO> T parseToItemDTO(T t,Map<String, Object> map) throws Exception {
        Class cls = t.getClass();
        if (cls.equals(ItemDTO.class)) {
            throw new IllegalArgumentException("请为[" +t.getClass().getSimpleName() + "]添加继承ItemDTO类的属性类。");
        }
        return (T)fillPropertyValue(cls, map);
    }

    /**
     * 无注解赋值
     * @param object
     * @param field
     * @param value
     * @throws IllegalAccessException
     */
    private static void setFieldValue(Object object, Field field, Object value) throws Exception {
        field.setAccessible(true);
        Object o = getObjectField(field, value+"");
        field.set(object,o);

    }

    /**
     * 目前只支持String类型，后续再扩展其他类型
     * @param object
     * @param field
     * @param value
     * @throws IllegalAccessException
     */
    private static void setFieldValue(Object object, Field field, Object value, EsItemField esItemField) throws Exception {
        field.setAccessible(true);
        //处理默认值转换
        String expect = esItemField.expect();
        if ((nullValue.equals(expect) && value == null) || expect.equals(value)) {
            value = esItemField.fill();
        }
        if (value == null) {
            return;
        }
        //处理日期转换
        String dateFormat = esItemField.dateFormat();
        if(!StringUtils.isEmpty(dateFormat)){
            Class<?> type = field.getType();
            if (type.equals(LocalDate.class)) {
                value = LocalDate.parse(value.toString(),DateTimeFormatter.ofPattern(dateFormat));
            }else if (type.equals(LocalDateTime.class)) {
                value = LocalDateTime.parse(value.toString(), DateTimeFormatter.ofPattern(dateFormat));
            }
            field.set(object, value);
            return;
        }
        Object o = getObjectField(field, value);
        field.set(object,o);

    }

    @SuppressWarnings("unchecked")
    public static Object getObjectField(Field field, Object value) throws Exception {
        Class typeClass = field.getType();
        //获取有参构造函数
        Constructor con = typeClass.getConstructor(value.getClass());
        //构造函数做初始化
        Object obj = con.newInstance(value);
        return obj;
    }

    private static List<Field> getAllField(Class clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
