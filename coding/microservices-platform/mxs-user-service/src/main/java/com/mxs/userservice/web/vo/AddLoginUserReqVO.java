package com.mxs.userservice.web.vo;

import lombok.Data;

/**
 * 添加登录用户请求参数
 * Created by C.SY on 2019/8/7.
 */
@Data
public class AddLoginUserReqVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 唯一标识码
     */
    private String idCode;
    /**
     * 中文姓名
     */
    private String nameCn;
}
