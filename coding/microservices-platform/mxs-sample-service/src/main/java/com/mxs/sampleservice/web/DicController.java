package com.mxs.sampleservice.web;


import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.service.DicService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.admin.SaveSiteReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-07-31
 */

@Slf4j
@RestController
@RequestMapping("/dic")
public class DicController {
    @Autowired
    private DicService dicService;

    @ApiOperation("字典值列表")
    @GetMapping("/list")
    public ResponseData list(){
        try {
            return ResponseData.builder().msg("查询成功").code(ResponseCode.SUCCESS.code).data(dicService.list()).build();
        } catch (BizException e) {
            log.error("SampleController->list 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->list 异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    // TODO
    @ApiOperation("保存字典值")
    @PostMapping("/save")
    public ResponseData save(@RequestBody SaveSiteReqVO reqVO){
        try {

            return ResponseData.builder().msg("保存站点成功").code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("SampleController->save 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->save 异常", e);
            return ResponseData.createFailedResult("保存站点失败");
        }
    }
}

