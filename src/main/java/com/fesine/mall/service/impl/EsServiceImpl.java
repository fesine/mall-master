package com.fesine.mall.service.impl;

import com.fesine.mall.config.EsClusterConfig;
import com.fesine.mall.service.IEsService;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/9/1
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/9/1
 */
public class EsServiceImpl implements IEsService {

    private EsClusterConfig clusterConfig;

    @Override
    public String getMetrics() {
        return clusterConfig.toString();
    }

    public EsClusterConfig getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(EsClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
    }
}
