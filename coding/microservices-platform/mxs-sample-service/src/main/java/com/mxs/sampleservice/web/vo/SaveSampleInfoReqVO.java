package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 保存样本信息参数
 *  Created by j.yang on 2019-06-14
 * 
 */
@Data
public class SaveSampleInfoReqVO {
    /**
     * 样本编号（无-新增 反之-修改）
     */
    private String sampleNo;
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 离体时间
     */
    private String separationTime;
    /**
     * 采集部位字典值ID
     */
    private Long collectionLocationDicId;
    /**
     * 采集部位 描述
     */
    private String collectionLocationDesc;
    /**
     * 样本类型-字典值ID
     */
    private Long typeDicId;
    /**
     * 样本类型 描述
     */
    private String typeDesc;
    /**
     * 样本组织块数
     */
    private Integer num;
    /**
     * 样本说明
     */
    private String desc;
    /**
     * 图片URL
     */
    private List<String> imgList;
    /**
     * 操作站点ID
     */
    private Long siteId;

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
    /**
     * 固定时间
     */
    private String fixedTime;
    /**
     * 扩展信息(json字符串)
     */
    private String memo;
    /**
     * 手术类型-字典值ID
     */
    private Long operationTypeDicId;
    /**
     * 手术类型-字典值描述
     */
    private String operationTypeDesc;
}
