package com.mxs.common.util;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author j.yang
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@TableName("t_login_user")
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 密码加密盐值
     */
    private String salt;
    /**
     * 唯一标识码
     */
    private String idCode;
    /**
     * 用户状态 1-正常 2-锁定
     */
    private Integer status;
    /**
     * 登录次数
     */
    private Integer loginTimes;
    /**
     * 密码过期时间
     */
    private Date passwordExpireTime;
    /**
     * 上次登录IP
     */
    private String lastLoginIp;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 中文姓名
     */
    private String nameCn;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
