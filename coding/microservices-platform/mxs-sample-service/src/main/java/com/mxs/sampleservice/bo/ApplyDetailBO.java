package com.mxs.sampleservice.bo;

import com.mxs.sampleservice.web.vo.CheckItemVO;
import lombok.Data;

import java.util.List;

/**
 * Created by j.yang on 2019-07-13
 */
@Data
public class ApplyDetailBO {
    private PatientInfoBO patientInfo;
    private List<SampleBO> sampleList;
    /**
     * 检查项目列表
     */
    private List<CheckItemVO> checkItemList;
}
