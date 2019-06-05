package com.softlab.wx.core.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by LiXiwen on 2019/4/2 16:22.
 **/
@Data
public class LogVo {
    private String level;
    private String application;
    private String tag;
    private String timestamp;
    private String content;
}