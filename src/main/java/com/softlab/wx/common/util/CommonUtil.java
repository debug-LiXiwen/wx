
package com.softlab.wx.common.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.ws.spi.http.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 公共工具类
 * Created by LiXiwen on 2019/3/25.
 */


public class CommonUtil {


    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */

    public File multipartToFile(MultipartFile multfile){
        File files = null;
        if (null != multfile){
            File dfile = null;
            try{
                dfile = File.createTempFile("prefix", "_" + multfile.getOriginalFilename());
                multfile.transferTo(dfile);
                files = dfile;

            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return files;



    }

}
