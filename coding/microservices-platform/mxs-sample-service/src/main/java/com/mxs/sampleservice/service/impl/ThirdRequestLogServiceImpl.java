package com.mxs.sampleservice.service.impl;

import com.mxs.sampleservice.entity.ThirdRequestLog;
import com.mxs.sampleservice.mapper.ThirdRequestLogMapper;
import com.mxs.sampleservice.service.ThirdRequestLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方接口请求日志表 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
@Service
public class ThirdRequestLogServiceImpl extends ServiceImpl<ThirdRequestLogMapper, ThirdRequestLog> implements ThirdRequestLogService {

}
