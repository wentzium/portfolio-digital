package com.mxs.thirdpartyservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Data
@RefreshScope
public class CommonSettings {

    @Value("${custom.security.permitAllUriArr}")
    private String[] permitAllUriArr;

    @Value("${custom.oauth2.clientId}")
    private String oauth2clientId;

    @Value("${custom.oauth2.password}")
    private String oauth2password;

}
