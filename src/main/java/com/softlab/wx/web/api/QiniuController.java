package com.softlab.wx.web.api;

import com.softlab.wx.common.util.CommonUtil;
import com.softlab.wx.common.util.ExecuteResult;
import com.softlab.wx.common.util.QiniuUtil;
import com.qiniu.api.auth.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class QiniuController {

    private static Logger logger = LoggerFactory.getLogger(QiniuController.class);


    @RequestMapping("qiniuCommon")
    private String qiniuCommon(HttpServletRequest request){
        return "qiniuCommon";
    }


/**
     * @param request
     * @param multipartFile
     * @return

*/
    @RequestMapping(value = "/qiniuUpload", method = RequestMethod.POST)
    @ResponseBody
    public String qiniuUpload( @RequestParam("imagefile") MultipartFile multipartFile,HttpServletRequest request, HttpServletResponse response) {

        ExecuteResult<String> executeResult = new ExecuteResult<String>();

        QiniuUtil qiniuUtil = new QiniuUtil();
        CommonUtil commonUtil = new CommonUtil();
        try {
/**
             * 上传文件扩展名

*/
            String filenameExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."), multipartFile.getOriginalFilename().length());

/**
             * MultipartFile 转 file 类型

*/
            File file = commonUtil.multipartToFile(multipartFile);

/**
             * 七牛云文件上传 服务  file文件 以及 文件扩展名

*/
            executeResult = qiniuUtil.uploadFile(file, filenameExtension);
            if (!executeResult.isSuccess()) {
                return "失败" + executeResult.getErrorMessages();
            }

        } catch (AuthException e) {
            logger.error("AuthException", e);
        }

        return executeResult.getResult();
    }

}
