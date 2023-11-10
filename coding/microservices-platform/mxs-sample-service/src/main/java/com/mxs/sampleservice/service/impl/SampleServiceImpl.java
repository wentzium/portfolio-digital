package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.*;
import com.mxs.sampleservice.bo.*;
import com.mxs.sampleservice.client.userservice.UserServiceProxy;
import com.mxs.sampleservice.entity.*;
import com.mxs.sampleservice.enums.*;
import com.mxs.sampleservice.mapper.SampleMapper;
import com.mxs.sampleservice.service.*;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.util.NumberUtil;
import com.mxs.sampleservice.web.vo.*;
import com.mxs.sampleservice.web.vo.admin.SaveDicItemReqVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 样本信息 服务实现类
 *  
 * Created by j.yang on 2019-09-02
 * 
 */

@Service
public class SampleServiceImpl extends ServiceImpl<SampleMapper, Sample> implements SampleService {
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PatientInfoService patientInfoService;
    @Autowired
    private UserServiceProxy userServiceProxy;
    @Autowired
    private SiteMapService siteMapService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private SampleOperateRecordService sampleOperateRecordService;
    @Autowired
    private TransferRelationService transferRelationService;
    @Autowired
    private SampleStatusService sampleStatusService;
    @Autowired
    private DicService dicService;
    @Autowired
    private DicItemService dicItemService;

    /**
     * 获取样本编号
     *
     * @param applyNo
     * @return
     */
    @Override
    public String getSampleNo(String applyNo) {
        String no = sequenceService.getNextStringValue(applyNo);
        return String.format("%s_%s", applyNo, no);
    }

    /**
     * 新建/保存样本信息
     *
     * @param reqVO
     * @return
     */
    @Transactional
    @Override
    public String saveSampleInfo(SaveSampleInfoReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        Apply apply = applyService.selectOne(new EntityWrapper<Apply>().eq("apply_no", reqVO.getApplyNo()));
        if (null == apply) {
            throw new BizException("申请单不存在");
        }

        if (StringUtils.isNotEmpty(reqVO.getOperationTypeDesc()) && NumberUtil.nullDefault(reqVO.getOperationTypeDicId(), 0L) <= 0) {
            // 用户选择其他时输入的手术类型，需要加入字典值列表
            Dic dic = dicService.selectOne(new EntityWrapper<Dic>().eq("item_code", ItemKeyEnum.OPERATION_TYPE.getCode()));
            SaveDicItemReqVO saveDicItemReqVO = new SaveDicItemReqVO();
            saveDicItemReqVO.setDicId(dic.getId());
            saveDicItemReqVO.setItemName(reqVO.getOperationTypeDesc());
            long dicItemId = dicService.save(saveDicItemReqVO);
            reqVO.setOperationTypeDicId(dicItemId);
        }
        String sampleNo = "";
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            // 新建
            Sample sample = new Sample();
            BeanUtils.copyProperties(reqVO, sample);

            if (StringUtils.isNotEmpty(reqVO.getSeparationTime())) {
                sample.setSeparationTime(DateTimeUtil.strToDate(reqVO.getSeparationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
            }

            sample.setApplyNo(apply.getApplyNo());
            sample.setApplyId(apply.getId());
            sample.setPatientId(apply.getPatientId());
            sample.setPatientNo(apply.getPatientNo());
            sample.setLastestSiteId(apply.getSiteId());
            String no = sequenceService.getNextStringValue(apply.getApplyNo(), 2, '0');
            sample.setSampleNo(String.format("%s_%s", apply.getApplyNo(), no));
            sampleNo = sample.getSampleNo();
            sample.setCreateBy(loginUser.getId().toString());
            sample.setCreateAt(new Date());
            sample.setUpdateAt(new Date());
            sample.setPhotoStatus(CollectionUtils.isEmpty(reqVO.getImgList()) ? 0 : 1);
            sample.setFixedStatus(FixedStatusEnum._0.getCode());
            sample.setFlowStatus(SampleFlowStatusEnum._1.getCode());
            sample.setOperationSiteOperateTime(new Date());
            sample.setSeparationStatus(SeparationStatusEnum._1.getCode());
            sample.setSeparationTime(new Date());
            sample.setOperationSiteUserId(loginUser.getId());

            // 固定时间不为空，则直接更新样本为 已固定，并且直接流转到接收站点
            if (StringUtils.isNotEmpty(reqVO.getFixedTime())) {
                sample.setFixedTime(DateTimeUtil.strToDate(reqVO.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
                sample.setFixedStatus(FixedStatusEnum._1.getCode());
                sample.setFlowStatus(SampleFlowStatusEnum._3.getCode());
                sample.setFixAt(new Date());
                sample.setFixUserId(loginUser.getId());
                sample.setFixSiteId(reqVO.getSiteId());
            }

            this.insert(sample);
            List<Image> imageList = Optional.of(reqVO.getImgList()).orElse(new ArrayList<>()).stream().map(item -> {
                Image image = new Image();
                image.setSampleNo(sample.getSampleNo());
                image.setSampleId(sample.getId());
                image.setUrl(item);
                image.setCreateAt(new Date());
                image.setUpdateAt(new Date());
                return image;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(imageList)) {
                imageService.insertBatch(imageList);
            }

            sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.SEPARATE, loginUser, reqVO.getSiteId());
            sampleStatusService.add(sample.getId(), SampleStatusValueEnum.NEW);
        } else {
            // 修改
            Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo()));
            if (null == sample) {
                throw new BizException("样本不存在");
            }
            if (null != sample.getFixedTime() && StringUtils.isEmpty(reqVO.getFixedTime())) {
                // 样本已固定，修改时固定时间必填
                throw new BizException("样本已固定，只能更新固定时间，不能清空");
            }

            sampleNo = reqVO.getSampleNo();
            BeanUtils.copyProperties(reqVO, sample);

            sample.setUpdateBy(loginUser.getId().toString());
            sample.setUpdateAt(new Date());
            sample.setPhotoStatus(CollectionUtils.isEmpty(reqVO.getImgList()) ? 0 : 1);

            // 固定时间不为空，则直接更新样本为 已固定，并且直接流转到接收站点
            if (StringUtils.isNotEmpty(reqVO.getFixedTime())) {
                sample.setFixedTime(DateTimeUtil.strToDate(reqVO.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
                sample.setFixedStatus(FixedStatusEnum._1.getCode());
                sample.setFlowStatus(SampleFlowStatusEnum._3.getCode());
                sample.setFixAt(new Date());
                sample.setFixUserId(loginUser.getId());
                sample.setFixSiteId(reqVO.getSiteId());
                // 退样状态重置
                sample.setReturnFlag(ReturnFlagEnum.NORMAL.getCode());
            }

            this.updateById(sample);

            List<Image> imageList = Optional.of(reqVO.getImgList()).orElse(new ArrayList<>()).stream().map(item -> {
                Image image = new Image();
                image.setSampleNo(sample.getSampleNo());
                image.setSampleId(sample.getId());
                image.setUrl(item);
                image.setCreateAt(new Date());
                image.setUpdateAt(new Date());
                return image;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(imageList)) {
                // 先删除样本图片，再插入
                imageService.delete(new EntityWrapper<Image>().eq("sample_id", sample.getId()));
                imageService.insertBatch(imageList);
            }
        }
        return sampleNo;
    }

    /**
     * 单个样本信息查询
     *
     * @param sampleNo
     * @return
     */
    @Override
    public SampleBO getBySampleNo(String sampleNo) {
        Sample sampleEntity = this.getSample(sampleNo);
        SampleBO bo = new SampleBO();
        BeanUtils.copyProperties(sampleEntity, bo);

        bo.setAvailableStatusDesc(AvailableStatusEnum.findByCode(sampleEntity.getAvailableStatus()).getDesc());
        bo.setSeparationStatusDesc(SeparationStatusEnum.findByCode(sampleEntity.getSeparationStatus()).getDesc());
        bo.setFixedStatusDesc(FixedStatusEnum.findByCode(sampleEntity.getFixedStatus()).getDesc());
        bo.setLogisticsStatusDesc(LogisticsStatusEnum.findByCode(sampleEntity.getLogisticsStatus()).getDesc());
        bo.setTagStatusDesc(TagStatusEnum.findByCode(sampleEntity.getTagStatus()).getDesc());
        bo.setPhotoStatusDesc(TagStatusEnum.findByCode(sampleEntity.getPhotoStatus()).getDesc());
        bo.setFixedTime(DateTimeUtil.format(sampleEntity.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        bo.setSeparationTime(DateTimeUtil.format(sampleEntity.getSeparationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        bo.setDeliveryTime(DateTimeUtil.format(sampleEntity.getDeliverySiteOperateTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        bo.setAcceptTime(DateTimeUtil.format(sampleEntity.getAcceptAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        bo.setReturnFlag(ReturnFlagEnum.RETURN.getCode() == sampleEntity.getReturnFlag());

        List<Image> imageList = imageService.selectList(new EntityWrapper<Image>().eq("sample_id", sampleEntity.getId()));
        bo.setImgList(StreamUtil.map(imageList, item -> item.getUrl()));

        PatientInfo patientInfoEntity = patientInfoService.selectById(sampleEntity.getPatientId());
        if (null == patientInfoEntity) {
            throw new BizException("未查询到样本关联的病人信息");
        }
        bo.setPatientNo(patientInfoEntity.getPatientNo());
        bo.setPatientName(patientInfoEntity.getName());
        bo.setSex(patientInfoEntity.getSex());
        bo.setDepartment(patientInfoEntity.getDepartment());
        LoginUser user = userServiceProxy.getByUserId(sampleEntity.getAcceptUserId());
        if (null != user) {
            bo.setAcceptUserName(user.getNameCn());
        }
        if (StringUtils.isNotEmpty(sampleEntity.getTypeDesc())) {
            bo.setIsFrozen(sampleEntity.getTypeDesc().contains("冰冻样品"));
        } else {
            bo.setIsFrozen(false);
        }

        // 查询样本物流信息
        bo.setLogisticsList(this.getLogistics(sampleNo));

        return bo;
    }

    /**
     * 查询申请单下的样本列表
     *
     * @param applyNo
     * @return
     */
    @Override
    public List<SampleBO> listByApplyNo(String applyNo) {
        List<Sample> sampleList = this.selectList(new EntityWrapper<Sample>().eq("apply_no", applyNo).eq("is_deleted", 0));

        return StreamUtil.map(sampleList, sampleEntity -> {
            SampleBO bo = new SampleBO();
            BeanUtils.copyProperties(sampleEntity, bo);
            // 目前只给基本信息
            bo.setAvailableStatusDesc(AvailableStatusEnum.findByCode(sampleEntity.getAvailableStatus()).getDesc());
            bo.setSeparationStatusDesc(SeparationStatusEnum.findByCode(sampleEntity.getSeparationStatus()).getDesc());
            bo.setFixedStatusDesc(FixedStatusEnum.findByCode(sampleEntity.getFixedStatus()).getDesc());
            bo.setLogisticsStatusDesc(LogisticsStatusEnum.findByCode(sampleEntity.getLogisticsStatus()).getDesc());
            bo.setTagStatusDesc(TagStatusEnum.findByCode(sampleEntity.getTagStatus()).getDesc());
            bo.setPhotoStatusDesc(TagStatusEnum.findByCode(sampleEntity.getPhotoStatus()).getDesc());
            bo.setFixedTime(DateTimeUtil.format(sampleEntity.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            bo.setSeparationTime(DateTimeUtil.format(sampleEntity.getSeparationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            bo.setDeliveryTime(DateTimeUtil.format(sampleEntity.getDeliverySiteOperateTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            bo.setAcceptTime(DateTimeUtil.format(sampleEntity.getAcceptAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            bo.setReturnFlag(ReturnFlagEnum.RETURN.getCode() == sampleEntity.getReturnFlag());
            if (StringUtils.isNotEmpty(sampleEntity.getTypeDesc())) {
                bo.setIsFrozen(sampleEntity.getTypeDesc().contains("冰冻样品"));
            } else {
                bo.setIsFrozen(false);
            }
            return bo;
        });
    }

    private Sample getSample(String sampleNo) {
        if (StringUtils.isEmpty(sampleNo)) {
            throw new BizException("样本编号不能为空");
        }
        Sample sampleEntity = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", sampleNo.trim()));
        if (null == sampleEntity) {
            throw new BizException("样本编号无效");
        }
        return sampleEntity;
    }

    /**
     * 作废样本
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public void cancelSample(CancelSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (CollectionUtils.isEmpty(reqVO.getSampleNoList())) {
            throw new BizException("样本编号列表不能为空");
        }
        if (StringUtils.isEmpty(reqVO.getReason())) {
            throw new BizException("作废原因不能为空");
        }

        for (String sampleNo : reqVO.getSampleNoList()) {
            Sample sample = this.getSample(sampleNo);
            if (null == sample) {
                continue;
            }
            Sample updateEntity = new Sample();
            updateEntity.setId(sample.getId());
            updateEntity.setAvailableStatus(AvailableStatusEnum._1.getCode());
            updateEntity.setRevokeReason(reqVO.getReason());
            updateEntity.setUpdateAt(new Date());
            updateEntity.setUpdateBy(loginUser.getId().toString());
            this.updateById(updateEntity);

            if (StringUtils.isNotEmpty(reqVO.getReason()) && NumberUtil.nullDefault(reqVO.getReasonDicId(), 0L) <= 0) {
                // 用户自己输入的原因，需要加入字典值列表
                Dic dic = dicService.selectOne(new EntityWrapper<Dic>().eq("item_code", ItemKeyEnum.REVOKE_REASON.getCode()));
                SaveDicItemReqVO saveDicItemReqVO = new SaveDicItemReqVO();
                saveDicItemReqVO.setDicId(dic.getId());
                saveDicItemReqVO.setItemName(reqVO.getReason());
                dicService.save(saveDicItemReqVO);
            }

            // 记录日志
            sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.CANCEL, loginUser, reqVO.getSiteId());
            // 记录状态值
            sampleStatusService.add(sample.getId(), SampleStatusValueEnum.CANCEL);
        }
        // TODO 通知第三方系统
    }

    /**
     * 查询固定站点样本列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public Page<SampleListForFixSiteBO> searchFixList(SearchFixListReqVO reqVO) {
        if (null == reqVO.getFixSiteId()) {
            throw new BizException("固定站点ID为空");
        }
        // 查询固定站点覆盖的门诊\手术站点
        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getFixSiteId()));
        if (CollectionUtils.isEmpty(mapSiteList)) {
            // 固定站点未覆盖任何门诊\手术站点，则无样本可固定
            return new Page<>();
        }
        if (CollectionUtils.isEmpty(reqVO.getMapSiteIdList())) {
            reqVO.setMapSiteIdList(mapSiteList.stream().map(item -> item.getToSiteId()).collect(Collectors.toList()));
        } else {
            // 选择的门诊\手术站点ID必须被映射到该固定站点
            reqVO.setMapSiteIdList(reqVO.getMapSiteIdList().stream().filter(item -> mapSiteList.contains(item)).collect(Collectors.toList()));
        }

        Page<SampleListForFixSiteBO> page = new Page<>(reqVO.getPage(), reqVO.getPageSize());
        List<SampleListForFixSiteBO> list = this.baseMapper.selectListForFixSite(page, reqVO);
        StreamUtil.getStream(list).forEach(item -> {
            item.setFixedStatusDesc(FixedStatusEnum.findByCode(item.getFixedStatus()).getDesc());
            LoginUser user = userServiceProxy.getByUserId(item.getOperateUserId());
            if (null != user) {
                item.setOperateUserNameCn(user.getNameCn());
            }
        });
        return page.setRecords(list);
    }

    /**
     * 固定样本 返回样本信息
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public SampleBO fixSample(FixedSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            throw new BizException("样本编号不能为空");
        }
        if (null == reqVO.getSiteId()) {
            throw new BizException("固定站点ID不能为空");
        }
        Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo().trim()));
        if (null == sample) {
            throw new BizException("样本不存在");
        }
        if (sample.getFixedStatus().equals(FixedStatusEnum._1.getCode())) {
            // 样本已固定，再次扫描直接返回样本信息，不做任何逻辑处理
            return this.getBySampleNo(sample.getSampleNo());
        }

        // 查询固定站点覆盖的门诊\手术站点
        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getSiteId()));
        if (!StreamUtil.getStream(mapSiteList).anyMatch(item -> item.getToSiteId().equals(sample.getSiteId()))) {
            // 扫描到的样本不属于前固定站点，直接返回样本详情，并笔记为不可修改
            SampleBO sampleBO = this.getBySampleNo(sample.getSampleNo());
            sampleBO.setCanModify(false);
            return sampleBO;
        }

        Sample updateEntity = new Sample();
        updateEntity.setId(sample.getId());

        updateEntity.setLogisticsStatus(LogisticsStatusEnum.SHIPPING.getCode());
        updateEntity.setFixSiteId(reqVO.getSiteId());
        updateEntity.setFixedTime(new Date());
        updateEntity.setFixAt(new Date());
        updateEntity.setFixUserId(loginUser.getId());
        updateEntity.setFixedStatus(FixedStatusEnum._1.getCode());
        // 目前无 转运站点，直接到 接收站点
//        updateEntity.setFlowStatus(SampleFlowStatusEnum._2.getCode());
        updateEntity.setFlowStatus(SampleFlowStatusEnum._3.getCode());
        // 重写退样标记
        updateEntity.setReturnFlag(ReturnFlagEnum.NORMAL.getCode());
        updateEntity.setFixativeType(reqVO.getFixativeType());
        updateEntity.setFixativeTypeDesc(reqVO.getFixativeTypeDesc());
        updateEntity.setSampleWeight(reqVO.getSampleWeight());
        updateEntity.setFixativeVolume(reqVO.getFixativeVolume());
        updateEntity.setTransferContainer(reqVO.getTransferContainer());
        updateEntity.setUpdateBy(loginUser.getId().toString());
        updateEntity.setUpdateAt(new Date());
        this.updateById(updateEntity);

        sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.FIX, loginUser, reqVO.getSiteId());

        sampleStatusService.add(sample.getId(), SampleStatusValueEnum.FIXED);

        return this.getBySampleNo(sample.getSampleNo());
    }

    /**
     * 查询转运站点样本列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public Page<SampleListForDeliverySiteBO> searchDeliveryList(SearchDeliveryListReqVO reqVO) {
        if (null == reqVO.getDeliverySiteId()) {
            throw new BizException("转运站点ID为空");
        }
        // 查询转运站点覆盖的固定站点
        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getDeliverySiteId()));
        if (CollectionUtils.isEmpty(mapSiteList)) {
            // 转运站点未覆盖任何固定站点，则无样本可转运
            return new Page<>();
        }
        if (CollectionUtils.isEmpty(reqVO.getMapSiteIdList())) {
            reqVO.setMapSiteIdList(mapSiteList.stream().map(item -> item.getToSiteId()).collect(Collectors.toList()));
        } else {
            // 选择的门诊\手术站点ID必须被映射到该固定站点
            reqVO.setMapSiteIdList(reqVO.getMapSiteIdList().stream().filter(item -> mapSiteList.contains(item)).collect(Collectors.toList()));
        }

        Page<SampleListForDeliverySiteBO> page = new Page<>(reqVO.getPage(), reqVO.getPageSize());
        List<SampleListForDeliverySiteBO> list = this.baseMapper.selectListForDeliverySite(page, reqVO);
        StreamUtil.getStream(list).forEach(item -> {
            LoginUser user = userServiceProxy.getByUserId(item.getFixUserId());
            if (null != user) {
                item.setFixUserNameCn(user.getNameCn());
            }
        });
        return page.setRecords(list);
    }

    /**
     * 查询多级转运站点样本列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public Page<SampleListForDeliverySiteBO> searchMultiDeliveryList(SearchMultiDeliveryListReqVO reqVO) {
        if (null == reqVO.getDeliverySiteId()) {
            throw new BizException("转运站点ID为空");
        }
        if (StringUtils.isEmpty(reqVO.getTransferNo())) {
            throw new BizException("转运箱编号不能为空");
        }
        List<TransferRelation> transferList = transferRelationService.selectList(new EntityWrapper<TransferRelation>().eq("transfer_no", reqVO.getTransferNo()));
        if (CollectionUtils.isEmpty(transferList)) {
            throw new BizException("该转运箱下没有样本信息");
        }

        Page<SampleListForDeliverySiteBO> page = new Page<>(reqVO.getPage(), reqVO.getPageSize());
        List<SampleListForDeliverySiteBO> list = this.baseMapper.selectListForMultiDeliverySite(page, StreamUtil.getStream(transferList).map(item -> item.getSampleNo()).collect(Collectors.toList()));
        StreamUtil.getStream(list).forEach(item -> {
            LoginUser user = userServiceProxy.getByUserId(item.getFixUserId());
            if (null != user) {
                item.setFixUserNameCn(user.getNameCn());
            }
        });
        return page.setRecords(list);
    }

    /**
     * 转运样本
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public void transferSample(TransferSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            throw new BizException("样本编号不能为空");
        }
        if (null == reqVO.getSiteId()) {
            throw new BizException("转运站点ID不能为空");
        }
        Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo().trim()));
        if (null == sample) {
            throw new BizException("样本不存在");
        }

        Sample updateEntity = new Sample();
        updateEntity.setId(sample.getId());

        updateEntity.setDeliverySiteId(reqVO.getSiteId());
        updateEntity.setDeliverySiteOperateTime(new Date());
        updateEntity.setDeliverySiteUserId(loginUser.getId());
        updateEntity.setFlowStatus(SampleFlowStatusEnum._3.getCode());
        // 重写退样标记
        updateEntity.setReturnFlag(ReturnFlagEnum.NORMAL.getCode());

        updateEntity.setUpdateBy(loginUser.getId().toString());
        updateEntity.setUpdateAt(new Date());
        this.updateById(updateEntity);

        TransferRelation relation = new TransferRelation();
        relation.setTransferNo(reqVO.getTransferNo());
        relation.setSampleId(sample.getId());
        relation.setSampleNo(sample.getSampleNo());
        relation.setTransferStatus(TransferStatusEnum._0.getCode());
        relation.setCreateAt(new Date());
        relation.setCreateBy(loginUser.getId().toString());
        transferRelationService.insert(relation);

        // 记录操作日志
        sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.TRANSFER, loginUser, reqVO.getSiteId());

        sampleStatusService.add(sample.getId(), SampleStatusValueEnum.TRANSFER);
    }

    /**
     * 获取病理号
     *
     * @param reqVO
     * @return
     */
    @Override
    public String getPathologyNo(GetPathologyNoReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            throw new BizException("样本编号不能为空");
        }
        Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo().trim()));
        if (null == sample) {
            throw new BizException("样本不存在");
        }
        // TODO 调用第三方接口获取病理号

        Apply apply = applyService.selectOne(new EntityWrapper<Apply>().eq("apply_no", sample.getApplyNo()));

        if (StringUtils.isNotEmpty(apply.getPathologyNo())) {
            return apply.getPathologyNo();
        }
        // 保存病理号和样本之间的关系
        // TODO 先临时获取
        String tempNo = String.format("%s%s", IdPrefixEnum.C.toString(), sequenceService.getNextStringValue(SequenceService.SEQ_pathology_NO, 8));
        Apply updateEntity = new Apply();
        updateEntity.setId(apply.getId());
        updateEntity.setPathologyNo(tempNo);
        updateEntity.setUpdateBy(loginUser.getId().toString());
        applyService.updateById(updateEntity);

        return tempNo;
    }

    /**
     * 查询接收站点样本列表
     *
     * @param reqVO
     * @return
     */
    @Override
    public Page<SampleListForReceiveSiteBO> searchReceiveList(SearchReceiveListReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("接收站点ID为空");
        }
        // 查询接收站点覆盖的转运站点集合
        // 接收站点目前全覆盖
//        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getSiteId()));
//        if (CollectionUtils.isEmpty(mapSiteList)) {
//            // 接收站点未覆盖任何转运站点，则无样本可转运
//            return new Page<>();
//        }

        Page<SampleListForReceiveSiteBO> page = new Page<>(reqVO.getPage(), reqVO.getPageSize());
        List<SampleListForReceiveSiteBO> list = this.baseMapper.selectListForReceiveSite(page, null);
        StreamUtil.getStream(list).forEach(item -> {
            LoginUser user = userServiceProxy.getByUserId(item.getReceiveUserId());
            if (null != user) {
                item.setReceiveUserNameCn(user.getNameCn());
            }
        });
        return page.setRecords(list);
    }

    /**
     * 拒收样本
     *
     * @param reqVO
     */
    @Override
    public void rejectSample(RejectSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            throw new BizException("样本编号不能为空");
        }
        if (null == reqVO.getSiteId()) {
            throw new BizException("接收站点ID不能为空");
        }
        if (StringUtils.isEmpty(reqVO.getReason())) {
            throw new BizException("拒收原因不能为空");
        }
        Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo().trim()));
        if (null == sample) {
            throw new BizException("样本不存在");
        }
        List<Sample> sampleList = this.selectList(new EntityWrapper<Sample>().eq("apply_no", sample.getApplyNo()));
        for (Sample s : sampleList) {
            // 未接收的 不处理
            if (s.getLogisticsStatus() != LogisticsStatusEnum.ACCEPT.getCode()) {
                continue;
            }
            Sample updateEntity = new Sample();
            updateEntity.setId(s.getId());

            updateEntity.setLogisticsStatus(LogisticsStatusEnum.REJECT.getCode());
            updateEntity.setRejectReason(reqVO.getReason());
            // 拒收也标记为 退样
            updateEntity.setReturnFlag(ReturnFlagEnum.RETURN.getCode());
            // 固定状态回退
            updateEntity.setFixedStatus(FixedStatusEnum._0.getCode());
            updateEntity.setFlowStatus(SampleFlowStatusEnum._1.getCode());
            updateEntity.setUpdateBy(loginUser.getId().toString());
            updateEntity.setUpdateAt(new Date());
            this.updateById(updateEntity);

            sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.REJECT, loginUser, reqVO.getSiteId());

            sampleStatusService.add(sample.getId(), SampleStatusValueEnum.REJECTION);
        }
    }

    /**
     * 接收样本
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public void receiveSample(ReceiveSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (StringUtils.isEmpty(reqVO.getSampleNo())) {
            throw new BizException("样本编号不能为空");
        }
        if (null == reqVO.getSiteId()) {
            throw new BizException("接收站点ID不能为空");
        }
        Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", reqVO.getSampleNo()));
        if (null == sample) {
            throw new BizException("样本不存在");
        }


        // 查询接收站点转运站点ID
//        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getSiteId()));
//        if (!StreamUtil.getStream(mapSiteList).anyMatch(item -> item.getToSiteId().equals(sample.getDeliverySiteId()))) {
//            // 扫描到的样本不属于前固定站点，直接返回样本详情，并笔记为不可修改
//            SampleBO sampleBO = this.getBySampleNo(sample.getSampleNo());
//            sampleBO.setCanModify(false);
//            throw new BizException("无接收权限");
//        }

        Sample updateEntity = new Sample();
        updateEntity.setId(sample.getId());

        updateEntity.setLogisticsStatus(LogisticsStatusEnum.ACCEPT.getCode());
        updateEntity.setFlowStatus(SampleFlowStatusEnum._4.getCode());
        updateEntity.setAcceptAt(new Date());
        updateEntity.setAcceptSiteId(reqVO.getSiteId());
        updateEntity.setAcceptUserId(loginUser.getId());

        updateEntity.setUpdateBy(loginUser.getId().toString());
        updateEntity.setUpdateAt(new Date());
        this.updateById(updateEntity);

        sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.ACCEPT, loginUser, reqVO.getSiteId());

        sampleStatusService.add(sample.getId(), SampleStatusValueEnum.ACCEPT);
    }

    /**
     * 查询样本物流信息
     *
     * @param sampleNo
     * @return
     */
    @Override
    public List<SampleLogisticsBO> getLogistics(String sampleNo) {
        if (StringUtils.isEmpty(sampleNo)) {
            throw new BizException("样本编号不能为空");
        }
        List<SampleOperateRecord> list = sampleOperateRecordService.selectList(new EntityWrapper<SampleOperateRecord>().eq("sample_no", sampleNo.trim()));
        return StreamUtil.getStream(list).map(item -> {
            SampleLogisticsBO bo = new SampleLogisticsBO();
            bo.setDesc(item.getOperateDesc());
            bo.setTime(DateTimeUtil.format(item.getCreateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 修改样本固定信息
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public void updateFixedInfo(FixedSampleReqVO reqVO) {
        Sample sample = this.getSample(reqVO.getSampleNo());

        UserContext loginUser = UserContextHolder.currentUser();
        // 只有样本固定人才能修改固定内容
        if (sample.getFixUserId() != loginUser.getId()) {
            throw new BizException("您无权修改该样本的固定信息");
        }

        Sample updateEntity = new Sample();
        updateEntity.setId(sample.getId());

        updateEntity.setFixativeType(reqVO.getFixativeType());
        updateEntity.setFixativeTypeDesc(reqVO.getFixativeTypeDesc());
        updateEntity.setSampleWeight(reqVO.getSampleWeight());
        updateEntity.setFixativeVolume(reqVO.getFixativeVolume());
        updateEntity.setTransferContainer(reqVO.getTransferContainer());
        updateEntity.setUpdateBy(loginUser.getId().toString());
        updateEntity.setUpdateAt(new Date());
        this.updateById(updateEntity);

        // 记录操作日志
        sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.UPDATE_FIX, loginUser, reqVO.getSiteId());
    }

    /**
     * 退样
     *
     * @param reqVO
     */
    @Override
    public void returnSample(ReturnSampleReqVO reqVO) {
        if (CollectionUtils.isEmpty(reqVO.getSampleNoList())) {
            throw new BizException("样本列表为空");
        }
        if (null == reqVO.getSiteId()) {
            throw new BizException("站点不能为空");
        }
        Site site = siteService.selectById(reqVO.getSiteId());
        if (null == site) {
            throw new BizException("站点无效");
        }

        // 目前只有固定站点、转运站点（一级）可以退样
        if (!site.getSiteType().equals(SiteTypeEnum.FIXED_SITE.getCode()) && !site.getSiteType().equals(SiteTypeEnum.DELIVERY_SITE.getCode())) {
            throw new BizException("站点无效");
        }
        UserContext loginUser = UserContextHolder.currentUser();
        for (String sampleNo : reqVO.getSampleNoList()) {
            Sample sample = this.selectOne(new EntityWrapper<Sample>().eq("sample_no", sampleNo.trim()));
            if (null == sample) {
                // 样本编号无效。则继续下一个
                continue;
            }

            sample.setReturnFlag(ReturnFlagEnum.RETURN.getCode());

            // TODO 固定状态、转运状态是否回退

            OperateRecordTypeEnum type = null;
            if (site.getSiteType().equals(SiteTypeEnum.FIXED_SITE.getCode())) {
                // 流程状态回退一步
                sample.setFlowStatus(sample.getFlowStatus() - 1);
                type = OperateRecordTypeEnum.RETURN_FROM_FIX;
            } else if (site.getSiteType().equals(SiteTypeEnum.DELIVERY_SITE.getCode())) {
                type = OperateRecordTypeEnum.RETURN_FROM_TRANSFER;
                // 流程状态回退一步(避免 为转运之前 就退养，固定站点无法显示)
                if (sample.getFlowStatus() > 2) {
                    sample.setFlowStatus(sample.getFlowStatus() - 1);
                }
            }
            this.updateById(sample);

            sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), type, loginUser, reqVO.getSiteId());
        }
    }

    @Override
    public void multiTransferSample(MulTransferSampleReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        if (null == reqVO.getSiteId()) {
            throw new BizException("转运站点ID不能为空");
        }
        if (StringUtils.isEmpty(reqVO.getOldTransferNo())) {
            throw new BizException("原转运箱无效");
        }
        if (StringUtils.isEmpty(reqVO.getNewTransferNo())) {
            throw new BizException("新转运箱无效");
        }
        List<TransferRelation> list = transferRelationService.selectList(new EntityWrapper<TransferRelation>().eq("transfer_no", reqVO.getOldTransferNo()));
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("原转运箱下无样本信息");
        }

        for (TransferRelation old : list) {
            TransferRelation relation = new TransferRelation();
            relation.setTransferNo(reqVO.getNewTransferNo());
            relation.setSampleId(old.getId());
            relation.setSampleNo(old.getSampleNo());
            relation.setTransferStatus(TransferStatusEnum._1.getCode());
            relation.setCreateAt(new Date());
            relation.setCreateBy(loginUser.getId().toString());
            transferRelationService.insert(relation);

            // 记录操作日志
            sampleOperateRecordService.addLog(old.getId(), old.getSampleNo(), OperateRecordTypeEnum.MULTI_TRANSFER, loginUser, reqVO.getSiteId());

            sampleStatusService.add(old.getId(), SampleStatusValueEnum.MULTI_TRANSFER);
        }

//        Sample updateEntity = new Sample();
//        updateEntity.setId(sample.getId());
//
//        updateEntity.setDeliverySiteId(reqVO.getSiteId());
//        updateEntity.setDeliverySiteOperateTime(new Date());
//        updateEntity.setDeliverySiteUserId(loginUser.getId());
//        updateEntity.setFlowStatus(SampleFlowStatusEnum._3.getCode());
//        // 重写退样标记
//        updateEntity.setReturnFlag(ReturnFlagEnum.NORMAL.getCode());
//
//        updateEntity.setUpdateBy(loginUser.getId().toString());
//        updateEntity.setUpdateAt(new Date());
//        this.updateById(updateEntity);
    }

    @Override
    public ExitPromptNumBO exitPrompt(ExitPromptReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("站点不能为空");
        }
        Site site = siteService.selectById(reqVO.getSiteId());
        if (null == site) {
            throw new BizException("站点无效");
        }

        ExitPromptNumBO bo = new ExitPromptNumBO();
        bo.setTotalNum(0);
        bo.setDealNum(0);

        // 查询站点覆盖的站点ID
        List<SiteMap> mapSiteList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getSiteId()));
        if (CollectionUtils.isEmpty(mapSiteList)) {
            return bo;
        }
        List<Long> siteIdList = mapSiteList.stream().map(item -> item.getToSiteId()).collect(Collectors.toList());
        SiteTypeEnum siteType = SiteTypeEnum.findByCode(site.getId().intValue());
        if (SiteTypeEnum.FIXED_SITE.equals(siteType)) {
            // 固定站点
            SearchFixListReqVO condition = new SearchFixListReqVO();
            condition.setMapSiteIdList(siteIdList);
            return this.baseMapper.selectNumForFixSite(condition);
        } else if (SiteTypeEnum.DELIVERY_SITE.equals(siteType)) {
            // 转运站点
            SearchDeliveryListReqVO condition = new SearchDeliveryListReqVO();
            condition.setMapSiteIdList(siteIdList);
            return this.baseMapper.selectNumForDeliverySite(condition);
        } else if (SiteTypeEnum.RECEIVE_SITE.equals(siteType)) {
            // 接收站点
            return this.baseMapper.selectNumForReceiveSite(siteIdList);
        }
        return null;
    }

    /**
     * 样本标签打印通知
     *
     * @param reqVO
     */
    @Override
    public void tagPrintedNotify(SampleTagPrintedNotifyReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("站点不能为空");
        }
        Site site = siteService.selectById(reqVO.getSiteId());
        if (null == site) {
            throw new BizException("站点无效");
        }
        Sample sample = this.getSample(reqVO.getSampleNo());

        Sample updateEntity = new Sample();
        updateEntity.setId(sample.getId());
        updateEntity.setTagStatus(TagStatusEnum.PRINTED.getCode());
        this.updateById(updateEntity);

        UserContext loginUser = UserContextHolder.currentUser();
        // 记录操作日志
        sampleOperateRecordService.addLog(sample.getId(), sample.getSampleNo(), OperateRecordTypeEnum.SAMPLE_TAG_PRINTED, loginUser, reqVO.getSiteId());

        sampleStatusService.add(sample.getId(), SampleStatusValueEnum.SAMPLE_TAG_PRINTED);
    }

    // 补齐方法
    private static String addedCharAndLength(Long value, int addedToLength, char addedCharacter) {
        StringBuilder sb = new StringBuilder();

        // 大于数字自身长度
        if (addedToLength > 0 && addedToLength > Long.toString(value).length()) {
            int addLengthCount = addedToLength - Long.toString(value).length();
            for (int i = 0; i < addLengthCount; i++) {
                sb.append(addedCharacter);
            }
        }
        return sb.append(value).toString();
    }
}
