package com.mxs.thirdpartyservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer //开启Resource Server功能
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    @Qualifier("redisTokenStore")
    TokenStore tokenStore;

    @Autowired
    private CommonSettings commonSettings;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers(commonSettings.getPermitAllUriArr()).permitAll()
//                .antMatchers("/user/login","/user/register").permitAll()
//                .antMatchers("/hystrix.stream").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                .antMatchers("/v2/**").permitAll()
                .antMatchers("/**").authenticated();

    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
    
}