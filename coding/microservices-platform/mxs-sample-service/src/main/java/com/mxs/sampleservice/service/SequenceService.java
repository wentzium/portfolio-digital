package com.mxs.sampleservice.service;

/**
 * 公共序列 service
 */
public interface SequenceService {
    public static final String SEQ_COMMON = "common_seq"; // 公共sequence 序列
    public static final String SEQ_VJ_FILE_ID_SEQ = "file_id"; //文件编号
    public static final int DEFAULT_ADDED_TO_LENGTH = 0; // 默认补齐查询位数
    public static final char DEFAULT_ADDED_CHARACTER = '0'; // 默认补齐填充字符

    public static final String SEQ_RECHARGE_OUT_TRADE_NO = "recharge_out_trade_no";//缴费记录唯一订单号
    public static final String SEQ_CARD_PRE_ORDER_NO = "card_pre_order_no";//卡产品订购单号

    public static final String SEQ_INVOICE_ORDER_NO = "invoice_order_no";//开票订单号
    public static final String SEQ_APPLY_NO = "apply_no"; // 申请单号
    public static final String SEQ_APPOINTMENT_APPLY_NO = "appointment_apply_no"; // 预约冰冻L申请单号
    public static final String SEQ_pathology_NO = "pathology_no"; // 测试病理号

    /**
     * 获取当前序列 默认查询 "common_seq" 公共seq
     *
     * @return
     */
    Long getCurrentLongValue();

    /**
     * 获取当前序列
     *
     * @param sequenceName 序列号名称 {@link file comm_sequence.sql}
     * @return
     */
    Long getCurrentLongValue(String sequenceName);

    /**
     * 获取当前序列 默认查询 "common_seq" 公共seq
     *
     * @return
     */
    String getCurrentStringValue();

    String getCurrentStringValue(String sequenceName);

    /**
     * 获取当前序列
     *
     * @param sequenceName  序列号名称 {@link file comm_sequence.sql}
     * @param addedToLength 补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                      不补齐
     * @return
     */
    String getCurrentStringValue(String sequenceName, int addedToLength);


    /**
     * 获取当前序列
     *
     * @param sequenceName 序列号名称 {@link file comm_sequence.sql}
     * @param businessType 业务类型
     * @param allLength    总长度
     *                     <p>
     *                     补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                     不补齐
     * @return
     */
    String getBusinessCurrentStringValue(String businessType, String sequenceName, int allLength);

    /**
     * 获取当前序列
     *
     * @param sequenceName   序列号名称 {@link file comm_sequence.sql}
     * @param addedToLength  补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                       不补齐
     * @param addedCharacter 当 addedToLength 大于0时 则 补齐的用 addedCharacter 去补齐
     * @return
     */
    String getCurrentStringValue(String sequenceName, int addedToLength, char addedCharacter);

    /**
     * 获取下一个序列 默认查询 "common_seq" 公共seq
     *
     * @return
     */
    Long getNextLongValue();

    /**
     * 获取下一个序列值
     *
     * @param sequenceName
     * @return
     */
    Long getNextLongValue(String sequenceName);

    /**
     * 获取下一个序列 默认查询 "common_seq" 公共seq
     *
     * @return
     */
    String getNextStringValue();

    String getNextStringValue(String sequenceName);

    /**
     * 获取下一个序列
     *
     * @param sequenceName  序列号名称 {@link file comm_sequence.sql}
     * @param addedToLength 补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                      不补齐
     * @return
     */
    String getNextStringValue(String sequenceName, int addedToLength);

    /**
     * 获取下一个序列
     *
     * @param sequenceName   序列号名称 {@link file comm_sequence.sql}
     * @param addedToLength  补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                       不补齐
     * @param addedCharacter 当 addedToLength 大于0时 则 补齐的用 addedCharacter 去补齐
     * @return
     */
    String getNextStringValue(String sequenceName, int addedToLength, char addedCharacter);

    /**
     * 获取下一个序列
     *
     * @param sequenceName    序列号名称 {@link file comm_sequence.sql}
     * @param addedToLength   补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
     *                        不补齐
     * @param randomNumLength 末位添加N为随机数字
     * @return
     */
    String getNextStringValue(String sequenceName, int addedToLength, int randomNumLength);

}
