package com.mxs.userservice.dto;

import com.mxs.common.util.LoginUser;
import lombok.Data;

@Data
public class UserLoginDTO {

    private JWT jwt;

    private LoginUser user;
}
