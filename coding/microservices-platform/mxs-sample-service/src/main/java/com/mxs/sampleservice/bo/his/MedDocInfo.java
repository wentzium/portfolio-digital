package com.mxs.sampleservice.bo.his;

import lombok.Data;

/**
 * Created by j.yang on 2019-07-09
 */
@Data
public class MedDocInfo {
    private String DefaultTime;
    private String DocID;
    private String DocTypeID;
    private String DocTitle;
    private String DocTime;
    private String DocSetID;
    private String DocVersion;
    private String CreatorID;
    private String CreatorName;
    private String ModifierID;
    private String ModifierName;
    private String PatientID;
    private String PatientName;
    private String VisitID;
    private String VisitTime;
    private String VisitType;
    private String DeptCode;
    private String DeptName;
    private String SignCode;
    private String ConfidCode;
    private String OrderValue;
    private String NeedCombin;
    private String FileType;
    private String FileName;
    private String FilePath;
    private String RecordTime;
    private String StatusDesc;
    private String RequestSignDate;
    private String ParentSignDate;
    private String SuperSignDate;
}
