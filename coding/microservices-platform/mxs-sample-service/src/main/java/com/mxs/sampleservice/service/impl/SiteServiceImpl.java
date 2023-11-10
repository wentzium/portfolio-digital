package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.mxs.common.util.CustomerTypeEnum;
import com.mxs.common.util.StreamUtil;
import com.mxs.common.util.UserContext;
import com.mxs.common.util.UserContextHolder;
import com.mxs.sampleservice.bo.SiteBO;
import com.mxs.sampleservice.entity.DicItem;
import com.mxs.sampleservice.entity.Site;
import com.mxs.sampleservice.entity.SiteMap;
import com.mxs.sampleservice.enums.SiteTypeEnum;
import com.mxs.sampleservice.mapper.SiteMapper;
import com.mxs.sampleservice.service.DicItemService;
import com.mxs.sampleservice.service.SiteMapService;
import com.mxs.sampleservice.service.SiteService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.admin.ListSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.RemoveSiteReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveSiteReqVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点表 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {
    @Autowired
    private SiteMapService siteMapService;
    @Autowired
    private DicItemService dicItemService;

    /**
     * 根据IP查询站点列表
     *
     * @param ip
     * @return
     */
    @Override
    public List<SiteBO> listSiteByIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            throw new BizException("IP地址不能为空");
        }
        List<Site> list = this.selectList(new EntityWrapper<Site>().eq("ip", ip));

        UserContext loginUser = UserContextHolder.currentUser();
        CustomerTypeEnum customerType = CustomerTypeEnum.findByCode(loginUser.getCustomerType());

        // 用户类型关联的站点类型
        List<Integer> hasPerSiteIdList = new ArrayList<>();
        if (null != customerType) {
            DicItem dicItem = dicItemService.selectOne(new EntityWrapper<DicItem>().eq("item_code", customerType.getItemCode()));
            if (null != dicItem && StringUtils.isNotEmpty(dicItem.getItemName())) {
                String[] idArr = dicItem.getItemName().split(",");
                for (String id : idArr) {
                    hasPerSiteIdList.add(Integer.parseInt(id.trim()));
                }
            }
        }

        return StreamUtil.getStream(list).filter(item -> CollectionUtils.isNotEmpty(hasPerSiteIdList) && hasPerSiteIdList.contains(item.getSiteType()))
                .map(item -> {
            SiteBO bo = new SiteBO();
            bo.setSiteId(item.getId());
            bo.setSiteName(item.getSiteName());
            bo.setSiteDesc(item.getSiteDesc());
            bo.setSiteType(item.getSiteType());
            bo.setSiteTypeDesc(SiteTypeEnum.findByCode(item.getSiteType()).getDesc());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增/保存站点信息
     *
     * @param reqVO
     */
    @Override
    public void save(SaveSiteReqVO reqVO) {
        if (StringUtils.isEmpty(reqVO.getSiteNo())) {
            throw new BizException("站点编号不能为空");
        }
        if (StringUtils.isEmpty(reqVO.getSiteName())) {
            throw new BizException("站点名称不能为空");
        }
        if (null == SiteTypeEnum.findByCode(reqVO.getSiteType())) {
            throw new BizException("站点类型无效");
        }
        if (StringUtils.isEmpty(reqVO.getIp())) {
            throw new BizException("绑定IP不能为空");
        }

        Site siteEntity;
        if (null == reqVO.getSiteId()) {
            Site existSiteNo = this.selectOne(new EntityWrapper<Site>().eq("site_no", reqVO.getSiteNo()));
            if (null != existSiteNo) {
                throw new BizException("站点编号已存在");
            }

            // 新增
            siteEntity = new Site();
            siteEntity.setSiteNo(reqVO.getSiteNo());
            siteEntity.setSiteName(reqVO.getSiteName());
            siteEntity.setIp(reqVO.getIp());
            siteEntity.setSiteType(reqVO.getSiteType());
            siteEntity.setSiteDesc(reqVO.getRemark());
            siteEntity.setCreateAt(new Date());
            siteEntity.setUpdateAt(new Date());

            this.insert(siteEntity);
        } else {
            try {
                siteEntity = this.baseMapper.selectById(reqVO.getSiteId());
                siteEntity.setSiteNo(reqVO.getSiteNo());
                siteEntity.setSiteName(reqVO.getSiteName());
                siteEntity.setIp(reqVO.getIp());
                siteEntity.setSiteType(reqVO.getSiteType());
                siteEntity.setSiteDesc(reqVO.getRemark());
                siteEntity.setUpdateAt(new Date());

                this.updateById(siteEntity);
            } catch (Exception e) {
                throw new BizException("站点编号已存在");
            }

            // 删除旧的站点映射关系
            this.siteMapService.delete(new EntityWrapper<SiteMap>().eq("site_id", siteEntity.getId()));
        }

        // 保存站点映射关系
        if (CollectionUtils.isNotEmpty(reqVO.getMapSiteIdList())) {
            List<SiteMap> siteMapList = reqVO.getMapSiteIdList().stream().map(item -> {
                SiteMap siteMapEntity = new SiteMap();
                siteMapEntity.setSiteId(siteEntity.getId());
                siteMapEntity.setToSiteId(item);
                siteMapEntity.setCreateAt(new Date());
                return siteMapEntity;
            }).collect(Collectors.toList());

            siteMapService.insertBatch(siteMapList);
        }
    }

    /**
     * 删除站点信息
     *
     * @param reqVO
     */
    @Override
    public void remove(RemoveSiteReqVO reqVO) {
        if (null == reqVO.getSiteId()) {
            throw new BizException("站点ID不能为空");
        }
        Site siteEntity = this.selectById(reqVO.getSiteId());
        if (null == siteEntity) {
            throw new BizException("站点无效");
        }
        this.deleteById(reqVO.getSiteId());

        // 同时删除映射和被映射的关系
        siteMapService.delete(new EntityWrapper<SiteMap>().eq("site_id", reqVO.getSiteId()));
        siteMapService.delete(new EntityWrapper<SiteMap>().eq("to_site_id", reqVO.getSiteId()));
    }

    @Override
    public List list(ListSiteReqVO reqVO) {

        Wrapper condition = new EntityWrapper<Site>().eq("1", 1);
        if (null != reqVO.getSiteType()) {
            condition.and().eq("site_type", reqVO.getSiteType());
        }
        if (StringUtils.isNotEmpty(reqVO.getIp())) {
            condition.and().like("ip", reqVO.getIp());
        }
        if (StringUtils.isNotEmpty(reqVO.getSiteNo())) {
            condition.and().like("ip", reqVO.getSiteNo());
        }
        if (StringUtils.isNotEmpty(reqVO.getSiteName())) {
            condition.and().like("ip", reqVO.getSiteName());
        }
        return this.selectList(condition);
    }

    /**
     * 查询站点详情
     *
     * @param siteId
     * @return
     */
    @Override
    public com.mxs.sampleservice.bo.admin.SiteBO getDetail(Long siteId) {
        if (null == siteId) {
            throw new BizException("站点ID不能为空");
        }
        Site siteEntity = this.selectById(siteId);
        if (null == siteEntity) {
            throw new BizException("站点无效");
        }
        com.mxs.sampleservice.bo.admin.SiteBO bo = new com.mxs.sampleservice.bo.admin.SiteBO();
        BeanUtils.copyProperties(siteEntity, bo);
        bo.setSiteId(siteEntity.getId());

        List<SiteMap> siteMapList = siteMapService.selectList(new EntityWrapper<SiteMap>().eq("site_id", siteId));
        bo.setMapSiteIdList(StreamUtil.getStream(siteMapList).map(item -> item.getToSiteId()).collect(Collectors.toList()));
        return bo;
    }
}
