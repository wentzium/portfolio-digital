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
 * Created by j.yang on 2019-08-12
 */
@Data
@Accessors(chain = true)
@TableName("t_appointment_apply")
public class AppointmentApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 预约申请单号
     */
    private String applyNo;
    /**
     * 手术室名称
     */
    private String operationRoomName;
    /**
     * 手术室编号
     */
    private String operationRoomNo;
    /**
     * 手术室电话
     */
    private String operationRoomTel;
    /**
     * 手术类型
     */
    private Integer operationType;
    /**
     * 手术类型描述
     */
    private String operationTypeDesc;
    /**
     * 手术医生
     */
    private String operationDoctor;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 手术日期
     */
    private Date operationTime;
    /**
     * 预计送样日期
     */
    private Date expectDeliverySampleTime;
    /**
     * 预约站点id
     */
    private Long siteId;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
