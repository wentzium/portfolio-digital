package com.mxs.sampleservice.web.vo.admin;

import lombok.Data;

import java.util.List;

/**
 * 查询站点请求参数
 *
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class ListSiteReqVO {
    private String ip;
    private String siteNo;
    private String siteName;
    private Integer siteType;
}
