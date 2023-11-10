package com.mxs.userservice;

import com.mxs.common.util.LoginUser;
import com.mxs.userservice.mapper.LoginUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: j.yang
 * @Date: 2019-07-23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginUserMapperTest {

    @Autowired
    private LoginUserMapper userMapper;

    @Test
    public void insert() {
        LoginUser user = new LoginUser();

        user.setCreateAt(new Date());
        user.setCreateBy("zyx");
        user.setMemo("memo");
        user.setUsername("zhangyingxuan");
        user.setPassword("password");

        Integer insert = userMapper.insert(user);

        Assert.assertTrue(insert > 0);

        log.info("ID: {}", user.getId());
    }
}
