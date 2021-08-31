package com.fesine.mall.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.ImageProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/8/5
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/8/5
 */
@Slf4j
public class Generator {
    public static String htmlGenerate(String template, Map<String, Object> variables)
            throws Exception {
        Configuration config = FreemarkerConfiguration.getConfiguration();
        Template tp = config.getTemplate(template);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.process(variables, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        return htmlStr;
    }

    public static void pdfGenerate(String htmlStr, OutputStream out) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes()));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }

    public static void pdfGeneratePlus(String htmlTemplate, Map<String, Object> dataMap,
                                       String targetPdf, Rectangle pageSize, String header,
                                       boolean isFooter, File watermark)
            throws Exception {
        /**
         * 根据freemarker模板生成html
         */
        String htmlStr = htmlGenerate(htmlTemplate, dataMap);
        final String charsetName = "UTF-8";
        Document document = new Document(pageSize);

        OutputStream out = new FileOutputStream(targetPdf);
        /**
         * 设置边距
         */
        // document.setMargins(30, 30, 30, 30);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        /**
         * 添加页码
         */
        PDFBuilder builder = new PDFBuilder(header, 10, pageSize, watermark, isFooter);
        writer.setPageEvent(builder);

        document.open();

        /**
         * html内容解析
         */
        HtmlPipelineContext htmlContext =
                new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider() {
                    @Override
                    public Font getFont(String fontname, String encoding, float size,
                                        final int style) {
                        if (fontname == null) {
                            /**
                             * 操作系统需要有该字体, 没有则需要安装; 当然也可以将字体放到项目中， 再从项目中读取
                             */
                            fontname = "STSong-Light";
                            encoding = "UniGB-UCS2-H";
                        }
                        Font font = null;
                        try {
                            font = new Font(BaseFont.createFont(fontname, encoding,
                                    BaseFont.NOT_EMBEDDED), size,
                                    style);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                        return font;
                    }
                })) {
                    @Override
                    public HtmlPipelineContext clone() throws CloneNotSupportedException {
                        HtmlPipelineContext context = super.clone();
                        ImageProvider imageProvider = this.getImageProvider();
                        context.setImageProvider(imageProvider);
                        return context;
                    }
                };

        /**
         * 图片解析
         */
        htmlContext.setImageProvider(new AbstractImageProvider() {
            String rootPath = "";

            @Override
            public String getImageRootPath() {
                return rootPath;
            }

            @Override
            public Image retrieve(String src) {
                if (StringUtils.isEmpty(src)) {
                    return null;
                }
                try {
                    Image image = Image.getInstance(new File(rootPath, src).toURI().toString());
                    /**
                     * 图片显示位置
                     */
                    image.setAbsolutePosition(400, 400);
                    if (image != null) {
                        store(src, image);
                        return image;
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
                return super.retrieve(src);
            }
        });
        htmlContext.setAcceptUnknown(true).autoBookmark(true)
                .setTagFactory(Tags.getHtmlTagProcessorFactory());

        /**
         * css解析
         */
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        cssResolver.setFileRetrieve(new FileRetrieve() {
            @Override
            public void processFromStream(InputStream in, ReadingProcessor processor) throws IOException {
                try (InputStreamReader reader = new InputStreamReader(in, charsetName)) {
                    int i = -1;
                    while (-1 != (i = reader.read())) {
                        processor.process(i);
                    }
                } catch (Throwable e) {
                }
            }

            /**
             * 解析href
             */
            @Override
            public void processFromHref(String href, ReadingProcessor processor) throws IOException {
                InputStream is = new ByteArrayInputStream(href.getBytes());
                try {
                    InputStreamReader reader = new InputStreamReader(is, charsetName);
                    int i = -1;
                    while (-1 != (i = reader.read())) {
                        processor.process(i);
                    }
                } catch (Exception e) {
                    log.error("", e);
                }

            }
        });

        HtmlPipeline htmlPipeline =
                new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer));
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
        XMLWorker worker = null;
        worker = new XMLWorker(pipeline, true);
        XMLParser parser = new XMLParser(true, worker, Charset.forName(charsetName));
        try (InputStream inputStream = new ByteArrayInputStream(htmlStr.getBytes())) {
            parser.parse(inputStream, Charset.forName(charsetName));
        }
        document.close();
    }
}
