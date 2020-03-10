package com.junlong0716.library;

import java.io.Serializable;

/**
 * @ClassName: Calendar
 * @Description: 日期 实体类
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 11:40 AM
 */
public class Calendar implements Serializable {
    // 年
    private int year;
    // 月
    private int month;
    // 日
    private int day;
    // 是否是今天
    private boolean isToday;
    // 日期的描述 可以有文字内容
    private String des;
    // 是否是可用的
    private boolean isAvailable;
    // 绘制时的坐标
    private int locationX;
    private int locationY;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
