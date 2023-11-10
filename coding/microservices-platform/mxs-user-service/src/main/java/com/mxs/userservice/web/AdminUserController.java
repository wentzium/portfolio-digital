package com.mxs.userservice.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.common.util.*;
import com.mxs.userservice.bo.BizException;
import com.mxs.userservice.dto.AdminUserDTO;
import com.mxs.userservice.enums.LoginTypeEnum;
import com.mxs.userservice.service.LoginUserService;
import com.mxs.userservice.web.vo.CheckUserReqVO;
import com.mxs.userservice.web.vo.admin.ListUserReqVO;
import com.mxs.userservice.web.vo.admin.SaveUserReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author j.yang
 * @since 2019-07-13
 */
@Slf4j
@Api("后台管理用户接口")
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private LoginUserService loginUserService;

    @ApiOperation("检查用户是否存在")
    @PostMapping("/checkUser")
    public ResponseData checkUser(@RequestBody CheckUserReqVO reqVO) {
        LoginTypeEnum loginType = LoginTypeEnum.findByCode(reqVO.getType());
        if (null == loginType) {
            return ResponseData.createFailedResult("类型无效");
        }
        if (StringUtils.isEmpty(reqVO.getUsername())) {
            return ResponseData.createFailedResult("用户名或标识码为空");
        }
        LoginUser loginUser = null;
        switch (loginType) {
            case USERNAME:
                loginUser = loginUserService.selectOne(new EntityWrapper<LoginUser>().eq("username", reqVO.getUsername().trim()));
                break;
            case UNIQUE_CODE:
                loginUser = loginUserService.selectOne(new EntityWrapper<LoginUser>().eq("id_code", reqVO.getUsername().trim()));
                break;
            default:
                break;
        }
        if (null == loginUser) {
            return ResponseData.createFailedResult("用户名不存在");
        }
        return ResponseData.createSuccessResult();
    }

    @ApiOperation("添加/保存用户信息")
    @PostMapping("/save")
    public ResponseData save(@RequestBody SaveUserReqVO reqVO) {
        if (StringUtils.isEmpty(reqVO.getUsername())) {
            return ResponseData.createFailedResult("用户名不能为空");
        }
        // 新建密码不能为空
        if (null == reqVO.getId() && StringUtils.isEmpty(reqVO.getPassword())) {
            return ResponseData.createFailedResult("密码不能为空");
        }
        if (StringUtils.isEmpty(reqVO.getNameCn())) {
            return ResponseData.createFailedResult("姓名不能为空");
        }
        if (null == reqVO.getCustomerType()) {
            return ResponseData.createFailedResult("用户类型不能为空");
        }
        if (null == CustomerTypeEnum.findByCode(reqVO.getCustomerType())) {
            return ResponseData.createFailedResult("用户类型无效");
        }
        try {
            loginUserService.save(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).msg("操作成功").build();
        } catch (BizException e) {
            log.error("AdminUserController->save 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg(e.getMessage()).build();
        } catch (Exception e) {
            log.error("AdminUserController->save 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg("操作失败").build();
        }
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/getUserDetail")
    public ResponseData getUserDetail(@RequestParam("id") Long id) {
        try {
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(loginUserService.getByUserId(id)).msg("查询成功").build();
        } catch (BizException e) {
            log.error("AdminUserController->getUserDetail 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg(e.getMessage()).build();
        } catch (Exception e) {
            log.error("AdminUserController->getUserDetail 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg("查询失败").build();
        }
    }

    @ApiOperation("用户列表查询")
    @PostMapping("/list")
    public ResponseData list(@RequestBody ListUserReqVO reqVO) {
        try {
            Page<AdminUserDTO> result = loginUserService.list(reqVO);
            return ResponseData.builder().code(ResponseCode.SUCCESS.getCode())
                    .data(result)
                    .paging(Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build())
                    .msg("查询成功").build();
        } catch (BizException e) {
            log.error("AdminUserController->save 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg(e.getMessage()).build();
        } catch (Exception e) {
            log.error("AdminUserController->save 业务异常", e.getMessage());
            return ResponseData.builder().code(ResponseCode.FAILED.getCode()).msg("操作失败").build();
        }
    }
}

