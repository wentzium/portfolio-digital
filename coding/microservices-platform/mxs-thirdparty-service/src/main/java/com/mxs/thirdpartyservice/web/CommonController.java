package com.mxs.thirdpartyservice.web;

import com.mxs.common.entity.*;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.thirdpartyservice.service.ICommonService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/common")
@RestController
@Slf4j
public class CommonController {

    @Autowired
    private ICommonService commonService;

    @GetMapping("/patient/{id}")
    public ResponseData findPatientById(@PathVariable("id") String id) {
        log.info("根据病人id获取病人信息， ID：{}", id);
        if (StringUtils.isEmpty(id)) {
            return ResponseData.createFailedResult(ResponseCode.FAILED.code, "参数错误");
        }
        TpPatientInfo patientById = commonService.findPatientById(id);
        log.info("根据病人id获取病人信息， TpPatientInfo：{}", patientById);
        return ResponseData.createSuccessResult(patientById);
    }

    @GetMapping("/exam/appointNo")
    public ResponseData<Long> getAppointNo() {
        log.info("获取预约号");
        Long examNo = commonService.getAppointmentNoFromHis();
        log.info("获取预约号：{}", examNo);
        return ResponseData.createSuccessResult(examNo);
    }

    @PostMapping("/exam/addPoints")
    public ResponseData<Boolean> addPoints(@RequestBody TpExamAppoints appoints) {
        log.info("新增预约");
        int result = commonService.addExamAppoints(appoints);
        log.info("新增预约：{}", appoints);
        return ResponseData.createSuccessResult(result > 0);
    }

    @GetMapping("/master/{patientId}")
    public ResponseData< List<TpExamMaster>> findExamMasterByPatientId(@PathVariable("patientId") String patientId) {
        log.info("根据病人id获取EXAM_MASTER信息， ID：{}", patientId);
        if (StringUtils.isEmpty(patientId)) {
            return ResponseData.createFailedResult(ResponseCode.FAILED.code, "参数错误");
        }
        List<TpExamMaster> examMasters = commonService.findExamMasterByPatientId(patientId);
        log.info("根据病人id获取EXAM_MASTER信息， List<TpExamMaster>：{}", examMasters);
        return ResponseData.createSuccessResult(examMasters);
    }

    @ApiOperation("获取检查项目信息")
    @GetMapping("/rptPattern")
    public ResponseData< List<TpExamRptPattern>> findExamRptPattern() {
        List<TpExamRptPattern> rptPatterns = commonService.findExamRptPattern();
        log.info("获取检查项目信息， List<TpExamRptPattern>：{}", rptPatterns);
        return ResponseData.createSuccessResult(rptPatterns);
    }

    @ApiOperation("新增examItems")
    @PostMapping("/exam/addItems")
    public ResponseData<Boolean> addExamItems(@RequestBody TpExamItems tpExamItems) {
        log.info("新增examItems： {}", tpExamItems);
        int i = commonService.addExamItems(tpExamItems);
        if (i > 0) {
            return ResponseData.createSuccessResult(true);
        } else {
            return ResponseData.createSuccessResult(false);
        }
    }
}
