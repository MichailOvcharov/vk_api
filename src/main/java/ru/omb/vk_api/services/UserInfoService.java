package ru.omb.vk_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.omb.vk_api.dto.ResponseUserDto;
import ru.omb.vk_api.dto.UserInfoDto;
import ru.omb.vk_api.entity.UserInfo;
import ru.omb.vk_api.repository.UserInfoRepository;
import ru.omb.vk_api.services.httpClient.GetRequestUserInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

    private final UserInfoRepository userInfoRepository;
    private final UserInfoDtoService userInfoDtoService;
    private final GetRequestUserInfo getRequestUserInfo;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, UserInfoDtoService userInfoDtoService, GetRequestUserInfo getRequestUserInfo) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoDtoService = userInfoDtoService;
        this.getRequestUserInfo = getRequestUserInfo;
    }

    @Async
    public CompletableFuture<List<UserInfo>> findAllUser() {
        return CompletableFuture.completedFuture(userInfoRepository.findAll());
    }

    public UserInfo findById(Integer id) {
        UserInfo userInfo;
        userInfo = userInfoRepository.findById(id).orElseThrow();
        return userInfo;
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            log.info("Изменяемый пользователь не может быть null");
        }
        findById(userInfo.getUserId());
        return userInfoRepository.save(userInfo);
    }

    public List<UserInfo> getUserInfoFromVkApi() {
        CompletableFuture<List<UserInfo>> userInfos = findAllUser();
        String ids = "";
        int i = 1;
        try {
            for (UserInfo userInfo : userInfos.get()) {
                if (i != userInfos.get().size()) {
                    ids = ids + userInfo.getUserId() + ",";
                    i++;
                } else {
                    ids = ids + userInfo.getUserId();
                }
            }
        } catch (ExecutionException | InterruptedException e ) {
            e.getMessage();
        }
        ResponseUserDto responseUserDto = getRequestUserInfo.sendHttpRequestGetUser(ids,"first_name,last_name,bdate,city,contacts");
        CompletableFuture<List<UserInfo>> userInfoList = updateAllUserInfo(responseUserDto.getListUserInfoDto());
        try {
            List<UserInfo> userInfosResult = userInfoList.get();
            return userInfosResult;
        } catch (ExecutionException | InterruptedException e) {
            e.getMessage();
        }
        return null;
    }

    @Async
    public CompletableFuture<List<UserInfo>> updateAllUserInfo(List<UserInfoDto> users) {
        for(UserInfoDto userInfoDto:users) {
            UserInfo userInfo = userInfoDtoService.mapUserInfoDtoToUserInfo(userInfoDto);
            updateUserInfo(userInfo);
        }
        return CompletableFuture.completedFuture(userInfoRepository.findAll());
    }

    public UserInfo create(UserInfo userInfo) {
        if (userInfo.getUserId() == null || userInfo.getUserId().equals("")) {
            log.info("id Пользователя не может быть нулевым или пустым!");
            return null;
        } else {
            return userInfoRepository.save(userInfo);
        }
    }

    public void delete(Integer id) {
        UserInfo userInfo = findById(id);
        userInfoRepository.delete(userInfo);
    }

}
