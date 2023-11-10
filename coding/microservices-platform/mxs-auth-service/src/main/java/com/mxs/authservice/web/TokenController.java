package com.mxs.authservice.web;

import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@Slf4j
@RefreshScope
public class TokenController {

    @Value("${custom.test.value}")
   private String value;

    @PostMapping("/logout")
    public ResponseData logout() {
        try {
            return ResponseData.createSuccessResult(value);
        }catch (Exception e) {
            log.error("登出失败", e);
            return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, ResponseCode.SERVER_ERROR.text);
        }
    }
}
