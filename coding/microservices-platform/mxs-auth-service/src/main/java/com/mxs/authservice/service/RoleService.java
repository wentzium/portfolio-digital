package com.mxs.authservice.service;

import com.mxs.authservice.entity.Role;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author j.yang
 * @since 2019-06-21
 */
public interface RoleService extends IService<Role> {

    List<Role> findRolesByUserId(Long userId);
}
