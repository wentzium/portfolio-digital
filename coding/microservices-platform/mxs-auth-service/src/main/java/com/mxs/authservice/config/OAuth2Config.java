package com.mxs.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private OAuth2ConfigSettings oAuth2ConfigSettings;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() //将客户端的信息存储在内存中
                .withClient(oAuth2ConfigSettings.getClient()) //创建了一个Client为"user-service"的客户端
                .secret(oAuth2ConfigSettings.getSecret())
                .scopes(oAuth2ConfigSettings.getScopes()) //客户端的域
                .authorizedGrantTypes(oAuth2ConfigSettings.getAuthorizedGrantTypes()) //配置类验证类型为 refresh_token和password
                .accessTokenValiditySeconds(Integer.parseInt(oAuth2ConfigSettings.getAccessTokenValiditySeconds())); //5min过期
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(redisTokenStore())
//                .tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .tokenServices(tokenServices())
                .authenticationManager(authenticationManager);
    }

    /**
     * redis存储方式
     *
     * @return
     */
    @Bean("redisTokenStore")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtTokenEnhancer());
//    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        //注意此处需要相应的jks文件
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mxs-jwt.jks"), "mxs123456".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessToken();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mxs-jwt"));
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenStore(redisTokenStore());
        defaultTokenServices.setTokenEnhancer(jwtTokenEnhancer());
        defaultTokenServices.setSupportRefreshToken(oAuth2ConfigSettings.getSupportRefreshToken());
        return defaultTokenServices;
    }
}