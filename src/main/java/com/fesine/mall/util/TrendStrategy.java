package com.fesine.mall.util;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/12/7
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/12/7
 */
public interface TrendStrategy<T,M,E> {

    /**
     * 获取trend key
     * @param trendObject 待处理周期上\下限值
     * @param trendNum 处理周期下\上限区间
     * @return
     */
    E getKey(T trendObject, M trendNum);

}
