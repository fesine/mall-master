package com.fesine.mall.config;

import com.fesine.mall.service.IEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/11/30
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/11/30
 */
@Component
public class EsMultiServiceManager {

    @Autowired
    private EsClientConfig clientConfig;

    @Autowired
    Map<String, IEsService> esServiceMap;

    @Autowired
    List<IEsService> serviceList;

    @PostConstruct
    public void init() {
        for (String s : clientConfig.getEsConfigMap().keySet()) {
            String beanName = s + IEsService.class.getName();
            if (esServiceMap.containsKey(beanName)) {
                esServiceMap.put(s, esServiceMap.get(beanName));
            }
        }

    }

    public IEsService getService(String group) {
        if (!esServiceMap.containsKey(group)) {
            throw new IllegalArgumentException("分组[" + group + "]不存在，请确认！");
        }
        return esServiceMap.get(group);
    }

}
