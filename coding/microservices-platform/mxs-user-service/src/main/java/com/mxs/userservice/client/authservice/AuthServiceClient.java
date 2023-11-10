package com.mxs.userservice.client.authservice;

import com.mxs.userservice.dto.JWT;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "auth-service",fallback =AuthServiceHystrix.class )
@FeignClient(value = "auth-service")
public interface AuthServiceClient {

    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization,
                 @RequestParam("grant_type") String type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);

    @DeleteMapping(value = "/oauth/token")
    Boolean revokeToken(@RequestHeader("Authorization") String authorization, @RequestParam("token") String token);

}