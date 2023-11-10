package com.mxs.sampleservice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.sampleservice.bo.ApplyDetailBO;
import com.mxs.sampleservice.bo.ApplyLogisticsQueryBO;
import com.mxs.sampleservice.bo.ApplyPrintBO;
import com.mxs.sampleservice.bo.operation.ApplyListForOperationSiteBO;
import com.mxs.sampleservice.bo.operation.ListForOperationSiteBO;
import com.mxs.sampleservice.entity.Apply;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.web.vo.InitApplyReqVO;
import com.mxs.sampleservice.web.vo.SaveApplyInfoReqVO;
import com.mxs.sampleservice.web.vo.SearchApplyDetailListReqVO;
import com.mxs.sampleservice.web.vo.SearchListForOperationSiteReqVO;

import java.util.List;

/**
 * Created by j.yang on 2019-08-19
 * 
 */
public interface ApplyService extends IService<Apply> {
    String getApplyNo();

    /**
     * 保存/修改 申请单信息
     *
     * @param reqVO
     * @return
     */
    String saveApplyInfo(SaveApplyInfoReqVO reqVO);

    /**
     * 获取申请单实体
     *
     * @param applyNo
     * @return
     */
    Apply getApplyEntity(String applyNo);

    ApplyPrintBO send(String applyNo);

    ApplyLogisticsQueryBO applyLogisticsQuery(String id);

    /**
     * 初始化申请单信息
     *
     * @param reqVO
     * @return
     */
    String initApply(InitApplyReqVO reqVO);

    /**
     * 查询申请单详情接口
     *
     * @param applyNo
     * @return
     */
    ApplyDetailBO getDetailInfo(String applyNo);

    Page<ListForOperationSiteBO> searchOperationList(SearchListForOperationSiteReqVO reqVO);

    List<ApplyListForOperationSiteBO> searchApplyDetailList(SearchApplyDetailListReqVO reqVO);
}
