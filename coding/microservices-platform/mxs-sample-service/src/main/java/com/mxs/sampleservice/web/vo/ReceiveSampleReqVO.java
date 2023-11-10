package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 接收样本请求对象
 *  Created by j.yang on 2019-06-13
 * 
 */
@Data
public class ReceiveSampleReqVO {
    /**
     * 接收站点ID
     */
    private Long siteId;
    /**
     * 样本编号
     */
    private String sampleNo;
}
