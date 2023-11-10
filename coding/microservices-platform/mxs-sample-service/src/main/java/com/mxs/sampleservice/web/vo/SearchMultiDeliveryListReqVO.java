package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 查询多级转运站点样本列表-请求参数
 *  Created by j.yang on 2019-06-17
 * 
 */
@Data
public class SearchMultiDeliveryListReqVO {
    private int page = 1;
    private int pageSize = 10;
    /**
     * 转运站点ID（当前操作的转运站点）
     */
    private Integer deliverySiteId;
    /**
     * 转运箱编号
     */
    private String transferNo;
}
