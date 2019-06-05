package com.softlab.wx.core.mapper;

import com.softlab.wx.core.model.vo.FanKui;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Mapper
@Repository
public interface FanKuiMapper {

    /** fid; name; number; phoneNumber; collageMajor; detail;**/

    /**
     * 反馈数据新增
    */
    @Insert("INSERT INTO fank(name,number,phone_number,collage_major,detail,time  ) VALUES (#{name},#{number},#{phoneNumber},#{collageMajor},#{detail},#{time})")
    boolean addFanKui(FanKui fanKui);

    @Select("select name,number,phone_number as phoneNumber,collage_major as collageMajor,detail ,time,fid from fank order by time desc")
    List<FanKui> selectAllFanKui();

    @Delete("delete from fank where fid=#{id}")
    int deleteFanKui(Integer id);
}
