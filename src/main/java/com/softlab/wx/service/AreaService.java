package com.softlab.wx.service;

import com.softlab.wx.common.WxException;
import com.softlab.wx.core.model.vo.Area;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public interface AreaService {

    /**
     * 单个通过id查询
     * @param areaId
     * @return
     */
    Area selectAreaById(Integer areaId);

    /**
     * 添加报修数据
     * @param area
     * @return
     */
    boolean addArea(Area area) throws WxException;

    List<Map<String,Object>> selectAllArea() throws WxException;

    int deleteAreaById(Integer id ) ;
}
