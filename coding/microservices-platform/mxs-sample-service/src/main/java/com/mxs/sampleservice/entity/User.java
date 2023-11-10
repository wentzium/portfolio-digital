package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-07-11
 */

@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer version;
    @TableField("nickName")
    private String nickName;
    private String username;
    private String password;
    private String salt;
    private String uuid;
    private Date lastLoginTime;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String memo;


}
