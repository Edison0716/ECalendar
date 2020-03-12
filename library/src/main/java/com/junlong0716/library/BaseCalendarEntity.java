package com.junlong0716.library;

import java.io.Serializable;
import java.util.Calendar;

import static java.lang.reflect.Array.set;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/11 9:32
 * desc :
 */
public class BaseCalendarEntity implements Serializable {

    // 年
    private int year;
    // 月
    private int month;
    // 日
    private int day;

    // 是否是今天
    private boolean isToday;

    // 绘制时的坐标
    private int locationX;

    private int locationY;

    // 是否是可用的
    private boolean isAvailable;

    // 时间戳
    private long timeStamp;

    public BaseCalendarEntity(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isToday = CalendarUtil.isToday(year, month, day);
        this.isAvailable = !CalendarUtil.isPass(year, month, day);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.timeStamp = calendar.getTimeInMillis();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
