package com.softlab.wx.core.mapper;

import com.softlab.wx.core.model.vo.Time;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Mapper
@Repository
public interface TimeMapper {

    @Select("SELECT COUNT(*) FROM time WHERE openid=#{openId}")
    int selectByUsername(@Param("openId") String openId);

    @Select("SELECT * FROM time WHERE openId=#{openId}")
    Time selectAllByUsername(@Param("openId") String openId);

    @Insert("INSERT INTO time (username,time,openid)VALUES(#{userName},#{time},#{openId})")
    void insert(Time time);

    @Update("update time set time=#{dateString},username=#{userName} where openid=#{openId}")
    boolean update(@Param("openId") String openId ,@Param("dateString") String dateString , @Param("userName") String userName);
}
