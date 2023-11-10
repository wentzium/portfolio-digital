package com.mxs.sampleservice.bo;

import lombok.Data;

/**
 * 退出提示数字
 * Created by j.yang on 2019-07-13
 */
@Data
public class ExitPromptNumBO {
    /**
     * 总数
     */
    private Integer totalNum;
    /**
     * 已处理数
     */
    private Integer dealNum;
}
