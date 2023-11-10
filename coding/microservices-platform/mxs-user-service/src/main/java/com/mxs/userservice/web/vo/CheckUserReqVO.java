package com.mxs.userservice.web.vo;

import lombok.Data;

/**
 * 校验用户是否存在请求参数
 * Created by C.SY on 2019/8/6.
 */
@Data
public class CheckUserReqVO {
    /**
     * 用户类型 1-用户名 2-用户唯一标识码
     */
    private Integer type;
    /**
     * 唯一值
     */
    private String username;
}
