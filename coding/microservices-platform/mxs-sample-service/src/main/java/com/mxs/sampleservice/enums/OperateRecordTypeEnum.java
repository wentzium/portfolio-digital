package com.mxs.sampleservice.enums;

/**
 * 样本操作记录类型枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum OperateRecordTypeEnum {
    /**
     * 离体
     */
    SEPARATE(0, "离体"),
    /**
     * 作废
     */
    CANCEL(1, "作废"),
    /**
     * 固定
     */
    FIX(2, "固定"),
    /**
     * 修改-固定内容
     */
    UPDATE_FIX(3, "修改固定内容"),
    /**
     * 退样(固定)
     */
    RETURN_FROM_FIX(4, "退样(固定)"),
    /**
     * 转运
     */
    TRANSFER(5, "转运"),
    /**
     * 退样(转运)
     */
    RETURN_FROM_TRANSFER(6, "退样(转运)"),
    /**
     * 转运
     */
    MULTI_TRANSFER(7, "多级转运"),
    /**
     * 接收
     */
    ACCEPT(8, "接收"),
    /**
     * 拒收
     */
    REJECT(9, "退样(接收)"),
    /**
     * 样本标签打印
     */
    SAMPLE_TAG_PRINTED(10, "样本标签打印");

    private Integer code;
    private String desc;

    OperateRecordTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OperateRecordTypeEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (OperateRecordTypeEnum e : OperateRecordTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
