package com.softlab.wx.core.model.vo;

import java.util.Date;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public class FanKui {
    /**
     * 系统id
     */
    private Integer fid;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生学号
     */
    private String number;
    /**
     * 学生电话号码
     */
    private String phoneNumber;
    /**
     * 学生所学课程
     */
    private String collageMajor;
    /**
     * 反馈内容
     */
    private String detail;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public FanKui(){ }

    public Integer getFid() { return fid; }

    public void setFid(Integer fid) { this.fid = fid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCollageMajor() { return collageMajor; }

    public void setCollageMajor(String collageMajor) { this.collageMajor = collageMajor; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }
}
