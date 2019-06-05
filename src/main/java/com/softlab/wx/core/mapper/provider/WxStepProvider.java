package com.softlab.wx.core.mapper.provider;

import com.softlab.wx.core.model.vo.Pace;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
/**
 *
 * Created by LiXiwen on 2019/3/25.
 *
 **/
public class WxStepProvider {


    /**
     *    批量插入
     * @param map
     * @return
     */
    public String batchInsert(Map map) {
        List<Pace> paces = (List<Pace>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sortbushu (user_id,user_name,user_rank,user_icon,user_paiwei,user_pace,user_img,user_color) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'list[{0}].userId}, #'{'list[{0}].userName}, #'{'list[{0}].userRank},#'{'list[{0}].userIcon},#'{'list[{0}].userPaiwei},#'{'list[{0}].userPace},#'{'list[{0}].userImg},#'{'list[{0}].userColor})"
        );

        for (int i = 0; i < paces.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < paces.size() - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    /**
     * 批量删除
     */
    /*public String batchDelete(Map map) {
        List<Pace> students = (List<Pace>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM student WHERE id IN (");
        for (int i = 0; i < students.size(); i++) {
            sb.append("'").append(students.get(i).getId()).append("'");
            if (i < students.size() - 1)
                sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }*/

}
