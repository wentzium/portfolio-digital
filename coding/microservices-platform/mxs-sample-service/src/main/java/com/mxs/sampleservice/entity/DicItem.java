package com.mxs.sampleservice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-07-12
 * 
 */

@Data
@Accessors(chain = true)
@TableName("t_dic_item")
public class DicItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 字典ID
     */
    private Long dicId;
    /**
     * 编码
     */
    private String itemCode;
    /**
     * 名称
     */
    private String itemName;
    /**
     * 是否默认值 0-否 1-是
     */
    private Integer isDefault;
    /**
     * 是否删除 0-否 1-是
     */
    private Integer isDeleted;


}
