package com.mxs.userservice.web.vo;

import lombok.Data;

/**
 * 登录请求参数
 * Created by C.SY on 2019/8/6.
 */
@Data
public class LoginReqVO {
    /**
     * 用户类型 1-用户名 2-用户唯一标识码
     */
    private Integer type;
    /**
     * 唯一值
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * ip地址
     */
    private String ip;
}
