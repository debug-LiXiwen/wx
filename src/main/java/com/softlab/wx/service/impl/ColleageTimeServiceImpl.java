package com.softlab.wx.service.impl;

import com.softlab.wx.core.mapper.ColleageTimeMapper;
import com.softlab.wx.core.model.vo.ColleageContent;
import com.softlab.wx.core.model.vo.ColleageTime;
import com.softlab.wx.service.ColleageTimeService;
import org.apache.tomcat.jni.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ColleageTimeServiceImpl implements ColleageTimeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ColleageTimeMapper colleageTimeMapper;

    @Autowired
    public ColleageTimeServiceImpl(ColleageTimeMapper colleageTimeMapper){
        this.colleageTimeMapper=colleageTimeMapper;
    }


    @Override
    public Map<String,Object> updateColleageContent(ColleageContent colleageContent) {
        Map<String,Object> rtv = new HashMap<>();
        if(0 < colleageTimeMapper.updateColleageContent(colleageContent)){
            rtv.put("code",0);
            rtv.put("message","success");
        }else{
            rtv.put("code",1);
            rtv.put("message","error");
        }
        return rtv;
    }

    @Override
    public HashMap<String,String> selectTip(){
        HashMap<String,String> hashMap=new HashMap<>();
        List<ColleageContent> tips=colleageTimeMapper.selectTip();
        List<ColleageContent> newTips=new ArrayList<>();
        for(ColleageContent tip:tips){
            if(tip.getClosable().equals("true")){
                newTips.add(tip);
            }
        }
        /**
         * 如果没有新通知，则跳到供电页面，需要在供电页面加入“暂时没有新通知”
         */
        System.out.println(newTips.size());
        ColleageContent tip=new ColleageContent();
        if(newTips.size()==0){
            hashMap.put("kind","gongdian");
            hashMap.put("closable","false");
            hashMap.put("tip","暂时没有新通知");
        }else {
            tip = newTips.get(new Random().nextInt(newTips.size()));
            hashMap.put("kind",tip.getBumen());
            hashMap.put("closable",tip.getClosable());
            hashMap.put("tip",tip.getTip());
        }

        return hashMap;
    }

    @Override
    public HashMap<String,Object> selectContent(String tu){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String,Object> hashMap=new HashMap<>();
        ColleageContent colleageContent=colleageTimeMapper.selectContent(tu);
        hashMap.put("title",colleageContent.getTitle());
        hashMap.put("detail",colleageContent.getDetail());
        hashMap.put("time",colleageContent.getTime());
        return hashMap;
    }


    @Override
    public HashMap<String,String> search() {

        HashMap<String,String> hashMap=new HashMap<>();
        // 设置每天日期格式
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm");
        //设置判断是否是周末日期格式
        SimpleDateFormat zmdateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar today=Calendar.getInstance();

        ColleageTime colleageTime=new ColleageTime();
        colleageTime.setElectric("http://www.ar1es.cn/gongdian.png");
        colleageTime.setBath("http://www.ar1es.cn/water.png");
        colleageTime.setHall("http://www.ar1es.cn/eat.png");
        colleageTime.setHospital("http://www.ar1es.cn/hospital.png");
        colleageTime.setLibrary("http://www.ar1es.cn/book.png");
        colleageTime.setSwim("http://www.ar1es.cn/swim.png");
        //判断供电，返回供电状态
        hashMap.put("electric",Electric(colleageTime,dateFormat,zmdateFormat,today));
        //判断图书馆状态，返回 yes ,no
        hashMap.put("library",Library(colleageTime,dateFormat,today));
        //判断游泳馆
        hashMap.put("swim",Swim(colleageTime,dateFormat,today));
        //判断浴池
        hashMap.put("bath",Bath(colleageTime,dateFormat,zmdateFormat,today));
        //判断校医院
        hashMap.put("hospital",Hospital(colleageTime,dateFormat,zmdateFormat,today));
        //判断食堂
        hashMap.put("hall",Hall(colleageTime,dateFormat));
        return hashMap;
    }


    public String Hall(ColleageTime colleageTime,SimpleDateFormat dateFormat){
        String sh="";
        Date now=null;
        Date hallBeginTime=null;
        Date hallEndTime=null;
        try {
            now = dateFormat.parse(dateFormat.format(new Date()));
            hallBeginTime = dateFormat.parse("06:00");
            hallEndTime = dateFormat.parse("21:30");
            sh = belongCalendar(now, hallBeginTime, hallEndTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sh=="1"){
            sh=colleageTime.getHall();
        }else{
            sh="http://www.ar1es.cn/eat1.png";
        }
        return sh;
    }
    public String Hospital(ColleageTime colleageTime,SimpleDateFormat dateFormat,SimpleDateFormat zmdateFormat,Calendar today){
        String sho="";
        Date now=null;
        Date hospitalBeginTime=null;
        Date hospitalEndTime=null;
        Date hospitalBeginTime2=null;
        Date hospitalEndTime2=null;
        try{
            now=dateFormat.parse(dateFormat.format(new Date()));
            hospitalBeginTime=dateFormat.parse("08:00");
            hospitalEndTime=dateFormat.parse("11:30");
            String s1=belongCalendar(now,hospitalBeginTime,hospitalEndTime);
            hospitalBeginTime2=dateFormat.parse("13:30");
            hospitalEndTime2=dateFormat.parse("17:30");
            String s2=belongCalendar(now,hospitalBeginTime2,hospitalEndTime2);
            if(s1=="1"||s2=="1"){
                sho="1";
            }else{
                sho="0";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(sho=="1"){
            sho=colleageTime.getHospital();
        }else{
            sho="http://www.ar1es.cn/hospital1.png";
        }
        return sho;

    }
    public String Bath(ColleageTime colleageTime,SimpleDateFormat dateFormat,SimpleDateFormat zmdateFormat,Calendar today){
        String sb="";
        Date now=null;
        Date bathBeginTime=null;
        Date bathEndTime=null;
        if(today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
            try{
                now=dateFormat.parse(dateFormat.format(new Date()));
                bathBeginTime=dateFormat.parse("09:00");
                bathEndTime=dateFormat.parse("20:00");
                sb=belongCalendar(now,bathBeginTime,bathEndTime);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else if(today.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
            sb="0";
        }else{
            try{
                now=dateFormat.parse(dateFormat.format(new Date()));
                bathBeginTime=dateFormat.parse("11:00");
                bathEndTime=dateFormat.parse("20:00");
                sb=belongCalendar(now,bathBeginTime,bathEndTime);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if(sb=="1"){
            sb=colleageTime.getBath();
        }else{
            sb="http://www.ar1es.cn/water1.png";
        }
        return sb;
    }
    public String Swim(ColleageTime colleageTime,SimpleDateFormat dateFormat,Calendar today){
        String sw="";
        Date now=null;
        Date swimBeginTime=null;
        Date swimEndTime=null;
        try {
            now = dateFormat.parse(dateFormat.format(new Date()));
            swimBeginTime = dateFormat.parse("06:00");
            swimEndTime = dateFormat.parse("19:30");
            sw = belongCalendar(now, swimBeginTime, swimEndTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sw=="1"){
            sw=colleageTime.getSwim();
        }else{
            sw="http://www.ar1es.cn/swim1.png";
        }
        return sw;
    }
    public String Library(ColleageTime colleageTime,SimpleDateFormat dateFormat,Calendar today){
        String sl="";
        Date now=null;
        Date libraryBeginTime=null;
        Date libararyEndTime=null;
        if(today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
            System.out.println("今天是周三，图书管下午闭关");
            //开始判断
            try {
                now = dateFormat.parse(dateFormat.format(new Date()));
                libraryBeginTime = dateFormat.parse("12:00");
                libararyEndTime = dateFormat.parse("18:00");
                sl = belongCalendar(now, libraryBeginTime, libararyEndTime);
                System.out.println("sl:"+sl);
                if(sl.equals("1")){
                    sl="http://www.ar1es.cn/book1.png";
                }else{
                    sl=colleageTime.getLibrary();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            try {
                now = dateFormat.parse(dateFormat.format(new Date()));
                libraryBeginTime = dateFormat.parse("08:00");
                libararyEndTime = dateFormat.parse("21:30");
                sl = belongCalendar(now, libraryBeginTime, libararyEndTime);
                if(sl.equals("1")){
                    sl=colleageTime.getLibrary();
                }else{
                    sl="http://www.ar1es.cn/book1.png";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sl;
    }
    public String Electric(ColleageTime colleageTime,SimpleDateFormat dateFormat,SimpleDateFormat zmdateFormat,Calendar today){

        String se="";

        System.out.println("现在时间是 "+zmdateFormat.format(today.getTime().getTime()));
        Date now=null;
        Date electricBeginTime=null;
        Date electricEndTime=null;
        Date electricBeginTime2=null;
        Date electricEndTime2=null;
        Date electricBeginTime3=null;
        Date electricEndTime3=null;

        if(today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
            System.out.println("今天是周末");
            System.out.println("今天是一周的第"+ today.get(Calendar.DAY_OF_WEEK) + "天(星期天为第一天)");
            //开始判断
            try {
                now = dateFormat.parse(dateFormat.format(new Date()));
                electricBeginTime = dateFormat.parse("05:30");
                electricEndTime = dateFormat.parse("23:00");
                se = belongCalendar(now, electricBeginTime, electricEndTime);
                if(se=="1"){
                    se=colleageTime.getElectric();
                }else{
                    se="http://www.ar1es.cn/gongdian1.png";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("今天不是周末");
            System.out.println("今天是一周的第"+ today.get(Calendar.DAY_OF_WEEK) + "天(星期天为第一天)");
            try{
                now = dateFormat.parse(dateFormat.format(new Date()));
                electricBeginTime = dateFormat.parse("05:30");
                electricEndTime = dateFormat.parse("08:00");
                String  s1 = belongCalendar(now, electricBeginTime, electricEndTime);

                electricBeginTime2 = dateFormat.parse("11:30");
                electricEndTime2 = dateFormat.parse("13:00");
                String  s2 = belongCalendar(now, electricBeginTime2, electricEndTime2);

                electricBeginTime3 = dateFormat.parse("15:30");
                electricEndTime3 = dateFormat.parse("22:30");
                String  s3 = belongCalendar(now, electricBeginTime3, electricEndTime3);
                if(s1=="1"||s2=="1"||s3=="1"){
                    se=colleageTime.getElectric();
                }else{
                    se="http://www.ar1es.cn/gongdian1.png";
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        System.out.println("electric :"+se);
        return se;
    }
    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String belongCalendar(Date nowTime, Date beginTime,
                                         Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return "1";
        } else {
            return "0";
        }
    }

}
