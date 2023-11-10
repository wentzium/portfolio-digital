package com.mxs.sampleservice.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.common.util.Paging;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.bo.SampleListForFixSiteBO;
import com.mxs.sampleservice.client.userservice.ThirdPartyServiceProxy;
import com.mxs.sampleservice.service.SampleService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 样本信息 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
@Slf4j
@RestController
@RequestMapping("/sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;
//
//    @GetMapping("/getSampleNo")
//    public ResponseData getSampleNo(String applyNo) {
//        try {
//            if (StringUtils.isEmpty(applyNo)) {
//                return ResponseData.createFailedResult("申请单号不能为空");
//            }
//            return ResponseData.createSuccessResult(sampleService.getSampleNo(applyNo));
//        } catch (Exception e) {
//            log.error("SampleController->getSampleNo异常", e);
//            return ResponseData.createFailedResult("获取失败");
//        }
//    }

    @ApiOperation("新增/保存样本信息")
    @PostMapping("/saveSampleInfo")
    public ResponseData saveSampleInfo(@RequestBody SaveSampleInfoReqVO reqVO) {
        try {
            String sampleNo = sampleService.saveSampleInfo(reqVO);
            return ResponseData.builder().msg("保存样本成功").code(ResponseCode.SUCCESS.code).data(sampleNo).build();
        } catch (BizException e) {
            log.error("SampleController->saveSampleInfo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->saveSampleInfo异常", e);
            return ResponseData.createFailedResult("保存样本信息失败");
        }
    }

    @ApiOperation("查询单个样本信息")
    @GetMapping("/getSample")
    public ResponseData getSampleInfo(String sampleNo) {
        try {
            return ResponseData.createSuccessResult(sampleService.getBySampleNo(sampleNo));
        } catch (BizException e) {
            log.error("SampleController->getSampleInfo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->getSampleInfo异常", e);
            return ResponseData.createFailedResult("作废样本失败");
        }
    }

    @ApiOperation("查询申请单下的样本列表")
    @GetMapping("/listByApplyNo")
    public ResponseData listByApplyNo(String applyNo) {
        try {
            return ResponseData.createSuccessResult(sampleService.listByApplyNo(applyNo));
        } catch (BizException e) {
            log.error("SampleController->listByApplyNo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->listByApplyNo异常", e);
            return ResponseData.createFailedResult("作废样本失败");
        }
    }

    @ApiOperation("作废样本")
    @PostMapping("/cancel")
    public ResponseData cancel(@RequestBody CancelSampleReqVO reqVO) {
        try {
            sampleService.cancelSample(reqVO);
            return ResponseData.createSuccessResult("作废样本成功");
        } catch (BizException e) {
            log.error("SampleController->cancel业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->cancel异常", e);
            return ResponseData.createFailedResult("作废样本失败");
        }
    }

    @ApiOperation("查询固定站点样本列表")
    @PostMapping("/searchFixList")
    public ResponseData searchFixList(@RequestBody SearchFixListReqVO reqVO) {
        try {
            Page<SampleListForFixSiteBO> result = sampleService.searchFixList(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("SampleController->searchFixList业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->searchFixList异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("固定样本")
    @PostMapping("/fixSample")
    public ResponseData fixSample(@RequestBody FixedSampleReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("固定样本成功")
                    .data(sampleService.fixSample(reqVO)).build();
        } catch (BizException e) {
            log.error("SampleController->fixSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->fixSample异常", e);
            return ResponseData.createFailedResult("固定样本失败");
        }
    }

    @ApiOperation("修改样本固定信息")
    @PostMapping("/updateFixedInfo")
    public ResponseData updateFixedInfo(@RequestBody FixedSampleReqVO reqVO) {
        try {
            sampleService.updateFixedInfo(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("修改成功").build();
        } catch (BizException e) {
            log.error("SampleController->updateFixedInfo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->updateFixedInfo异常", e);
            return ResponseData.createFailedResult("修改失败");
        }
    }

    @ApiOperation("查询转运站点样本列表")
    @PostMapping("/searchDeliveryList")
    public ResponseData searchDeliveryList(@RequestBody SearchDeliveryListReqVO reqVO) {
        try {
            Page result = sampleService.searchDeliveryList(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("SampleController->searchDeliveryList业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->searchDeliveryList异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("查询多级转运站点样本列表")
    @PostMapping("/searchMultiDeliveryList")
    public ResponseData searchMultiDeliveryList(@RequestBody SearchMultiDeliveryListReqVO reqVO) {
        try {
            Page result = sampleService.searchMultiDeliveryList(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("SampleController->searchDeliveryList业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->searchDeliveryList异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("转运样本（一级转运站点）")
    @PostMapping("/transferSample")
    public ResponseData transferSample(@RequestBody TransferSampleReqVO reqVO) {
        try {
            sampleService.transferSample(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("转运样本成功").build();
        } catch (BizException e) {
            log.error("SampleController->transferSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->transferSample异常", e);
            return ResponseData.createFailedResult("转运样本失败");
        }
    }

    @ApiOperation("多级转运样本（二级及以上转运站点）")
    @PostMapping("/multiTransferSample")
    public ResponseData multiTransferSample(@RequestBody MulTransferSampleReqVO reqVO) {
        try {
            sampleService.multiTransferSample(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("转运样本成功").build();
        } catch (BizException e) {
            log.error("SampleController->multiTransferSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->multiTransferSample异常", e);
            return ResponseData.createFailedResult("转运样本失败");
        }
    }

    @ApiOperation("获取病理号")
    @PostMapping("/getPathologyNo")
    public ResponseData getPathologyNo(@RequestBody GetPathologyNoReqVO reqVO) {
        try {
            String no = sampleService.getPathologyNo(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(no).msg("获取病理号成功").build();
        } catch (BizException e) {
            log.error("SampleController->getPathologyNo业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->getPathologyNo异常", e);
            return ResponseData.createFailedResult("获取病理号失败");
        }
    }

    @ApiOperation("查询接收站点样本列表")
    @PostMapping("/searchReceiveList")
    public ResponseData searchReceiveList(@RequestBody SearchReceiveListReqVO reqVO) {
        try {
            Page result = sampleService.searchReceiveList(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("SampleController->searchReceiveList业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->searchReceiveList异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("拒收样本")
    @PostMapping("rejectSample")
    public ResponseData rejectSample(@RequestBody RejectSampleReqVO reqVO) {
        try {
            sampleService.rejectSample(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("样本退样成功").build();
        } catch (BizException e) {
            log.error("SampleController->rejectSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->rejectSample异常", e);
            return ResponseData.createFailedResult("样本退样失败");
        }
    }

    @ApiOperation("接收样本")
    @PostMapping("receiveSample")
    public ResponseData receiveSample(@RequestBody ReceiveSampleReqVO reqVO) {
        try {
            sampleService.receiveSample(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("接收样本成功").build();
        } catch (BizException e) {
            log.error("SampleController->receiveSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->receiveSample异常", e);
            return ResponseData.createFailedResult("接收样本失败");
        }
    }

    @ApiOperation("查询物流信息")
    @GetMapping("/getLogistics")
    public ResponseData getLogistics(@RequestParam("sampleNo") String sampleNo) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("查询成功")
                    .data(sampleService.getLogistics(sampleNo)).build();
        } catch (BizException e) {
            log.error("SampleController->getLogistics业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->getLogistics异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("退样")
    @PostMapping("returnSample")
    public ResponseData returnSample(@RequestBody ReturnSampleReqVO reqVO) {
        try {
            sampleService.returnSample(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("退样成功").build();
        } catch (BizException e) {
            log.error("SampleController->returnSample业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->returnSample异常", e);
            return ResponseData.createFailedResult("退样失败");
        }
    }

    @ApiOperation("退出提示")
    @PostMapping("/exitPrompt")
    public ResponseData exitPrompt(@RequestBody ExitPromptReqVO reqVO) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("退出成功")
                    .data(sampleService.exitPrompt(reqVO)).build();
        } catch (BizException e) {
            log.error("SampleController->exitPrompt 退出异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->exitPrompt 异常", e);
            return ResponseData.createFailedResult("退出失败");
        }
    }

    @ApiOperation("样本标签打印通知")
    @PostMapping("/tagPrintedNotify")
    public ResponseData tagPrintedNotify(@RequestBody SampleTagPrintedNotifyReqVO reqVO) {
        try {
            sampleService.tagPrintedNotify(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("通知成功").build();
        } catch (BizException e) {
            log.error("SampleController->tagPrintedNotify 退出异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("SampleController->tagPrintedNotify 异常", e);
            return ResponseData.createFailedResult("通知失败");
        }
    }

    @Autowired
    private ThirdPartyServiceProxy thirdPartyServiceProxy;
    @GetMapping("/getAppointNo")
    public ResponseData getAppointNo(){
        String appointNo = thirdPartyServiceProxy.getAppointNo();
        return ResponseData.builder().data(appointNo).build();
    }
}