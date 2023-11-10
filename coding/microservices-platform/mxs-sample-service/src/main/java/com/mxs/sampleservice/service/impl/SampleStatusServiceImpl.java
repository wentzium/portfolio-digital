package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.UserContext;
import com.mxs.common.util.UserContextHolder;
import com.mxs.sampleservice.entity.SampleStatus;
import com.mxs.sampleservice.enums.SampleStatusValueEnum;
import com.mxs.sampleservice.mapper.SampleStatusMapper;
import com.mxs.sampleservice.service.SampleStatusService;
import com.mxs.sampleservice.util.BizException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 * Created by j.yang on 2019-08-19
 * 
 */

@Service
public class SampleStatusServiceImpl extends ServiceImpl<SampleStatusMapper, SampleStatus> implements SampleStatusService {
    @Override
    public void add(Long sampleId, SampleStatusValueEnum statusValue) {
        if (null == sampleId) {
            throw new BizException("样本编号不能为空");
        }
        if (null == statusValue) {
            throw new BizException("样本状态值不能为空");
        }

        UserContext loginUser = UserContextHolder.currentUser();

        SampleStatus entity = new SampleStatus();
        entity.setSampleId(sampleId);
        entity.setStatusValue(statusValue.getCode());
        entity.setCreateAt(new Date());
        entity.setCreateBy(loginUser.getId().toString());

        this.insert(entity);
    }
}
