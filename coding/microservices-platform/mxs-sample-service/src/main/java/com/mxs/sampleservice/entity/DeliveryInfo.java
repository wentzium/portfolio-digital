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
 * Created by j.yang on 2019-07-14
 */

@Data
@Accessors(chain = true)
@TableName("t_delivery_info")
public class DeliveryInfo implements Serializable {

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
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 物流信息描述
     */
    private String desc;
    private Date createAt;
    private String creatBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
