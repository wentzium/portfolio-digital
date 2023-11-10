package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.LoginUser;
import com.mxs.sampleservice.mapper.LoginUserMapper;
import com.mxs.sampleservice.service.LoginUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {

}
