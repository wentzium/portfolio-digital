package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.List;

/**
 * 查询固定站点列表请求参数
 *  Created by j.yang on 2019-06-17
 * 
 */
@Data
public class SearchFixListReqVO {
    private int page = 1;
    private int pageSize = 10;
    /**
     * 固定站点ID（当前操作的固定站点）
     */
    private Integer fixSiteId;

    /**
     * 申请单号
     */
    private String applyNo;

    /**
     * 固定站点覆盖的门诊\手术站点ID集合
     */
    private List<Long> mapSiteIdList;
}
