package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 查询转运站点列表请求参数
 *  Created by j.yang on 2019-06-17
 * 
 */
@Data
public class SearchDeliveryListReqVO {
    private int page = 1;
    private int pageSize = 10;
    /**
     * 转运站点ID（当前操作的转运站点）
     */
    private Integer deliverySiteId;
    /**
     * 转运站点覆盖的固定站点ID集合
     */
    private List<Long> mapSiteIdList;
}
