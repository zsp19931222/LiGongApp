package com.example.app3.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/28.
 */

public class TimeTool {
    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转换日期格式
     */
    public static String TimeStamp2date(long redpacket_time, String fromat) {
        Date date = new Date(redpacket_time);
        SimpleDateFormat sdf = new SimpleDateFormat(
                fromat);
        String time = sdf.format(date);
        return time;
    }

    public static String getDateSx(long time) {
        int hour = Integer.valueOf(TimeStamp2date(time, "HH"));
        String s = null;
        if (hour >= 0 && hour <= 12) {
            s = "上午";
        } else if (hour > 12 && hour <= 24) {
            s = "下午";
        }
        return s;
    }
}
