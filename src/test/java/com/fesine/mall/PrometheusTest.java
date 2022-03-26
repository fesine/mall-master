package com.fesine.mall;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/3/21
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/3/21
 */
public class PrometheusTest {

//    @Test
    public void test() {
        try {
            String url = "10.211.55.176:9091";
            CollectorRegistry registry = new CollectorRegistry();
            Gauge guage = Gauge.build("my_custom_metric", "This is my custom metric.").create();
            guage.set(23.12);
            guage.register(registry);
            PushGateway pg = new PushGateway(url);
            Map<String, String> groupingKey = new HashMap<String, String>();
            groupingKey.put("instance", "my_instance");
            pg.pushAdd(registry, "my_job", groupingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void test2() {
        try {
            String url = "10.211.55.176:9091";
            CollectorRegistry registry = new CollectorRegistry();
            Gauge guage = Gauge.build("my_custom_metric", "This is my custom metric.").labelNames("app", "date").create();
            String date = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
            guage.labels("my-pushgateway-test-0", date).set(25.25);
            guage.labels("my-pushgateway-test-1", date).dec();
            guage.labels("my-pushgateway-test-2", date).dec(2);
            guage.labels("my-pushgateway-test-3", date).inc();
            guage.labels("my-pushgateway-test-4", date).inc(5);
            guage.register(registry);
            PushGateway pg = new PushGateway(url);
            Map<String, String> groupingKey = new HashMap<String, String>();
            groupingKey.put("instance", "my_instance");
            pg.pushAdd(registry, "my_job", groupingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static CollectorRegistry registry = new CollectorRegistry();
    static String url = "10.211.55.176:9091";
    Map<String, String> groupingKey = new HashMap<>();
     static Gauge guage;
     static {
        guage = Gauge.build("iis_status_metric", "This is iis log with status metric.")
                .labelNames("sysCode", "sysName", "appCode", "appName", "serverIp", "csUriStem", "clientIp", "dateTime")
                .create();
        guage.register(registry);
    }

    PushGateway pg = new PushGateway(url);


    @Before
    public void before(){

    }

    @Test
    public void test3() {

        groupingKey.put("instance", "iis_instance");
        for (int i = 0; i < 100; i++) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0." + i, date).set(i);

//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.148", date).set(200);
//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.45", date).set(404);
//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.12", date).set(201);
//            guage.labels("my-pushgateway-test-1", date).dec();
//            guage.labels("my-pushgateway-test-2", date).dec(2);
//            guage.labels("my-pushgateway-test-3", date).inc();
//            guage.labels("my-pushgateway-test-4", date).inc(5);
            try {
                pg.pushAdd(registry, "iis_job", groupingKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test4() {
        groupingKey.put("instance", "iis_instance");
        for (int i = 0; i < 100; i++) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "99.15.228." + i, date).set(i);

//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.148", date).set(200);
//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.45", date).set(404);
//                guage.labels("01", "测试系统", "/aaa/", "测试系统", "127.0.0.1", "/aaa/test/test.aspx", "127.0.0.12", date).set(201);
//            guage.labels("my-pushgateway-test-1", date).dec();
//            guage.labels("my-pushgateway-test-2", date).dec(2);
//            guage.labels("my-pushgateway-test-3", date).inc();
//            guage.labels("my-pushgateway-test-4", date).inc(5);
            guage.setToCurrentTime();
            try {
                pg.pushAdd(registry, "iis_job", groupingKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
