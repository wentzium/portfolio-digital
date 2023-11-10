package com.mxs.sampleservice.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 样本业务对象
 * Created by j.yang on 2019-07-13
 */
@Data
public class SampleBO {
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 病人编号（外部系统）
     */
    private String patientNo;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 样本编号
     */
    private String sampleNo;
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
     * 可用状态  0-可用 1-作废
     */
    private Integer availableStatus;
    /**
     * 可用状态描述
     */
    private String availableStatusDesc;
    /**
     * 离体状态 0-未离体 1-已离体
     */
    private Integer separationStatus;
    /**
     * 离体状态描述
     */
    private String separationStatusDesc;
    /**
     * 固定状态 0-未固定 1-已固定
     */
    private Integer fixedStatus;
    /**
     * 固定状态描述
     */
    private String fixedStatusDesc;
    /**
     * 物流状态 0-未送出 1-运送中 2-已签收 3-拒收
     */
    private Integer logisticsStatus;
    /**
     * 物流状态描述
     */
    private String logisticsStatusDesc;
    /**
     * 标签状态 0-未打印 1-已打印
     */
    private Integer tagStatus;
    /**
     * 标签状态描述
     */
    private String tagStatusDesc;
    /**
     * 拍照状态 0-未拍照 1-已拍照
     */
    private Integer photoStatus;
    /**
     * 拍照状态描述
     */
    private String photoStatusDesc;
    /**
     * 固定时间
     */
    private String fixedTime;
    /**
     * 样本图片列表
     */
    private List<String> imgList;

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
     * 转运时间
     */
    private String deliveryTime;
    /**
     * 接收时间
     */
    private String acceptTime;
    /**
     * 样本物流信息
     */
    private List<SampleLogisticsBO> logisticsList;
    /**
     * 是否可以修改 默认可以
     */
    private Boolean canModify = true;
    /**
     * 是否冰冻样品
     */
    private Boolean isFrozen;
    private String memo;

    /**
     * 科别
     */
    private String department;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手术类型-字典值ID
     */
    private Long operationTypeDicId;
    /**
     * 手术类型-字典值描述
     */
    private String operationTypeDesc;
    /**
     * 退样标记
     */
    private Boolean returnFlag;
    /**
     * 接收人姓名
     */
    private String acceptUserName;
    /**
     * 来源ID
     */
    private Long siteId;
}
