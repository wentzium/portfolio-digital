package com.mxs.sampleservice.bo.admin;

import lombok.Data;

/**
 * Created by j.yang on 2019-07-09.
 */
@Data
public class DicKeyBO {
    private Long id;
    /**
     * 字典key
     */
    private String code;
    /**
     * 字典描述
     */
    private String desc;
}
