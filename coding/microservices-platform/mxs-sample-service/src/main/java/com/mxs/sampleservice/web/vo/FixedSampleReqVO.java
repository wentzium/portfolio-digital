package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 固定样本请求对象
 *  Created by j.yang on 2019-06-14
 * 
 */
@Data
public class FixedSampleReqVO {
    /**
     * 固定站点ID
     */
    private Long siteId;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 固定液类型
     */
    private Integer fixativeType;
    /**
     * 固定液类型描述
     */
    private String fixativeTypeDesc;
    /**
     * 样品重量
     */
    private String sampleWeight;
    /**
     * 固定液体积
     */
    private String fixativeVolume;
    /**
     * 转运容器
     */
    private String transferContainer;
}
