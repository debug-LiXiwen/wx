package com.softlab.wx.web.api;

import com.softlab.wx.common.RestData;
import com.softlab.wx.core.mapper.ColleageTimeMapper;
import com.softlab.wx.core.model.vo.ColleageContent;
import com.softlab.wx.core.model.vo.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by LiXiwen on 2019/3/28 13:45.
 **/

/**
 * 版本信息
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class VersionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ColleageTimeMapper colleageTimeMapper;
    @Autowired
    public VersionController(ColleageTimeMapper colleageTimeMapper){
        this.colleageTimeMapper=colleageTimeMapper;
    }

    /**
     * 版本控制
     * @return
     */

    @RequestMapping(value = "/version",method = RequestMethod.GET)
    public RestData VersionControll(){
        HashMap<String,String> hashMap=new HashMap<>();
        Version v = colleageTimeMapper.selectVersion();
        String version=v.getVersion();
        String data=v.getData();
        hashMap.put("version",version);
        hashMap.put("data",data);
        return new RestData(hashMap);
    }


    @RequestMapping(value = "/updateVersion",method = RequestMethod.POST)
    public RestData updateVersion(@RequestBody Version version){
        HashMap<String,Object> hashMap=new HashMap<>();
        int a = colleageTimeMapper.updateVersion(version);
        if(0 < a){
            hashMap.put("code",0);
            hashMap.put("message","success");
        }else{
            hashMap.put("code",1);
            hashMap.put("message","error");
        }
        return new RestData(hashMap);
    }
}
