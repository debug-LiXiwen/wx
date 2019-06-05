package com.softlab.wx.service.impl;

import com.softlab.wx.core.mapper.TimeMapper;
import com.softlab.wx.core.model.vo.Time;
import com.softlab.wx.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
@Service
public class TimeServiceImpl implements TimeService {
    @Autowired
    private TimeMapper timeMapper;

    @Override
    public Time selectAllByOpenId(String openId){
        return timeMapper.selectAllByUsername(openId);
    }

    @Override
    public int selectByOpenId(String openId) {
        return timeMapper.selectByUsername(openId);
    }

    @Override
    public void insert(Time time) {
        timeMapper.insert(time);
    }

    @Override
    public boolean update(String openId,String dateString,String userName){
        return timeMapper.update(openId,dateString,userName);
    }
}
