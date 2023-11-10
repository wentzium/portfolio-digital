package com.mxs.sampleservice.web.vo.admin;

import lombok.Data;

import java.util.List;

/**
 * 保存站点请求参数
 *
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class SaveSiteReqVO {
    /**
     * 站点ID（更新时必填）
     */
    private Long siteId;
    /**
     * 站点类型
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
     * 站点绑定的IP
     */
    private String ip;
    /**
     * 备注
     */
    private String remark;
    /**
     * 站点覆盖（目前只有固定站点需要覆盖）
     */
    private List<Long> mapSiteIdList;
}
