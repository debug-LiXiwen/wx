package com.softlab.wx.core.model.vo;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public class Course {
    /**
     * 课程id
     */
    private String cid;
    /**
     *  课程名称
     */
    private String course;
    /**
     * 课程获赞数
     */
    private Integer zan;

    /**
     * 空参构造方法
     */
    public Course(){ }

    public String getCid() {return cid; }

    public void setCid(String cid) { this.cid = cid; }

    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }

    public Integer getZan() { return zan; }

    public void setZan(Integer zan) { this.zan = zan; }
}
