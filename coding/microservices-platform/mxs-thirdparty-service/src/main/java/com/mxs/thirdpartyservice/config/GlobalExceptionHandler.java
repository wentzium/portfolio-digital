package com.mxs.thirdpartyservice.config;

import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public ResponseData defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.error("系统异常", e);
        if (e instanceof NoHandlerFoundException) {
            return ResponseData.createFailedResult(ResponseCode.NOT_FOUND.code, e.getMessage());
        } else {
            return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, e.getMessage());
        }
    }
}
