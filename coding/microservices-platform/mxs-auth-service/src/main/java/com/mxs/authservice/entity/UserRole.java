package com.mxs.authservice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author j.yang
 * @since 2019-06-21
 */
@Data
@Accessors(chain = true)
@TableName("r_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer version;
    private Long userId;
    private Long roleId;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String memo;


}
