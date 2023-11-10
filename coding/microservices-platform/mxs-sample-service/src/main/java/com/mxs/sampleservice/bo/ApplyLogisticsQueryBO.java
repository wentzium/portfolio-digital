package com.mxs.sampleservice.bo;

import lombok.Data;

import java.util.List;

/**
 * 物流查询对象
 * Created by j.yang on 2019-07-13
 */
@Data
public class ApplyLogisticsQueryBO {
    /**
     * 申请单列表
     */
    private List<ApplyBO> applyList;
    /**
     * 申请单详情
     */
    private ApplyDetailBO applyDetail;
    /**
     * 样本详情
     */
    private SampleBO sample;
}
