package com.softlab.wx.core.mapper;

import com.softlab.wx.core.model.vo.Area;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/


/**
 * 在以前的Dao层这块，hibernate和mybatis
 * 都可以使用注解或者使用mapper配置文件。在这里我们使用spring的JPA来完成基本的增删改查。
 * 说明:
 * 一般有两种方式实现与数据库实现CRUD：
 * 第一种是xml的mapper配置。
 * 第二种是使用注解，@Insert、@Select、@Update、@Delete 这些来完成。本篇使用的是第二种
 */
@Mapper
@Repository
public interface AreaMapper {
    /**
     * 根据系统ID查询地点信息
     */
    @Select("SELECT area_id AS areaId,area_location AS areaLocation,area_context AS areaContext," +
            "user_name AS userName,user_num AS userNum," +
            "user_phone AS userPhone ,time FROM mend where area_id=#{areaId}")
    Area selectById(@Param("areaId") Integer areaId);

    /**
     * 报修数据新增
     */
    @Insert("INSERT INTO mend(area_location,area_context ,user_name,user_num ,user_phone,time ) VALUES (#{areaLocation},#{areaContext},#{userName},#{userNum},#{userPhone},#{time})")
    boolean addArea(Area area);

    @Select("SELECT area_id AS areaId,area_location AS areaLocation,area_context AS areaContext," +
            "user_name AS userName,user_num AS userNum," +
            "user_phone AS userPhone,time  FROM mend ORDER BY time DESC")
    List<Area> selectAllArea();

    @Delete("delete from mend where area_id=#{id}")
    int delete(Integer id);

}
