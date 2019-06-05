package com.softlab.wx.service;

import com.softlab.wx.core.model.vo.Course;

import java.util.List;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public interface CourseService {
    List<Course> selectByOrder();
    Course selectByCourse(String course);
    boolean add(String course,int count);
}
