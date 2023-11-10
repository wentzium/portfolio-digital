package com.mxs.sampleservice.client.userservice;

import com.mxs.common.entity.TpExamAppoints;
import com.mxs.common.entity.TpExamItems;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class HystrixClientFallback implements ThirdPartyServiceFeignClient {

    @Override
    public ResponseData getPatientInfo(String id) {
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "thirdparty服务调用失败");
    }

    @Override
    public ResponseData getAppointNo() {
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "thirdparty服务调用失败");
    }

    @Override
    public ResponseData addPoints(TpExamAppoints bo) {
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "thirdparty服务调用失败");
    }

    @Override
    public ResponseData findExamMasterByPatientId(@PathVariable("patientId") String patientId) {
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "thirdparty服务调用失败");
    }

    @Override
    public ResponseData findExamRptPattern() {
        return null;
    }

    @Override
    public ResponseData addExamItems(@RequestBody TpExamItems bo) {
        return null;
    }
}