package com.softlab.wx.service.impl;

import com.softlab.wx.core.mapper.WxStepDecryptMapper;
import com.softlab.wx.core.model.vo.Bushu;
import com.softlab.wx.core.model.vo.Pace;
import com.softlab.wx.core.model.vo.UserRun;
import com.softlab.wx.service.WxStepDecryptService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@Service
public class WxStepDecryptServiceImpl implements WxStepDecryptService {

    private static final Logger logger = LoggerFactory.getLogger(WxStepDecryptService.class);

    private final WxStepDecryptMapper wxStepDecryptMapper;
    public  String openidA;
    @Autowired
    public WxStepDecryptServiceImpl(WxStepDecryptMapper wxStepDecryptMapper){
        this.wxStepDecryptMapper=wxStepDecryptMapper;
    }





    @Override
    public boolean insertRunData(String userOpenId,String kilometer, String runTime, String nickName) {

        UserRun userRun=new UserRun();
        userRun.setUserOpenId(userOpenId);
        userRun.setKilometer(kilometer);
        userRun.setNickName(nickName);
        userRun.setRunTime(runTime);
        //wxStepDecryptMapper.insertRunData(userRun);
        return wxStepDecryptMapper.insertRunData(userRun);
    }



    @Override
    public boolean addData(Bushu bushu, String userInfo){
        openidA=bushu.getUserId();
        int paceSum=0;
        int n;
        //先转成JSONBbject对象
        JSONObject jsonObject = JSON.parseObject(userInfo);
        //获取其中key为stepInfoList的jsonArray
        JSONArray jsonArray=jsonObject.getJSONArray("stepInfoList");

        for (int i = 28; i < jsonArray.size(); i++) {
            JSONObject job=jsonArray.getJSONObject(i);
            int s1 =(Integer) job.get("timestamp");
            int a=(Integer) job.get("step");
            System.out.println("timestamp: " +i+"  "+ s1+"  step="+a);
            paceSum+=a;
        }
        if(bushu.getUserName().length()>5){
            //String s=bushu.getUserName().substring(0,3)+".";
            bushu.setUserName(bushu.getUserName().substring(0,3)+".");
        }
        logger.info("名字为 "+bushu.getUserName()+" "+bushu.getDays()+"天的步数和= "+paceSum);
        bushu.setUserPace(paceSum);
        /**
         * 如果不存在  添加
         * 否则  更新
         */
        n=wxStepDecryptMapper.selectById(bushu.getUserId());
        if(0==n){
            boolean b= wxStepDecryptMapper.insertPace(bushu);
            System.out.println("插入状态:"+b);
            if(!b){
                return false;
            }
            else{
                return true;
            }
        }else{
            System.out.println("更新数据");
            return wxStepDecryptMapper.updatePace(bushu);
        }

    }


    @Override
    public String getOpenidA(){
        return openidA;
    }


    /**
     * 返回包含个人的和所有人的信息
     * @param oppid
     * @return list
     */
    @Override
    public TreeMap<String,Object> selectAllPaceByOrder(String oppid){
        System.out.println("执行返回数据接口");
        List<Map<String,Object>> list=new ArrayList<>();
        //LinkedHashMap<String,String> linkedHashMap=new LinkedHashMap<String,String>();
        List<Pace> pacesOld=new ArrayList<Pace>();
        List<Pace> pacesNew=new ArrayList<Pace>();
        pacesOld=wxStepDecryptMapper.selectAllPaceByOrder();
        int n=wxStepDecryptMapper.selectNum();


        int m1=(int)(n*1/55);
        int m2=(int)(n*2/55);
        int m3=(int)(n*3/55);
        int m4=(int)(n*4/55);
        int m5=(int)(n*5/55);
        int m6=(int)(n*6/55);
        int m7=(int)(n*7/55);
        int m8=(int)(n*8/55);
        int m9=(int)(n*9/55);


        int n1=m1+1;
        int n2=m2+m1+1;
        int n3=m3+m2+m1+1;
        int n4=m4+m3+m2+1;
        int n5=m5+m4+m3+m2+m1+1;
        int n6=m6+m5+m4+m3+m2+m1+1;
        int n7=m7+m6+m5+m4+m3+m2+m1+1;
        int n8=m8+m7+m6+m5+m4+m3+m2+m1+1;
        int n9=m9+m8+m7+m6+m5+m4+m3+m2+m1+1;

       /* System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);
        System.out.println(n4);
        System.out.println(n5);
        System.out.println(n6);
        System.out.println(n7);
        System.out.println(n8);
        System.out.println(n9);*/


        int i=1;
        //for(Pace p : pacesOld){
        for(int j=0;j<pacesOld.size();j++){
            Pace p=pacesOld.get(j);
            if(i<=n1){
                p.setUserColor("#fff143");
                p.setUserImg("wang");
                p.setUserPaiwei("冠军王者");
            }
            if(i>n1&&i<=n2){
                p.setUserColor("#ffcc66");
                p.setUserImg("xing");
                p.setUserPaiwei("冲刺星耀");
            }
            if(i>n2&&i<=n3){
                p.setUserColor("#725E82");
                p.setUserImg("da");
                p.setUserPaiwei("力行大师");
            }
            if(i>n3&&i<=n4){
                p.setUserColor("#ed5736");
                p.setUserImg("zuan");
                p.setUserPaiwei("运动钻石");
            }
            if(i>n4&&i<=n5){
                p.setUserColor("#0094ff");
                p.setUserImg("bo");
                p.setUserPaiwei("健步铂金");
            }
            if(i>n5&&i<=n6){
                p.setUserColor("#eacd76");
                p.setUserImg("huang");
                p.setUserPaiwei("速行黄金");
            }
            if(i>n6&&i<=n7){
                p.setUserColor("#e9e7ef");
                p.setUserImg("bai");
                p.setUserPaiwei("行走白银");
            }
            if(i>n7&&i<=n8){
                p.setUserColor("#a78e44");
                p.setUserImg("qing");
                p.setUserPaiwei("踱步青铜");
            }
            if(i>n8&&i<=n9){
                p.setUserColor("#1bd1a5");
                p.setUserImg("zhai");
                p.setUserPaiwei("宅家咸鱼");
            }
            if(i>n9){
                p.setUserColor("#003371");
                p.setUserImg("tang");
                p.setUserPaiwei("躺着不动");

            }
            pacesNew.add(p);
            p.setUserRank(i);
            i++;
            //System.out.println(i);

        }
        boolean b;
        if(0!=wxStepDecryptMapper.selectNewPaceNum()){
            b= wxStepDecryptMapper.deleteNewPaceDetail();
            logger.info("删除是否成功："+b);
            //批量插入
            //wxStepDecryptMapper.insertNewSortPace(paces);
            System.out.println("已经有数据，删除后再插入数据状态："+wxStepDecryptMapper.insertNewSortPace(pacesNew));
            /*for(Pace p1:paces){
                wxStepDecryptMapper.insertNewSortPace(p1);
            }*/
        }else{
            //批量插入
            System.out.println("无数据，直接插入");
            //wxStepDecryptMapper.insertNewSortPace(paces);
            System.out.println("插入状态："+wxStepDecryptMapper.insertNewSortPace(pacesNew));
        }

        List<Pace> paces1=new ArrayList<>();
        //获取个人信息
        Pace myPace = wxStepDecryptMapper.selectMyPaceInNew(oppid);
        Map<String, Object> myLinkedHashMap=new HashMap<>(8);
        myLinkedHashMap.put("Irank",myPace.getUserRank());
        myLinkedHashMap.put("Iicon",myPace.getUserIcon());
        myLinkedHashMap.put("Iname",myPace.getUserName());
        myLinkedHashMap.put("Ipaiwei",myPace.getUserPaiwei());
        myLinkedHashMap.put("Ipace",myPace.getUserPace());

        System.out.println(myPace.getUserPace());

        myLinkedHashMap.put("Iimg",myPace.getUserImg());
        myLinkedHashMap.put("Istyle",myPace.getUserColor());

        System.out.println(myPace.getUserName());

        TreeMap<String,Object> treeMap=new TreeMap<>();

        paces1=wxStepDecryptMapper.selectAllPaceInNew(oppid);

        for(Pace p2:paces1){
            Map<String, Object> linkedHashMap=new HashMap<>(8);
            linkedHashMap.put("rank",p2.getUserRank());
            linkedHashMap.put("icon",p2.getUserIcon());
            linkedHashMap.put("name",p2.getUserName());
            linkedHashMap.put("paiwei",p2.getUserPaiwei());
            linkedHashMap.put("pace",p2.getUserPace());
            linkedHashMap.put("img",p2.getUserImg());
            linkedHashMap.put("style",p2.getUserColor());
            list.add(linkedHashMap);
        }
        treeMap.put("my",myLinkedHashMap);
        treeMap.put("all",list);
        return treeMap;
    }

    @Override
    public List<UserRun> selectRundata() {
        return wxStepDecryptMapper.selectRundata();
    }

}
