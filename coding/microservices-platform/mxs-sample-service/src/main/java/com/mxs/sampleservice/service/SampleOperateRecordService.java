package com.mxs.sampleservice.service;

import com.mxs.common.util.UserContext;
import com.mxs.sampleservice.entity.SampleOperateRecord;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.enums.OperateRecordTypeEnum;

/**
 * 
 * 样本操作记录表 服务类
 *  Created by j.yang on 2019-08-20
 * 
 */
public interface SampleOperateRecordService extends IService<SampleOperateRecord> {
    void addLog(Long sampleId, String sampleNo, OperateRecordTypeEnum type, UserContext loginUser, Long siteId);
}
