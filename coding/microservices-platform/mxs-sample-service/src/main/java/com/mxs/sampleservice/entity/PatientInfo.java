package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-07-15
 */

@Data
@Accessors(chain = true)
@TableName("t_patient_info")
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 病人编号
     */
    private String patientNo;
    /**
     * 病人姓名
     */
    private String name;
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
     * 籍贯
     */
    private String birthPlace;
    /**
     * 职业
     */
    private String occupation;
    /**
     * 地址
     */
    private String address;
    /**
     * 送检医院
     */
    private String inspectionHospital;
    /**
     * 科别
     */
    private String department;
    /**
     * 门诊号
     */
    private String outpatientNo;
    /**
     * 住院号
     */
    private String hospitalNo;
    /**
     * 病房
     */
    private String sickroom;
    /**
     * 病床
     */
    private String bedNo;
    /**
     * 病史摘要及临床检查所见
     */
    private String medicalHistorySummary;
    /**
     * 手术名称及手术所见
     */
    private String operationSummary;
    /**
     * 临床诊断（丢弃）
     */
    private String clinicalDiagnosis;
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
    private Date treatmentDate;
    /**
     * 剂量
     */
    private String dose;
    /**
     * 刮宫或采样日期
     */
    private Date dcOrSamplingDate;
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
    private Date tumorDiscoveryDate;
    /**
     * 转移位置
     */
    private String transferLocation;
    /**
     * 手术名称-字典值ID
     */
    private Long operationId;
    /**
     * 手术名称描述
     */
    private String operationName;
    /**
     * 主诉
     */
    private String chiefComplaint;
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
     * 是否曾接受病理检查 1-是 0-否
     */
    private int hasPathologyCheck;
    /**
     * 检查历史json字符串
     */
    private String checkHistory;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
