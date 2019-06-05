package com.softlab.wx.core.mapper;

import com.softlab.wx.core.model.vo.ColleageContent;

import com.softlab.wx.core.model.vo.Version;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Mapper
@Repository
public interface ColleageTimeMapper {


    @Select("SELECT bumen,title,detail,time FROM colleagecontent WHERE bumen=#{tu}")
    ColleageContent selectContent(@Param("tu") String tu);

    @Select("SELECT bumen,closable,tip FROM colleagecontent")
    List<ColleageContent> selectTip();

    @Update("UPDATE colleagecontent SET title=#{title},detail=#{detail},time=#{time},closable=#{closable},tip=#{tip} WHERE bumen=#{bumen}")
    int updateColleageContent(ColleageContent colleageContent);

    @Select("select version,data from version")
    Version selectVersion();

    @Update("update version set version=#{version},data=#{data}")
    int updateVersion(Version version);

}
