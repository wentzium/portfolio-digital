package com.mxs.userservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.*;
import com.mxs.userservice.bo.BizException;
import com.mxs.userservice.client.authservice.AuthServiceClient;
import com.mxs.userservice.config.CommonSettings;
import com.mxs.userservice.dto.AdminUserDTO;
import com.mxs.userservice.dto.JWT;
import com.mxs.userservice.dto.UserLoginDTO;
import com.mxs.userservice.enums.LoginTypeEnum;
import com.mxs.userservice.enums.LoginUserStatusEnum;
import com.mxs.userservice.mapper.LoginUserMapper;
import com.mxs.userservice.service.LoginUserService;
import com.mxs.userservice.web.vo.LoginReqVO;
import com.mxs.userservice.web.vo.admin.ListUserReqVO;
import com.mxs.userservice.web.vo.admin.SaveUserReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */
@Service
@Slf4j
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {
    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private CommonSettings commonSettings;

    @Override
    public UserLoginDTO login(LoginReqVO reqVO) {
        LoginTypeEnum loginType = LoginTypeEnum.findByCode(reqVO.getType());
        LoginUser loginUser = null;
        switch (loginType) {
            case USERNAME:
                loginUser = this.selectOne(new EntityWrapper<LoginUser>().eq("username", reqVO.getUsername().trim()));
                break;
            case UNIQUE_CODE:
                loginUser = this.selectOne(new EntityWrapper<LoginUser>().eq("id_code", reqVO.getUsername().trim()));
                break;
            default:
                break;
        }
        if (null == loginUser) {
            throw new UserLoginException("用户不存在");
        }
        if (!BPwdEncoderUtil.matches(reqVO.getPassword(), loginUser.getPassword())) {
            throw new UserLoginException("密码错误");
        }

        if (null != loginUser.getPasswordExpireTime() && loginUser.getPasswordExpireTime().before(new Date())) {
            throw new UserLoginException("密码已过期，请联系管理员修改");
        }
        // 登录次数+1
        LoginUser updateEntity = new LoginUser();
        updateEntity.setId(loginUser.getId());
        updateEntity.setLoginTimes(loginUser.getLoginTimes() + 1);
        updateEntity.setUpdateAt(new Date());
        updateEntity.setLastLoginTime(new Date());
        updateEntity.setLastLoginIp(reqVO.getIp());
        this.updateById(updateEntity);

        // dXNlci1zZXJ2aWNlOjEyMzQ1Ng==
//        byte[] encode = Base64.getEncoder().encode("user-service:123456".getBytes());
        byte[] encode = Base64.getEncoder().encode((commonSettings.getOauth2clientId() + ":" + commonSettings.getOauth2password()).getBytes());
        String clientUsernamePassword = new String(encode);
        JWT jwt = authServiceClient.getToken("Basic " + clientUsernamePassword, "password", reqVO.getUsername(), reqVO.getPassword());
        // 获得用户菜单
        if (jwt == null) {
            throw new UserLoginException("error internal");
        }
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(loginUser);

        return userLoginDTO;
    }

    @Override
    public boolean logout(String authorization) {
        if (StringUtils.isBlank(authorization)) {
            return false;
        }
        String token = authorization.replace("bearer ", "");
        byte[] encode = Base64.getEncoder().encode((commonSettings.getOauth2clientId() + ":" + commonSettings.getOauth2password()).getBytes());
        String clientUsernamePassword = new String(encode);
        Boolean responseData = authServiceClient.revokeToken("Basic " + clientUsernamePassword, token);
        log.info("logout: {}", responseData);
        if (responseData != null && responseData) {
            return true;
        }
        return false;
    }

    /**
     * 查询用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public AdminUserDTO getByUserId(Long userId) {
        if (null == userId) {
            throw new BizException("用户ID不能为空");
        }
        LoginUser loginUser = this.selectById(userId);
        if (null == loginUser) {
            throw new BizException("用户不存在");
        }

        AdminUserDTO dto = new AdminUserDTO();
        BeanUtils.copyProperties(loginUser, dto);

        dto.setStatusDesc(LoginUserStatusEnum.findByCode(loginUser.getStatus()).getDesc());
        dto.setCustomerTypeDesc(CustomerTypeEnum.findByCode(loginUser.getCustomerType()).getDesc());
        dto.setPasswordExpireTime(DateTimeUtil.format(loginUser.getPasswordExpireTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        dto.setLastLoginTime(DateTimeUtil.format(loginUser.getLastLoginTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        dto.setCreateAt(DateTimeUtil.format(loginUser.getCreateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
        dto.setUpdateAt(DateTimeUtil.format(loginUser.getUpdateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));

        return dto;
    }

    @Override
    public void save(SaveUserReqVO reqVO) {
        LoginUser loginUser;
        if (null == reqVO.getId()) {
            LoginUser existUsername = this.selectOne(new EntityWrapper<LoginUser>().eq("username", reqVO.getUsername()));
            if (null != existUsername) {
                throw new BizException("用户名已存在");
            }
            // 新增
            loginUser = new LoginUser();
            loginUser.setUsername(reqVO.getUsername());
            loginUser.setPassword(BPwdEncoderUtil.BCryptPassword(reqVO.getPassword()));
            loginUser.setStatus(LoginUserStatusEnum.NORMAL.getCode());
            loginUser.setNameCn(reqVO.getNameCn());
            loginUser.setCustomerType(reqVO.getCustomerType());
            loginUser.setPhone(reqVO.getPhone());
            loginUser.setDept(reqVO.getDept());
            loginUser.setDeptName(reqVO.getDeptName());
            if (null != reqVO.getStatus()) {
                loginUser.setStatus(reqVO.getStatus());
            }
            loginUser.setCreateAt(new Date());
            loginUser.setUpdateAt(new Date());

            this.insert(loginUser);
        } else {
            // 修改
            loginUser = this.selectById(reqVO.getId());
            if (StringUtils.isNotEmpty(reqVO.getPassword())) {
                loginUser.setPassword(BPwdEncoderUtil.BCryptPassword(reqVO.getPassword()));
            }
            loginUser.setNameCn(reqVO.getNameCn());
            loginUser.setCustomerType(reqVO.getCustomerType());
            loginUser.setPhone(reqVO.getPhone());
            loginUser.setDept(reqVO.getDept());
            loginUser.setDeptName(reqVO.getDeptName());
            if (null != reqVO.getStatus()) {
                loginUser.setStatus(reqVO.getStatus());
            }

            this.updateById(loginUser);
        }
        // TODO 不用用户类型，需要保存用户和站点类型之间的映射关系
    }

    /**
     * 查询用户列表
     *
     * @param reqVO
     */
    @Override
    public Page<AdminUserDTO> list(ListUserReqVO reqVO) {

        Page<LoginUser> page = new Page<>(reqVO.getPage() <= 0 ? 1 : reqVO.getPage(), reqVO.getPageSize() <= 0 ? 10 : reqVO.getPageSize());
        Page<LoginUser> pageResult = this.selectPage(page, new EntityWrapper<>());
        pageResult.getPages();

        Page<AdminUserDTO> result = new Page<>();
        BeanUtils.copyProperties(pageResult, result, "records");
        result.getPages();

        result.setRecords(StreamUtil.getStream(pageResult.getRecords()).map(item -> {
            AdminUserDTO dto = new AdminUserDTO();
            BeanUtils.copyProperties(item, dto);

            dto.setStatusDesc(LoginUserStatusEnum.findByCode(item.getStatus()).getDesc());
            dto.setCustomerTypeDesc(CustomerTypeEnum.findByCode(item.getCustomerType()).getDesc());
            dto.setPasswordExpireTime(DateTimeUtil.format(item.getPasswordExpireTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            dto.setLastLoginTime(DateTimeUtil.format(item.getLastLoginTime(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            dto.setCreateAt(DateTimeUtil.format(item.getCreateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));
            dto.setUpdateAt(DateTimeUtil.format(item.getUpdateAt(), DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND));

            return dto;
        }).collect(Collectors.toList()));

        return result;
    }
}
