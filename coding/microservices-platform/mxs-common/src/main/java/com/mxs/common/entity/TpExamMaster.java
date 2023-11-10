package com.mxs.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TpExamMaster {

   private Date examDateTime;
   private Long visitId;
   private String patientLocalId;

   private String patientId;

   private String examClass;
   private String examSubClass;
   private String clinSymp;
   private String relevantDiag;

   private String clinDiag;
}
