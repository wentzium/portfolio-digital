package com.mxs.sampleservice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.bo.DicKeyBO;
import com.mxs.sampleservice.bo.DicValueBO;
import com.mxs.sampleservice.bo.admin.AdminDicValueBO;
import com.mxs.sampleservice.entity.Dic;
import com.mxs.sampleservice.web.vo.admin.ListDicItemReqVO;
import com.mxs.sampleservice.web.vo.admin.ListDicReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveDicItemReqVO;

import java.util.List;

/**
 * 服务类
 *
 * Created by j.yang on 2019-08-23
 * 
 */

public interface DicService extends IService<Dic> {
    /**
     * 字典列表
     *
     * @return
     */
    List<DicKeyBO> list();

    /**
     * 保存字典值
     *
     * @param reqVO
     */
    long save(SaveDicItemReqVO reqVO);

    /**
     * 字典值详情
     *
     * @param dicItemId
     * @return
     */
    AdminDicValueBO getDetail(Long dicItemId);

    /**
     * 删除字典值
     *
     * @param dicItemId
     */
    void remove(Long dicItemId);

    /**
     * 字典值列表
     *
     * @return
     */
    Page<AdminDicValueBO> listDicItem(ListDicItemReqVO reqVO);

    Page<com.mxs.sampleservice.bo.admin.DicKeyBO> searchAdminDicList(ListDicReqVO reqVO);
}
