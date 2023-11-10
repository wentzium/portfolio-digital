package com.mxs.zuul.config;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常类 过滤器 直接扩展 SendErrorFilter 类
 *
 * @author oKong
 */
@Slf4j
@Component
public class CustomErrorFilter extends SendErrorFilter {

    @Override
    public Object run() {
        //重写 run方法
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            //直接复用异常处理类
            log.info("异常信息:{}", ctx.getThrowable().getCause());
            //这里可对不同异常返回不同的错误码
            HttpServletResponse response = ctx.getResponse();
            response.getOutputStream().write(("{\"code\":\"999999\",\"msg\":\"" + ExceptionUtils.getFullStackTrace(ctx.getThrowable()) + "\"}").getBytes());
        } catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

}