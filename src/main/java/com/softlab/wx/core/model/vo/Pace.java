package com.softlab.wx.core.model.vo;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public class Pace {
    private Integer systemId;
    private String userId;
    private String userName;
    private Integer userRank;
    /**
     * 用户头像,一个网址以字符串形式存储
     */
    private String userIcon;
    private String userPaiwei;
    private Integer userPace;
    /**
     * 用户段位图片名
     */
    private String userImg;
    private String userColor;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
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

    public Integer getUserRank() {
        return userRank;
    }

    public void setUserRank(Integer userRank) {
        this.userRank = userRank;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserPaiwei() {
        return userPaiwei;
    }

    public void setUserPaiwei(String userPaiwei) {
        this.userPaiwei = userPaiwei;
    }

    public Integer getUserPace() {
        return userPace;
    }

    public void setUserPace(Integer userPace) {
        this.userPace = userPace;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }
}
