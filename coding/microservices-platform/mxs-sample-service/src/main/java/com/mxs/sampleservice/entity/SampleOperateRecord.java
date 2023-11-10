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
@TableName("t_sample_operate_record")
public class SampleOperateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 样本主键
     */
    private Long sampleId;
    private String sampleNo;
    /**
     * 操作类型 0-新建 1-修改 2-作废 3-固定 4-接收 5-退回 6-转运
     */
    private Integer operateType;
    /**
     * 操作描述
     */
    private String operateDesc;
    /**
     * 操作站点ID（在哪个站点操作的）
     */
    private Long siteId;
    /**
     * 批次号（针对批量操作）
     */
    private String batchNo;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
