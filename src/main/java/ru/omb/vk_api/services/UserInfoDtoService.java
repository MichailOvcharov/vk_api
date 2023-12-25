package ru.omb.vk_api.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.omb.vk_api.dto.UserInfoDto;
import ru.omb.vk_api.entity.UserInfo;
import ru.omb.vk_api.services.httpClient.GetRequestUserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class UserInfoDtoService {

    public UserInfo mapUserInfoDtoToUserInfo(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userInfoDto.getId());
        userInfo.setUserFirstName(userInfoDto.getFirst_name());
        userInfo.setUserLastName(userInfoDto.getLast_name());
        if (userInfoDto.getBdate() != null && !userInfoDto.getBdate().equals("null") && !userInfoDto.getBdate().equals("")) {
            String[] birthDatePart =  userInfoDto.getBdate().split("\\.");
            // Формат даты: D.M Если года нет ставлю 1900
            if (birthDatePart.length == 2) {
                Calendar calendar = new GregorianCalendar(1900, Integer.parseInt(birthDatePart[0]) , Integer.parseInt(birthDatePart[0]));
                userInfo.setUserBirthdate(calendar.getTime());
            }
            // Формат даты: D.M.YYYY
            if (birthDatePart.length == 3) {
                Calendar calendar = new GregorianCalendar(Integer.parseInt(birthDatePart[2]) , Integer.parseInt(birthDatePart[1]) - 1, Integer.parseInt(birthDatePart[0]));
                userInfo.setUserBirthdate(calendar.getTime());
            }
        }
        if (userInfoDto.getMobile_phone() != null && !userInfoDto.getMobile_phone().equals("null") && !userInfoDto.getMobile_phone().equals("")) {
            userInfo.setUserContacts(userInfoDto.getMobile_phone());
        }
        if (userInfoDto.getCity() != null && userInfoDto.getCity().getTitle() != null && !userInfoDto.getCity().getTitle().equals("")) {
            userInfo.setUserCity(userInfoDto.getCity().getTitle());
        }
        return userInfo;
    }
}
