package com.mxs.authservice.auth;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.mxs.authservice.entity.Role;
import com.mxs.authservice.service.LoginUserService;
import com.mxs.authservice.service.RoleService;
import com.mxs.common.util.LoginUser;
import com.mxs.common.util.RoleContext;
import com.mxs.common.util.UserContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserService.selectOne(new EntityWrapper<LoginUser>().eq("username", username));
        List<Role> roleList = roleService.findRolesByUserId(loginUser.getId());


        UserContext userContext = new UserContext();

        BeanUtils.copyProperties(loginUser, userContext);

        List<RoleContext> authorities = Lists.newArrayList();

        for (Role role : roleList) {
            RoleContext roleContext = new RoleContext();
            roleContext.setAuthority(role.getCode());

            authorities.add(roleContext);
        }

        userContext.setAuthorities(authorities);

        return userContext;
    }
}