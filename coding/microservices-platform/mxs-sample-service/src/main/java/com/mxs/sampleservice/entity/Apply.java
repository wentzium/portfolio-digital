package com.mxs.sampleservice.entity;

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
 * Created by j.yang on 2019-07-14
 */

@Data
@Accessors(chain = true)
@TableName("t_apply")
public class Apply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 病人ID
     */
    private Long patientId;
    /**
     * 病人编号
     */
    private String patientNo;
    /**
     * 病理号
     */
    private String pathologyNo;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 状态 0-未发送 1-已发送
     */
    private Integer status;
    /**
     * 是否已打印 0-未打印 1-已打印
     */
    private Integer hasPrinted;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;
}
