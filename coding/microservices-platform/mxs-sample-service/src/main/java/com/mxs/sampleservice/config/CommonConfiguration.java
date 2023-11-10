package com.mxs.sampleservice.config;

import com.mxs.common.config.UserContextInterceptor;
import com.mxs.sampleservice.hystrix.SpringCloudHystrixConcurrencyStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CommonConfiguration extends WebMvcConfigurerAdapter{
	/**
	 * 请求拦截器
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }
	
    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的用户上下文信息
     */
//    @Bean
//    @ConditionalOnClass(Feign.class)
//    public FeignUserContextInterceptor feignTokenInterceptor() {
//        return new FeignUserContextInterceptor();
//    }

    /**
     * RestTemplate拦截器
     * @return
     */
//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
//        return restTemplate;
//    }
    
    @Bean
	public SpringCloudHystrixConcurrencyStrategy springCloudHystrixConcurrencyStrategy() {
		return new SpringCloudHystrixConcurrencyStrategy();
	}



}