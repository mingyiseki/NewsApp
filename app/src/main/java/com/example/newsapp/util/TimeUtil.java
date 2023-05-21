package com.example.newsapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public int getSecond(Date beginTime) {
        //设置时间格式，为了 能转换成 字符串
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //利用时间格式，把当前时间转为字符串
        df.format(beginTime);
        //当前时间 转为 长整型 Long
        Long begin = beginTime.getTime();
        //获取结束时间
        Date finishTime = new Date();
        //结束时间 转为 Long 类型
        Long end = finishTime.getTime();
        // 时间差 = 结束时间 - 开始时间，这样得到的差值是毫秒级别
        long timeLag = end - begin;
        //天
        long day = timeLag / (24 * 60 * 60 * 1000);
        //小时
        long hour = (timeLag / (60 * 60 * 1000) - day * 24);
        //分钟
        long minute = ((timeLag / (60 * 1000)) - day * 24 * 60 - hour * 60);
        //秒，顺便说一下，1秒 = 1000毫秒
        long s = (timeLag / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);

        return (int) (timeLag / 1000);
    }

    public boolean isOverTime(Date beginTime, int second) {
        //设置时间格式，为了 能转换成 字符串
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //利用时间格式，把当前时间转为字符串
        df.format(beginTime);
        //当前时间 转为 长整型 Long
        Long begin = beginTime.getTime();
        //获取结束时间
        Date finishTime = new Date();
        //结束时间 转为 Long 类型
        Long end = finishTime.getTime();
        // 时间差 = 结束时间 - 开始时间，这样得到的差值是毫秒级别
        long timeLag = end - begin;
        //天
        long day = timeLag / (24 * 60 * 60 * 1000);
        //小时
        long hour = (timeLag / (60 * 60 * 1000) - day * 24);
        //分钟
        long minute = ((timeLag / (60 * 1000)) - day * 24 * 60 - hour * 60);
        //秒，顺便说一下，1秒 = 1000毫秒
        long s = (timeLag / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);
        return ((int) (timeLag / 1000)) >= second;
    }

}
