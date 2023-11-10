package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 作废样本请求参数
 *
 *  Created by j.yang on 2019-06-12
 * 
 */

@Data
public class CancelSampleReqVO {
    private Long siteId;
    /**
     * 样本编号列表，支持批量作废
     */
    private List<String> sampleNoList;
    /**
     * 作废原因
     */
    private String reason;
    /**
     *作废原因-字典值ID
     */
    private Long reasonDicId;
}
