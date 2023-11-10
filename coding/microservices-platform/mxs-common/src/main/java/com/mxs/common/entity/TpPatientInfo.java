package com.mxs.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TpPatientInfo {

    private String patientNo;
    private String name;
    private String sex;
    private String age;
    private String marriageStatus;
    private String birthPlace;
    private String occupation;
    private String address;
    private String inspectionHospital;
    private String department;
    private String outPatientNo;
    private String sickroom;
    private String hospitalNo;
    private String bedNo;
    private Date lastVisitDate;
    private String diagnosis;
}
