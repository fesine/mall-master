package com.fesine.mall.pdf;

import com.fesine.mall.util.FileUtil;
import com.fesine.mall.util.PathUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.io.FileUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
public class ParseHtmlTable {
    public static final String pdfDestPath = "target/";
    public static final String htmlPath = PathUtil.getCurrentPath() +"/test.html";

    public static void main(String[] args) throws IOException, DocumentException {
        String pdfName = "test.pdf";
        ParseHtmlTable parseHtmlTable = new ParseHtmlTable();
        String htmlStr = FileUtils.readFileToString(new File(htmlPath));
        parseHtmlTable.html2pdf(htmlStr, pdfName, "/Users/fesine/Library/Fonts");
    }


    public void html2pdf(String html, String pdfName, String fontDir) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = (ITextFontResolver) renderer.getSharedContext().getFontResolver();
            //添加字体库 begin
            File f = new File(fontDir);
            if (f.isDirectory()) {
                File[] files = f.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        String lower = name.toLowerCase();
                        return lower.endsWith(".otf") || lower.endsWith(".ttf") || lower.endsWith(".ttc");
                    }
                });
                for (int i = 0; i < files.length; i++) {
                    fontResolver.addFont(files[i].getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                }
            }
            //添加字体库end
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            byte[] buff = os.toByteArray();
            //保存到磁盘上
            FileUtil.byte2File(buff, pdfDestPath, pdfName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
