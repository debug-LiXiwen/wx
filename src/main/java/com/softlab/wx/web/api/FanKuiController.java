package com.softlab.wx.web.api;

import com.softlab.wx.common.ErrorMessage;
import com.softlab.wx.common.RestData;
import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.core.model.vo.FanKui;
import com.softlab.wx.service.FanKuiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class FanKuiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FanKuiService fanKuiService;

    /**
     * 实现反馈添加功能
     */
    @RequestMapping(value = "/addFan", method = RequestMethod.POST)
    public RestData addFanKui(@RequestBody  FanKui fanKui , HttpServletRequest request){
        logger.info("反馈开始新增"+ JsonUtil.getJsonString(fanKui));
        if((0 >= fanKui.getNumber().length() || 10 < fanKui.getNumber().length()) || 0 >= fanKui.getName().length() || 11 != fanKui.getPhoneNumber().length()){
            return new RestData(1,"输入数据格式不符合要求");
        }else {
            boolean flag = fanKuiService.addFanKui(fanKui);
            if (flag) {
                return new RestData(fanKui.getFid());
            } else {
                return new RestData(1, ErrorMessage.POST_FANKUI_FAILED);
            }
        }
    }
}
