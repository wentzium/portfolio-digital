package com.mxs.sampleservice.service;

import com.mxs.sampleservice.entity.SampleStatus;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.enums.SampleStatusValueEnum;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-19
 */
public interface SampleStatusService extends IService<SampleStatus> {
    /**
     * 添加样本状态值
     *
     * @param sampleId
     * @param statusValue
     */
    void add(Long sampleId, SampleStatusValueEnum statusValue);
}
