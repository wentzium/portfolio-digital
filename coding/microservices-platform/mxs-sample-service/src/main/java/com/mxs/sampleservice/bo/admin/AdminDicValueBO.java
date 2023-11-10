package com.mxs.sampleservice.bo.admin;

import lombok.Data;

/**
 * Created by j.yang on 2019-07-09.
 */
@Data
public class AdminDicValueBO {
    /**
     * 字典ID
     */
    private Long dicId;
    /**
     * 字典code
     */
    private String dicCode;
    /**
     * 字典描述
     */
    private String dicDesc;

    /**
     * 主键
     */
    private Long id;
    /**
     * 字典值code
     */
    private String code;
    /**
     * 字典值描述
     */
    private String desc;
    /**
     * 是否默认显示
     */
    private Boolean defaultShow;
    /**
     * 是否已删除 0-否 1-是
     */
    private Boolean isDeleted;
}
