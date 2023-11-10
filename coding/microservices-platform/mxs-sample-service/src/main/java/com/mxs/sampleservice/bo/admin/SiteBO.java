package com.mxs.sampleservice.bo.admin;

import lombok.Data;

import java.util.List;

/**
 * Created by j.yang on 2019-07-09
 */
@Data
public class SiteBO {
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 站点类型 1-手术站点 2-预约站点 3-门诊站点 4-固定站点 5-运送站点 6-接收站点'
     */
    private Integer siteType;
    /**
     * 站点编号
     */
    private String siteNo;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 备注
     */
    private String siteDesc;
    private String ip;
    /**
     * 覆盖的站点ID集合
     */
    private List<Long> mapSiteIdList;
}
