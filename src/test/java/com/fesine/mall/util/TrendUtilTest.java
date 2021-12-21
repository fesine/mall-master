package com.fesine.mall.util;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/7
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/7
 */
public class TrendUtilTest {

    private static final DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyyMM");

    private static final LocalDate today = LocalDate.now();

    public static void main(String[] args) throws Exception {
        CustTrendResp resp = new CustTrendResp();
        resp.setCompletePercent(BigDecimal.ZERO);
        resp.setNotCompletePercent(BigDecimal.ZERO);
        HashMap<Object, CustTrendResp> map = new HashMap<>();
        map.put("202112", initCustTrendResp());
        map.put("202110", initCustTrendResp());
        map.put("202107", initCustTrendResp());
        map.put("202106", initCustTrendResp());
        map.put("202103", initCustTrendResp());
        map.put("202101", initCustTrendResp());
        Map<Object, List> trendMap = TrendUtil.convertTrendMap("month", 11, today,
                map, resp, new MonthTrendStrategy());
        System.out.println(JSON.toJSONString(trendMap));
    }

    public static CustTrendResp initCustTrendResp() {
        CustTrendResp resp = new CustTrendResp();
        resp.setCustNum(new Random().nextInt(100));
        resp.setCompletePercent(BigDecimal.valueOf(new Random().nextDouble()));
        resp.setNotCompletePercent(BigDecimal.valueOf(new Random().nextDouble()));
        return resp;
    }
}
