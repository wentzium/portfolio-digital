package com.mxs.sampleservice.service.impl;

import com.mxs.common.util.ImageUtil;
import com.mxs.sampleservice.entity.Image;
import com.mxs.sampleservice.mapper.ImageMapper;
import com.mxs.sampleservice.service.ImageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.sampleservice.util.BizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;


/*
 *图片信息 服务实现类
 * 
 * Created by j.yang on 2019-08-29
 * 
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Value("${custom.file.image.url}")
    private String imageUrl;

    @Value("${custom.file.image.upload.path}")
    private String imagePath;

    /**
     * 图片base64上传
     *
     * @param base64Str
     * @return
     */
    @Override
    public String upload(String base64Str) {
        if (StringUtils.isEmpty(base64Str)) {
            throw new BizException("图片base64码不能为空");
        }

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString().replaceAll("-", "");
        boolean b = ImageUtil.base64StrToImage(base64Str, imagePath+fileName);
        if (b) {
            return imageUrl + fileName;
        } else {
            return null;
        }
    }
}
