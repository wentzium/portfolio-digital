package com.mxs.sampleservice.mapper;

import com.mxs.sampleservice.entity.CommSequence;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 公共序列表 Mapper 接口
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
public interface CommSequenceMapper extends BaseMapper<CommSequence> {
    Long getCurrentValue(String sequenceName);

    Long getNextValue(String sequenceName);
}
