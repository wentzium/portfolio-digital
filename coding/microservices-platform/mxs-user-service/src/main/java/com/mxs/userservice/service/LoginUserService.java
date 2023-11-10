package com.mxs.userservice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mxs.common.util.LoginUser;
import com.mxs.userservice.dto.AdminUserDTO;
import com.mxs.userservice.dto.UserLoginDTO;
import com.mxs.userservice.web.vo.LoginReqVO;
import com.mxs.userservice.web.vo.admin.ListUserReqVO;
import com.mxs.userservice.web.vo.admin.SaveUserReqVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
public interface LoginUserService extends IService<LoginUser> {
    UserLoginDTO login(LoginReqVO reqVO);

    boolean logout(String authorization);

    AdminUserDTO getByUserId(Long userId);

    /**
     * 新增/保存用户信息
     *
     * @param reqVO
     */
    void save(SaveUserReqVO reqVO);

    /**
     * 查询用户列表
     *
     * @param reqVO
     */
    Page<AdminUserDTO> list(ListUserReqVO reqVO);
}
