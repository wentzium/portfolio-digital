package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * 保存预约冰冻申请单请求信息
 *  Created by j.yang on 2019-06-16
 * 
 */
@Data
public class SaveAppointmentApplyReqVO {
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
    private String operationTime;
    /**
     * 预计送样日期
     */
    private String expectDeliverySampleTime;
    /**
     * 预约站点id
     */
    private Long siteId;
}
