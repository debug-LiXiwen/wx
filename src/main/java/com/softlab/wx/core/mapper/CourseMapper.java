package com.softlab.wx.core.mapper;

import com.softlab.wx.core.model.vo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Mapper
@Repository
public interface CourseMapper {

    /**
     * 查询是否有某个课程
     */
    @Select("SELECT * FROM course where course=#{course}")
    Course selectByCourse(@Param("course") String course);

    @Select("SELECT * FROM course ORDER BY zan DESC LIMIT 10")
    List<Course> selectByOrder();
    /**
     * 实现让点赞数增加
     */
    @Update("update course set zan= #{count} where course=#{course}")
    boolean add(String course,int count);

}
