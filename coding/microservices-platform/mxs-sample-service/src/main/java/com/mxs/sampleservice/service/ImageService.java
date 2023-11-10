package com.mxs.sampleservice.service;

import com.mxs.sampleservice.entity.Image;
import com.baomidou.mybatisplus.service.IService;

/**
 * 图片信息 服务类
 * 
 * Created by j.yang on 2019-08-22
 * 
 */
public interface ImageService extends IService<Image> {
    /**
     * 图片base64上传
     *
     * @param base64Str
     * @return
     */
    String upload(String base64Str);
}
