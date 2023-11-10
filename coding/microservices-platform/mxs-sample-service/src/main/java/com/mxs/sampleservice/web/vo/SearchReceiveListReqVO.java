package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 查询接收站点样本列表-请求参数
 *  Created by j.yang on 2019-06-17
 * 
 */
@Data
public class SearchReceiveListReqVO {
    private int page = 1;
    private int pageSize = 10;
    /**
     * 站点ID（当前操作的接收站点）
     */
    private Integer siteId;
}
