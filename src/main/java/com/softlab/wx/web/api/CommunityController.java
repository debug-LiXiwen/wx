package com.softlab.wx.web.api;

import com.softlab.wx.common.RestData;
import com.softlab.wx.common.WxException;
import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.core.model.vo.Comment;
import com.softlab.wx.core.model.vo.Community;
import com.softlab.wx.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class CommunityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }


    /**
     * 一个问题的信息，内容，评论
     * 没有返回头像
     * @param systemId
     * @param request
     * @return
     *
     */
    @RequestMapping(value = "/detail/{systemId}", method = RequestMethod.GET)
    public RestData getCommunityDetailByCommunitySystemId(@PathVariable(value = "systemId") Integer systemId, HttpServletRequest request) {
        logger.info("getCommunityDetailBySystemId : ");
        try {
            Community community = new Community();
            community.setSystemId(systemId);
            List<Map<String, Object>> al = new ArrayList<>();
            al.add(communityService.getCommunityDetailByCommunitySystemId(community));
            List<Map<String, Object>> datas = communityService.getAllComment(community);
            logger.info(JsonUtil.getJsonString(al));
            logger.info(JsonUtil.getJsonString(datas));
            return new RestData(datas, al);
        } catch (WxException e) {
            return new RestData(1, e.getMessage());
        }
    }


    /**
     * 所有问题的标题显示
     * @param request
     * @return
     */
    @RequestMapping(value = "/community", method = RequestMethod.GET)
    public RestData getAllCommunity(HttpServletRequest request) {
        logger.info("getAllCommunity : ");
        try {
            List<Map<String, Object>> data = communityService. getAllCommunity();

            return new RestData(data);
        } catch (WxException e) {
            return new RestData(1, e.getMessage());
        }
    }
    /**
     * 通过关键字搜索
     * @param keyword
     * @param request
     * @return
     */
    @RequestMapping(value = "/community/search", method = RequestMethod.GET)
    public RestData getCommunityByKeyword(@RequestParam(value = "keyword") String keyword, HttpServletRequest request){
        logger.info("getCommunityByKeyword : " + JsonUtil.getJsonString(keyword));
        try {
            List<Map<String, Object>> data = communityService.getCommunityByKeyword(keyword);
            return new RestData(data);
        } catch (WxException e) {
            return new RestData(1, e.getMessage());
        }
    }

    /**
     * 发帖
     *
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public RestData insertCommunity (@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam(value = "title") String title, @RequestParam(value = "oppidA") String oppidA, @RequestParam("content") String content, @RequestParam(value = "category") String category, HttpServletRequest request, HttpServletResponse response) throws WxException {
        logger.info("post : " + oppidA + " , " + title + " , "  + category + "");
        Community community = new Community();
        community.setCategory(category);
        community.setContent(content);
        community.setOppidA(oppidA);
        community.setTitle(title);
        if(null != file) {
            String msg = communityService.qiniuUpload(file);
            community.setPic(msg);
            try {
                boolean flag = communityService.insertCommunity(community);
                return new RestData(flag);

            } catch (WxException e) {
                return new RestData(1, e.getMessage());
            }
        }else{
            try {
                boolean flag = communityService.insertCommunity(community);
                return new RestData(flag);

            } catch (WxException e) {
                return new RestData(1, e.getMessage());
            }
        }
    }
    /**
     * 发评论  PathVariable不能接收中文
     * @param comment
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail/comment", method = RequestMethod.POST)
    public RestData insertCommunityComment(@RequestBody Comment comment, HttpServletRequest request){
        logger.info(" insertComment: " + JsonUtil.getJsonString(comment));
        //logger.info(" insertCommunity: " + JsonUtil.getJsonString(community));
        try {
            boolean flag = communityService.insertCommunityComment(comment, comment.getSystemId());
            return new RestData(flag);
        } catch (WxException e) {
            return new RestData(5,e.getMessage());
        }
    }


    /**
     * 通过oppid查询
     * @param oppid
     * @param request
     * @return
     * @throws WxException
     */
    @RequestMapping(value = "/my/{oppid}", method = RequestMethod.GET)
    public RestData selectMyCommunity(@PathVariable(value = "oppid") String oppid,  HttpServletRequest request) throws WxException {
        logger.info("selectMyCommunity: " + JsonUtil.getJsonString(oppid));

        try {
            List <Map<String, Object>> data = communityService.selectAllCommunityByWriter(oppid);
            return new RestData(data);
        } catch (WxException e) {
            return new RestData(1, e.getMessage());
        }

    }


    /**
     * 删除我的贴子
     * @param systemId
     * @param request
     * @return
     * @throws WxException
     */
    @RequestMapping(value = "/my/delete/{systemId}", method = RequestMethod.POST)
    public RestData deleteMyCommunity(@PathVariable(value = "systemId") Integer systemId, HttpServletRequest request) throws WxException{
        logger.info("deleteMyCommunity: " + JsonUtil.getJsonString(systemId));
        try {
            Community community = new Community();
            community.setSystemId(systemId);
            boolean flag = communityService.deleteCommunityBySystemId(community);
            return new RestData(flag);
        } catch (WxException e) {
            return new RestData(5,e.getMessage());
        }
    }



    /**
     * 获取审核文章
     *
     * @param request
     * @return
     * @throws WxException
     */
    @RequestMapping(value = "/getPassCommunity", method = RequestMethod.GET)
    public RestData getPassCommunity(HttpServletRequest request) throws WxException{
        logger.info("getPassCommunity");
       try {
           List<Map<String, Object>> data = communityService. getAllCommunities();
           return new RestData(data);
       } catch (WxException e) {
           return new RestData(1, e.getMessage());
       }

   }

    /**
     * 把审核通过的文章存到数据库
     * @param systemId
     * @return
     * @throws WxException
     */
    @RequestMapping(value = "/passCommunity", method = RequestMethod.GET)
    public RestData passCommunity(@RequestParam(value = "systemId") Integer systemId) throws WxException{
        logger.info("passCommunity" + JsonUtil.getJsonString(systemId));
        try {
            boolean flag = communityService.insertPassCommunity(systemId);
            Map<String, Object> rtv = communityService.deleteArticleAndCommentById(systemId);
            return new RestData(flag);
        } catch (WxException e) {
            return new RestData(1, e.getMessage());
        }
    }


    /**
     * 管理员删除不符合条件的帖子
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteArticle" , method = RequestMethod.GET)
    public Map<String, Object> deleteArticleAndCommentById(@RequestParam(value = "id") Integer id){
        logger.info("deleteArticle : ");
        Map<String, Object> rtv = new LinkedHashMap<>();
        try{
            rtv = communityService.deleteArticleAndCommentById(id);
        }catch(WxException e){
            rtv.put("code",1);
            rtv.put("message",e.getMessage());
        }
        return rtv;
    }




}

