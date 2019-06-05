package com.softlab.wx.common.util;

import com.softlab.wx.core.model.vo.Area;
import com.softlab.wx.web.api.WxStepController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

public class TokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    public boolean panArea(Area area){
        if(null==area.getAreaLocation()){
            return false;
        }
        return false;
    }

    public static String getRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        logger.info("token= "+token);
        return token;
    }

}
