package com.mxs.authservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mxs.authservice.entity.Role;
import com.mxs.authservice.entity.UserRole;
import com.mxs.authservice.mapper.RoleMapper;
import com.mxs.authservice.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.authservice.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author j.yang
 * @since 2019-06-21
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<Role> findRolesByUserId(Long userId) {

        List<UserRole> userRoles = this.userRoleService.selectList(new EntityWrapper<UserRole>().eq("user_id", userId));
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }

        List<Long> roleIdList = userRoles.stream().map(dto -> dto.getRoleId()).collect(Collectors.toList());

        List<Role> roleList = roleService.selectList(new EntityWrapper<Role>().in("role_id", roleIdList));

        return  roleList;
    }
}
