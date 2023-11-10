package com.mxs.sampleservice.service.impl;

import com.mxs.sampleservice.entity.PatientHistoryInfo;
import com.mxs.sampleservice.mapper.PatientHistoryInfoMapper;
import com.mxs.sampleservice.service.PatientHistoryInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 
 * 病人历史信息 服务实现类
 * 
 * Created by j.yang on 2019-08-27
 * 
 */
@Service
public class PatientHistoryInfoServiceImpl extends ServiceImpl<PatientHistoryInfoMapper, PatientHistoryInfo> implements PatientHistoryInfoService {

}
