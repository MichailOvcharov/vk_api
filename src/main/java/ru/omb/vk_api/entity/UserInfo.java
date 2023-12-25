package ru.omb.vk_api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "user_f_name")
    private String userFirstName;
    @Column(name = "user_l_name")
    private String userLastName;
    @Column(name = "user_b_date")
    private Date userBirthdate;
    @Column(name = "user_city")
    private String userCity;
    @Column(name = "user_contacts")
    private String userContacts;

    public UserInfo() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Date getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(Date userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(String userContacts) {
        this.userContacts = userContacts;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userBirthdate=" + userBirthdate +
                ", userCity='" + userCity + '\'' +
                ", userContacts='" + userContacts + '\'' +
                '}';
    }
}
