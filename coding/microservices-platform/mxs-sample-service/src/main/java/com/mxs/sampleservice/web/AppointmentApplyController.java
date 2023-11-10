package com.mxs.sampleservice.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.common.util.Paging;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.bo.AppointmentApplyListBO;
import com.mxs.sampleservice.service.AppointmentApplyService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.ListAppointmentApplyReqVO;
import com.mxs.sampleservice.web.vo.SaveAppointmentApplyReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 预约冰冻申请单 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/appointmentApply")
@Slf4j
public class AppointmentApplyController {
    @Autowired
    private AppointmentApplyService appointmentApplyService;

    @ApiOperation("保存预约冰冻申请单")
    @PostMapping(value = "/save")
    public ResponseData save(@RequestBody SaveAppointmentApplyReqVO reqVO) {
        try {
            String applyNo = appointmentApplyService.save(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.code).msg("保存成功").data(applyNo).build();
        } catch (BizException e) {
            log.error("AppointmentApplyController->save业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AppointmentApplyController->save异常", e);
            return ResponseData.createFailedResult("保存失败");
        }
    }

    @ApiOperation("查询预约冰冻申请单")
    @PostMapping(value = "/list")
    public ResponseData list(@RequestBody ListAppointmentApplyReqVO reqVO) {
        try {
            Page<AppointmentApplyListBO> result = appointmentApplyService.list(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("AppointmentApplyController->list业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AppointmentApplyController->list异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }
}

