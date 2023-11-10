package com.mxs.sampleservice.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class CheckItemVO {
    private String examClass;
    private String examSubClass;
    private String descItem;
    private String descName;
    private String description;
    private String descriptionCode;
    private String inputCode;
    private int flag;
}
