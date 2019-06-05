package com.softlab.wx.web.api;

import com.alibaba.fastjson.JSON;
import com.softlab.wx.common.RestData;
import com.softlab.wx.common.WxException;
import com.softlab.wx.core.model.vo.LogVo;
import com.softlab.wx.service.AreaService;
import com.softlab.wx.service.FanKuiService;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LiXiwen on 2019/5/27 20:24.
 **/
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class AdminApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AreaService areaService;

    @Autowired
    private FanKuiService fanKuiService;

    @RequestMapping(value = "/selectAllFanKui" , method = RequestMethod.GET)
    public RestData selectAllFanKui(){
        try{
            LogVo logVo = new LogVo();
            logVo.setApplication("wx");
            logVo.setLevel("info");
            logVo.setTag(""+this.getClass());
            logVo.setContent("lalalla");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logVo.setTimestamp(sdf.format(new Date()));
            // 1 创建HttpClinet，相当于打开浏览器
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建发送POST请求
            HttpPost httpPost = new HttpPost("http://222.27.227.103:8080/producer/send");
            ((HttpEntityEnclosingRequest) httpPost).setEntity(
                    new StringEntity(JSON.toJSONString(logVo),
                            ContentType.create("application/json", "UTF-8")));
            try {
                CloseableHttpResponse response = httpClient.execute(httpPost);
                if (response.getEntity() != null) {
                    logger.info(""+response.getStatusLine().getStatusCode(),
                            EntityUtils.toString(response.getEntity(), "UTF-8"));
                } else {
                    logger.info(""+response.getStatusLine().getStatusCode(), "");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            List<Map<String,Object>> data = fanKuiService.selectAllFanKui();
            return new RestData(data);
        }catch (WxException e){
            return new RestData(1,e.getMessage());
        }
    }


    @RequestMapping(value = "/selectAllArea" , method = RequestMethod.GET)
    public RestData selectAllArea(){
        try{
            List<Map<String,Object>> data = areaService.selectAllArea();
            return new RestData(data);
        }catch (WxException e){
            return new RestData(1,e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteArea" , method = RequestMethod.GET)
    public RestData deleteArea(@RequestParam("id") Integer id){
        int a =areaService.deleteAreaById(id);
        if(0 != a){
            return new RestData(0,"success");
        }else{
            return new RestData(1,"error");
        }
    }

    @RequestMapping(value = "/deleteFan" , method = RequestMethod.GET)
    public RestData deleteFan(@RequestParam("id") Integer id){
        int a =fanKuiService.deleteFanKui(id);
        if(0 != a){
            return new RestData(0,"success");
        }else{
            return new RestData(1,"error");
        }
    }



}
