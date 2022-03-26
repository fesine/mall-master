package com.fesine.mall.util;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.util.list.AllDto;
import com.fesine.mall.util.list.ParentDto;
import com.fesine.mall.util.list.Sub1Dto;
import com.fesine.mall.util.list.Sub1Sub1Dto;
import com.fesine.mall.util.list.Sub1Sub2Dto;
import com.fesine.mall.util.list.Sub1Sub2Sub1Dto;
import com.fesine.mall.util.list.Sub2Dto;
import com.fesine.mall.util.list.Sub2Sub1Dto;
import com.fesine.mall.util.list.Sub2Sub2Dto;
import com.fesine.mall.util.list.lon.CustInfo;
import com.fesine.mall.util.list.lon.RiskFlatInfo;
import com.fesine.mall.util.list.lon.RiskInfo;
import com.fesine.mall.util.list.lon.SignalInfo;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/19
 */
public class ListUtilTest {

    @Test
    public void testRisk() throws Exception {
        SignalInfo s1 = new SignalInfo("Y",394);
        SignalInfo s11 = new SignalInfo("N",98);
        List<SignalInfo> sList1 = new ArrayList<>();
        sList1.add(s1);
        sList1.add(s11);
        CustInfo c1 = new CustInfo("Z", 140, sList1);
        SignalInfo s2 = new SignalInfo("N",263);
        List<SignalInfo> sList2 = new ArrayList<>();
        sList2.add(s2);
        CustInfo c2 = new CustInfo("E", 63, sList2);
        List<CustInfo> cList = new ArrayList<>();
        cList.add(c1);
        cList.add(c2);
        RiskInfo riskInfo = new RiskInfo("借款人", cList);
        System.out.println(JSON.toJSONString(riskInfo));
        List<RiskFlatInfo> merge = ListUtil.dp(new RiskFlatInfo(), riskInfo);
        System.out.println();
        System.out.println(JSON.toJSONString(merge));
    }

    @Test
    public void test(){
        Sub1Sub1Dto s1s1_1 = new Sub1Sub1Dto();
        s1s1_1.setSub1Sub1Id("s1s1_1");
        Sub1Sub1Dto s1s1_2 = new Sub1Sub1Dto();
        s1s1_2.setSub1Sub1Id("s1s1_2");
        Sub1Sub1Dto s1s1_3 = new Sub1Sub1Dto();
        s1s1_3.setSub1Sub1Id("s1s1_3");
        List<Sub1Sub1Dto> s1s1List = new ArrayList<>();
        s1s1List.add(s1s1_1);
        s1s1List.add(s1s1_2);
        s1s1List.add(s1s1_3);
        Sub1Sub2Dto s1s2_1 = new Sub1Sub2Dto();
        s1s2_1.setSub1Sub2Id("s1s2_1");
        Sub1Sub2Sub1Dto s1s2s1_1 = new Sub1Sub2Sub1Dto();
        s1s2s1_1.setSub1Sub2Sub1Id("s1s2s1_1");
        List<Sub1Sub2Sub1Dto> sub1Sub2Sub1DtoList = new ArrayList<>();
        sub1Sub2Sub1DtoList.add(s1s2s1_1);
        Sub1Sub2Dto s1s2_2 = new Sub1Sub2Dto();
        s1s2_2.setSub1Sub2Id("s1s2_2");
        s1s2_2.setSub1Sub2Sub1DtoList(sub1Sub2Sub1DtoList);
        Sub1Sub2Dto s1s2_3 = new Sub1Sub2Dto();
        s1s2_3.setSub1Sub2Id("s1s2_3");
        s1s2_3.setSub1Sub2Sub1DtoList(sub1Sub2Sub1DtoList);
        s1s2_1.setSub1Sub2Sub1DtoList(sub1Sub2Sub1DtoList);
        List<Sub1Sub2Dto> s1s2List = new ArrayList<>();
        s1s2List.add(s1s2_1);
        s1s2List.add(s1s2_2);
        s1s2List.add(s1s2_3);
        Sub1Dto sub1Dto1 = new Sub1Dto();
        sub1Dto1.setS1Id("s1_1");
        sub1Dto1.setSub1Sub1DtoList(s1s1List);
        sub1Dto1.setSub1Sub2DtoList(s1s2List);
        List<Sub1Dto> sub1DtoList = new ArrayList<>();
        sub1DtoList.add(sub1Dto1);

        Sub2Sub1Dto s2s1_1 = new Sub2Sub1Dto();
        s2s1_1.setSub2Sub1Id("s2s1_1");
        Sub2Sub1Dto s2s1_2 = new Sub2Sub1Dto();
        s2s1_2.setSub2Sub1Id("s2s1_2");
        List<Sub2Sub1Dto> sub2Sub1DtoList = new ArrayList<>();
        sub2Sub1DtoList.add(s2s1_1);
        sub2Sub1DtoList.add(s2s1_2);
        Sub2Sub2Dto s2s2_1 = new Sub2Sub2Dto();
        s2s2_1.setSub2Sub2Id("s2s2_1");
        Sub2Sub2Dto s2s2_2 = new Sub2Sub2Dto();
        s2s2_2.setSub2Sub2Id("s2s2_2");
        List<Sub2Sub2Dto> sub2Sub2DtoList = new ArrayList<>();
        sub2Sub2DtoList.add(s2s2_1);
        sub2Sub2DtoList.add(s2s2_2);
        Sub2Dto s2_1 = new Sub2Dto();
        s2_1.setS2Id("s2_1");
        s2_1.setSub2Sub1DtoList(sub2Sub1DtoList);
        s2_1.setSub2Sub2DtoList(sub2Sub2DtoList);
        List<Sub2Dto> sub2DtoList = new ArrayList<>();
        sub2DtoList.add(s2_1);
        ParentDto parentDto = new ParentDto();
        parentDto.setPid("p1");
        parentDto.setSub1DtoList(sub1DtoList);
        parentDto.setSub2DtoList(sub2DtoList);
        System.out.println(JSON.toJSONString(parentDto));
        try {
            List<AllDto> merge = ListUtil.dp2(new AllDto(), parentDto);
            System.out.println();
            System.out.println(JSON.toJSONString(merge));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2(){
        List<Model> modelList = Lists.newArrayList();
        modelList.add(new Model(1, "a", "zhuoli", 1));
        modelList.add(new Model(2, "b", "zhuoli", 1));
        modelList.add(new Model(3, "c", "Alice", 1));

        modelList.add(new Model(4, "d", "zhuoli", 2));

        modelList.add(new Model(5, "e", "zhuoli", 3));
        modelList.add(new Model(6, "f", "Michael", 3));
        modelList.add(new Model(7, "g", "Michael", 3));

        // 按指定字段（type）分组
        Map<Integer, List<Model>> modelMap = modelList.stream().collect(Collectors.groupingBy(Model::getType));
        Collection<List<Model>> mapValues = modelMap.values();
        List<List<Model>> dimensionValue = new ArrayList<>(mapValues);    // 原List

        List<List<Model>> result = new ArrayList<>(); // 返回集合
        ListUtil.descartes(dimensionValue, result, 0, new ArrayList<Model>());
        for (List<Model> models : result) {
            List<Integer> resList = new ArrayList<>();
            for (Model model : models) {
                resList.add(model.getId());
            }
            System.out.println(resList.toString());
        }

    }
}
