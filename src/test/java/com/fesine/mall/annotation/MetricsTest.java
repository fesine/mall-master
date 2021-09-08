package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.DynamicItemDTO;
import com.fesine.mall.annotation.entity.ItemDTO;
import com.fesine.mall.annotation.entity.MetricsDTO;
import com.fesine.mall.annotation.util.MetricsAnnotationParseUtil;

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
        subMap.put("s_sex", "false");
        subMap.put("s_age", "NaN");
        subMap.put("s_birthday", "1994-08-08");
        subMap.put("s_married", "0");
        subMap.put("s_character", "NaN");
        subMap.put("s_height", "170");
        subMap.put("s_heavy", "75.5");
        subMap.put("s_money", "3999.989882");
        subMap.put("fesine", "浙江杭州");
        subMap.put("浙江杭州", "310000");
        subMap.put("s_createTime", "2021-09-02T00:51+08:00[Asia/Shanghai]");
        Map<String, Object> ssubMap1 = new HashMap<>();
        ssubMap1.put("ss_name", "fesine-sub");
        ssubMap1.put("ss_age", "36");
        Map<String, Object> ssubMap3 = new HashMap<>();
        ssubMap3.put("ss_name", "test-ssub");
        ssubMap3.put("ss_age", "66");
        ssubMap1.put("ssub", ssubMap3);
        subMap.put("sub", ssubMap1);
        subMap.put("subItemDTO", ssubMap1);
        subMap2.put("s_name", "dap");
        subMap2.put("s_age", "30");
        subMap2.put("s_birthday", "2002-10-01");
        //subMap2.put("s_married", "1");
        subMap2.put("s_character", "N");
        subMap2.put("s_sex", "true");
        subMap2.put("s_height", "170");
        subMap2.put("s_heavy", "75.5");
        subMap2.put("s_money", "139199.81682");
        subMap2.put("dap", "江苏无锡");
        subMap2.put("s_createTime", "2021-09-02T00:52+08:00[Asia/Shanghai]");
        //subMap2.put("江苏无锡", "213000");
        Map<String, Object> ssubMap2 = new HashMap<>();
        ssubMap2.put("ss_name", "dap-sub");
        ssubMap2.put("ss_age", "NaN");
        Map<String, Object> ssubMap4 = new HashMap<>();
        ssubMap4.put("ss_name", "test-ss-ssub");
        ssubMap4.put("ss_age", "88");
        ssubMap2.put("ssub", ssubMap4);
        subMap2.put("sub", ssubMap2);
        subMap2.put("subItemDTO", ssubMap2);
        mapList.add(subMap);
        mapList.add(subMap2);
        List<Map<String, Object>> subItemList = new ArrayList<>();
        subItemList.add(ssubMap1);
        subItemList.add(ssubMap2);
        subMap.put("subItemList", subItemList);
        subMap2.put("subItemList", subItemList);
        MyMetricsDTO mymetricsDTO = (MyMetricsDTO)MetricsAnnotationParseUtil.parseToDTO(metricsDTO, map);
        System.out.println(mymetricsDTO);
        MetricsDTO<DynamicItemDTO> dynamicItemDTOMetricsDTO = new MyDynamicMetricsDTO();
        dynamicItemDTOMetricsDTO = MetricsAnnotationParseUtil.parseToDTO(dynamicItemDTOMetricsDTO
                , map);
        ItemDTO itemDTO = new MyItemDTO();
        itemDTO = MetricsAnnotationParseUtil.parseToItemDTO(itemDTO, subMap);
        List<MyItemDTO> list = MetricsAnnotationParseUtil.parseToItemList(new MyItemDTO(), mapList);
        System.out.println();
        //List myItemDTOList = new ArrayList<MyItemDTO>();
        //Type type = myItemDTOList.getClass().getGenericSuperclass();
        //if (type instanceof ParameterizedType) {
        //    // 当前集合的泛型类型
        //    ParameterizedType pt = (ParameterizedType) type;
        //    // 得到泛型里的class类型对象
        //    Type[] actualTypeArguments = pt.getActualTypeArguments();
        //    Class<?> itemClass = (Class<?>) actualTypeArguments[0];
        //    System.out.println();
        //}
    }
}
