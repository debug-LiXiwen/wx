package com.softlab.wx.service.impl;

import com.qiniu.api.auth.AuthException;
import com.softlab.wx.common.WxException;
import com.softlab.wx.common.util.CommonUtil;
import com.softlab.wx.common.util.ExecuteResult;
import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.common.util.QiniuUtil;
import com.softlab.wx.core.mapper.CommunityMapper;
import com.softlab.wx.core.model.vo.Comment;
import com.softlab.wx.core.model.vo.Community;
import com.softlab.wx.core.model.vo.Pace;
import com.softlab.wx.service.CommunityService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@Service
public class CommunityServiceImpl implements CommunityService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommunityMapper communityMapper;

    @Autowired
    public CommunityServiceImpl (CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
    }

    @Override
    public Map<String, Object> getCommunityDetailByCommunitySystemId(Community community) throws WxException {
        Map<String, Object> map = new HashMap<>();
        logger.info("CommunityServiceImpl:getCommunityDetailByCommunitySystemId start :");
        if (null != community.getSystemId()){
            List<Community> communityList = communityMapper.selectCommunityByCondition(community);
            logger.info(communityList.toString());
            if (null != communityList && 1 == communityList.size()){
                community = communityList.get(0);
                logger.info(JsonUtil.getJsonString(community));
                map.put("systemId", community.getSystemId());
                map.put("commentNumber",community.getCommentsNumber());
                map.put("title", community.getTitle());
                map.put("writer", community.getWriter());
                map.put("userPaiwei",community.getUserPaiwei());
                map.put("userPaiweiImg",community.getUserImg());
                map.put("icon", community.getIcon());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time", formatter.format(community.getTime()));
                map.put("content", community.getContent());
                map.put("category",community.getCategory());
                if (null != community.getPic()){
                    map.put("pic", community.getPic());
                }
                if (null != community.getPic1()){
                    map.put("pic1", community.getPic());
                }
                if (null != community.getPic2()){
                    map.put("pic2", community.getPic());
                }
                map.put("viewsNumber", community.getViewsNumber());
                map.put("likesNumber", community.getLikesNumber());
                map.put("commentsNumber", community.getCommentsNumber());
            }
            return map;
        }else{
            map.put("error","系统异常!");
            return map;
            //throw new WxException("系统异常!");
        }
        //return map;
    }

    @Override
    public List<Map<String, Object>> getAllCommunity() throws WxException{
        List<Map<String, Object>> al = new ArrayList<>();
        List<Community> communityList = communityMapper.selectAllCommunity();
        if (null != communityList){
            for (Community community1 : communityList){
                Map<String, Object> map = new HashMap<>(8);
                map.put("id",community1.getSystemId());
                map.put("writer", community1.getWriter());
                map.put("title", community1.getTitle());
                map.put("commentNumber",community1.getCommentsNumber());
                map.put("userPaiwei",community1.getUserPaiwei());
                map.put("userPaiweiImg",community1.getUserImg());
                if (null != community1.getPic()){
                    map.put("pic", community1.getPic());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time", formatter.format(community1.getTime()));
                map.put("viewsNumber", community1.getViewsNumber());
                map.put("icon", community1.getIcon());
                map.put("category",community1.getCategory());
                al.add(map);
            }
            return al;
        }
        else{
            throw new WxException("系统异常!");
        }

    }

    @Override
    public List<Map<String, Object>> getAllCommunities() throws WxException {
        List<Map<String, Object>> al = new ArrayList<>();
        List<Community> communityList = communityMapper.selectCommunities();
        if (null != communityList){
            for (Community community1 : communityList){
                Map<String, Object> map = new HashMap<>(8);
                map.put("writer", community1.getWriter());
                map.put("title", community1.getTitle());
                map.put("id", community1.getSystemId());
                map.put("content", community1.getContent());
                al.add(map);
            }
            return al;
        }
        else{
            throw new WxException("系统异常!");
        }
    }

    @Override
    public List<Map<String, Object>> getCommunityByKeyword(String keyword) throws WxException{
        List<Map<String, Object>> al = new ArrayList<>();
        Community community = new Community();
        List<Community> communityList = communityMapper.selectCommunityByKeyword(keyword);
        if (null != communityList){
            for (Community community1 : communityList){
                Map<String, Object> map = new HashMap<>(8);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("id",community1.getSystemId());
                map.put("time", formatter.format(community1.getTime()));
                map.put("viewsNumber", community1.getViewsNumber());
                map.put("writer", community1.getWriter());
                map.put("commentNumber",community1.getCommentsNumber());
                if (null != community1.getPic()){
                    map.put("pic", community1.getPic());
                }
                map.put("userPaiwei",community1.getUserPaiwei());
                map.put("userPaiweiImg",community1.getUserImg());
                map.put("title", community1.getTitle());
                map.put("icon", community1.getIcon());
                map.put("category",community1.getCategory());
                al.add(map);
            }
            return al;
        }
        else{
            throw new WxException("系统异常!");
        }
    }



    @Override
    public boolean insertCommunity(Community community) throws WxException{
        boolean flag = false;
        String oppidA = community.getOppidA();
        if (null != community){
            Pace pace=communityMapper.getPaiweiAndPaiweiImg(oppidA);
            community.setUserPaiwei(pace.getUserPaiwei());
            community.setUserImg(pace.getUserImg());
            community.setWriter(communityMapper.getUserName(oppidA));
            community.setTitle(community.getTitle());
            community.setContent(community.getContent());
            community.setLikesNumber(0);
            community.setCommentsNumber(0);
            community.setViewsNumber(0);
            community.setIcon(communityMapper.getUserIcon(oppidA));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = null;
            try {
                time= sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {

                e.printStackTrace();
            }
            community.setTime(time);
            int success = communityMapper.insertCommunity(community);
            if (0 < success){
                flag =true;
            }
        } else {
            throw new WxException("系统异常!");
        }
        return flag;
    }

    @Override
    public boolean insertPassCommunity(Integer systemId) throws WxException {
        boolean flag = false;
        Community community = new Community();
        community.setSystemId(systemId);

        List<Community> list = communityMapper.selectPassCommunityByCondition(community);
        if (null != list && 1 == list.size()){
            community = list.get(0);

            int success = communityMapper.insertPassCommunity(community);
            if (0 < success){
                flag = true;
            }
        } else {
            throw new WxException("系统异常!");
        }

        return flag;
    }


    @Override
    public boolean insertCommunityComment(Comment comment, Integer id) throws WxException{
        boolean flag = false;
        if (null != comment){
            Pace pace=communityMapper.getPaiweiAndPaiweiImg(comment.getOppidA());
            comment.setUserPaiwei(pace.getUserPaiwei());
            comment.setUserImg(pace.getUserImg());
            comment.setLikesNumber(0);
            comment.setCommunityId(id);
            Community community = new Community();
            community.setSystemId(id);
            List<Community> communityList = communityMapper.selectCommunityByCondition(community);
            if (null != communityList && 1 == communityList.size()){
                community = communityList.get(0);
                community.setCommentsNumber(community.getCommentsNumber() + 1);
            }
            System.out.println(comment.getOppidA());
            comment.setPic(communityMapper.getUserIcon(comment.getOppidA()));
            comment.setTime(new Date());
            boolean b=communityMapper.updateCommentNumber(community);
            System.out.println("commentnumber update :"+b);
            int success = communityMapper.insertCommunityComment(comment);
            if (0 < success){
                flag = true;
            }
        } else {
            throw new WxException("系统异常!");
        }
        return flag;
    }



    @Override
    public List<Map<String, Object>> getAllComment(Community community) throws WxException{
        List<Map<String, Object>> Al = new ArrayList<>();
        int systemId = community.getSystemId();
        List<Comment> commentList = communityMapper.selectAllComment(systemId);
        if (null != commentList){
            for (Comment comment1 : commentList){
                Map<String, Object> map = new HashMap<>(8);
                map.put("writer", comment1.getWriter());
                map.put("content", comment1.getContent());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time", formatter.format(comment1.getTime()));
                map.put("likesNumber", comment1.getLikesNumber());
                map.put("icon",comment1.getPic());
                map.put("openid",comment1.getOppidA());
                map.put("userPaiwei",comment1.getUserPaiwei());
                map.put("userPaiweiImg",comment1.getUserImg());
                Al.add(map);
            }
            return Al;
        }
        else{
            throw new WxException("系统异常!");
        }
    }


    @Override
    public List<Map<String, Object>> selectAllCommunityByWriter(String oppidA) throws WxException{
        List<Map<String, Object>> Al = new ArrayList<>();
        List <Community> communityList = communityMapper.selectAllCommunityByWriter(oppidA);
        if (null != communityList){
            for (Community community1 : communityList){
                Map<String, Object> map = new HashMap<>(8);
                map.put("id",community1.getSystemId());
                map.put("title", community1.getTitle());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time", formatter.format(community1.getTime()));
                map.put("commentsNumber", community1.getCommentsNumber());
                map.put("icon", community1.getIcon());
                if (null != community1.getPic()){
                    map.put("pic", community1.getPic());
                }
                map.put("writer", community1.getWriter());
                if(null!=community1.getUserPaiwei())
                    map.put("userPaiwei",community1.getUserPaiwei());
                else
                    map.put("userPaiwei","");
                map.put("userPaiweiImg",community1.getUserImg());
                map.put("category",community1.getCategory());
                Al.add(map);
            }
        } else{
            throw new WxException("系统异常!");
        }
        return Al;
    }


    @Override
    public int countAllCommunityByWriter(String writer) throws WxException{
        List <Community> communityList = communityMapper.selectAllCommunityByWriter(writer);
        if (null != communityList){
            return communityList.size();
        } else{
            throw new WxException("系统异常!");
        }
    }


    @Override
    public boolean deleteCommunityBySystemId(Community community) throws WxException{
        boolean flag = false;
        if (null != community) {
            int systemId = community.getSystemId();
            int success = communityMapper.deleteCommunity(systemId);
            if (0 < success){
                flag = true;
            } else {
                throw new WxException("系统异常!");
            }
        }
        return flag;
    }


    @Override
    public String qiniuUpload(MultipartFile file) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        QiniuUtil qiniuUtil = new QiniuUtil();
        CommonUtil commonUtil = new CommonUtil();
        try {
            File file_up = commonUtil.multipartToFile(file);

            String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

            executeResult = qiniuUtil.uploadFile(file_up, filenameExtension);

            if (!executeResult.isSuccess()) {
                return "失败" + executeResult.getErrorMessages();
            }

        } catch (AuthException | JSONException e) {
            logger.error("AuthException", e);
        }
        return executeResult.getResult();
    }

    @Override
    public Map<String, Object> deleteArticleAndCommentById(Integer id) throws WxException{
        Map<String, Object> rtv = new LinkedHashMap<>();
        if(0 != communityMapper.deleteArticleAndCommentById(id)){
            rtv.put("code",0);
            rtv.put("message","success");
        }else{
            throw new WxException("failed deleteArticle");
        }
        return rtv;
    }
}