package com.mxs.thirdpartyservice.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-22
 */
@Data
@Accessors(chain = true)
@TableName("EXAM.EXAM_APPOINTS")
public class ExamAppoints implements Serializable {

    private static final long serialVersionUID = 1L;

    private String examNo;
    private String localIdClass;
    private String patientLocalId;
    private String patientId;
    private Integer visitId;
    private String name;
    private String sex;
    private Date dateOfBirth;
    private String examClass;
    private String examSubClass;
    private String clinSymp;
    private String physSign;
    private String relevantLabTest;
    private String relevantDiag;
    private String clinDiag;
    private String examGroup;
    private String performedBy;
    private String patientSource;
    private String facility;
    private Date reqDateTime;
    private String reqDept;
    private String reqPhysician;
    private String reqMemo;
    private String technician;


}
