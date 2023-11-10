package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.UserContext;
import com.mxs.sampleservice.entity.SampleOperateRecord;
import com.mxs.sampleservice.enums.OperateRecordTypeEnum;
import com.mxs.sampleservice.mapper.SampleOperateRecordMapper;
import com.mxs.sampleservice.service.SampleOperateRecordService;
import com.mxs.sampleservice.util.BizException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 样本操作记录表 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
@Service
public class SampleOperateRecordServiceImpl extends ServiceImpl<SampleOperateRecordMapper, SampleOperateRecord> implements SampleOperateRecordService {
    @Override
    public void addLog(Long sampleId, String sampleNo, OperateRecordTypeEnum type, UserContext loginUser, Long siteId) {
        if (null == type) {
            throw new BizException("操作类型无效");
        }
        if (null == loginUser) {
            throw new BizException("操作用户不能为空");
        }
        if (null == siteId) {
            throw new BizException("操作站点ID不能为空");
        }
        SampleOperateRecord log = new SampleOperateRecord();
        log.setSampleId(sampleId);
        log.setSampleNo(sampleNo);
        log.setOperateType(type.getCode());
        log.setOperateDesc(String.format("%s %s %s", type.getDesc(), "护士", loginUser.getNameCn()));
        log.setSiteId(siteId);
        log.setCreateBy(loginUser.getId().toString());
        log.setCreateAt(new Date());
        this.insert(log);
    }

}
