package com.mxs.sampleservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 转运站点列表业务对象
 * Created by j.yang on 2019-07-13
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SampleListForDeliverySiteBO {
    /**
     * 录入站点ID
     */
    private String siteId;
    /**
     * 病人编号（外部系统）
     */
    private String patientNo;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 样本组织块数
     */
    private Integer num;
    /**
     * 离体时间
     */
    private String separationTime;
    /**
     * 固定时间
     */
    private String fixedTime;
//    /**
//     * 手术护士\门诊护士 username
//     */
//    private Long OperateUserId;
//    /**
//     * 手术护士\门诊护士 中文姓名
//     */
//    private String OperateUserNameCn;

    /**
     * 固定站点用户ID
     */
    private Long fixUserId;
    /**
     * 固定站点用户 中文姓名
     */
    private String fixUserNameCn;
}
