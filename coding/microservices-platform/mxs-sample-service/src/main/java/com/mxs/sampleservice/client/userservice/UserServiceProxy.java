package com.mxs.sampleservice.client.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.common.util.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by j.yang on 2019-07-12
 */
@Component
public class UserServiceProxy {
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    public LoginUser getByUserId(Long userId) {
        Map data = (Map) userServiceFeignClient.getUserById(userId).getData();
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
//        return MapBeanUtils.mapToBean(LoginUser.class, data);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String value = objectMapper.writeValueAsString(data);
            LoginUser loginUser = objectMapper.readValue(value, LoginUser.class);
            return loginUser;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
