package ru.omb.vk_api.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CityDto {
    @JsonSetter("id")
    private Integer id;
    @JsonSetter("title")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CityDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
