package com.softlab.wx.core.mapper.provider;


import com.softlab.wx.core.model.vo.Comment;
import com.softlab.wx.core.model.vo.Community;
import org.apache.ibatis.jdbc.SQL;

/**
 * author Aries
 * date 2019-3-13
 */

public class CommunityProvider {

    public String selectCommunityByCondition(Community community){
        return new SQL() {
            {
                SELECT("community_systemId as systemId, community_title as title, community_writer as writer, " +
                        "community_pic as pic, community_pic1 as pic1, community_pic2 as pic2, community_time as time, community_content as content, " +
                        "community_viewsNumber as viewsNumber, community_likesNumber as likesNumber, " +
                        "community_commentsNumber as commentsNumber, community_icon as icon, community_oppidA as oppidA");
                FROM("community_article");
                if (null != community.getSystemId()) {
                    WHERE("community_systemId=#{systemId}");
                }
                if (null != community.getTitle()) {
                    WHERE("community_title=#{title}");
                }
                if (null != community.getWriter()) {
                    WHERE("community_writerId=#{writerId}");
                }
                WHERE("community_systemId=#{systemId}");
            }
        }.toString();
    };



    public String selectAllCommunity(){
        return new SQL() {
            {
                SELECT("community_systemId as systemId, community_title as title, community_writer as writer, " +
                        "community_pic as pic, community_time as time, community_content as content, " +
                        "community_viewsNumber as viewsNumber, community_likesNumber as likesNumber, " +
                        "community_commentsNumber as commentsNumber");
                FROM("community_article");
                ORDER_BY("time");
            }
        }.toString();
    }



    public String insertCommunity(Community community){
        return new SQL(){
            {
                INSERT_INTO("community_article");
                if (null != community.getWriter() && null != community.getPic()){
                    VALUES("community_writer", community.getWriter());
                    VALUES("community_pic", community.getPic());
                    if (null != community.getTitle()){
                        VALUES("community_title", community.getTitle());
                    }
                    if (null != community.getContent()){
                        VALUES("communtiy_content", community.getContent());
                    }
                }
            }
        }.toString();
    }



    public String insertCommunityComment(Comment comment, Community community){
        return new SQL() {
            {
                SELECT("community_systemId as systemId");
                FROM("community_article");
                if (null != community.getSystemId()){
                    INSERT_INTO("community_comment");
                    if (null != comment.getContent()) {
                        VALUES("comment_content", comment.getContent());
                    }
                    if (null != comment.getWriter()) {
                        VALUES("comment_writer", comment.getWriter());
                    }
                    if (null != comment.getPic()) {
                        VALUES("comment_pic", comment.getPic());
                    }
                    if (null != comment.getWriter()) {
                        VALUES("comment_writer", comment.getWriter());
                    }
                }
            }

        }.toString();
    }



    public String selectAllComment(int systemId){
        return new SQL() {
            {
                SELECT("comment_systemId as systemId, comment_communityId as communityId, comment_writer as writer, " +
                        "comment_pic as pic, comment_time as time, comment_content as content, comment_likesNumber as " +
                        "likesNumber, comment_isAnonym as isAnonym");
                FROM("community_comment");
                WHERE("comment_communityId=systemId");
            }
        }.toString();
    }



}
