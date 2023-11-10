package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 查询病人检查历史请求参数
 *  Created by j.yang on 2019-06-18
 * 
 */
@Data
public class SearchPatientCheckHisReqVO {
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 病人编号
     */
    private String patientNo;
}
