package com.mxs.sampleservice.web;


import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.service.SiteService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.admin.ListSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.RemoveSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveSiteReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/site")
public class AdminSiteController {
    @Autowired
    private SiteService siteService;

    @ApiOperation("新增/保存站点信息")
    @PostMapping("/save")
    public ResponseData save(@RequestBody SaveSiteReqVO reqVO) {
        try {
            siteService.save(reqVO);
            return ResponseData.builder().msg("保存站点成功").code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("SiteController->save 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SiteController->save 异常", e);
            return ResponseData.createFailedResult("保存站点失败");
        }
    }

    @ApiOperation("删除站点信息")
    @PostMapping("/remove")
    public ResponseData remove(@RequestBody RemoveSiteReqVO reqVO) {
        try {
            siteService.remove(reqVO);
            return ResponseData.builder().msg("删除站点成功").code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("SiteController->remove 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SiteController->remove 异常", e);
            return ResponseData.createFailedResult("删除站点失败");
        }
    }

    @ApiOperation("站点查询")
    @PostMapping("/list")
    public ResponseData list(@RequestBody ListSiteReqVO reqVO) {
        try {
            return ResponseData.builder().msg("查询站点成功").data(siteService.list(reqVO)).code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("SiteController->list 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SiteController->list 异常", e);
            return ResponseData.createFailedResult("查询站点失败");
        }
    }

    @ApiOperation("站点详情")
    @GetMapping("/getDetail")
    public ResponseData getDetail(@RequestParam("siteId") Long siteId) {
        try {
            return ResponseData.builder().msg("查询站点成功").data(siteService.getDetail(siteId)).code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("SiteController->getDetail 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SiteController->getDetail 异常", e);
            return ResponseData.createFailedResult("查询站点失败");
        }
    }
}

