package com.mxs.sampleservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.mxs.common.entity.TpExamAppoints;
import com.mxs.common.entity.TpExamItems;
import com.mxs.common.util.*;
import com.mxs.sampleservice.bo.*;
import com.mxs.sampleservice.bo.operation.ApplyListForOperationSiteBO;
import com.mxs.sampleservice.bo.operation.ListForOperationSiteBO;
import com.mxs.sampleservice.client.userservice.ThirdPartyServiceProxy;
import com.mxs.sampleservice.client.userservice.UserServiceProxy;
import com.mxs.sampleservice.entity.*;
import com.mxs.sampleservice.enums.*;
import com.mxs.sampleservice.mapper.ApplyMapper;
import com.mxs.sampleservice.service.*;
import com.mxs.sampleservice.util.BarCodeUtils;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.util.NumberUtil;
import com.mxs.sampleservice.util.pdf.PDFBuilder;
import com.mxs.sampleservice.web.vo.*;
import com.mxs.sampleservice.web.vo.admin.SaveDicItemReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * Created by j.yang on 2019-08-20
 */
@Slf4j
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private CommSequenceService commSequenceService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private PatientInfoService patientInfoService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private SampleStatusService sampleStatusService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private UserServiceProxy userServiceProxy;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private DicItemService dicItemService;
    @Autowired
    private ThirdPartyServiceProxy thirdPartyServiceProxy;
    @Autowired
    private DicService dicService;
    @Autowired
    private CheckItemService checkItemService;

    @Value("${custom.file.pdf.path}")
    private String pdfPath;
    @Value("${custom.file.image.url}")
    private String imageUrl;
    @Value("${custom.file.image.upload.path}")
    private String imagePath;

    @Override
    public String getApplyNo() {
        String applyNo = String.format("%s%s", IdPrefixEnum.A.toString(), sequenceService.getNextStringValue(SequenceService.SEQ_APPLY_NO, 6));
        // 插入样本号生成编号
        CommSequence entity = new CommSequence();
        entity.setName(applyNo);
        entity.setCurrentValue(0L);
        entity.setIncrement(1);
        commSequenceService.insert(entity);
        return applyNo;
    }

    private String getApplyNo(IdPrefixEnum prefix, Long siteId) {
        String applyNo = String.format("%s%s%s", prefix.toString(), siteId, sequenceService.getNextStringValue(SequenceService.SEQ_APPLY_NO, 6));
        // 插入样本号生成编号
        CommSequence entity = new CommSequence();
        entity.setName(applyNo);
        entity.setCurrentValue(0L);
        entity.setIncrement(1);
        commSequenceService.insert(entity);
        return applyNo;
    }

    private String getApplyNoNew() {
        String applyNo = thirdPartyServiceProxy.getAppointNo();
        // 插入样本号生成编号
        CommSequence entity = new CommSequence();
        entity.setName(applyNo);
        entity.setCurrentValue(0L);
        entity.setIncrement(1);
        commSequenceService.insert(entity);
        return applyNo;
    }

    @Transactional
    @Override
    public String saveApplyInfo(SaveApplyInfoReqVO reqVO) {
        String applyNo = "";
        Apply apply = this.selectOne(new EntityWrapper<Apply>().eq("apply_no", reqVO.getApplyNo().trim()));
        if (null == apply) {
            throw new BizException("申请单不存在");
        }
        PatientInfo patientInfo = patientInfoService.selectById(apply.getPatientId());
        if (null == patientInfo) {
            throw new BizException("没有查询到申请单对应的病人信息");
        }
        BeanUtils.copyProperties(reqVO, patientInfo);
        patientInfo.setChiefComplaint(reqVO.getClinSymp());

        if (StringUtils.isNotEmpty(reqVO.getDcOrSamplingDate())) {
            patientInfo.setDcOrSamplingDate(DateTimeUtil.strToDate(reqVO.getDcOrSamplingDate(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
        }
        if (StringUtils.isNotEmpty(reqVO.getTreatmentDate())) {
            patientInfo.setTreatmentDate(DateTimeUtil.strToDate(reqVO.getTreatmentDate(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
        }
        if (StringUtils.isNotEmpty(reqVO.getTumorDiscoveryDate())) {
            patientInfo.setTumorDiscoveryDate(DateTimeUtil.strToDate(reqVO.getTumorDiscoveryDate(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
        }
        Site currentSite = siteService.selectById(reqVO.getSiteId());
        if (null == currentSite) {
            throw new BizException("当前操作站点无效");
        }
        // 手术站点录入时，且手术名称字典值ID为空，说明用户是选择其他时录入，需要存入字典值
        if (currentSite.getSiteType() == SiteTypeEnum.OPERATION_SITE.getCode()
                && StringUtils.isNotEmpty(reqVO.getOperationName())
                && NumberUtil.nullDefault(reqVO.getOperationId(), 0L) <= 0) {
            // 用户选择其他时添加的手术名称，需要加入字典值列表
            Dic dic = dicService.selectOne(new EntityWrapper<Dic>().eq("item_code", ItemKeyEnum.OPERATION_NAME.getCode()));
            SaveDicItemReqVO saveDicItemReqVO = new SaveDicItemReqVO();
            saveDicItemReqVO.setDicId(dic.getId());
            saveDicItemReqVO.setItemName(reqVO.getOperationName());
            long dicItemId = dicService.save(saveDicItemReqVO);
            reqVO.setOperationId(dicItemId);
            patientInfo.setOperationId(dicItemId);
        }

        patientInfo.setUpdateAt(new Date());
        patientInfo.setCheckHistory(JSON.toJSONString(reqVO.getCheckHistory()));
        patientInfoService.updateById(patientInfo);

        if (CollectionUtils.isNotEmpty(reqVO.getCheckItemList())) {
            // 先删除旧数据，再插入
            CheckItem updateCheckItem = new CheckItem();
            updateCheckItem.setDeleteFlag(1);
            updateCheckItem.setUpdateAt(new Date());
            checkItemService.update(updateCheckItem, new EntityWrapper<CheckItem>().eq("apply_id", apply.getId()));

            checkItemService.insertBatch(reqVO.getCheckItemList().stream().map(item -> {
                CheckItem po = new CheckItem();
                po.setApplyId(apply.getId());
                po.setCheckItem(JSON.toJSONString(item));
                po.setCreateAt(new Date());
                po.setUpdateAt(new Date());
                po.setDeleteFlag(0);
                return po;

            }).collect(Collectors.toList()));

            reqVO.getCheckItemList().stream().forEach(item -> {
                TpExamItems tpExamItems = new TpExamItems();
                tpExamItems.setExamNo(reqVO.getApplyNo());
                tpExamItems.setExamItem(item.getDescription());
                tpExamItems.setExamItemCode(item.getDescriptionCode());
                tpExamItems.setExamSubClass(item.getExamSubClass());
                tpExamItems.setExamItemNo(1);
                thirdPartyServiceProxy.addExamItems(tpExamItems);
            });
        }
        return applyNo;
    }

    @Override
    public Apply getApplyEntity(String applyNo) {
        if (StringUtils.isEmpty(applyNo)) {
            throw new BizException("申请单编号不能为空");
        }
        Apply applyEntity = this.selectOne(new EntityWrapper<Apply>().eq("apply_no", applyNo));
        if (null == applyEntity) {
            throw new BizException("申请单编号无效");
        }
        return applyEntity;
    }

    /**
     * 发送申请单
     *
     * @param applyNo
     * @return
     */
    @Transactional
    @Override
    public ApplyPrintBO send(String applyNo) {
        // 更新申请单状态为：已发送
        Apply entity = this.getApplyEntity(applyNo);

        entity.setStatus(ApplyStatusEnum._1.getCode());
        entity.setHasPrinted(1);
        entity.setUpdateAt(new Date());
        this.updateById(entity);

        // 申请单下的样本均记录一个状态值
        List<Sample> sampleList = sampleService.selectList(new EntityWrapper<Sample>().eq("apply_id", entity.getId()));
        StreamUtil.getStream(sampleList).forEach(item -> {
            sampleStatusService.add(item.getId(), SampleStatusValueEnum.SEND);
        });

        PatientInfo patientInfo = patientInfoService.selectById(entity.getPatientId());

        ApplyPrintBO bo = new ApplyPrintBO();
        bo.setApplyNo(entity.getApplyNo());
        bo.setPathologyNo(StrUtil.toStr(entity.getPathologyNo()));
        bo.setPatientName(StrUtil.toStr(patientInfo.getName()));
        bo.setSex(StrUtil.toStr(patientInfo.getSex()));
        bo.setAge(null == patientInfo.getAge() ? 0 : patientInfo.getAge());
        bo.setMarriageStatus(StrUtil.toStr(patientInfo.getMarriageStatus()));
        bo.setOccupation(StrUtil.toStr(patientInfo.getOccupation()));
        bo.setOutpatientNo(StrUtil.toStr(patientInfo.getOutpatientNo()));
        bo.setHospitalNo(StrUtil.toStr(patientInfo.getHospitalNo()));
        bo.setDepartment(StrUtil.toStr(patientInfo.getDepartment()));
        bo.setSickroom(StrUtil.toStr(patientInfo.getSickroom()));
        bo.setBedNo(StrUtil.toStr(patientInfo.getBedNo()));
        bo.setInspectionHospital(StrUtil.toStr(patientInfo.getInspectionHospital()));
        bo.setInspectionDate(DateTimeUtil.format(new Date()));
        bo.setReceiveDate(DateTimeUtil.format(new Date()));
        bo.setConcat("");
        bo.setPhone("");
        LoginUser createUser = userServiceProxy.getByUserId(Long.parseLong(entity.getCreateBy()));
        bo.setReceiver(null == createUser ? "" : StrUtil.toStr(createUser.getNameCn()));
        bo.setMedicalHistorySummary(StrUtil.toStr(patientInfo.getMedicalHistorySummary()));
        bo.setImageInspect("");
        bo.setLaboratoryInspect("");
        bo.setOperationSummary(StrUtil.toStr(patientInfo.getOperationSummary()));
        bo.setTumorSite(StrUtil.toStr(patientInfo.getTumorSite()));
        bo.setTumorSizeAndShape(StrUtil.toStr(patientInfo.getTumorSizeAndShape()));
        bo.setTumorDiscoveryDate(DateTimeUtil.format(patientInfo.getTumorDiscoveryDate()));
        bo.setTransferLocation(StrUtil.toStr(patientInfo.getTransferLocation()));
        bo.setHasRadiotherapy("");
        bo.setHasChemotherapy("");
        bo.setMenstrualDuration("");
        bo.setLastMenstrual(StrUtil.toStr(patientInfo.getLastMenstrual()));
        bo.setDcOrSamplingDate(DateTimeUtil.format(patientInfo.getDcOrSamplingDate()));
        bo.setHRTDateAndDose("");
        bo.setContraceptiveName("");
        bo.setContraceptiveDate("");
        bo.setPregnancy(StrUtil.toStr(patientInfo.getPregnancy()));
        bo.setParity(StrUtil.toStr(patientInfo.getParity()));
        bo.setLastPregnancy(StrUtil.toStr(patientInfo.getLastPregnancy()));
        bo.setHCG("");
        bo.setIUD("");
        bo.setOverInspectionUnit("");
        bo.setOverInspectionDate("");
        bo.setOverPathologyNo("");
        bo.setOverPathologyDiagnosis("");
        bo.setClinicalDiagnosis("");
        bo.setInspectionPurpose("");
        bo.setAddress(StrUtil.toStr(patientInfo.getAddress()));
        bo.setChiefComplaint(StrUtil.toStr(patientInfo.getChiefComplaint()));
        PatientCheckHisBO checkHisBO = JSON.parseObject(StrUtil.toStr(patientInfo.getCheckHistory()), PatientCheckHisBO.class);
        bo.setInspectItem(String.format("%s-%s-%s", StrUtil.toStr(patientInfo.getExamClass()), StrUtil.toStr(patientInfo.getExamSubClass()),
                null != checkHisBO ? StrUtil.toStr(checkHisBO.getClinDiag()) : ""));
        bo.setInspectResult(StrUtil.toStr(patientInfo.getRelevantDiag()));
        bo.setEndocrineTherapyFlag(patientInfo.getEndocrineTherapyFlag());
        bo.setTreatmentDate(DateTimeUtil.format(patientInfo.getTumorDiscoveryDate()));
        bo.setDose(StrUtil.toStr(patientInfo.getDose()));

        UserContext currentUser = UserContextHolder.currentUser();
        bo.setRemark("待定");
        bo.setInspectionDoctor(StrUtil.toStr(currentUser.getNameCn()));
        bo.setPrintUser(StrUtil.toStr(currentUser.getNameCn()));
        bo.setPrintDate(DateTimeUtil.format(new Date()));
        bo.setFixedBy("待定");
        bo.setOperationName(StrUtil.toStr(patientInfo.getOperationName()));

        // 申请单样本信息
        bo.setSampleList(new ArrayList<>());
        int index = 0;
        if (CollectionUtils.isNotEmpty(sampleList)) {
            for (Sample s : sampleList) {
                // 手术名称为空时，手术名称取第一个样本的手术类型描述字段
                if (StringUtils.isEmpty(bo.getOperationName()) && index == 0) {
                    bo.setOperationName(StrUtil.toStr(s.getOperationTypeDesc()));
                }
                SamplePrintBO samplePrintBO = new SamplePrintBO();
                samplePrintBO.setSampleNo(s.getSampleNo());
                samplePrintBO.setSampleName(StringUtils.isEmpty(s.getCollectionLocationDesc()) ? "" : s.getCollectionLocationDesc());
                samplePrintBO.setSampleType(StringUtils.isEmpty(s.getTypeDesc()) ? "" : s.getTypeDesc());
                samplePrintBO.setSampleLocation(StringUtils.isEmpty(s.getCollectionLocationDesc()) ? "" : s.getCollectionLocationDesc());
                samplePrintBO.setSeparationTime(DateTimeUtil.format(s.getSeparationTime()));
                samplePrintBO.setFixedTime(DateTimeUtil.format(s.getFixedTime()));
                samplePrintBO.setNum(NumberUtil.integerNotNull(s.getNum(), 0));
                bo.getSampleList().add(samplePrintBO);
                index++;
            }
        }
        this.generatePdf(bo);

        // 通知LIS第三方系统
        this.saveExamAppoints(patientInfo, entity);

        return bo;
    }

    private ApplyPrintBO generatePdf(ApplyPrintBO bo) {
        // pdf保存路径
//        pdfPath = "C:\\Users\\C.SY\\Desktop\\ddw\\";
        String pdfFilePath = String.format("%s%s.pdf", pdfPath, bo.getApplyNo());
        try {
            // 创建一个文档
            Document document = new Document(PageSize.A4, PDFBuilder.marginX, PDFBuilder.marginX, PDFBuilder.marginY, 70);
            // pdf输出流
            OutputStream outputStream = new FileOutputStream(pdfFilePath);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
            // 添加页眉页脚
            pdfWriter.setPageEvent(new PDFBuilder(bo));
            document.open();

            Barcode128 code128 = new Barcode128();
            code128.setCode(bo.getApplyNo());
            code128.setCodeType(Barcode.CODE128);
            com.itextpdf.text.Image code128Image = code128.createImageWithBarcode(pdfWriter.getDirectContent(), null, null);
            code128Image.setAbsolutePosition(document.right(160), document.top(46));
            code128Image.scaleAbsolute(80, 40);
            document.add(code128Image);

            String imgBase64 = Base64.toBase64String(BarCodeUtils.generateBarCode128(bo.getApplyNo(), false, false));
            bo.setImgBase64(imgBase64);

//            DicItem dicItem = dicItemService.selectOne(new EntityWrapper<DicItem>().eq("item_code", "102001"));
            // 申请单新模板
            DicItem dicItem = dicItemService.selectOne(new EntityWrapper<DicItem>().eq("item_code", "102002"));
            String htmlData = templateService.processByContent(dicItem.getItemName(), bo);
            InputStream inputStream = new ByteArrayInputStream(htmlData.getBytes());

            XMLWorkerHelper.getInstance().parseXHtml(
                    pdfWriter,
                    document,
                    inputStream,
                    StandardCharsets.UTF_8);
            document.close();

            return bo;

//            try {
//                imagePath = "C:\\Users\\C.SY\\Desktop\\ddw\\";
//                String htmlPath = String.format("%s%s.html", imagePath, bo.getApplyNo());
//                //设置输出文件
//                File file = new File(htmlPath);
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                FileWriter out = new FileWriter(file);
//                out.write(htmlData);
//                out.flush();
//                out.close();
////                return String.format("%s%s.html", imageUrl, bo.getApplyNo());
//            } catch (Exception e) {
//                log.error("生成申请单html异常", e);
//                e.printStackTrace();
//                return null;
//            }
        } catch (Exception e) {
            log.error("生成申请单pdf失败", e);
            return null;
        }
    }

    /**
     * 物流查询
     *
     * @param id
     * @return
     */
    @Override
    public ApplyLogisticsQueryBO applyLogisticsQuery(String id) {
        ApplyLogisticsQueryBO result = new ApplyLogisticsQueryBO();
        List<PatientInfo> patientInfoList = patientInfoService.selectList(new EntityWrapper<PatientInfo>().eq("patient_no", id));
        if (CollectionUtils.isNotEmpty(patientInfoList)) {
            //输入的是病人ID，返回病人的所有申请单列表
            List<Apply> applyList = this.selectList(new EntityWrapper<Apply>().in("patient_id", StreamUtil.map(patientInfoList, f -> f.getId())));
            if (CollectionUtils.isEmpty(applyList)) {
                throw new BizException("未查询到病人的申请单信息");
            }
            result.setApplyList(applyList.stream().map(item -> {
                ApplyBO bo = new ApplyBO();
                bo.setApplyNo(item.getApplyNo());
                bo.setPatientNo(id);
                bo.setPathologyNo(item.getPathologyNo());
                bo.setStatus(item.getStatus());
                bo.setStatusDesc(ApplyStatusEnum.findByCode(item.getStatus()).getDesc());
                return bo;
            }).collect(Collectors.toList()));
            return result;
        }
        Apply apply = this.selectOne(new EntityWrapper<Apply>().eq("apply_no", id));
        if (null != apply) {
            ApplyDetailBO applyDetailBO = new ApplyDetailBO();
            PatientInfo patientInfoEntity = patientInfoService.selectById(apply.getPatientId());

            PatientInfoBO patientInfoBO = new PatientInfoBO();
            BeanUtils.copyProperties(patientInfoEntity, patientInfoBO);
            applyDetailBO.setPatientInfo(patientInfoBO);

            List<Sample> sampleEntityList = sampleService.selectList(new EntityWrapper<Sample>().eq("apply_no", apply.getApplyNo()));
            applyDetailBO.setSampleList(StreamUtil.getStream(sampleEntityList).map(sampleEntity -> {
                SampleBO sampleBO = new SampleBO();
                BeanUtils.copyProperties(sampleEntity, sampleBO);

                sampleBO.setAvailableStatusDesc(AvailableStatusEnum.findByCode(sampleEntity.getAvailableStatus()).getDesc());
                sampleBO.setSeparationStatusDesc(SeparationStatusEnum.findByCode(sampleEntity.getSeparationStatus()).getDesc());
                sampleBO.setFixedStatusDesc(FixedStatusEnum.findByCode(sampleEntity.getFixedStatus()).getDesc());
                sampleBO.setLogisticsStatusDesc(LogisticsStatusEnum.findByCode(sampleEntity.getLogisticsStatus()).getDesc());
                sampleBO.setTagStatusDesc(TagStatusEnum.findByCode(sampleEntity.getTagStatus()).getDesc());
                sampleBO.setPhotoStatusDesc(TagStatusEnum.findByCode(sampleEntity.getPhotoStatus()).getDesc());
                sampleBO.setFixedTime(DateTimeUtil.format(sampleEntity.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setSeparationTime(DateTimeUtil.format(sampleEntity.getSeparationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setDeliveryTime(DateTimeUtil.format(sampleEntity.getDeliverySiteOperateTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setAcceptTime(DateTimeUtil.format(sampleEntity.getAcceptAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setReturnFlag(ReturnFlagEnum.RETURN.getCode() == sampleEntity.getReturnFlag());
                if (StringUtils.isNotEmpty(sampleEntity.getTypeDesc())) {
                    sampleBO.setIsFrozen(sampleEntity.getTypeDesc().contains("冰冻样品"));
                } else {
                    sampleBO.setIsFrozen(false);
                }

//                List<Image> imageList = imageService.selectList(new EntityWrapper<Image>().eq("sample_id", sampleEntity.getId()));
//                sampleBO.setImgList(StreamUtil.map(imageList, item -> item.getUrl()));

                sampleBO.setPatientNo(patientInfoBO.getPatientNo());
                sampleBO.setPatientName(patientInfoBO.getName());

                return sampleBO;
            }).collect(Collectors.toList()));

            result.setApplyDetail(applyDetailBO);
            return result;
        }
        Sample sample = sampleService.selectOne(new EntityWrapper<Sample>().eq("sample_no", id));
        if (null != sample) {
            result.setSample(sampleService.getBySampleNo(id));
        }
        return result;
    }

    /**
     * 初始化申请单信息
     *
     * @param reqVO
     * @return
     */
    @Override
    public String initApply(InitApplyReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
//        PatientInfo patientInfo = patientInfoService.selectOne(new EntityWrapper<PatientInfo>().eq("patient_no", reqVO.getPatientNo()).orderBy("id", true));
//        if (null == patientInfo) {
//            throw new BizException("病人信息不存在");
//        }
        Site siteEntity = siteService.selectById(reqVO.getSiteId());
        if (null == siteEntity) {
            throw new BizException("站点无效");
        }
        Apply applyEntity = new Apply();
//        String applyNo = this.getApplyNo(siteEntity.getSiteType() == SiteTypeEnum.OPERATION_SITE.getCode() ? IdPrefixEnum.S : IdPrefixEnum.C,
//                siteEntity.getSiteType() == SiteTypeEnum.OPERATION_SITE.getCode() ? reqVO.getSiteId() : 0L);
        String applyNo = this.getApplyNoNew();
        applyEntity.setApplyNo(applyNo);
        applyEntity.setPatientId(reqVO.getPatientId());
        applyEntity.setPatientNo(reqVO.getPatientNo());
        applyEntity.setStatus(ApplyStatusEnum._0.getCode());

        applyEntity.setSiteId(reqVO.getSiteId());
        applyEntity.setCreateBy(loginUser.getId().toString());
        applyEntity.setCreateAt(new Date());
        applyEntity.setUpdateAt(new Date());
        this.insert(applyEntity);

        return applyNo;
    }

    @Override
    public ApplyDetailBO getDetailInfo(String applyNo) {
        Apply apply = this.selectOne(new EntityWrapper<Apply>().eq("apply_no", applyNo));
        if (null == apply) {
            throw new BizException("申请单不存在");
        }
        ApplyDetailBO applyDetailBO = new ApplyDetailBO();
        if (null != apply) {
            PatientInfo patientInfoEntity = patientInfoService.selectById(apply.getPatientId());

            PatientInfoBO patientInfoBO = new PatientInfoBO();
            BeanUtils.copyProperties(patientInfoEntity, patientInfoBO);
            patientInfoBO.setCheckHistory(JSON.parseObject(StrUtil.toStr(patientInfoEntity.getCheckHistory()), PatientCheckHisBO.class));
            applyDetailBO.setPatientInfo(patientInfoBO);

            if (null != patientInfoEntity.getDcOrSamplingDate()) {
                patientInfoBO.setDcOrSamplingDate(DateTimeUtil.format(patientInfoEntity.getDcOrSamplingDate()));
            }
            if (null != patientInfoEntity.getTreatmentDate()) {
                patientInfoBO.setTreatmentDate(DateTimeUtil.format(patientInfoEntity.getTreatmentDate()));
            }
            if (null != patientInfoEntity.getTumorDiscoveryDate()) {
                patientInfoBO.setTumorDiscoveryDate(DateTimeUtil.format(patientInfoEntity.getTumorDiscoveryDate()));
            }

            List<Sample> sampleEntityList = sampleService.selectList(new EntityWrapper<Sample>().eq("apply_no", apply.getApplyNo()));
            applyDetailBO.setSampleList(StreamUtil.getStream(sampleEntityList).map(sampleEntity -> {
                SampleBO sampleBO = new SampleBO();
                BeanUtils.copyProperties(sampleEntity, sampleBO);

                sampleBO.setSex(patientInfoBO.getSex());
                sampleBO.setDepartment(patientInfoBO.getDepartment());
                sampleBO.setAvailableStatusDesc(AvailableStatusEnum.findByCode(sampleEntity.getAvailableStatus()).getDesc());
                sampleBO.setSeparationStatusDesc(SeparationStatusEnum.findByCode(sampleEntity.getSeparationStatus()).getDesc());
                sampleBO.setFixedStatusDesc(FixedStatusEnum.findByCode(sampleEntity.getFixedStatus()).getDesc());
                sampleBO.setLogisticsStatusDesc(LogisticsStatusEnum.findByCode(sampleEntity.getLogisticsStatus()).getDesc());
                sampleBO.setTagStatusDesc(TagStatusEnum.findByCode(sampleEntity.getTagStatus()).getDesc());
                sampleBO.setPhotoStatusDesc(TagStatusEnum.findByCode(sampleEntity.getPhotoStatus()).getDesc());
                sampleBO.setFixedTime(DateTimeUtil.format(sampleEntity.getFixedTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setSeparationTime(DateTimeUtil.format(sampleEntity.getSeparationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setDeliveryTime(DateTimeUtil.format(sampleEntity.getDeliverySiteOperateTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setAcceptTime(DateTimeUtil.format(sampleEntity.getAcceptAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
                sampleBO.setReturnFlag(ReturnFlagEnum.RETURN.getCode() == sampleEntity.getReturnFlag());
                if (StringUtils.isNotEmpty(sampleEntity.getTypeDesc())) {
                    sampleBO.setIsFrozen(sampleEntity.getTypeDesc().contains("冰冻样品"));
                } else {
                    sampleBO.setIsFrozen(false);
                }
//                List<Image> imageList = imageService.selectList(new EntityWrapper<Image>().eq("sample_id", sampleEntity.getId()));
//                sampleBO.setImgList(StreamUtil.map(imageList, item -> item.getUrl()));

                sampleBO.setPatientNo(patientInfoBO.getPatientNo());
                sampleBO.setPatientName(patientInfoBO.getName());

                return sampleBO;
            }).collect(Collectors.toList()));

            List<CheckItem> poList = checkItemService.selectList(new EntityWrapper<CheckItem>()
                    .eq("apply_id", apply.getId()).eq("delete_flag", 0));
            applyDetailBO.setCheckItemList(StreamUtil.getStream(poList).map(item -> JSON.parseObject(item.getCheckItem(), CheckItemVO.class)).collect(Collectors.toList()));
        }

        return applyDetailBO;
    }

    @Override
    public Page<ListForOperationSiteBO> searchOperationList(SearchListForOperationSiteReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("手术站点ID为空");
        }
        Site siteEntity = siteService.selectById(reqVO.getSiteId());
        if (null == siteEntity || !siteEntity.getSiteType().equals(SiteTypeEnum.OPERATION_SITE.getCode())) {
            throw new BizException("手术站点无效");
        }
        UserContext loginUser = UserContextHolder.currentUser();
        reqVO.setUserId(loginUser.getId().toString());

        Page<ListForOperationSiteBO> page = new Page<>(reqVO.getPage(), reqVO.getPageSize());
        List<ListForOperationSiteBO> list = this.baseMapper.searchOperationList(page, reqVO);
        return page.setRecords(list);
    }

    /**
     * 手术站点-申请单明细列表查询
     *
     * @param reqVO
     * @return
     */
    @Override
    public List<ApplyListForOperationSiteBO> searchApplyDetailList(SearchApplyDetailListReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("手术站点ID为空");
        }
        if (StringUtils.isEmpty(reqVO.getPatientNo())) {
            throw new BizException("病人ID不能为空");
        }
        Site siteEntity = siteService.selectById(reqVO.getSiteId());
        if (null == siteEntity || !siteEntity.getSiteType().equals(SiteTypeEnum.OPERATION_SITE.getCode())) {
            throw new BizException("手术站点无效");
        }
        List<PatientInfo> patientEntityList = patientInfoService.selectList(new EntityWrapper<PatientInfo>().eq("patient_no", reqVO.getPatientNo()));
        if (CollectionUtils.isEmpty(patientEntityList)) {
            throw new BizException("病人信息无效");
        }
        UserContext loginUser = UserContextHolder.currentUser();
        // 倒序
        List<Apply> applyList = this.selectList(new EntityWrapper<Apply>().eq("site_id", reqVO.getSiteId())
                .eq("create_by", loginUser.getId().toString()).in("patient_id", StreamUtil.map(patientEntityList, f -> f.getId()))
                .eq("is_deleted", 0)
                .orderBy("create_at", false));

        return StreamUtil.getStream(applyList).map(item -> {
            ApplyListForOperationSiteBO bo = new ApplyListForOperationSiteBO();
            bo.setApplyNo(item.getApplyNo());
            bo.setCreateTime(DateTimeUtil.format(item.getCreateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            bo.setHasPrinted(item.getHasPrinted() == 0 ? false : true);
            return bo;
        }).collect(Collectors.toList());
    }

    private boolean saveExamAppoints(PatientInfo patientInfo, Apply apply) {
        List<CheckItem> checkItemList = checkItemService.selectList(new EntityWrapper<CheckItem>()
                .eq("apply_id", apply.getId()).eq("delete_flag", 0));
        TpExamAppoints bo = new TpExamAppoints();
        bo.setExamNo(apply.getApplyNo());
        bo.setPatientId(patientInfo.getPatientNo());
        bo.setName(patientInfo.getName());
        bo.setSex(patientInfo.getSex());
        bo.setFacility(patientInfo.getInspectionHospital());
//        bo.setClinSymp(patientInfo.getOperationSummary());
        bo.setPhysSign(patientInfo.getOperationSummary());
        bo.setMailing_address(patientInfo.getAddress());
        if (CollectionUtils.isNotEmpty(checkItemList)) {
            CheckItemVO vo = JSON.parseObject(checkItemList.get(0).getCheckItem(), CheckItemVO.class);
            bo.setExamSubClass(vo.getExamSubClass());
        }

        return thirdPartyServiceProxy.addPoints(bo);
    }
}
