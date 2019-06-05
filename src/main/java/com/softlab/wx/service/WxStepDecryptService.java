package com.softlab.wx.service;

import com.softlab.wx.core.model.vo.Bushu;
import com.softlab.wx.core.model.vo.UserRun;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

public interface WxStepDecryptService {
    String  getOpenidA();
    boolean addData(Bushu bushu, String userInfo);
    boolean insertRunData(String userOpenId,String kilometer,String runTime,String nickName );
    TreeMap<String,Object> selectAllPaceByOrder(String oppid);

    List<UserRun> selectRundata();

}
/**
    Page({

        data: {
        },

        //选择并上传图片--Max：9张
        selectImage: function () {
            wx.chooseImage({
                    count: 9,
                    sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],

            success: function (res) {
                //将图片路径循环赋值给filePath参数
                for (var i = 0; i < res.tempFilePaths.length; i++) {

                    var imgUrl = res.tempFilePaths[i];

                    wx.uploadFile({
                            //上传图片的网路请求地址
                            url: 'http://localhost:8080//upload/uploadPic',
                            //选择
                            filePath: imgUrl,
                            name: 'file',

                            success: function (res) {
                        console.log("success");
                    },

                    fail: function (res) {
                        console.log("error");
                    }
          });

                }//for循环结束

            }
    });

            console.log("****正在添加图片*****");

        },


    })

**/
