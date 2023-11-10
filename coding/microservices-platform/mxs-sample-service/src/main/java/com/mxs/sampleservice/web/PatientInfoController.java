package com.mxs.sampleservice.web;


import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.service.PatientInfoService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.GetDocInfoListFromHisReqVO;
import com.mxs.sampleservice.web.vo.SearchPatientCheckHisReqVO;
import com.mxs.sampleservice.web.vo.SyncPatientInfoReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 病人信息 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
@Slf4j
@RestController
@RequestMapping("/patientInfo")
public class PatientInfoController {
    @Autowired
    private PatientInfoService patientInfoService;

    @ApiOperation("同步病人信息")
    @PostMapping("/sync")
    public ResponseData sync(@RequestBody SyncPatientInfoReqVO reqVO) {
        try {
            return ResponseData.createSuccessResult(patientInfoService.sync(reqVO));
        } catch (BizException e) {
            log.error("PatientInfoController->sync业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("PatientInfoController->sync异常", e);
            return ResponseData.createFailedResult("同步病人信息失败");
        }
    }

    @ApiOperation("查询病人检查历史")
    @PostMapping("/searchCheckHis")
    public ResponseData searchCheckHis(@RequestBody SearchPatientCheckHisReqVO reqVO) {
        try {
            return ResponseData.createSuccessResult(patientInfoService.searchCheckHis(reqVO));
        } catch (BizException e) {
            log.error("PatientInfoController->searchCheckHis业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("PatientInfoController->searchCheckHis异常", e);
            return ResponseData.createFailedResult("查询病人检查历史失败");
        }
    }

    @ApiOperation("获取电子病历结果列表")
    @PostMapping("/getMedicalHistoryResultList")
    public ResponseData getMedicalHistoryResultList(@RequestBody GetDocInfoListFromHisReqVO reqVO) {
        try {
            return ResponseData.createSuccessResult(patientInfoService.getMedicalHistoryResultList(reqVO));
        } catch (BizException e) {
            log.error("PatientInfoController->getMedicalHistoryResultList业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("PatientInfoController->getMedicalHistoryResultList异常", e);
            return ResponseData.createFailedResult("获取电子病历结果失败");
        }
    }

//    @ApiOperation("获取病历文档信息列表")
//    @PostMapping("/getDocInfoList")
//    public ResponseData getDocInfoList(@RequestBody GetDocInfoListFromHisReqVO reqVO) {
//        try {
//            return ResponseData.createSuccessResult(patientInfoService.getDocInfoList(reqVO));
//        } catch (BizException e) {
//            log.error("PatientInfoController->getDocInfoList业务异常", e);
//            return ResponseData.createFailedResult(e.getMessage());
//        } catch (Exception e) {
//            log.error("PatientInfoController->getDocInfoList异常", e);
//            return ResponseData.createFailedResult("获取病历文档信息列表失败");
//        }
//    }
//
//    @ApiOperation("获取his系统病历检查结果")
//    @PostMapping("/getMedicalHistorySummary")
//    public ResponseData getMedicalHistorySummary(@RequestBody GetDocInfoListFromHisReqVO reqVO) {
//        try {
//            return ResponseData.createSuccessResult(patientInfoService.getDocInfoList(reqVO));
//        } catch (BizException e) {
//            log.error("PatientInfoController->getDocInfoList业务异常", e);
//            return ResponseData.createFailedResult(e.getMessage());
//        } catch (Exception e) {
//            log.error("PatientInfoController->getDocInfoList异常", e);
//            return ResponseData.createFailedResult("获取his系统病历检查结果失败");
//        }
//    }
}

