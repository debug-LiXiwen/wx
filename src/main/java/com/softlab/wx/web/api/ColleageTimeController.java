package com.softlab.wx.web.api;

import com.softlab.wx.common.RestData;
import com.softlab.wx.core.model.vo.ColleageContent;
import com.softlab.wx.service.ColleageTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class ColleageTimeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ColleageTimeService colleageTimeService;

    @Autowired
    public ColleageTimeController(ColleageTimeService colleageTimeService){
        this.colleageTimeService=colleageTimeService;
    }

    /**
     * 每个图标是否亮的接口
     * @return
     */
    @RequestMapping(value="/colleage/time",method = RequestMethod.GET)
    public RestData colleageTime(){

        return new RestData(colleageTimeService.search());

    }

    /**
     * tip小建议
     * @return
     */

    @RequestMapping(value="/colleage/tip",method = RequestMethod.GET)
    public RestData colleageTip(){
        return new RestData(colleageTimeService.selectTip());
    }

    @RequestMapping(value="/colleage/tip/detail/{bumen}",method = RequestMethod.GET)
    public RestData colleageTipDetail(@PathVariable(value="bumen") String bumen){
        return new RestData(colleageTimeService.selectContent(bumen));
    }

    /**
     * 点击小图标返回每个部门内容详情
     * @param tu
     * @return
     */
    @RequestMapping(value="/colleage/content/{tu}",method = RequestMethod.GET)
    public RestData colleageContent(@PathVariable(value="tu") String tu){
        return new RestData(colleageTimeService.selectContent(tu));
    }

    /**
     * 管理colleageContent新通知
     * @param colleageContent
     * @param httpServletRequest
     * @return
     */
    /**
     *  bumen : gongdian book  eat  swim  hospital water
     *
     */
    @RequestMapping(value = "/mangeNefuContent",method = RequestMethod.POST)
    public Map<String,Object> MangeNefuContent(@RequestBody ColleageContent colleageContent, HttpServletRequest httpServletRequest){
        return colleageTimeService.updateColleageContent(colleageContent);
    }

}
