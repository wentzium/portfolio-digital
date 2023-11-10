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
@TableName("t_sample_status")
public class SampleStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 样本ID
     */
    private Long sampleId;
    /**
     * 状态值
     */
    private String statusValue;
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String memo;


}
