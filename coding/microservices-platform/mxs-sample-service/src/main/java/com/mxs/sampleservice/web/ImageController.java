package com.mxs.sampleservice.web;


import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.service.ImageService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.ImageUploadReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 图片信息 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public ResponseData upload(@RequestBody ImageUploadReqVO imageUploadReqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(imageService.upload(imageUploadReqVO.getBase64Str())).build();
        } catch (BizException e) {
            log.error("AppointmentApplyController->upload 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AppointmentApplyController->upload 异常", e);
            return ResponseData.createFailedResult("上传失败");
        }
    }
}

