package com.softlab.wx.service.impl;

import com.softlab.wx.common.WxException;
import com.softlab.wx.core.mapper.AreaMapper;
import com.softlab.wx.core.model.vo.Area;
import com.softlab.wx.service.AreaService;
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
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    /**
     * 通过id查询
     * @param userId
     * @return
     */
    @Override
    public Area selectAreaById(Integer userId) {
        return areaMapper.selectById(userId);
    }

    /**
     * 添加报修数据
     * @param area
     * @return
     */
    @Override
    public boolean addArea(Area area) throws WxException {
        boolean flag=false;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化当前时间
            area.setTime(sdf.parse(sdf.format(new Date())));
            flag = areaMapper.addArea(area);
        }catch(Exception e){
            throw new WxException("error");
        }
        return flag;
    }

    @Override
    public List<Map<String,Object>> selectAllArea() throws WxException{
        List<Area> list = areaMapper.selectAllArea();
        List<Map<String,Object>> rtv  = new ArrayList<>();
        if(null != list && 0 != list.size()){
            for(Area area : list ){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("areaId",area.getAreaId());
                map.put("name",area.getUserName());
                map.put("number",area.getUserNum());
                map.put("phoneNumber",area.getUserPhone());
                map.put("context",area.getAreaLocation());
                map.put("location",area.getAreaLocation());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("time",sdf.format(area.getTime()));
                rtv.add(map);
            }
        }else{
            throw new WxException("报修内容为空");
        }
        return rtv;
    }

    @Override
    public int deleteAreaById(Integer id) {
        return areaMapper.delete(id);

    }
}
