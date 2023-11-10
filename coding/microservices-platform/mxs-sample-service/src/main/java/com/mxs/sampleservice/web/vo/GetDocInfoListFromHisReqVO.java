package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * his系统查询病人病历文档列表请求参数
 *  Created by j.yang on 2019-06-13
 * 
 */
@Data
public class GetDocInfoListFromHisReqVO {
    private String patientNo;
    private String visitId;
    private String visitTime;
}
