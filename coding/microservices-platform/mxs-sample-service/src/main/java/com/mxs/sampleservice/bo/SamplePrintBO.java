package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * Created by j.yang on 2019-07-13
 */
@Data
public class SamplePrintBO {
//    private int sortNum;
    private String sampleNo;
    private String sampleType;
    private String sampleName;
    private String sampleLocation;
    private String separationTime;
    private String fixedTime;
    /**
     * 样本组织块数
     */
    private Integer num;
}
