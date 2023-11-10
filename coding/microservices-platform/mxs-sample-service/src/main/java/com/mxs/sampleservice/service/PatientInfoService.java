package com.mxs.sampleservice.service;

import com.mxs.sampleservice.bo.PatientCheckHisBO;
import com.mxs.sampleservice.bo.PatientInfoBO;
import com.mxs.sampleservice.bo.his.MedDocInfo;
import com.mxs.sampleservice.entity.PatientInfo;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.web.vo.ConvertToHtmlFromHistReqVO;
import com.mxs.sampleservice.web.vo.GetDocInfoListFromHisReqVO;
import com.mxs.sampleservice.web.vo.SearchPatientCheckHisReqVO;
import com.mxs.sampleservice.web.vo.SyncPatientInfoReqVO;

import java.util.List;
import java.util.Map;

/**
 * 病人信息 服务类
 *  Created by j.yang on 2019-08-22
 * 
 */
public interface PatientInfoService extends IService<PatientInfo> {
    /**
     * 同步病人信息
     *
     * @param reqVO
     * @return
     */
    Map<String, Object> sync(SyncPatientInfoReqVO reqVO);

    /**
     * 查询病人检查历史
     *
     * @param reqVO
     * @return
     */
    List<PatientCheckHisBO> searchCheckHis(SearchPatientCheckHisReqVO reqVO);

    /**
     * 获取病历文档信息列表
     *
     * @param reqVO
     */
    List<MedDocInfo> getDocInfoList(GetDocInfoListFromHisReqVO reqVO);

    /**
     * 获取his系统病历检查结果
     *
     * @param reqVO
     * @return
     */
    String getMedicalHistorySummary(ConvertToHtmlFromHistReqVO reqVO);

    List<String> getMedicalHistoryResultList(GetDocInfoListFromHisReqVO reqVO);
}
