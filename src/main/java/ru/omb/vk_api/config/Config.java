package ru.omb.vk_api.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import ru.omb.vk_api.services.httpClient.GetRequestUserInfo;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class Config {

    @Value("${pool_size}")
    public Integer pool_size;

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        log.debug("Создание taskExecutor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(pool_size);
        executor.setMaxPoolSize(pool_size);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("UserFindAllThread-");
        executor.initialize();
        return executor;
    }

}
