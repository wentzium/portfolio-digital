package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by j.yang on 2019-07-10
 */

@Data
@Accessors(chain = true)
@TableName("t_site_map")
public class SiteMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 站点id
     */
    private Long siteId;
    /**
     * 被映射的站点ID
     */
    private Long toSiteId;

    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
