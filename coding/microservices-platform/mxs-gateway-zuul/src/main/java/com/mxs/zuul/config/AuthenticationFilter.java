package com.mxs.zuul.config;

import com.google.common.collect.Lists;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @Author: zhangyingxuan
 * @Date: 2019-07-16 19:01
 */
@Slf4j
@Component
public class AuthenticationFilter extends ZuulFilter {

    @Autowired
    private Environment env;
    private static final String HTTP_X_FORWARDED_FOR = "tmx-client-ip";

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //执行顺序  0 靠前执行 在spring cloud zuul提供的pre过滤器之后执行，默认的是小于0的。
        //除了参数校验类的过滤器 一般上直接放在 PreDecoration前
        //即：PRE_DECORATION_FILTER_ORDER - 1;
        //常量类都在：org.springframework.cloud.netflix.zuul.filters.support.FilterConstants 下
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //获取请求的上下文类 注意是：com.netflix.zuul.context包下的
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = ctx.getRequest();
        //避免中文乱码
        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        //获取请求IP
        String remoteAddr = request.getRemoteAddr();
        ctx.getZuulRequestHeaders().put(HTTP_X_FORWARDED_FOR, remoteAddr);
        //打印日志
        String requestURI = request.getRequestURI().replace("//", "/");
        log.info("请求方式：{},地址：{}", request.getMethod(), requestURI);
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            return null;
        }
        String value = env.getProperty("custom.unauthorize.uri.list", "/api/user-service/user/register,/api/user-service/user/login,/api/user-service/swagger-ui.html,/api/user-service/swagger-resources/**,/api/user-service/v2/**,/api/sample-service/swagger-ui.html,/api/sample-service/swagger-resources/**,/api/sample-service/v2/**,/api/sample-service/appointmentApply/list");
        List<String> uriArr = Lists.newArrayList(value.split(","));

        for (String uri : uriArr) {
            if (requestURI.equals(uri)) {
                return null;
            }
            if (uri.endsWith("/**")) {
                String temp = uri.substring(0, uri.length() - 3);
                if (requestURI.startsWith(temp)) {
                    return null;
                }
            }
        }

        //使其不进行转发
        ctx.setSendZuulResponse(false);
        //这里可进行自定义提示
        ctx.setResponseBody("{\"code\":\"999500\",\"msg\":\"非法访问\"}");
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        //或者添加一个额外参数也可以 传递参数可以使用
        ctx.set("checkAuth", false);

        //这返回值没啥用
        return null;

    }
}
