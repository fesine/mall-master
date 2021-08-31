package com.fesine.mall.pdf;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
@Slf4j
public class BaseUtil {
    /**
     * @描述：文件转byte[]
     * @返回：byte[]
     */
    public static byte[] file2byte(File file) {
        FileInputStream fileInputStream = null;
        byte[] bFile = null;
        try {
            bFile = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    log.error("", e);
                }
            }

        }
        return bFile;
    }
}
