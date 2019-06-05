package com.softlab.wx.web.api;

import com.softlab.wx.common.util.JsonUtil;
import com.softlab.wx.core.model.vo.Course;
import com.softlab.wx.core.model.vo.Time;
import com.softlab.wx.core.model.vo.courseFan;
import com.softlab.wx.service.CourseService;
import com.softlab.wx.service.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final TimeService timeService;
    private final CourseService courseService;

    @Autowired
    public CourseController(TimeService timeService,CourseService courseService){
        this.timeService=timeService;
        this.courseService=courseService;
    }

    Date currentTime;
    SimpleDateFormat formatter;
    String dateString = null;
    boolean flag=false;

    /**
     * 限制用户一天只能投一次票
     * @param userName
     * @return
     */
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public boolean getTime(@RequestParam(value = "openId") String openId,@RequestParam(value = "userName") String userName){
        currentTime = new Date();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化当前时间
        dateString = formatter.format(currentTime);// 转成字符串
        int n = timeService.selectByOpenId(openId);
        logger.info("n= "+n);
        Time time3 = new Time();

        if(0!=n){
            Time time=timeService.selectAllByOpenId(openId);
            try{
                Date one = formatter.parse(dateString);
                Date two=formatter.parse(time.getTime());
                long time1=one.getTime();
                long time2=two.getTime();
                long c=time1-time2;
                logger.info("time c = "+c);
                if(c<24000*60*60){
                    //如果时间小于24小时，返回true 表示不能再投票
                    logger.info("flag  ==  true");
                    flag=true;
                }else {
                    //如果时间大于24小时，返回false 可以再投票
                    logger.info("flag  ==  false");
                    flag = false;
                    timeService.update(openId,dateString,userName);
                }
            }catch(ParseException e1){
                logger.info("想重复投票");
            }
        }else{
            logger.info("n==0 执行添加用户时间操作");
            time3.setOpenId(openId);
            time3.setTime(dateString);
            time3.setUsername(userName);
            timeService.insert(time3);
            flag=false;
        }
        return flag;
    }

    /**
     * 用户点赞
     * @param cf
     * @param request
     */
    @RequestMapping(value = "/zan", method = RequestMethod.POST)
    public void addZan(@RequestBody courseFan cf ,HttpServletRequest request){

        logger.info("该用户要投票的课程信息"+ JsonUtil.getJsonString(cf));
        String like1="";
        String like2="";
        String like3="";
        String like4="";
        String like5="";
        String like6="";

        like1= cf.getLike1();
        like2=cf.getLike2();
        like3=cf.getLike3();
        like4=cf.getLike4();
        like5=cf.getLike5();
        like6=cf.getLike6();
        System.out.println(like1.length()+","+like2.length()+","+like3.length()+","+like4.length());
        if(like1.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like1);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
                count++;
                logger.info("找到课程，投票数开始增加,"+like1+" 的当前票数为 "+count);
                courseService.add(like1,count);
                logger.info("票数增加完毕");
        }else{
            logger.info("该用户第一类课程没有投票");
        }
        if(like2.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like2);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
            count++;
            logger.info("找到课程，投票数开始增加,"+like2+"  的当前票数为 "+count);
            courseService.add(like2,count);
            logger.info("票数增加完毕");
        }
        else{
            logger.info("该用户第二类课程没有投票");
        }
        if(like3.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like3);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
            count++;
            logger.info("找到课程，投票数开始增加,"+like3+"  的当前票数为 "+count);
            courseService.add(like3,count);
            logger.info("票数增加完毕");
        }
        else{
            logger.info("该用户第三类课程没有投票");
        }
        if(like4.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like4);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
            count++;
            logger.info("找到课程，投票数开始增加,"+like4+"  的当前票数为 "+count);
            courseService.add(like4,count);
            logger.info("票数增加完毕");
        }
        else{
            logger.info("该用户第四类课程没有投票");
        }
        if(like5.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like5);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
            count++;
            logger.info("找到课程，投票数开始增加,"+like5+"  的当前票数为 "+count);
            courseService.add(like5,count);
            logger.info("票数增加完毕");
        }else{
            logger.info("该用户第五类课程没有投票");
        }
        if(like6.length()!=0){
            Course course1=new Course();
            course1=courseService.selectByCourse(like6);
            //String coursename=course1.getCourse();
            int count=course1.getZan();
            count++;
            logger.info("找到课程，投票数开始增加,"+like6+"  的当前票数为 "+count);
            courseService.add(like6,count);
            logger.info("票数增加完毕");
        }else {
            logger.info("该用户第六类课程没有投票");
        }

    }

    /**
     * 对数据库进行排序并返回前10名
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public List<LinkedHashMap<String, String>> sort(){
        List<Course> courses = courseService.selectByOrder();
        List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
        int i=0;
        for (Course course:courses) {
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
            String courseName=course.getCourse();
            int courseZan=course.getZan();
            //System.out.println(courseName+" "+courseZan);
            linkedHashMap.put("course",courseName);
            linkedHashMap.put("zan", String.valueOf(courseZan));
            i++;
            linkedHashMap.put("rank", String.valueOf(i));
            list.add(linkedHashMap);
        }
        return list;
    }



}
