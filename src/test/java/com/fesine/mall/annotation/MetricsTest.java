package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.DynamicItemDTO;
import com.fesine.mall.annotation.entity.ItemDTO;
import com.fesine.mall.annotation.entity.MetricsDTO;
import com.fesine.mall.annotation.util.MetricsAnnotationParseUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
public class MetricsTest {

    public static void main(String[] args) throws Exception {
        MetricsDTO<MyItemDTO> metricsDTO = new MyMetricsDTO();
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        map.put("s_ip", mapList);
        Map<String, Object> subMap = new HashMap<>();
        Map<String, Object> subMap2 = new HashMap<>();
        subMap.put("s_name", "fesine");
        subMap.put("s_age", "15");
        subMap.put("fesine", "浙江杭州");
        subMap.put("浙江杭州", "310000");
        subMap2.put("s_name", "dap");
        subMap2.put("s_age", "30");
        subMap2.put("dap", "江苏无锡");
        subMap2.put("江苏无锡", "213000");
        mapList.add(subMap);
        mapList.add(subMap2);
        MyMetricsDTO mymetricsDTO = (MyMetricsDTO)MetricsAnnotationParseUtil.parseToDTO(metricsDTO, map);
        System.out.println();
        MetricsDTO<DynamicItemDTO> dynamicItemDTOMetricsDTO = new MyDynamicMetricsDTO();
        dynamicItemDTOMetricsDTO = MetricsAnnotationParseUtil.parseToDTO(dynamicItemDTOMetricsDTO
                , map);
        ItemDTO itemDTO = new MyItemDTO();
        itemDTO = MetricsAnnotationParseUtil.parseToItemDTO(itemDTO, subMap);
        List<MyItemDTO> list = MetricsAnnotationParseUtil.parseToItemList(new MyItemDTO(), mapList);
        System.out.println();
        List myItemDTOList = new ArrayList<MyItemDTO>();
        Type type = myItemDTOList.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            // 当前集合的泛型类型
            ParameterizedType pt = (ParameterizedType) type;
            // 得到泛型里的class类型对象
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            Class<?> itemClass = (Class<?>) actualTypeArguments[0];
            System.out.println();
        }
    }
}
