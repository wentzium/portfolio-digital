package com.mxs.sampleservice.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.common.util.Paging;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.bo.operation.ListForOperationSiteBO;
import com.mxs.sampleservice.client.userservice.ThirdPartyServiceProxy;
import com.mxs.sampleservice.service.ApplyService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * 前端控制器
 *  Created by j.yang on 2019-08-03
 * 
 */

@Api("Apply API")
@RestController
@RequestMapping("/apply")
@Slf4j
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ThirdPartyServiceProxy thirdPartyServiceProxy;

    /**
     * 获取申请单号
     *
     * @return
     */
//    @GetMapping("/getApplyNo")
//    public ResponseData getApplyNo() {
//        try {
//            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(applyService.getApplyNo()).build();
//        } catch (Exception e) {
//            log.error("getApplyNo异常", e);
//            return ResponseData.createFailedResult("操作异常");
//        }
//    }
    @ApiOperation("初始化申请单信息")
    @PostMapping("/initApply")
    public ResponseData initApply(@RequestBody InitApplyReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(applyService.initApply(reqVO)).build();
        } catch (BizException e) {
            log.error("ApplyController->initApply业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->initApply异常", e);
            return ResponseData.createFailedResult("初始化申请失败");
        }
    }

    /**
     * 查询病人下的申请单列表
     *
     * @return
     */
//    @GetMapping("/listByPatientNo")
//    public ResponseData listByPatientNo() {
//        try {
//            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(applyService.getApplyNo()).build();
//        } catch (Exception e) {
//            log.error("getApplyNo异常", e);
//            return ResponseData.createFailedResult("操作异常");
//        }
//    }

    /**
     * 保存申请单信息
     *
     * @return
     */
    @ApiOperation("保存申请单")
    @PostMapping("/saveApplyInfo")
    public ResponseData saveApplyInfo(@RequestBody SaveApplyInfoReqVO reqVO) {
        try {
            if (StringUtils.isEmpty(reqVO.getApplyNo())) {
                return ResponseData.createFailedResult("申请单编号为空");
            }
            return ResponseData.createSuccessResult(applyService.saveApplyInfo(reqVO));
        } catch (BizException e) {
            log.error("ApplyController->saveApplyInfo 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->saveApplyInfo 异常", e);
            return ResponseData.createFailedResult("保存失败");
        }
    }

    @ApiOperation("申请单详情查询")
    @GetMapping("/getDetailInfo")
    public ResponseData getDetailInfo(@RequestParam("applyNo") String applyNo) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(applyService.getDetailInfo(applyNo)).build();
        } catch (BizException e) {
            log.error("ApplyController->getDetailInfo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->getDetailInfo异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("发送申请单")
    @PostMapping("/send")
    public ResponseData send(@RequestBody SendApplyReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.code).data(applyService.send(reqVO.getApplyNo())).msg("发送申请单成功").build();
        } catch (BizException e) {
            log.error("ApplyController->send 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->send 异常", e);
            return ResponseData.createFailedResult("发送申请单失败");
        }
    }

    @ApiOperation("物流查询统一入口")
    @GetMapping("/applyLogisticsQuery")
    public ResponseData applyLogisticsQuery(@RequestParam("id") String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return ResponseData.createFailedResult("ID不能为空");
            }

            return ResponseData.builder().data(applyService.applyLogisticsQuery(id)).code(ResponseCode.SUCCESS.getCode()).build();
        } catch (BizException e) {
            log.error("ApplyController->applyLogisticsQuery业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->applyLogisticsQuery异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("手术站点-默认列表(登录医生在该站点录入的所有申请单列表)")
    @PostMapping("/searchOperationList")
    public ResponseData searchOperationList(@RequestBody SearchListForOperationSiteReqVO reqVO) {
        try {
            Page<ListForOperationSiteBO> result = applyService.searchOperationList(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("ApplyController->searchListForDoctor 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->searchListForDoctor 异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("手术站点-申请单明细")
    @PostMapping("/searchOperationList/applyDetailList")
    public ResponseData searchApplyDetailList(@RequestBody SearchApplyDetailListReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(applyService.searchApplyDetailList(reqVO)).msg("查询成功").build();
        } catch (BizException e) {
            log.error("ApplyController->searchApplyDetailList 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->searchApplyDetailList 异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    // TODO
    @ApiOperation("打印申请单通知接口")
    @PostMapping("/printedNotify")
    public ResponseData printedNotify(@RequestBody SearchApplyDetailListReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(null).msg("通知成功").build();
        } catch (BizException e) {
            log.error("ApplyController->printedNotify 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("ApplyController->printedNotify 异常", e);
            return ResponseData.createFailedResult("通知失败");
        }
    }

    @ApiOperation("查询检查项目")
    @GetMapping("/searchExamRptPattern")
    public ResponseData searchExamRptPattern() {
        try {
            return ResponseData.createSuccessResult(thirdPartyServiceProxy.findExamRptPattern());
        } catch (BizException e) {
            log.error("PatientInfoController->searchExamRptPattern 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("PatientInfoController->searchExamRptPattern 异常", e);
            return ResponseData.createFailedResult("查询检查项目");
        }
    }
}

