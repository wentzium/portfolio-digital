package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.DateTimeFormatStrEnum;
import com.mxs.common.util.DateTimeUtil;
import com.mxs.common.util.UserContext;
import com.mxs.common.util.UserContextHolder;
import com.mxs.sampleservice.bo.AppointmentApplyListBO;
import com.mxs.sampleservice.entity.AppointmentApply;
import com.mxs.sampleservice.enums.IdPrefixEnum;
import com.mxs.sampleservice.enums.OperationTypeEnum;
import com.mxs.sampleservice.mapper.AppointmentApplyMapper;
import com.mxs.sampleservice.service.AppointmentApplyService;
import com.mxs.sampleservice.service.SequenceService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.ListAppointmentApplyReqVO;
import com.mxs.sampleservice.web.vo.SaveAppointmentApplyReqVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 预约冰冻申请单 服务实现类
 *
 * Created by j.yang on 2019-09-03
 */

@Service
public class AppointmentApplyServiceImpl extends ServiceImpl<AppointmentApplyMapper, AppointmentApply> implements AppointmentApplyService {
    @Autowired
    private SequenceService sequenceService;

    /**
     * 保存/修改 预约冰冻申请单
     *
     * @param reqVO
     * @return
     */
    @Override

    public String save(SaveAppointmentApplyReqVO reqVO) {
        UserContext loginUser = UserContextHolder.currentUser();
        String applyNo = "";
        if (StringUtils.isEmpty(reqVO.getApplyNo())) {
            // 保存
            applyNo = String.format("%s%s", IdPrefixEnum.B.toString(), sequenceService.getNextStringValue(SequenceService.SEQ_APPOINTMENT_APPLY_NO, 8));
            AppointmentApply entity = new AppointmentApply();
            BeanUtils.copyProperties(reqVO, entity);
            if (StringUtils.isNotEmpty(reqVO.getOperationTime())) {
                entity.setOperationTime(DateTimeUtil.strToDate(reqVO.getOperationTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
            }
            if (StringUtils.isNotEmpty(reqVO.getExpectDeliverySampleTime())) {
                entity.setExpectDeliverySampleTime(DateTimeUtil.strToDate(reqVO.getExpectDeliverySampleTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
            }
            entity.setApplyNo(applyNo);
            entity.setCreateBy(loginUser.getId().toString());
            entity.setCreateAt(new Date());
            entity.setUpdateAt(new Date());
            this.insert(entity);
        } else {
            // 修改
            AppointmentApply entity = this.selectOne(new EntityWrapper<AppointmentApply>().eq("apply_no", reqVO.getApplyNo()));
            if (null == entity) {
                throw new BizException("预约冰冻申请单不存在");
            }
            BeanUtils.copyProperties(reqVO, entity);
            entity.setUpdateBy(loginUser.getId().toString());
            entity.setUpdateAt(new Date());
            this.updateById(entity);
            // TODO 可能需要加入审计记录
        }
        return applyNo;
    }

    @Override
    public Page<AppointmentApplyListBO> list(ListAppointmentApplyReqVO reqVO) {
        Page<AppointmentApply> page = new Page<>(reqVO.getPage() <= 0 ? 1 : reqVO.getPage(), reqVO.getPageSize() <= 0 ? 10 : reqVO.getPageSize());
        page.setDescs(Arrays.asList("create_at"));
        DateTime now = new DateTime();
        String startTime = DateTimeUtil.format(now.toDate(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_DAY);
        now = now.plusDays(1);
        String endTime = DateTimeUtil.format(now.toDate(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_DAY);
        Page<AppointmentApply> pageResult = this.selectPage(page, new EntityWrapper<AppointmentApply>().ge("operation_time", startTime).lt("operation_time", endTime));

        Page<AppointmentApplyListBO> result = new Page<>();
        BeanUtils.copyProperties(pageResult, result, "records");
        result.getPages();

        result.setRecords(Optional.ofNullable(pageResult.getRecords()).orElse(new ArrayList<>()).stream().map(item -> {
            AppointmentApplyListBO bo = new AppointmentApplyListBO();
            BeanUtils.copyProperties(item, bo);

            bo.setOperationType(item.getOperationType());
            bo.setOperationTypeDesc(item.getOperationTypeDesc());
            bo.setOperationTime(new DateTime(item.getOperationTime()).toString(DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
            bo.setExpectDeliverySampleTime(new DateTime(item.getExpectDeliverySampleTime()).toString(DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr()));
            return bo;
        }).collect(Collectors.toList()));
        return result;
    }
}
