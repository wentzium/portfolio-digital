package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * 查询预约冰冻申请单返回对象
 * Created by j.yang on 2019-07-13
 */
@Data
public class AppointmentApplyListBO {
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
}
