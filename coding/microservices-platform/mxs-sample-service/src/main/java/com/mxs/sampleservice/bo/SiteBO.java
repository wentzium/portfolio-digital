package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * 站点业务对象
 * Created by j.yang on 2019-07-13
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
     * 站点类型中文描述
     */
    private String siteTypeDesc;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 站点描述
     */
    private String siteDesc;
}
