package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-07-11
 */

@Data
@Accessors(chain = true)
@TableName("t_site")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 站点编号
     */
    private String siteNo;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 站点类型 1-手术站点 2-预约站点 3-门诊站点 4-固定站点 5-运送站点 6-接收站点 7-运送站点（二级）
     */
    private Integer siteType;
    /**
     * 站点描述
     */
    private String siteDesc;
    /**
     * 站点IP地址
     */
    private String ip;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
