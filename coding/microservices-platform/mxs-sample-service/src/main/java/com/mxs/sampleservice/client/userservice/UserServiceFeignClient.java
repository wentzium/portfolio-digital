package com.mxs.sampleservice.client.userservice;

import com.mxs.common.util.ResponseData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user/list", consumes = "application/json")
    ResponseData getUserList();

    @RequestMapping(method = RequestMethod.GET,value="/user/getUserById",consumes = "application/json")
    ResponseData getUserById(@RequestParam("userId") Long userId);
}
