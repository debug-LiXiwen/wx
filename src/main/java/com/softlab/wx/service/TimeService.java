package com.softlab.wx.service;

import com.softlab.wx.core.model.vo.Time;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public interface TimeService {

    Time selectAllByOpenId(String openId);

    int selectByOpenId(String openId);

    void insert(Time time);

    boolean update(String openId,String dateString,String userName);
}
