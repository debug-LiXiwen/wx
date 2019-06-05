package com.softlab.wx.core.model.vo;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

public class Bushu {
    private String userId;
    private String userName;
    private String userIcon;
    private Integer userPace;
    private String encryptedData;
    private String iv;
    private String sessionKey;
    private Integer days;
    private String openId;


    public Integer getUserPace() {
        return userPace;
    }

    public void setUserPace(Integer userPace) {
        this.userPace = userPace;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
