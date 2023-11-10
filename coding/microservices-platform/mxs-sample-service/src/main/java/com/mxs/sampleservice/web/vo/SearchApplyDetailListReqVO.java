package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 手术站点-申请单明细列表查询参数
 *  Created by j.yang on 2019-06-17
 * 
 */
@Data
public class SearchApplyDetailListReqVO {
    /**
     * 当前操作的固定站点
     */
    private Integer siteId;

    /**
     * 登录用户ID
     */
    private String userId;
    /**
     * 病人编号
     */
    private String patientNo;
}
