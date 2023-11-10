package com.mxs.sampleservice.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by j.yang on 2019-08-12
 */

@Data
@Accessors(chain = true)
@TableName("t_check_item")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 申请单ID
     */
    private Long applyId;
    /**
     * 检查项目
     */
    private String checkItem;
    /**
     * 删除标记 0-未删除 1-已删除
     */
    private Integer deleteFlag;
    /**
     * 创建日期
     */
    private Date createAt;
    /**
     * 修改日期
     */
    private Date updateAt;


}
