package com.mxs.sampleservice.web.vo;

import lombok.Data;

/**
 * 样本标签打印通知请求参数
 * Created by C.SY on 2019/6/12.
 */
@Data
public class SampleTagPrintedNotifyReqVO {
    /**
     * 操作站点ID
     */
    private Long siteId;
    /**
     * 样本编号
     */
    private String sampleNo;
}
