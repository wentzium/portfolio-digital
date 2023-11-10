package com.mxs.userservice.dto;

import lombok.Data;

/**
 * Created by j.yang on 2019/6/12.
 */
@Data
public class AdminUserDTO {
    /**
     * 物理主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
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
     * 用户类型 1-临床医生 2-病理科医生 3-手术室护士 4-保障科护士
     */
    private Integer customerType;
    /**
     * 用户类型描述
     */
    private String customerTypeDesc;
    /**
     * 唯一标识码
     */
    private String idCode;
    /**
     * 用户状态 1-正常 2-锁定
     */
    private Integer status;
    /**
     * 状态描述
     */
    private String statusDesc;
    /**
     * 登录次数
     */
    private Integer loginTimes;
    /**
     * 密码过期时间
     */
    private String passwordExpireTime;
    /**
     * 上次登录IP
     */
    private String lastLoginIp;
    /**
     * 上次登录时间
     */
    private String lastLoginTime;
    /**
     * 中文姓名
     */
    private String nameCn;
    private String createAt;
    private String createBy;
    private String updateAt;
    private String updateBy;
    private String memo;
}
