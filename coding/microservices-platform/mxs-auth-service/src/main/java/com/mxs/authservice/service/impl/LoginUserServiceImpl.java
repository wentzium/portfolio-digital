package com.mxs.authservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.authservice.mapper.LoginUserMapper;
import com.mxs.authservice.service.LoginUserService;
import com.mxs.common.util.LoginUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author j.yang
 * @since 2019-06-23
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {

}
