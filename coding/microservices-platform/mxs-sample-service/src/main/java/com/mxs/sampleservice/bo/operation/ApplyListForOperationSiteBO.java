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
public class ApplyListForOperationSiteBO {
    /**
     * 申请单编号
     */
    private String applyNo;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 是否已打印
     */
    private boolean hasPrinted;
}
