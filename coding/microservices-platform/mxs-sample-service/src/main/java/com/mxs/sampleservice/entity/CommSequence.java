package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-07-14
 */

@Data
@Accessors(chain = true)
@TableName("comm_sequence")
public class CommSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer version;
    private String name;
    private Long currentValue;
    private Integer increment;
    private Integer isValid;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String memo;


}
