package com.mxs.sampleservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.task.pool")
public class TaskThreadPoolConfig {
    private int corePoolSize = 5;

    private int maxPoolSize = 50;

    private int keepAliveSeconds = 60;

    private int queueCapacity = 10000;

    private String taskNamePrefix = "MXS-AsyncTask-";


}
