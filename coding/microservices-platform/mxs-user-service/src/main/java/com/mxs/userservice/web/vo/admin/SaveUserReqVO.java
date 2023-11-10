package com.mxs.userservice.web.vo.admin;

import lombok.Data;

/**
 * 新增/保存用户请求参数
 * Created by C.SY on 2019/8/7.
 */
@Data
public class SaveUserReqVO {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 中文姓名
     */
    private String nameCn;
    /**
     * 电话
     */
    private String phone;
    /**
     * 科室字典值ID
     */
    private Integer dept;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 用户类型
     */
    private Integer customerType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 用户状态 1-正常 2-锁定
     */
    private Integer status;
}
