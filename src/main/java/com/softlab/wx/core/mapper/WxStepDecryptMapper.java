package com.softlab.wx.core.mapper;

import com.softlab.wx.core.mapper.provider.WxStepProvider;
import com.softlab.wx.core.model.vo.Bushu;
import com.softlab.wx.core.model.vo.Pace;
import com.softlab.wx.core.model.vo.UserRun;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Mapper
@Repository
public interface WxStepDecryptMapper {

    /**
     * 跑步存储
     * @param
     * @return
     *  private String userOpenId;
     *     private Integer kilometer;
     *     private String nickName;
     *     private String runTime;
     *     private Integer runRank;
     */
    @Insert("INSERT INTO run (user_openId,user_rundata,user_runtime,user_nickname)VALUES(#{userOpenId},#{kilometer},#{runTime},#{nickName})")
    boolean insertRunData(UserRun userRun);


    /**
     * 查询所有人步数数据
     *
     * @param
     * @return
     */

    @Select("SELECT user_openId as userOpenId,user_rundata as kilometer,user_runtime as runTime,user_nickname as nickName FROM run")
    List<UserRun> selectRundata();


    @Insert("INSERT INTO bushu (user_id,user_name,user_icon,user_pace)VALUES(#{userId},#{userName},#{userIcon},#{userPace})")
    boolean insertPace(Bushu bushu);

    /*@Insert("INSERT INTO sortbushu (user_id,user_name,user_rank,user_icon,user_paiwei,user_pace,user_img,user_color) " +
            "VALUES(#{userId},#{userName},#{userRank},#{userIcon},#{userPaiwei},#{userPace},#{userImg},#{userColor})")
    */

    /**
     * 注解批量插入数据
     * MyBatis会把UserDAO的insertAll方法中的List类型的参数存入一个Map中,
     * 默认的key是”list”, 可以用@Param注解自定义名称,
     * MyBatis在调用@InsertProvide指定的方法时将此map作为参数传入,
     * 所有代码中使用List users = (List) map.get(“list”);获取list参数.
     *
     * @param pace
     * @return
     */
    @InsertProvider( type=WxStepProvider.class ,method="batchInsert")
    boolean insertNewSortPace(List<Pace> pace);

    @Select("SELECT COUNT(*) FROM sortbushu")
    int selectNewPaceNum();

    /**
     * 删除排序后表中所有内容,让主键置0
     * a、Mysql 让主键 归0: 　TRUNCATE TABLE TableName
     * b、只清空数据表，主键不归0： DELETE FROM TableName
     * @return
     */
    @Delete("TRUNCATE TABLE sortbushu")
    boolean deleteNewPaceDetail();

    @Select("SELECT user_name AS userName,user_rank AS userRank,user_icon AS userIcon,user_paiwei AS userPaiwei,user_img AS userImg,user_color AS userColor FROM bushu WHERE user_id=#{oppid} ")
    Pace selectMyPace(@Param("oppid") String oppid);

    /**
     * 在新的排序后的表中返回当前用户信息
     * @param userId
     * @return
     */
    @Select("SELECT user_name AS userName,user_rank AS userRank,user_icon AS userIcon,user_paiwei AS userPaiwei,user_pace AS userPace,user_img AS userImg,user_color AS userColor FROM sortbushu WHERE user_id=#{userId}")
    Pace selectMyPaceInNew(@Param("userId") String userId);

    /**
     * 在新的排序后的表中返回除了当前用户的前50名所有信息
     * @return
     */
    @Select("SELECT system_id AS systemId,user_id AS userId,user_name AS userName,user_rank AS userRank,user_icon AS userIcon,user_paiwei AS userPaiwei,user_pace AS userPace,user_img AS userImg,user_color AS userColor FROM sortbushu WHERE user_id!=#{oppid}")
    List<Pace> selectAllPaceInNew(@Param("oppid") String oppid);

    @Select("SELECT COUNT(*) FROM bushu")
    int selectNum();

    @Select("SELECT system_id AS systemId,user_name AS userName,user_rank AS userRank,user_icon AS userIcon,user_paiwei AS userPaiwei,user_img AS userImg,user_color AS userColor FROM bushu WHERE user_id!=#{oppid} ORDER BY user_pace DESC LIMIT 50")
    List<Pace> selectAllPace();

    @Select("SELECT system_id AS systemId,user_id AS userId,user_name AS userName,user_rank AS userRank,user_icon AS userIcon,user_paiwei AS userPaiwei,user_pace AS userPace ,user_img AS userImg,user_color AS userColor FROM bushu ORDER BY user_pace DESC")
    List<Pace> selectAllPaceByOrder();

    /**
     * 查询是否存在这个人的数据
     * @param oppid
     * @return
     */
    @Select("SELECT COUNT(*) FROM bushu WHERE user_id=#{oppid}")
    int selectById(@Param("oppid") String oppid);

    /**
     * 如果存在步数且步数不相等，则更新
     * @param bushu
     * @return
     */
    @Update("update bushu set user_pace= #{userPace} ,user_name=#{userName} ,user_icon=#{userIcon} ,user_id=#{userId} where user_id=#{userId}")
    boolean updatePace(Bushu bushu);
}
