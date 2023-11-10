package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 拒收样本请求对象
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class RejectSampleReqVO {
    /**
     * 接收站点ID
     */
    private Long siteId;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 拒收原因
     */
    private String reason;
}
