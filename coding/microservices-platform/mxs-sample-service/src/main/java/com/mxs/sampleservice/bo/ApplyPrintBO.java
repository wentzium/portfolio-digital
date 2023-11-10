package com.mxs.sampleservice.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 申请单打印业务参数
 * Created by j.yang on 2019-07-13
 */
@Data
public class ApplyPrintBO {
    /**
     * 申请单号
     */
    private String applyNo;
    /**
     * 病理号
     */
    private String pathologyNo;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 婚姻状态
     */
    private String marriageStatus;
    /**
     * 职业
     */
    private String occupation;
    /**
     * 门诊号
     */
    private String outpatientNo;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 科别
     */
    private String department;
    /**
     * 病房
     */
    private String sickroom;
    /**
     * 病床
     */
    private String bedNo;
    /**
     * 送检医院
     */
    private String inspectionHospital;
    /**
     * 送检日期
     */
    private String inspectionDate;
    /**
     * 收到日期
     */
    private String receiveDate;
    /**
     * 联系信息
     */
    private String concat;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 接收员
     */
    private String receiver;
    /**
     * 病史摘要及临床检查所见
     */
    private String medicalHistorySummary;
    /**
     * 影像检查
     */
    private String imageInspect;
    /**
     * 实验室检查
     */
    private String laboratoryInspect;
    /**
     * 手术名称及手术所见
     */
    private String operationSummary;
    /**
     * 手术名称
     */
    private String operationName;
    /**
     * 肿瘤部位
     */
    private String tumorSite;
    /**
     * 肿瘤大小形状
     */
    private String tumorSizeAndShape;
    /**
     * 肿瘤发现日期
     */
    private String tumorDiscoveryDate;
    /**
     * 转移位置
     */
    private String transferLocation;
    /**
     * 是否接受放疗
     */
    private String hasRadiotherapy;
    /**
     * 是否接受化疗
     */
    private String hasChemotherapy;
    /**
     * 经期持续时间
     */
    private String menstrualDuration;
    /**
     * 末次月经
     */
    private String lastMenstrual;
    /**
     * 刮宫或采样日期
     */
    private String dcOrSamplingDate;
    /**
     * 激素治疗日期及剂量
     */
    private String HRTDateAndDose;
    /**
     * 避孕药名称
     */
    private String contraceptiveName;
    /**
     * 避孕日期
     */
    private String contraceptiveDate;
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
     * 小便或血hCG
     */
    private String HCG;
    /**
     * 宫内节育器
     */
    private String IUD;
    /**
     * 检查单位（过去）
     */
    private String overInspectionUnit;
    /**
     * 检查日期（过去）
     */
    private String overInspectionDate;
    /**
     * 病理号（过去）
     */
    private String overPathologyNo;
    /**
     * 病理诊断（过去）
     */
    private String overPathologyDiagnosis;
    /**
     * 临床诊断（映像）
     */
    private String clinicalDiagnosis;
    /**
     * 送检目的
     */
    private String inspectionPurpose;

    List<SamplePrintBO> sampleList;
    /**
     * 备注（页脚）
     */
    private String remark;
    /**
     * 送检医生（页脚）
     */
    private String inspectionDoctor;
    /**
     * 打印人
     */
    private String printUser;
    /**
     * 打印时间
     */
    private String printDate;
    /**
     * 申请单条形码图片base64字符串
     */
    private String imgBase64;
    /**
     * 地址
     */
    private String address;
    /**
     * 主诉
     */
    private String chiefComplaint;
    /**
     * 检查项目
     */
    private String inspectItem;
    /**
     * 检查结果
     */
    private String inspectResult;
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
     * 固定护士
     */
    private String fixedBy;
}
