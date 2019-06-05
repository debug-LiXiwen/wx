package com.softlab.wx.web.api;

import com.softlab.wx.common.ErrorMessage;
import com.softlab.wx.common.RestData;
import com.softlab.wx.common.WxException;
import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.core.model.vo.Area;
import com.softlab.wx.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by LiXiwen on 2019/3/10.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class AreaRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public RestData addUser(@RequestBody Area area , HttpServletRequest request) {
        logger.info("报修开始新增 : " + JsonUtil.getJsonString(area));
        if((0 >= area.getUserNum().length() || 10 < area.getUserNum().length()) || 0 >= area.getUserName().length() ||  11 != area.getUserPhone().length()){
            return new RestData(1,"输入数据格式不符合要求");
        }else {
            try {
                boolean flag = areaService.addArea(area);
                if (flag) {
                    return new RestData("success");
                } else {
                    return new RestData(1, ErrorMessage.POST_AREA_FAILED);
                }
            } catch (WxException e) {
                return new RestData(1,e.getMessage());
            }
        }

    }

}
