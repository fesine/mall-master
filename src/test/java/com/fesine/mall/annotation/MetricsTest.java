package com.fesine.mall.annotation;

import com.fesine.mall.annotation.entity.DynamicItemDTO;
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
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        List<Map<String, String>> mapList = new ArrayList<>();
        map.put("s_ip", mapList);
        Map<String, String> subMap = new HashMap<>();
        Map<String, String> subMap2 = new HashMap<>();
        subMap.put("s_name", "fesine");
        subMap.put("s_age", "15");
        subMap2.put("s_name", "dap");
        subMap2.put("s_age", "30");
        mapList.add(subMap);
        mapList.add(subMap2);

        metricsDTO = MetricsAnnotationParseUtil.parseToDTO(metricsDTO, map);
        System.out.println();
        MetricsDTO<DynamicItemDTO> dynamicItemDTOMetricsDTO = new MyDynamicMetricsDTO();
        dynamicItemDTOMetricsDTO = MetricsAnnotationParseUtil.parseToDTO(dynamicItemDTOMetricsDTO
                , map);
        System.out.println();
    }
}
