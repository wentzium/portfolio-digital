package com.mxs.sampleservice.web;


import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.service.SiteService;
import com.mxs.sampleservice.util.BizException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 站点表 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
@Slf4j
@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @ApiOperation("查询IP对应的站点列表")
    @GetMapping("/listByIp")
    public ResponseData listSiteByIp(@RequestParam("ip") String ip) {
        try {
            return ResponseData.builder().msg("查询成功").code(ResponseCode.SUCCESS.code).data(siteService.listSiteByIp(ip)).build();
        } catch (BizException e) {
            log.error("SampleController->listSiteByIp业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->listSiteByIp异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

}

