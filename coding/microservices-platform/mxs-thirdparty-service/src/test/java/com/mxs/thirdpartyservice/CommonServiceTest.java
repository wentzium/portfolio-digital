package com.mxs.thirdpartyservice;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mxs.common.entity.*;
import com.mxs.thirdpartyservice.entity.PatsInHospital;
import com.mxs.thirdpartyservice.service.ICommonService;
import com.mxs.thirdpartyservice.service.PatsInHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: j.yang
 * @Date: 2019-08-13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonServiceTest {

    @Autowired
    private ICommonService commonService;


    @Autowired
    private PatsInHospitalService patsInHospitalService;

    @Test
    public void findById() {
        TpPatientInfo patientById = commonService.findPatientById("259767");

        log.info("ID: {}",patientById);

//        List<PatsInHospital> patsInHospitals = patsInHospitalService.selectList(new EntityWrapper<PatsInHospital>().eq("PATIENT_ID", "7684763"));
//
//        if (!CollectionUtils.isEmpty(patsInHospitals)) {
//           log.info( patsInHospitals.get(0).getDiagnosis());
//        }
    }

    @Test
    public void getAppointmentNoFromHis() {
        Long value = commonService.getAppointmentNoFromHis();
        log.info("NextValue: {}", value);
        Assert.assertNotNull(value);
    }

    @Test
    public void findExamMasterByPatientId(){
        List<TpExamMaster> examMasterByPatientId = commonService.findExamMasterByPatientId("259742");

        Assert.assertNotNull(examMasterByPatientId);

        Assert.assertFalse(examMasterByPatientId.isEmpty());
    }

    @Test
    public void findExamRptPattern(){
        List<TpExamRptPattern> rptPatternList = commonService.findExamRptPattern();

        Assert.assertNotNull(rptPatternList);

        Assert.assertFalse(rptPatternList.isEmpty());
    }

    @Test
    public void addExamAppoints(){
        TpExamAppoints appoints = new TpExamAppoints();
        appoints.setExamNo("6339357");
        appoints.setLocalIdClass("D");
        appoints.setPatientId("259742");
        appoints.setVisitId(4L);
        appoints.setName("黄竹君");
        appoints.setSex("女");
        appoints.setDateOfBirth(new Date());
        appoints.setExamClass("心电图");
        appoints.setExamSubClass("普通");
        appoints.setClinSymp("咳嗽咳痰1周，有高血压、冠心病病史");
        appoints.setPhysSign("肺部罗音、心率不齐");
        appoints.setClinDiag("咳嗽.咳痰");
        appoints.setPerformedBy("210331");
        appoints.setPatientSource("2");
        appoints.setReqDateTime(new Date());
        appoints.setReqDept("210403");
        appoints.setReqPhysician("梁岚");
        int examMasterByPatientId = commonService.addExamAppoints(appoints);

        Assert.assertTrue(examMasterByPatientId > 0);
    }

    @Test
    public void addExamItems() throws UnsupportedEncodingException {
        TpExamItems examItems = new TpExamItems();
        examItems.setCosts(new BigDecimal(200.12));
        examItems.setExamNo("6339357");
        examItems.setExamItem("检查条目张营轩");
        examItems.setExamItemCode("D678");
        examItems.setExamItemNo(1);
//        examItems.setExamSubClass("examsubclass");
        int i = commonService.addExamItems(examItems);
        Assert.assertTrue(i>0);
    }


}
