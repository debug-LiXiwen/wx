package com.softlab.wx.service.impl;

import com.softlab.wx.common.WxException;
import com.softlab.wx.core.mapper.FanKuiMapper;
import com.softlab.wx.core.model.vo.FanKui;
import com.softlab.wx.service.FanKuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@Service
public class FanKuiServiceImpl implements FanKuiService {

    @Autowired
    private FanKuiMapper fanKuiMapper;
    /**
     * 添加反馈数据
     * @param fanKui
     * @return
     */
    @Override
    public boolean addFanKui(FanKui fanKui){
        boolean flag=false;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化当前时间
            fanKui.setTime(sdf.parse(sdf.format(new Date())));
            flag = fanKuiMapper.addFanKui(fanKui);
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map<String,Object>> selectAllFanKui() throws WxException {
        List<FanKui> list = fanKuiMapper.selectAllFanKui();
        List<Map<String,Object>> rtv = new ArrayList<>();
        if(null != list && 0 != list.size()){
            for (FanKui f : list) {
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("fid",f.getFid());
                map.put("name",f.getName());
                map.put("number",f.getNumber());
                map.put("phoneNumber",f.getPhoneNumber());
                map.put("collageMajor",f.getCollageMajor());
                map.put("detail",f.getDetail());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time",sdf.format(f.getTime()));
                rtv.add(map);
            }
        }else{
            throw new WxException("反馈内容为空");
        }
        return rtv;
    }

    @Override
    public int deleteFanKui(Integer id){
        return fanKuiMapper.deleteFanKui(id);
    }


}
