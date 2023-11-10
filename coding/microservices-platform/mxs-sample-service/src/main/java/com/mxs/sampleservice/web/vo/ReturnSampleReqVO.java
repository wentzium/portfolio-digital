package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 退样
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class ReturnSampleReqVO {
    /**
     * 样本编号列表（支持批量退样）
     */
    private List<String> sampleNoList;
    /**
     * 站点ID（退样操作的站点）
     */
    private Long siteId;
}
