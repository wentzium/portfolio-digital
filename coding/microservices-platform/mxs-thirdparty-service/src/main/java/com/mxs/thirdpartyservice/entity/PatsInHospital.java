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
 * @since 2019-08-24
 */
@Data
@Accessors(chain = true)
@TableName("INPADM.PATS_IN_HOSPITAL")
public class PatsInHospital implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientId;
    private Integer visitId;
    private String wardCode;
    private String deptCode;
    private Integer bedNo;
    private Date admissionDateTime;
    private Date admWardDateTime;
    private String diagnosis;
    private String patientCondition;
    private String nursingClass;
    private String doctorInCharge;
    private Date operatingDate;
    private Date billingDateTime;
    private Double prepayments;
    private Double totalCosts;
    private Double totalCharges;
    private String guarantor;
    private String guarantorOrg;
    private String guarantorPhoneNum;
    private Integer settledIndicator;
    private Date billCheckedDateTime;
    private Integer tsPersons;
    private Double payFromAccount;
    private Double payFromFund;
    private Double payFromInsured;
    private Double payWay;
    private String deptInCharge;
    private Date startDateTime;
    private Long frequencyNurse;
    private String bedLabel;
    private String nurseInCharge;
    private Integer isAccept;


}
