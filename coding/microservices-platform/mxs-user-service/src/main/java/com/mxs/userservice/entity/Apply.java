package com.mxs.userservice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author j.yang
 * @since 2019-07-15
 */
@Data
@Accessors(chain = true)
@TableName("t_apply")
public class Apply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer version;
    /**
     * 申请单编号
     */
    private String code;
    /**
     * 申请单状态
     */
    private String state;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String memo;


}
