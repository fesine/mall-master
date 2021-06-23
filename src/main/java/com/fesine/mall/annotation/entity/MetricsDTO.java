package com.fesine.mall.annotation.entity;

import java.util.List;

/**
 * @description: 注解实体基类，所有需要转换的类都需要继承此类
 * @author: fesine
 * @createTime:2021/6/23
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/6/23
 */
public abstract class MetricsDTO <I extends ItemDTO> {

    private List<I> itemList;

    public List<I> getItemList() {
        return itemList;
    }

    public void setItemList(List<I> itemList) {
        this.itemList = itemList;
    }
}
