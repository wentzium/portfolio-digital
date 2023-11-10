package com.mxs.sampleservice.util.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 *  Created by j.yang on 2019-07-22
 * 
 */

public class MyFontsProvider extends XMLWorkerFontProvider {
    public MyFontsProvider() {
        super(null, null);
    }

    @Override
    public Font getFont(String fontname, String encoding, boolean embedded, float size, int style) {
        String fntnames = fontname;
        Font FontChinese = null;
        if (fntnames == null) {
            fntnames = "宋体";
        }
        if (size == 0) {
            size = 4;
        }
        try{
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            FontChinese = new Font(bfChinese, 14, Font.NORMAL);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(FontChinese==null){
            FontChinese = super.getFont(fntnames, encoding, size, style);
        }
        return FontChinese;
    }
}
