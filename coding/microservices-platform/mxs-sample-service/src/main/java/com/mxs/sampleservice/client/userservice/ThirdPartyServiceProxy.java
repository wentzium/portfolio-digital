package com.mxs.sampleservice.client.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.common.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by j.yang on 2019-07-11
 */
@Component
public class ThirdPartyServiceProxy {
    @Autowired
    private ThirdPartyServiceFeignClient thirdPartyServiceFeignClient;

    public TpPatientInfo getPatientInfo(String patientNo) {
        Map data = (Map) thirdPartyServiceFeignClient.getPatientInfo(patientNo).getData();
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String value = objectMapper.writeValueAsString(data);
            TpPatientInfo patientInfo = objectMapper.readValue(value, TpPatientInfo.class);
            return patientInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAppointNo() {
        Object data = thirdPartyServiceFeignClient.getAppointNo().getData();
        String appointNo = String.valueOf(data);
        if (StringUtils.isEmpty(appointNo)) {
            return null;
        }
        return appointNo;
    }

    public boolean addPoints(TpExamAppoints bo) {
        Object data = thirdPartyServiceFeignClient.addPoints(bo).getData();
        if (null == data || Boolean.FALSE == data) {
            return false;
        }
        return true;
    }

    public List<TpExamMaster> findExamMasterByPatientId(String patientNo) {
        List data = (List) thirdPartyServiceFeignClient.findExamMasterByPatientId(patientNo).getData();
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        List<TpExamMaster> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object map : data) {
            try {
                String value = objectMapper.writeValueAsString(map);
                TpExamMaster examMaster = objectMapper.readValue(value, TpExamMaster.class);
                resultList.add(examMaster);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return resultList;
    }

    public List<TpExamRptPattern> findExamRptPattern() {
        List data = (List) thirdPartyServiceFeignClient.findExamRptPattern().getData();
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        List<TpExamRptPattern> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object map : data) {
            try {
                String value = objectMapper.writeValueAsString(map);
                TpExamRptPattern examMaster = objectMapper.readValue(value, TpExamRptPattern.class);
                resultList.add(examMaster);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return resultList;
    }

    public boolean addExamItems(TpExamItems bo) {
        Object data = thirdPartyServiceFeignClient.addExamItems(bo).getData();
        if (null == data || Boolean.FALSE == data) {
            return false;
        }
        return true;
    }
}
