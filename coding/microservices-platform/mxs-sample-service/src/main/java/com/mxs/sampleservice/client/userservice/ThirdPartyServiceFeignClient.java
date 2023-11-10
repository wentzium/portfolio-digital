package com.mxs.sampleservice.client.userservice;

import com.mxs.common.entity.TpExamAppoints;
import com.mxs.common.entity.TpExamItems;
import com.mxs.common.util.ResponseData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "thirdparty-service", fallback = HystrixClientFallback.class)
public interface ThirdPartyServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/common/patient/{id}", consumes = "application/json")
    ResponseData getPatientInfo(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/common/exam/appointNo", consumes = "application/json")
    ResponseData getAppointNo();

    @RequestMapping(method = RequestMethod.POST, value = "/common/exam/addPoints", consumes = "application/json")
    ResponseData addPoints(@RequestBody TpExamAppoints bo);

    @RequestMapping(method = RequestMethod.GET, value = "/common/master/{patientId}", consumes = "application/json")
    ResponseData findExamMasterByPatientId(@PathVariable("patientId") String patientId);

    @RequestMapping(method = RequestMethod.GET, value = "/common/rptPattern", consumes = "application/json")
    ResponseData findExamRptPattern();

    @RequestMapping(method = RequestMethod.POST, value = "/common/exam/addItems", consumes = "application/json")
    ResponseData addExamItems(@RequestBody TpExamItems bo);
}
