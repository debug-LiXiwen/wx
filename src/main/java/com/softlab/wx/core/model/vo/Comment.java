package com.softlab.wx.core.model.vo;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
import java.util.Date;


/**
 * author Aries
 * date 2019-3-16
 */

public class Comment {

    /**
     * 评论ID
     */
    private Integer systemId;

    /**
     * 评论问题ID
     */
    private Integer communityId;

    /**
     * 评论作者
     */
    private String writer;

    /**
     * 评论作者ID
     */
    private String oppidA;

    /**
     * 评论作者头像
     */
    private String pic;

    /**
     * 评论完成时间
     */
    private Date time;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论点赞数
     */
    private Integer likesNumber;

    /**
     * 是否匿名
     */

    private Integer isAnonym;

    /**
     * 用户排位名字
     */
    private String userPaiwei;
    /**
     * 用户排位图片
     */
    private String userImg;

    public String getUserPaiwei() {
        return userPaiwei;
    }

    public void setUserPaiwei(String userPaiwei) {
        this.userPaiwei = userPaiwei;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getWriter() {
        return writer;
    }

    public String getOppidA() { return oppidA; }

    public void setOppidA(String oppidA) { this.oppidA = oppidA; }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(Integer likesNumber) {
        this.likesNumber = likesNumber;
    }

    public Integer getIsAnonym() {
        return isAnonym;
    }

    public void setIsAnonym(Integer isAnonym){
        this.isAnonym = isAnonym;
    }
}