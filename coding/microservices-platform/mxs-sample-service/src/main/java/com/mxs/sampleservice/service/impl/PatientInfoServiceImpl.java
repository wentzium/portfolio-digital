package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Maps;
import com.mxs.common.entity.TpExamMaster;
import com.mxs.common.entity.TpPatientInfo;
import com.mxs.common.util.*;
import com.mxs.sampleservice.bo.PatientCheckHisBO;
import com.mxs.sampleservice.bo.PatientInfoBO;
import com.mxs.sampleservice.bo.his.MedDocInfo;
import com.mxs.sampleservice.client.userservice.ThirdPartyServiceProxy;
import com.mxs.sampleservice.entity.PatientInfo;
import com.mxs.sampleservice.mapper.PatientInfoMapper;
import com.mxs.sampleservice.service.ApplyService;
import com.mxs.sampleservice.service.PatientInfoService;
import com.mxs.sampleservice.service.SampleService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 病人信息 服务实现类
 *  
 * Created by j.yang on 2019-08-29
 * 
 */
@Service
public class PatientInfoServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements PatientInfoService {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private ThirdPartyServiceProxy thirdPartyServiceProxy;
    @Autowired
    private HisServiceProxy hisServiceProxy;
    @Value("${his.docItemNum}")
    private Integer docItemNum;

    @Transactional
    @Override
    public Map<String, Object> sync(SyncPatientInfoReqVO reqVO) {
        // TODO 校验站点的合法性

        if (StringUtils.isEmpty(reqVO.getPatientNo())) {
            throw new BizException("病人编号不能为空");
        }
        UserContext loginUser = UserContextHolder.currentUser();
        Map<String, Object> resultMap = Maps.newHashMap();

        reqVO.setPatientNo(reqVO.getPatientNo().trim());
        PatientInfoBO bo = getFromHis(reqVO.getPatientNo());
        if (null == bo) {
            throw new BizException("未查询到病人信息");
        }

//        PatientInfo entity = this.selectOne(new EntityWrapper<PatientInfo>().eq("patient_no", reqVO.getPatientNo()));
//        if (null == entity) {
        // 不存在病人信息，新建
        PatientInfo entity = new PatientInfo();
        BeanUtils.copyProperties(bo, entity);

        // 默认值，先写死
        bo.setOperationSummary("");
        bo.setChiefComplaint("");
        bo.setSpecialPhysicalInspect("");
        bo.setImageInspect("");
        entity.setCreateBy(loginUser.getId().toString());
        entity.setCreateAt(new Date());
        entity.setUpdateAt(new Date());
        entity.setClinicalDiagnosis(bo.getClinicalDiagnosis());

        this.insert(entity);
        resultMap.put("patientInfo", bo);
//        } else {
//            // 已存在病人信息，更新
//            BeanUtils.copyProperties(bo, entity);
//
//            entity.setUpdateBy(loginUser.getId().toString());
//            entity.setUpdateAt(new Date());
//
//            this.updateById(entity);
//
//            PatientInfo newEntity = this.selectById(entity.getId());
//            PatientInfoBO newBo = new PatientInfoBO();
//            BeanUtils.copyProperties(newEntity, newBo);
//            if (null != entity.getDcOrSamplingDate()) {
//                newBo.setDcOrSamplingDate(DateTimeUtil.format(entity.getDcOrSamplingDate()));
//            }
//            if (null != entity.getTreatmentDate()) {
//                newBo.setTreatmentDate(DateTimeUtil.format(entity.getTreatmentDate()));
//            }
//            if (null != entity.getTumorDiscoveryDate()) {
//                newBo.setTumorDiscoveryDate(DateTimeUtil.format(entity.getTumorDiscoveryDate()));
//            }
//            resultMap.put("patientInfo", newBo);
//        }

        // 同步病人信息时，同时初始一条申请单信息
        InitApplyReqVO initApplyReqVO = new InitApplyReqVO();
        initApplyReqVO.setPatientNo(reqVO.getPatientNo());
        initApplyReqVO.setSiteId(reqVO.getSiteId());
        initApplyReqVO.setPatientId(entity.getId());
        String applyNo = applyService.initApply(initApplyReqVO);

        resultMap.put("applyNo", applyNo);

        return resultMap;
    }

    /**
     * 调用his系统获取病人信息
     *
     * @param patientNo
     * @return
     */
    private PatientInfoBO getFromHis(String patientNo) {
        TpPatientInfo patientInfo = thirdPartyServiceProxy.getPatientInfo(patientNo);
        if (null == patientInfo) {
            return null;
        }
        PatientInfoBO bo = new PatientInfoBO();
        bo.setPatientNo(patientInfo.getPatientNo());
        bo.setName(patientInfo.getName());
        bo.setSex(patientInfo.getSex());
        try {
            bo.setAge(null == patientInfo.getAge() ? 0 : Integer.parseInt(patientInfo.getAge()));
        } catch (Exception e) {
            bo.setAge(0);
        }
        bo.setMarriageStatus(patientInfo.getMarriageStatus());
        bo.setBirthPlace(patientInfo.getBirthPlace());
        bo.setOccupation(patientInfo.getOccupation());
        bo.setAddress(patientInfo.getAddress());
        bo.setInspectionHospital(patientInfo.getInspectionHospital());
        bo.setDepartment(patientInfo.getDepartment());
        bo.setOutpatientNo(patientInfo.getOutPatientNo());
        bo.setHospitalNo(patientInfo.getHospitalNo());
        bo.setSickroom(patientInfo.getSickroom());
        bo.setBedNo(patientInfo.getBedNo());
//        if (StringUtils.isNotEmpty(patientInfo.getDiagnosis())) {
//            bo.setMedicalHistorySummary(String.format("临床诊断：%s", patientInfo.getDiagnosis()));
//        }
        bo.setClinicalDiagnosis(patientInfo.getDiagnosis());

//        bo.setMedicalHistorySummary("发现颈前肿物");
//        bo.setPregnancy("妊次");
//        bo.setParity("产次");
//        bo.setLastPregnancy("末次妊娠");
//        bo.setMenstrualHistory("月经史");
//        bo.setFirstMenstruation("初经");
//        bo.setPeriod("周期");
//        bo.setPreMenstrual("前次月经");
//        bo.setLastMenstrual("末次月经");
//        bo.setEndocrineTherapyFlag(1);
//        bo.setTreatmentDate(DateTimeUtil.format(new Date()));
//        bo.setDose("剂量");
//        bo.setDcOrSamplingDate(DateTimeUtil.format(new Date()));
//        bo.setTumorSite("肿瘤部位");
//        bo.setTumorSizeAndShape("肿瘤大小形状");
//        bo.setActivityDegree("活动度");
//        bo.setTumorGrowthRate("肿瘤生长速度");
//        bo.setFirmness("肿瘤坚度");
//        bo.setTumorDiscoveryDate(DateTimeUtil.format(new Date()));
//        bo.setTransferLocation("转移位置");

        return bo;
    }

    @Override
    public List<PatientCheckHisBO> searchCheckHis(SearchPatientCheckHisReqVO reqVO) {
        List<TpExamMaster> list = thirdPartyServiceProxy.findExamMasterByPatientId(reqVO.getPatientNo().trim());
        return StreamUtil.getStream(list).map(item -> {
            PatientCheckHisBO bo = new PatientCheckHisBO();
            bo.setPatientNo(item.getPatientId());
            bo.setCheckItem(String.format("%s-%s-%s", item.getExamClass(), item.getExamSubClass(), StrUtil.toStr(item.getClinDiag())));
            bo.setClinSymp(item.getClinSymp());
            bo.setExamClass(item.getExamClass());
            bo.setExamSubClass(item.getExamSubClass());
            bo.setRelevantDiag(item.getRelevantDiag());
            bo.setClinDiag(item.getClinDiag());
            bo.setVisitId(null == item.getVisitId() ? "0" : item.getVisitId().toString());
            bo.setVisitTime(DateTimeUtil.format(item.getExamDateTime()));
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取病历文档信息列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<MedDocInfo> getDocInfoList(GetDocInfoListFromHisReqVO reqVO) {
        return hisServiceProxy.getDocInfos(reqVO);
    }

    /**
     * 获取his系统病历检查结果
     *
     * @param reqVO
     * @return
     */
    @Override
    public String getMedicalHistorySummary(ConvertToHtmlFromHistReqVO reqVO) {
        return hisServiceProxy.convertToHTML(reqVO);
    }

    @Override
    public List<String> getMedicalHistoryResultList(GetDocInfoListFromHisReqVO reqVO) {
        List<String> result = new ArrayList<>();
        List<MedDocInfo> docInfoList = hisServiceProxy.getDocInfos(reqVO);
        if (CollectionUtils.isNotEmpty(docInfoList)) {
            for (int i = 0; i < docInfoList.size() && i < docItemNum; i++) {
                MedDocInfo docInfo = docInfoList.get(i);
                ConvertToHtmlFromHistReqVO p = new ConvertToHtmlFromHistReqVO();
                p.setPatientNo(docInfo.getPatientID());
                p.setVisitId(docInfo.getVisitID());
                p.setDocId(docInfo.getDocID());
                String htmlStr = hisServiceProxy.convertToHTML(p);
                if (StringUtils.isNotEmpty(htmlStr)) {
                    result.add(htmlStr);
                }
            }
        }
        return result;
    }
}
