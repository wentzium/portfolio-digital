package com.mxs.userservice.client.authservice;

import com.mxs.userservice.dto.JWT;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }

    @Override
    public Boolean revokeToken(String authorization, String token) {
        return null;
    }
}