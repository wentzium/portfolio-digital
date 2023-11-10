package com.mxs.userservice.web.vo.admin;

import lombok.Data;

/**
 * Created by C.SY on 2019/7/10.
 */
@Data
public class ListUserReqVO {
    private int page = 1;
    private int pageSize = 10;
}
