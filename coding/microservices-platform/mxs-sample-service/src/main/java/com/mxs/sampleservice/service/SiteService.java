package com.mxs.sampleservice.service;

import com.baomidou.mybatisplus.service.IService;
import com.mxs.sampleservice.bo.SiteBO;
import com.mxs.sampleservice.entity.Site;
import com.mxs.sampleservice.web.vo.admin.ListSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.RemoveSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveSiteReqVO;

import java.util.List;

/**
 * <p>
 * 站点表 服务类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
public interface SiteService extends IService<Site> {
    List<SiteBO> listSiteByIp(String ip);

    /**
     * 新增/保存站点信息
     *
     * @param reqVO
     */
    void save(SaveSiteReqVO reqVO);

    /**
     * 删除站点信息
     *
     * @param reqVO
     */
    void remove(RemoveSiteReqVO reqVO);

    /**
     * 查询站点信息
     *
     * @return
     */
    List list(ListSiteReqVO reqVO);

    /**
     * 查询站点详情
     *
     * @param siteId
     * @return
     */
    com.mxs.sampleservice.bo.admin.SiteBO getDetail(Long siteId);
}
