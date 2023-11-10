package com.mxs.sampleservice.web.vo.admin;

import lombok.Data;

/**
 *  Created by j.yang on 2019-06-12
 * 
 */

@Data
public class ListDicItemReqVO {
    private int page = 1;
    private int pageSize = 10;
    private String keyName;
}
