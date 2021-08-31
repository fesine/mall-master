package com.fesine.mall.pdf;

import com.itextpdf.text.PageSize;

import java.util.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Map<String, Object> mp = new HashMap<String, Object>();
        try {
            String outputFile = "target/abc1.pdf";// 生成后的路径
            Map<String, Object> dataMap = new HashMap<String, Object>();

            List<String> titleList = Arrays.asList("属性1", "属性2", "属性3", "属性4", "属性5", "属性6", "属性7");
            dataMap.put("titleList", titleList);

            List<List<String>> dataList = new ArrayList<List<String>>();
            for (int i = 0; i < 100; i++) {
                dataList
                        .add(Arrays.asList("数据1_" + i, "数据2_" + i, "数据3_数据3_数据3_数据3_数据3_数据3_数据3_" +
                                        "数据3_数据3_" + i,
                                "数据4_" + i, "数据5_" + i, "数据6_" + i, "数据7_" + i));
            }
            dataMap.put("dataList", dataList);

            //File water = new File("C:\\Users\\zhongjy\\Desktop\\test123\\water.png");
            Generator.pdfGeneratePlus("table.html", dataMap, outputFile,
                    PageSize.A4, "", true, null);
            mp.put("code", "200");
            mp.put("url", outputFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            mp.put("code", "500");
        }

    }
}
