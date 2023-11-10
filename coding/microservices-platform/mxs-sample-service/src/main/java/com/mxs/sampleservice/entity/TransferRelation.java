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
 * 转运箱和样本关系表
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-07-14
 */
@Data
@Accessors(chain = true)
@TableName("t_transfer_relation")
public class TransferRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 转运箱编号
     */
    private String transferNo;
    /**
     * 样本ID
     */
    private Long sampleId;
    private String sampleNo;
    /**
     * 转运状态 0-正常 1-已转箱
     */
    private Integer transferStatus;
    private Date createAt;
    private String createBy;
    private Date updateAt;
    private String updateBy;
    private String memo;


}
