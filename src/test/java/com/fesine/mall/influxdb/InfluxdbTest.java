package com.fesine.mall.influxdb;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.impl.InfluxDBImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/3/26
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/3/26
 */
public class InfluxdbTest {

    private static InfluxDB influxDB;

    static {
        influxDB = new InfluxDBImpl("http://10.211.55.176:8086",
                null, null, new OkHttpClient.Builder());
    }

    @Test
    public void test() {
        BatchPoints points = BatchPoints.database("alarm_engine").build();
        for (int i = 1; i <= 100; i++) {
            Map<String, String> labels = new HashMap<>();
            labels.put("sysCode", "testSys");
            labels.put("sysName", "测试系统");
            labels.put("appCode", "testApp");
            labels.put("appName", "测试应用");
            labels.put("url", "/test/sys/app/query" + (i % 5) + ".aspx");
            labels.put("serverIp", "127.0.0." + i % 2);
//            labels.put("clientIp", String.format("127.0.%s.%s", new Random().nextInt(6) + "", new Random().nextInt(10) + ""));
            Point point = Point.measurement("status_client_metric")
                    .tag(labels)
                    .addField("clientIp", String.format("127.0.%s.%s",
                            new Random().nextInt(6) + "", new Random().nextInt(10) + ""))
                    .addField("value", i % 3 * 100 + 100)
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).build();
            Point point1 = Point.measurement("time_taken_client_metric")
                    .tag(labels)
                    .addField("clientIp", String.format("127.0.%s.%s",
                            new Random().nextInt(6) + "", new Random().nextInt(10) + ""))
                    .addField("value", i % 10 * 105 + new Random().nextInt(1000))
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).build();
            points.point(point);
            points.point(point1);
        }
        influxDB.write(points);
    }

}
