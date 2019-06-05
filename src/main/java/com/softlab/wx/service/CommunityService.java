package com.softlab.wx.service;


import com.softlab.wx.common.WxException;
import com.softlab.wx.core.model.vo.Comment;
import com.softlab.wx.core.model.vo.Community;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * author Aries
 * date 2019-3-13
 */



public interface CommunityService {

    /**
     * 根据文章systemId获取文章详情
     *
     */
    Map<String, Object> getCommunityDetailByCommunitySystemId(Community community) throws WxException;


    /**
     *打印出已审核文章
     *
     */
    List<Map<String, Object>> getAllCommunity() throws WxException;


    /**
     *打印出未审核文章
     *
     */
    List<Map<String, Object>> getAllCommunities() throws WxException;

    /**
     *文章搜索
     *
     */
    List<Map<String, Object>> getCommunityByKeyword(String keyword) throws WxException;


    /**
     * 发布文章
     *
     */
    boolean insertCommunity(Community community) throws WxException;


    /**
     * 发布可展示文章
     *
     */
    boolean insertPassCommunity(Integer SystemId) throws WxException;

    /**
     * 发布评论
     */
    boolean insertCommunityComment(Comment comment, Integer id) throws WxException;

    /**
     * 根据文章systemId获取所有评论
     *
     */
    List<Map<String, Object>> getAllComment(Community community) throws WxException;


    /**
     * 查询作者的所有问题
     */
    List<Map<String, Object>> selectAllCommunityByWriter(String oppidA) throws WxException;


    /**
     * 查询作者的问题总数
     */
    int countAllCommunityByWriter(String writer) throws WxException;


    /**
     * 删除作者问题
     */
    boolean deleteCommunityBySystemId(Community community) throws WxException;


    /**
     * 上传图片
     */
    String qiniuUpload(MultipartFile file) throws WxException;


    Map<String, Object> deleteArticleAndCommentById(Integer id) throws WxException;
}