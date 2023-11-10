package com.mxs.thirdpartyservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-22
 */
@Data
@Accessors(chain = true)
@TableName("EXAM.EXAM_ITEMS")
public class ExamItems implements Serializable {

    private static final long serialVersionUID = 1L;

    private String examNo;
    private Integer examItemNo;
    private String examItem;
    private String examItemCode;
    private Double costs;
    private String examSubClass;


}
