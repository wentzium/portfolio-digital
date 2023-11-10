package com.mxs.sampleservice.web.vo.admin;

import lombok.Data;

/**
 * 保存字典值请求参数
 *
 *  Created by j.yang on 2019-06-12
 * 
 */
@Data
public class SaveDicItemReqVO {
    /**
     * 字典值ID（更新时必填）
     */
    private Long id;
    /**
     * 字典ID
     */
    private Long dicId;
    /**
     * 字典值名称
     */
    private String itemName;
    /**
     * 是否默认值
     */
    private Boolean isDefault;
}
