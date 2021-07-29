package com.fesine.mall;

import org.joda.time.DateTime;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/7/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/7/28
 */
public class DateTest {

    public static void main(String[] args) {
        String instant = "2021-01-01";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-02-28";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-03-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-04-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-05-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-06-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-07-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-08-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-09-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-10-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-11-30";
        testQuarterMonth(instant, "/");
        //test(instant);
        instant = "2021-12-30";
        testQuarterMonth(instant, "/");
        //test(instant);
    }

    public static String test(String date){
         return test(date,"");
    }

    public static String test(String date,String concat){
        DateTime now = new DateTime(date);
        int month = now.getMonthOfYear();
        int quarter = (month - 1) / 3;
        int year = now.getYear();
        if (quarter == 0) {
            year--;
            quarter = 12;
            //System.out.println(year + concat + quarter);
            return year + concat + quarter;

        } else {
            //System.out.println(year + concat+"0" + quarter * 3);
            return year + concat + "0" + quarter * 3;
        }
    }

    /**
     * 根据日期，输出上一年12月份，今年3月份，今年6月份，今年9月份
     * @param date
     * @param concat
     */
    public static void testQuarterMonth(String date,String concat){
        DateTime now = new DateTime(date);
        int month = now.getMonthOfYear();
        //上年财报 全年
        int lastYearTemp = month - 1;
        DateTime lastYear = now.minusMonths(lastYearTemp);
        String lastYearMonth = test(lastYear.toString("yyyy-MM-dd"), concat);
        System.out.println(date+"：上一年最后月份："+ lastYearMonth);
        //第一季财报 全年
        int firstQuarterTemp = month - 4;
        DateTime firstQuarter = now.minusMonths(firstQuarterTemp);
        String firstQuarterMonth = test(firstQuarter.toString("yyyy-MM-dd"), concat);
        System.out.println(date + "：今年第一季度月份：" + firstQuarterMonth);
        //半年财报 >=6月份
        if(month >= 6){
            int halfYearTemp = month - 7;
            DateTime halfYear = now.minusMonths(halfYearTemp);
            String halfYearMonth = test(halfYear.toString("yyyy-MM-dd"), concat);
            System.out.println(date + "：今年半年月份：" + halfYearMonth);
        }
        //第三季度财报 >=9
        if(month >=9){
            int thirdQuarterTemp = month - 11;
            DateTime thirdQuarter = now.minusMonths(thirdQuarterTemp);
            String thirdQuarterMonth = test(thirdQuarter.toString("yyyy-MM-dd"), concat);
            System.out.println(date + "：今年第三季度月份：" + thirdQuarterMonth);
        }
        System.out.println();

    }
}
