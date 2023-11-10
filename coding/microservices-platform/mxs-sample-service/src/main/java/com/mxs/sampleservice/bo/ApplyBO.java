package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * 申请单业务对象
 * Created by j.yang on 2019-07-13
 */
@Data
public class ApplyBO {
    /**
     * 病人编号
     */
    private String patientNo;
    /**
     * 申请单号
     */
    String applyNo;
    /**
     * 病理号
     */
    String pathologyNo;
    /**
     * 申请单状态
     */
    Integer status;

    /**
     * 状态描述
     */
    String statusDesc;
}
