package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * Created by j.yang on 2019-07-13
 */
@Data
public class PatientCheckHisBO {
    /**
     * 病人编号（第三方）
     */
    private String patientNo;
    /**
     * 检查项目
     */
    private String checkItem;
    /**
     * 访问ID
     */
    private String visitId;
    /**
     * 访问时间
     */
    private String visitTime;
    /**
     * 主诉
     */
    private String clinSymp;
    /**
     * 检查项目
     */
    private String examClass;
    /**
     * 检查名称
     */
    private String examSubClass;
    /**
     * 检查结果
     */
    private String relevantDiag;

    /**
     *
     */
    private String clinDiag;
}
