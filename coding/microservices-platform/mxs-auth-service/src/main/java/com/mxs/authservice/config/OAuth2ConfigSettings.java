package com.mxs.authservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "custom.oauth2")
@Data
public class OAuth2ConfigSettings {
    private String client;
    private String secret;
    private String scopes;
    private String authorizedGrantTypes;
    private String accessTokenValiditySeconds;
    private Boolean supportRefreshToken;
}
