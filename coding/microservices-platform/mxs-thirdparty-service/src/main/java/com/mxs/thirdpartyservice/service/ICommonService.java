package com.mxs.thirdpartyservice.service;

import com.mxs.common.entity.*;

import java.util.List;

public interface ICommonService {
    TpPatientInfo findPatientById(String patientId);

    /**
     * 系统调取HIS生成新的预约号
     */
    Long getAppointmentNoFromHis();

    /**
     * 新增申请单
     * @return
     */
    int addExamAppoints(TpExamAppoints tpExamAppoints);

    /**
     *  根据patientId查询ExamMaster
     * @param patientId
     * @return
     */
    List<TpExamMaster> findExamMasterByPatientId(String patientId);

    /**
     * 查询检查项目
     * @return
     */
    List<TpExamRptPattern> findExamRptPattern();

    /**
     * 新增检查item
     * @param examItems
     * @return
     */
    int addExamItems(TpExamItems examItems);
}
