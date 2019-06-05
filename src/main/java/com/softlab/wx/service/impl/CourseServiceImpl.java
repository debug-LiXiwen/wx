package com.softlab.wx.service.impl;


import com.softlab.wx.core.mapper.CourseMapper;
import com.softlab.wx.core.model.vo.Course;
import com.softlab.wx.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> selectByOrder(){
        return courseMapper.selectByOrder();
    }
    @Override
    public Course selectByCourse(String course){
        return courseMapper.selectByCourse(course);
    }

    @Override
    public boolean add(String course,int count){
        String sql = "update course set zan=" + count + " where course=?";
        return courseMapper.add(course,count);

    }

}
