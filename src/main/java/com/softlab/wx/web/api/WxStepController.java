package com.softlab.wx.web.api;

import com.google.gson.Gson;
import com.softlab.wx.common.ErrorMessage;
import com.softlab.wx.common.RestData;
import com.softlab.wx.common.util.AES;
import com.softlab.wx.common.util.HttpRequestor;
import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.core.model.vo.Bushu;
import com.softlab.wx.core.model.vo.UserRun;
import com.softlab.wx.service.WxStepDecryptService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.*;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class WxStepController {

    private static final Logger logger = LoggerFactory.getLogger(WxStepController.class);

    private final WxStepDecryptService wxStepDecryptService;
    @Autowired
    public WxStepController(WxStepDecryptService wxStepDecryptService){
        this.wxStepDecryptService=wxStepDecryptService;
    }


    private String appid = "wxde7de0ed99d29c9e";
    private String secret = "e44118aef2f5d33497615483d7af3b15";
    private String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=";
    private String url0 = "&grant_type=authorization_code";


    @RequestMapping(value="/rundata",method = RequestMethod.POST)
    public RestData runData(@RequestBody UserRun userRun ){

        logger.info("kilometer="+userRun.getKilometer()+",runtime="+userRun.getRunTime());
        boolean b=wxStepDecryptService.insertRunData(userRun.getUserOpenId(),userRun.getKilometer(),userRun.getRunTime(),userRun.getNickName());
        if(b){
            return new RestData("insert user rundata success");
        }else{
            return new RestData("insert user rundata fail");
        }
    }

    @RequestMapping(value="/selectRundata",method=RequestMethod.GET)
    public RestData selectRundata(){
        List<UserRun> list=wxStepDecryptService.selectRundata();
        return new RestData(list);
    }


    /**
     * 接收code，返回oppnid 和 sessionkey  进行下一步的获取步数
     * @param code
     * @return
     */
    @RequestMapping(value = "/onlogin")
    public String login(@RequestParam(value = "code") String code) {
        logger.info("code : "+code);
        String oppid = "";
        String newOppid="";
        JSONObject oppidObj = null;
        try {
            oppid = new HttpRequestor().doGet(url + code + url0);    //url+code+url0
            oppidObj = JSONObject.fromObject(oppid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        newOppid="["+oppid.replace('\"', '\'')+"]";

        logger.info("获取到的json串" + newOppid);
        JSONArray json = JSONArray.fromObject(newOppid); // 首先把字符串转成 JSONArray  对象
        logger.info("json.size:="+json.size());
        if(json.size()>0){
                JSONObject job = json.getJSONObject(0);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        }
        Gson gson = new Gson();
        logger.info("onlogin suceess");
        return gson.toJson(oppidObj);
    }

    @RequestMapping(value="/decrypt" ,method = RequestMethod.POST)
    public RestData decrypt(@RequestBody Bushu bushu){
        logger.info("decrypt start");
        logger.info("bushu info="+ JsonUtil.getJsonString(bushu));
        HashMap<String,String> hashMap=new LinkedHashMap<>();
        try{
            byte[] resultByte  = AES.decrypt(Base64.decodeBase64(bushu.getEncryptedData()),
                    Base64.decodeBase64(bushu.getSessionKey()),
                    Base64.decodeBase64(bushu.getIv()));
            if(null != resultByte && resultByte.length > 0){
                String userInfo = new String(resultByte, "UTF-8");

                logger.info(userInfo);
                /**
                 * 解析 此用户3天的步数，在serviceImpl中判断，
                 * 步数不存在，添加步数，
                 * 如果步数存在并且相等，则只显示，不添加，不更新，
                 * 如果步数存在不相等，更新步数。
                 * 均在  addData 中完成
                 *
                 */
                logger.info("oppidA: "+bushu.getUserId()+"days: "+bushu.getDays()+"username: "+bushu.getUserName()+"EncryptedData"+bushu.getEncryptedData());
                System.out.println("addData:"+wxStepDecryptService.addData(bushu,userInfo));

            }else{
                return new RestData(1, ErrorMessage.JIE_MI);
            }
        }catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        hashMap.put("解密情况","success");
        wxStepDecryptService.selectAllPaceByOrder(bushu.getUserId());
        return new RestData(hashMap);
    }

    @RequestMapping(value = "/selectPace", method = RequestMethod.GET)
    public RestData selectPaceDetail(@RequestParam(value = "openId") String openId){
        logger.info("selectPace start ");
        Map<String,Object> linkedHashMap=new HashMap<>(8);
        TreeMap<String,Object> treeMap = wxStepDecryptService.selectAllPaceByOrder(openId);
        Object myLinkedHashMap=treeMap.get("my");
        Object allPaceList=treeMap.get("all");
        //System.out.println(allPaceList.size()+myPace.getUserName());
        return new RestData(myLinkedHashMap,allPaceList);
    }


}
