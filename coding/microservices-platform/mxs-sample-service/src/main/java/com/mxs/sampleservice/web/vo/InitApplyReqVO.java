package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 初始化申请单请求参数
 *  Created by j.yang on 2019-06-11
 * 
 */
@Data
public class InitApplyReqVO {
    /**
     * 病人编号
     */
    private String patientNo;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 病人ID
     */
    private Long patientId;
}
