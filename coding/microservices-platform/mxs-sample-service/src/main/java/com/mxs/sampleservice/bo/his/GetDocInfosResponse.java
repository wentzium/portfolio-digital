package com.mxs.sampleservice.bo.his;

import lombok.Data;

import java.util.List;

/**
 * Created by j.yang on 2019-07-09
 */
@Data
public class GetDocInfosResponse {
    private Integer GetDocInfosResult;
    private List<MedDocInfo> lstDocInfos;
}
