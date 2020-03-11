package com.junlong0716.library;

import java.io.Serializable;

/**
 * @ClassName: Calendar
 * @Description: 日期 实体类
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 11:40 AM
 */
public class RangeCalendarEntity extends BaseCalendarEntity implements Serializable {

    // 日期的描述 可以有文字内容
    private String des;
    // 首次点击
    private boolean startCheckedDay;
    // 终止点击
    private boolean endCheckedDay;
    // 选中范围内的日期
    private boolean rangedCheckedDay;

    public RangeCalendarEntity(int year, int month, int day) {
        super(year, month, day);
    }

    public boolean isStartCheckedDay() {
        return startCheckedDay;
    }

    public void setStartCheckedDay(boolean startCheckedDay) {
        this.startCheckedDay = startCheckedDay;
    }

    public boolean isEndCheckedDay() {
        return endCheckedDay;
    }

    public void setEndCheckedDay(boolean endCheckedDay) {
        this.endCheckedDay = endCheckedDay;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isRangedCheckedDay() {
        return rangedCheckedDay;
    }

    public void setRangedCheckedDay(boolean rangedCheckedDay) {
        this.rangedCheckedDay = rangedCheckedDay;
    }
}
