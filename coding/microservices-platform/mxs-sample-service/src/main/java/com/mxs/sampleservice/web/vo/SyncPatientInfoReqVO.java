package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 同步病人信息请求参数
 *  Created by j.yang on 2019-06-20
 * 
 */
@Data
public class SyncPatientInfoReqVO {
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 病人编号
     */
    private String patientNo;
}
