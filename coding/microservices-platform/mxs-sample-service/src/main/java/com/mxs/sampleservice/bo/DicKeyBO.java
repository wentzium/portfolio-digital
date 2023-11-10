package com.mxs.sampleservice.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by j.yang on 2019-07-13
 */
@Data
public class DicKeyBO {
    /**
     * 字典key
     */
    private String code;
    /**
     * 字典描述
     */
    private String desc;

    private List<DicValueBO> valueList;
}
