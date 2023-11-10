package com.mxs.sampleservice.util.pdf;

import com.itextpdf.layout.element.Table;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.mxs.sampleservice.bo.ApplyPrintBO;

import java.awt.*;
import java.io.IOException;

/**
 *  Created by j.yang on 2019-07-27
 * 
 */
public class PDFBuilder extends com.itextpdf.text.pdf.PdfPageEventHelper {
    private Phrase leftHeader;
    private Phrase rightHeader;

    public static final int marginX = 20;
    public static final int marginY = 5;

    private static BaseFont baseFont;
    // 生成下划线空白占位符
    private static String Blank;
    // 页眉字体
    private static Font font;
    // 下划线字体
    private static Phrase blankPhrase;
    private ApplyPrintBO bo;

    public PDFBuilder() {
        this.leftHeader = new Phrase("", PDFBuilder.font);
        this.rightHeader = new Phrase("", PDFBuilder.font);
    }

    public PDFBuilder(ApplyPrintBO bo) {
        this.bo = bo;
    }

    public PDFBuilder(String leftFooter, String rightFooter) {
        this.leftHeader = new Phrase(leftFooter, PDFBuilder.font);
        this.rightHeader = new Phrase(rightFooter, PDFBuilder.font);
    }

    static {
        try {
            // 中文字体依赖itext得itext-asian包
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 230; i++) {
                sb.append("\u00a0");
            }
            Blank = sb.toString();
            font = new Font(PDFBuilder.baseFont, 9, Font.UNDEFINED);
            blankPhrase = new Phrase(PDFBuilder.Blank, new Font(PDFBuilder.baseFont, Font.DEFAULTSIZE, Font.UNDERLINE));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * @param writer
//     * @param document
//     */
//    @Override
//    public void onEndPage(PdfWriter writer, Document document) {
//        int yMargin = -35;
//        float top = document.top(yMargin);
//        float bottom = document.bottom(yMargin);
//        // 第一页不生成页眉页脚
////        if (document.getPageNumber() == 1) {
////            return;
////        }
//
//        Phrase left_1 = new Phrase(String.format("备注：%s", bo.getRemark()), PDFBuilder.font);
//        Phrase left_2 = new Phrase(String.format("打印人：%s", bo.getPrintUser()), PDFBuilder.font);
//        Phrase right_1 = new Phrase(String.format("送检医生：%s", bo.getInspectionDoctor()), PDFBuilder.font);
//        Phrase right_2 = new Phrase(String.format("打印时间：%s", bo.getPrintDate()), PDFBuilder.font);
//
//        //生成下划线，使用空格占位
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_LEFT, PDFBuilder.blankPhrase,
//                20, document.bottom(-20), 0);
//        //生成左侧页脚
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_LEFT, left_1,
//                document.left(), bottom, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_LEFT, left_2,
//                document.left(), document.bottom(-45), 0);
//        //生成右侧页脚
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_RIGHT, right_1,
//                document.right(), bottom, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_RIGHT, right_2,
//                document.right(), document.bottom(-45), 0);
//        String pageDesc = String.format("第%s页", document.getPageNumber());
//        //生成页脚页数
//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_CENTER, new Phrase(pageDesc, PDFBuilder.font),
//                (document.right() + document.left()) / 2, document.bottom(-60), 0);
//
//    }
}
