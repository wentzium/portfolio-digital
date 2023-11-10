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
 * <p>
 * 第三方接口请求日志表
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-07-14
 */
@Data
@Accessors(chain = true)
@TableName("t_third_request_log")
public class ThirdRequestLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 第三方名称 HIS、手麻系统等
     */
    private String thirdName;
    private String requestUrl;
    private String requestContent;
    private String responseContent;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
