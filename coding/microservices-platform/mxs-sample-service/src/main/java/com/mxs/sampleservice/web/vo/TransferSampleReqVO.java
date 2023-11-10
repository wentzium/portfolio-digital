package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 转运样本请求对象
 *  Created by j.yang on 2019-06-20
 * 
 */
@Data
public class TransferSampleReqVO {
    /**
     * 转运站点ID
     */
    private Long siteId;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 转运箱编号
     */
    private String transferNo;
}
