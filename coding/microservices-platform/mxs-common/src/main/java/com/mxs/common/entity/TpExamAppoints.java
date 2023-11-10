package com.mxs.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TpExamAppoints {
    private String examNo;
    private String localIdClass = "P";
    private String patientLocalId;
    private String patientId;
    private Long visitId;
    private String name;
    private String sex;
    private Date dateOfBirth;
    private String examClass = "病理";
    private String examSubClass;
    private String clinSymp;
    private String physSign;
    private String relevantLabTest;
    private String relevantDiag;
    private String clinDiag;
    private String examGroup = "病理科";
    private String performedBy = "4101";
    private String patientSource = "2";
    private String facility;
    private Date reqDateTime;
    private String reqDept;
    private String reqPhysician;
    private String reqMemo;
    private String technician;
    // 通信地址
    private String mailing_address;
}
