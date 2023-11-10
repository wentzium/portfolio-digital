package com.mxs.sampleservice.web.vo;

import com.mxs.sampleservice.bo.PatientCheckHisBO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 保存申请单请求信息
 *  Created by j.yang on 2019-06-14
 * 
 */
@Data
public class SaveApplyInfoReqVO {
    /**
     * 申请单号（无-新增，反之-修改）
     */
    private String applyNo;
    /**
     * 门诊/手术 站点ID
     */
    private Long siteId;
    /**
     * 病史摘要及临床检查所见
     */
    private String medicalHistorySummary;
    /**
     * 手术名称及手术所见
     */
    private String operationSummary;
    /**
     * 妊次
     */
    private String pregnancy;
    /**
     * 产次
     */
    private String parity;
    /**
     * 末次妊娠
     */
    private String lastPregnancy;
    /**
     * 月经史
     */
    private String menstrualHistory;
    /**
     * 初经
     */
    private String firstMenstruation;
    /**
     * 周期
     */
    private String period;
    /**
     * 前次月经
     */
    private String preMenstrual;
    /**
     * 末次月经
     */
    private String lastMenstrual;
    /**
     * 是否内分泌治疗 0-否 1-是
     */
    private Integer endocrineTherapyFlag;
    /**
     * 治疗日期
     */
    private String treatmentDate;
    /**
     * 剂量
     */
    private String dose;
    /**
     * 刮宫或采样日期
     */
    private String dcOrSamplingDate;
    /**
     * 肿瘤部位
     */
    private String tumorSite;
    /**
     * 肿瘤大小形状
     */
    private String tumorSizeAndShape;
    /**
     * 活动度
     */
    private String activityDegree;
    /**
     * 肿瘤生长速度
     */
    private String tumorGrowthRate;
    /**
     * 肿瘤坚度
     */
    private String firmness;
    /**
     * 肿瘤发现日期
     */
    private String tumorDiscoveryDate;
    /**
     * 转移位置
     */
    private String transferLocation;
    /**
     * 主诉
     */
    private String clinSymp;
    /**
     * 专科查体
     */
    private String specialPhysicalInspect;
    /**
     * 影像检查
     */
    private String imageInspect;
    /**
     * 检查项目
     */
    private String examClass;
    /**
     * 检查项目名称
     */
    private String examSubClass;
    /**
     * 检查结果
     */
    private String relevantDiag;
    /**
     * 手术名称-字典值ID
     */
    private Long operationId;
    /**
     * 手术名称描述
     */
    private String operationName;
    /**
     * 送检医院
     */
    private String inspectionHospital;
    /**
     * 是否曾接受病理检查 1-是 0-否
     */
    private int hasPathologyCheck;
    /**
     * 检查项目列表
     */
    private List<CheckItemVO> checkItemList;
    /**
     * 检查历史
     */
    private PatientCheckHisVO checkHistory;
}
