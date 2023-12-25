package ru.omb.vk_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.omb.vk_api.dto.ResponseUserDto;
import ru.omb.vk_api.entity.UserInfo;
import ru.omb.vk_api.repository.UserInfoRepository;
import ru.omb.vk_api.services.UserInfoService;
import ru.omb.vk_api.services.httpClient.GetRequestUserInfo;

import java.util.List;

@SpringBootApplication
public class VkApiApplication implements CommandLineRunner {

    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    GetRequestUserInfo getRequestUserInfo;

    public static void main(String[] args) {
        SpringApplication.run(VkApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
