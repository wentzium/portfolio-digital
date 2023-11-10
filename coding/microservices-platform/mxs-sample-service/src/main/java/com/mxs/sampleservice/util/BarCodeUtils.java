package com.mxs.sampleservice.util;

import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *  Created by j.yang on 2019-07-23
 * 
 */
@Slf4j
public class BarCodeUtils {
    /**
     * 生成code128条形码
     *
     * @param message       要生成的文本
     * @param withQuietZone 是否两边留白
     * @param hideText      隐藏可读文本
     * @return 图片对应的字节码
     */
    public static byte[] generateBarCode128(String message, boolean withQuietZone, boolean hideText) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 200;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight(bean.getModuleWidth()/0.02);
//        if (width != null) {
//            bean.setModuleWidth(width);
//        }
//        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生产条形码
        bean.generateBarcode(canvas, message);
        try {
            canvas.finish();
        } catch (IOException e) {
            log.error("生成申请单条形码异常", e);
            return null;
        }

        return ous.toByteArray();
    }

    public static void main(String[] args) throws Exception {


        byte[] imgbyte = generateBarCode128("12321342142",  false, false);
        // 传入图片路径，获取图片
        OutputStream os = new FileOutputStream("D:\\Users\\sy_chu\\Desktop\\generate\\t.png");
        os.write(imgbyte, 0, imgbyte.length);
        os.flush();
        os.close();
    }
}
