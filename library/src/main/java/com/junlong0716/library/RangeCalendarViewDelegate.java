package com.junlong0716.library;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;

import java.util.List;

/**
 * @ClassName: CalendarViewDelegate
 * @Description: 处理多选的所有逻辑操作
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 10:38 AM
 */
public final class RangeCalendarViewDelegate {

    // 第一次点击
    public static boolean FIRST_CLICK = false;
    // 第二次点击
    public static boolean SECOND_CLICK = false;
    // 第一次点击记录的日期实体
    public static RangeCalendarEntity FIRST_CLICK_CALENDAR_ENTITY = null;
    // 第二次点击记录的日期实体
    public static RangeCalendarEntity SECOND_CLICK_CALENDAR_ENTITY = null;

    public static List<List<RangeCalendarEntity>> DATES = null;


    static void setDates(List<List<RangeCalendarEntity>> dates){
        DATES = dates;
    }



    /**
     * 处理点击事件
     * @param clickEntity 选中的日期
     */
    static void handleClick(RangeCalendarEntity clickEntity) {
        if (!FIRST_CLICK) {
            // 第一次点击
            FIRST_CLICK = true;
            clickEntity.setStartCheckedDay(true);
            FIRST_CLICK_CALENDAR_ENTITY = clickEntity;
        } else if (!SECOND_CLICK) {
            // 第二次点击
            SECOND_CLICK = true;
            clickEntity.setEndCheckedDay(true);
            SECOND_CLICK_CALENDAR_ENTITY = clickEntity;
            // 比较这两个日期的先后
            if (CalendarUtil.isPass(FIRST_CLICK_CALENDAR_ENTITY, SECOND_CLICK_CALENDAR_ENTITY)) {
                resetDate(clickEntity);
                // 继续走一遍
                handleClick(clickEntity);
            }
        } else {
            // 第三次点击
            resetDate(clickEntity);
            // 继续走一遍
            handleClick(clickEntity);
        }

        handleRange();
    }

    // 重置操作
    private static void resetDate(@NonNull RangeCalendarEntity clickEntity) {
        clickEntity.setStartCheckedDay(false);
        clickEntity.setEndCheckedDay(false);
        FIRST_CLICK = false;
        SECOND_CLICK = false;
        FIRST_CLICK_CALENDAR_ENTITY = null;
        SECOND_CLICK_CALENDAR_ENTITY = null;

        for (List<RangeCalendarEntity> date : DATES) {
            for (RangeCalendarEntity rangeCalendarEntity : date) {
                rangeCalendarEntity.setStartCheckedDay(false);
                rangeCalendarEntity.setRangedCheckedDay(false);
                rangeCalendarEntity.setEndCheckedDay(false);
            }
        }
    }

    // 标记区间的
    private static void handleRange() {
        if (!SECOND_CLICK){
            return;
        }
        for (List<RangeCalendarEntity> date : DATES) {
            for (RangeCalendarEntity rangeCalendarEntity : date) {
                if (rangeCalendarEntity.getTimeStamp() >= FIRST_CLICK_CALENDAR_ENTITY.getTimeStamp() && rangeCalendarEntity.getTimeStamp()<SECOND_CLICK_CALENDAR_ENTITY.getTimeStamp()){
                    rangeCalendarEntity.setRangedCheckedDay(true);
                }
            }
        }
    }
}
