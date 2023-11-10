package com.mxs.common.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author j.yang
 * @version 1.0.0
 * @ClassName TpExamItem.java
 * @Description TODO
 * @createTime 2019/08/17/ 13:48:16
 */
@Data
public class TpExamItems {
    private String examNo;
    // 留空
    private Integer examItemNo;
    //  EXAM_RPT_PATTERN表中的DESCRIPTION
    private String examItem;
    //  EXAM_RPT_PATTERN表中的DESCRIPTION_CODE
    private String examItemCode;
    // 留空
    private BigDecimal costs;
    // EXAM_RPT_PATTERN表中的EXAM_SUBCLASS,;
    private String examSubClass;
}
