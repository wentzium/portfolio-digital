package com.mxs.sampleservice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.bo.AppointmentApplyListBO;
import com.mxs.sampleservice.entity.AppointmentApply;
import com.mxs.sampleservice.web.vo.ListAppointmentApplyReqVO;
import com.mxs.sampleservice.web.vo.SaveAppointmentApplyReqVO;

import java.util.List;

/**
 * 预约冰冻申请单 服务类
 *
 * Created by j.yang on 2019-08-22
 * 
 */

public interface AppointmentApplyService extends IService<AppointmentApply> {
    /**
     * 保存/修改 预约冰冻申请单
     *
     * @param reqVO
     * @return
     */
    String save(SaveAppointmentApplyReqVO reqVO);

    Page<AppointmentApplyListBO> list(ListAppointmentApplyReqVO reqVO);
}
