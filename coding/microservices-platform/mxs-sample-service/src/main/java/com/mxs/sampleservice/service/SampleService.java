package com.mxs.sampleservice.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.bo.*;
import com.mxs.sampleservice.entity.Sample;
import com.mxs.sampleservice.web.vo.*;

import java.util.List;


/**
 * 
 * 样本信息 服务类
 * Created by j.yang on 2019-08-22
 * 
 */

public interface SampleService extends IService<Sample> {
    String getSampleNo(String applyNo);

    /**
     * 保存/修改 样本信息
     *
     * @param reqVO
     */
    String saveSampleInfo(SaveSampleInfoReqVO reqVO);

    /**
     * 单个查询样本信息
     *
     * @param sampleNo
     * @return
     */
    SampleBO getBySampleNo(String sampleNo);

    /**
     * 查询申请单下的样本列表
     *
     * @param applyNo
     * @return
     */
    List<SampleBO> listByApplyNo(String applyNo);

    /**
     * 作废样本
     *
     * @param reqVO
     */
    void cancelSample(CancelSampleReqVO reqVO);

    /**
     * 查询固定站点样本列表
     *
     * @param reqVO
     * @return
     */
    Page<SampleListForFixSiteBO> searchFixList(SearchFixListReqVO reqVO);

    /**
     * 固定样本
     *
     * @param reqVO
     */
    SampleBO fixSample(FixedSampleReqVO reqVO);

    /**
     * 查询转运站点样本列表
     *
     * @param reqVO
     * @return
     */
    Page<SampleListForDeliverySiteBO> searchDeliveryList(SearchDeliveryListReqVO reqVO);

    /**
     * 查询多级转运站点样本列表
     *
     * @param reqVO
     * @return
     */
    Page<SampleListForDeliverySiteBO> searchMultiDeliveryList(SearchMultiDeliveryListReqVO reqVO);

    /**
     * 转运样本
     *
     * @param reqVO
     */
    void transferSample(TransferSampleReqVO reqVO);

    /**
     * 获取病理号
     *
     * @param reqVO
     * @return
     */
    String getPathologyNo(GetPathologyNoReqVO reqVO);

    /**
     * 查询接收站点样本列表
     *
     * @param reqVO
     * @return
     */
    Page<SampleListForReceiveSiteBO> searchReceiveList(SearchReceiveListReqVO reqVO);

    /**
     * 拒收样本
     *
     * @param reqVO
     */
    void rejectSample(RejectSampleReqVO reqVO);

    /**
     * 接收样本
     *
     * @param reqVO
     */
    void receiveSample(ReceiveSampleReqVO reqVO);

    /**
     * 查询样本物流信息
     *
     * @param sampleNo
     * @return
     */
    List<SampleLogisticsBO> getLogistics(String sampleNo);

    /**
     * 修改样本固定信息
     *
     * @param reqVO
     */
    void updateFixedInfo(FixedSampleReqVO reqVO);

    /**
     * 退样
     *
     * @param reqVO
     */
    void returnSample(ReturnSampleReqVO reqVO);

    /**
     * 多级转运样本
     *
     * @param reqVO
     */
    void multiTransferSample(MulTransferSampleReqVO reqVO);

    /**
     * 退出提示
     *
     * @param reqVO
     * @return
     */
    ExitPromptNumBO exitPrompt(ExitPromptReqVO reqVO);

    /**
     * 样本标签打印通知
     */
    void tagPrintedNotify(SampleTagPrintedNotifyReqVO reqVO);
}
