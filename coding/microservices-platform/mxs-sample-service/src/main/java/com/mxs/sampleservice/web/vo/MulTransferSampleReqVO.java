package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 多级转运样本请求对象
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class MulTransferSampleReqVO {
    /**
     * 转运站点ID
     */
    private Long siteId;
    /**
     * 旧的转运箱编号
     */
    private String oldTransferNo;
    /**
     * 新的转运箱编号
     */
    private String newTransferNo;
}
