package com.softlab.wx.core.model.vo;

/**
 * Created by LiXiwen on 2019/4/11 12:54.
 **/
public class UserRun {

    private Integer systemId;
    private String userOpenId;
    private String kilometer;
    private String nickName;
    private String runTime;
    private Integer runRank;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public Integer getRunRank() {
        return runRank;
    }

    public void setRunRank(Integer runRank) {
        this.runRank = runRank;
    }
}
