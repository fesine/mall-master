package com.fesine.mall;

import com.fesine.mall.config.ReportSysConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @description: 主启动类
 * @author: fesine
 * @createTime:2021/5/19
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/19
 */
@SpringBootApplication
@MapperScan(basePackages = "com.fesine.mall.dao")
@Slf4j
public class MallApplication {

    @Autowired
    private ReportSysConfig config;

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class,args);

    }

    @PostConstruct
    public void init(){
        List<ReportSysConfig.SysConfig> configList = config.getSysConfigList();
        //for (ReportSysConfig.SysConfig sysConfig : configList) {
        //    log.info(sysConfig.getSysName()+"-->"+sysConfig.getSysCodeList().toString());
        //}
        log.info(configList.toString());
    }
}
