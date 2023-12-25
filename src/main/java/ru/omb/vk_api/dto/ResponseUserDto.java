package ru.omb.vk_api.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class ResponseUserDto {
    @JsonSetter("response")
    List<UserInfoDto> listUserInfoDto;

    public List<UserInfoDto> getListUserInfoDto() {
        return listUserInfoDto;
    }

    public void setListUserInfoDto(List<UserInfoDto> listUserInfoDto) {
        this.listUserInfoDto = listUserInfoDto;
    }

    @Override
    public String toString() {
        return "ResponseUserDto{" +
                "listUserInfoDto=" + listUserInfoDto +
                '}';
    }
}
