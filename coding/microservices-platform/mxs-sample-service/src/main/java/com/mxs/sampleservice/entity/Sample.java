package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by j.yang on 2019-07-11
 */

@Data
@Accessors(chain = true)
@TableName("t_sample")
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 申请单ID
     */
    private Long applyId;
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 病人ID
     */
    private Long patientId;
    /**
     * 病人编号（第三方系统）
     */
    private String patientNo;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 离体时间
     */
    private Date separationTime;
    /**
     * 采集部位字典值ID
     */
    private Long collectionLocationDicId;
    /**
     * 采集部位字典值描述
     */
    private String collectionLocationDesc;
    /**
     * 样本类型-字典值ID
     */
    private Long typeDicId;
    /**
     * 样本类型-字典值ID
     */
    private String typeDesc;
    /**
     * 手术类型-字典值ID
     */
    private Long operationTypeDicId;
    /**
     * 手术类型-字典值描述
     */
    private String operationTypeDesc;
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
     * 离体状态 0-未离体 1-已离体
     */
    private Integer separationStatus;
    /**
     * 固定状态 0-未固定 1-已固定
     */
    private Integer fixedStatus;
    /**
     * 物流状态 0-未送出 1-运送中 2-已签收 3-拒收
     */
    private Integer logisticsStatus;
    /**
     * 标签状态 0-未打印 1-已打印
     */
    private Integer tagStatus;
    /**
     * 拍照状态 0-未拍照 1-已拍照
     */
    private Integer photoStatus;
    /**
     * 固定时间
     */
    private Date fixedTime;
    /**
     * 固定液类型 存放字典值ID
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
     * 拒收原因
     */
    private String rejectReason;
    /**
     * 手术/门诊站点操作用户ID
     */
    private Long operationSiteUserId;
    /**
     * 手术/门诊站点操作时间
     */
    private Date operationSiteOperateTime;
    /**
     * 固定用户ID
     */
    private Long fixUserId;
    /**
     * 固定时间
     */
    private Date fixAt;
    /**
     * 固定站点ID
     */
    private Long fixSiteId;
    /**
     * 接收用户ID
     */
    private Long acceptUserId;
    /**
     * 接收时间
     */
    private Date acceptAt;
    /**
     * 接收站点ID
     */
    private Long acceptSiteId;
    /**
     * 作废原因
     */
    private String revokeReason;
    /**
     * 运送站点操作用户ID
     */
    private Long deliverySiteUserId;
    /**
     * 运送站点操作时间
     */
    private Date deliverySiteOperateTime;
    /**
     * 转运站点ID
     */
    private Long deliverySiteId;
    /**
     * 样本最后一次操作的站点ID 门诊站点/手术站点/预约站点/固定站点/转运站点/接收站点
     */
    private Long lastestSiteId;
    /**
     * 新样本编号-根据病理号重新生成的样本编号
     */
    private String newSampleNo;
    /**
     * 流程状态 1-待固定 2-待转运 3-待接收 4-已接收
     */
    private Integer flowStatus;
    /**
     * 来源站点ID
     */
    private Long siteId;
    /**
     * 退样标记 1-正常 2-退样中
     */
    private Integer returnFlag;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;
}
