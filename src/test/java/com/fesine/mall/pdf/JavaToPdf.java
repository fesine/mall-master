package com.fesine.mall.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
public class JavaToPdf {
    private static final String DEST = "target/Test.pdf";

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        document.open();
        Font f1 = FontFactory.getFont(null, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("Test Create PDF", f1));
        document.close();
        writer.close();
    }
}
