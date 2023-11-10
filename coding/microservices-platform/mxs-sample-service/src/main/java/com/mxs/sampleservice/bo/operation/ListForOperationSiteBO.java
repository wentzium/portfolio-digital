package com.mxs.sampleservice.bo.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 手术站点-默认列表业务对象
 * Created by j.yang on 2019-07-13
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListForOperationSiteBO {
    /**
     * 病人编号（外部系统）
     */
    private String patientNo;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 申请单数量
     */
    private Integer applyNum;
}
