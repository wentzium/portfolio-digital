package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * 同步病人信息时返回的申请单信息
 * Created by j.yang on 2019-07-13
 */
@Data
public class ApplyForSyncPatientInfoBO {
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 申请单状态
     */
    private Integer applyStatus;
    /**
     * 申请单状态描述
     */
    private String applyStatusDesc;
}
