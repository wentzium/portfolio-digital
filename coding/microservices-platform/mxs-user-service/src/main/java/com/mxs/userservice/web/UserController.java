package com.mxs.userservice.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mxs.common.util.*;
import com.mxs.userservice.client.sampleservice.SampleServiceFeignClient;
import com.mxs.userservice.dto.UserLoginDTO;
import com.mxs.userservice.entity.Apply;
import com.mxs.userservice.enums.LoginTypeEnum;
import com.mxs.userservice.enums.LoginUserStatusEnum;
import com.mxs.userservice.service.LoginUserService;
import com.mxs.userservice.web.vo.AddLoginUserReqVO;
import com.mxs.userservice.web.vo.CheckUserReqVO;
import com.mxs.userservice.web.vo.LoginReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author j.yang
 * @since 2019-07-13
 */
@Slf4j
@Api("User API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${config.value:default123}")
    private String configValue;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private SampleServiceFeignClient sampleServiceFeignClient;


    //    @HystrixCommand(
//            fallbackMethod = "buildFallbackUserList",
//            threadPoolKey = "userThreadPool",
//            threadPoolProperties =
//                    {@HystrixProperty(name = "coreSize", value = "30"),
//                            @HystrixProperty(name = "maxQueueSize", value = "10")},
//            commandProperties = {
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
//    )
    @ApiOperation("获取用户列表")
    @GetMapping("/list")
//    public ResponseData list(OAuth2Authentication authorization) {
//        log.info("Authorization: {}", authorization);
    public ResponseData list() {
        UserContext userContext = UserContextHolder.currentUser();
        log.info("Current User: {}", userContext);
        List<LoginUser> userList = loginUserService.selectList(new EntityWrapper<LoginUser>());
        return ResponseData.createSuccessResult(userList);
    }

    @ApiOperation("测试配置中心读取value")
    @GetMapping("/configValue")
    public ResponseData configValue() {
        return ResponseData.createSuccessResult(configValue);
    }

    @ApiOperation("测试feign")
    @GetMapping("/feign")
    public ResponseData feignDemo() {
        ResponseData<List<Apply>> applies = sampleServiceFeignClient.getApplies();

        return applies;
    }
//
//    @ApiOperation("测试feign2")
//    @GetMapping("/feign2")
//    public ResponseData feignDemo2() {
//        ResponseData<Map<String, Object>> applies = sampleServiceFeignClient.union();
//
//        return applies;
//    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public ResponseData postUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("nameCn") String nameCn) {
        if (StringUtils.isEmpty(username)) {
            return ResponseData.createFailedResult("用户名为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ResponseData.createFailedResult("密码为空");
        }
        if (StringUtils.isEmpty(nameCn)) {
            return ResponseData.createFailedResult("姓名不能为空");
        }
        LoginUser user = new LoginUser();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        user.setNameCn(nameCn);
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());
        boolean insert = loginUserService.insert(user);
        if (insert) {
            return ResponseData.createSuccessResult(user);
        }
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "注册失败");
    }


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

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public ResponseData addUser(@RequestBody AddLoginUserReqVO reqVO) {
        if (StringUtils.isEmpty(reqVO.getUsername())) {
            return ResponseData.createFailedResult("用户名为空");
        }
        if (StringUtils.isEmpty(reqVO.getPassword())) {
            return ResponseData.createFailedResult("密码为空");
        }
        if (StringUtils.isEmpty(reqVO.getNameCn())) {
            return ResponseData.createFailedResult("姓名不能为空");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(reqVO.getUsername());
        loginUser.setPassword(BPwdEncoderUtil.BCryptPassword(reqVO.getPassword()));
        loginUser.setStatus(LoginUserStatusEnum.NORMAL.getCode());
        loginUser.setNameCn(reqVO.getNameCn());
        loginUser.setCreateAt(new Date());
        loginUser.setUpdateAt(new Date());
        boolean insert = loginUserService.insert(loginUser);
        if (insert) {
            return ResponseData.createSuccessResult();
        }
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "添加用户失败");
    }

    @ApiOperation("登陆")
    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginReqVO reqVO) {
        LoginTypeEnum loginType = LoginTypeEnum.findByCode(reqVO.getType());
        if (null == loginType) {
            return ResponseData.createFailedResult("类型无效");
        }
        if (StringUtils.isEmpty(reqVO.getUsername())) {
            return ResponseData.createFailedResult("用户名或标识码为空");
        }

//        reqVO.setIp(UserContextHolder.getContext().getClientIp());
        UserLoginDTO userLoginDTO = loginUserService.login(reqVO);
        return ResponseData.createSuccessResult(userLoginDTO);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseData logout(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return ResponseData.createFailedResult(ResponseCode.FAILED.code, "登出失败");
        }

        boolean result = loginUserService.logout(authorization);

        new SecurityContextLogoutHandler().logout(request, null, null);
        if (result) {
            return ResponseData.createSuccessResult();
        }
        return ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, ResponseCode.SERVER_ERROR.text);
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/getUserById")
    public ResponseData getUserById(Long userId) {
        return ResponseData.builder().code(ResponseCode.SUCCESS.getCode()).data(loginUserService.selectById(userId)).build();
    }
}

