package com.fesine.mall.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/7
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/7
 */
public class MonthTrendStrategy implements TrendStrategy<LocalDate, Integer, String> {

    private static final DateTimeFormatter DATE_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 获取trend key
     *
     * @param trendObject 待处理周期上\下限值
     * @param trendNum    处理周期下\上限区间
     * @return
     */
    @Override
    public String getKey(LocalDate trendObject, Integer trendNum) {
        return trendObject.minusMonths(trendNum).format(DATE_FORMATTER_YM);
    }

}
