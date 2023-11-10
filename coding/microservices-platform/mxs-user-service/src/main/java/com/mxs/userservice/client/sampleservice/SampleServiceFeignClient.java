package com.mxs.userservice.client.sampleservice;

import com.mxs.common.util.ResponseData;
import com.mxs.userservice.entity.Apply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyingxuan
 * @Date: 2019-07-18 17:19
 */
@FeignClient("sample-service")
public interface SampleServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/apply/list", consumes = "application/json")
    ResponseData<List<Apply>> getApplies();

    @RequestMapping(method = RequestMethod.GET, value = "/sample/apply/union", consumes = "application/json")
    ResponseData<Map<String, Object>> union(@RequestHeader(value = "Authorization") String authorization);
}
