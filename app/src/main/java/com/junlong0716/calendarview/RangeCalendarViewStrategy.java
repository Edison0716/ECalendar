package com.junlong0716.calendarview;

import androidx.annotation.NonNull;
import com.junlong0716.library.CalendarUtil;
import com.junlong0716.library.ICalendarStrategy;
import com.junlong0716.library.range.RangeCalendarEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CalendarViewDelegate
 * @Description: 处理多选的所有逻辑操作
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 10:38 AM
 */
public final class RangeCalendarViewStrategy implements ICalendarStrategy<RangeCalendarEntity> {

    // 第一次点击
    private boolean FIRST_CLICK = false;
    // 第二次点击
    private boolean SECOND_CLICK = false;
    // 第一次点击记录的日期实体
    private RangeCalendarEntity FIRST_CLICK_CALENDAR_ENTITY = null;
    // 第二次点击记录的日期实体
    private RangeCalendarEntity SECOND_CLICK_CALENDAR_ENTITY = null;
    private List<List<RangeCalendarEntity>> mCalendarDates;
    // 单例
    private static volatile RangeCalendarViewStrategy STRATEGY = null;

    public static RangeCalendarViewStrategy getInstance() {
        if (STRATEGY == null) {
            synchronized (RangeCalendarViewStrategy.class) {
                if (STRATEGY == null) {
                    STRATEGY = new RangeCalendarViewStrategy();
                }
            }
        }
        return STRATEGY;
    }

    private RangeCalendarViewStrategy() {

    }

    /**
     * 处理点击事件
     * @param clickEntity 选中的日期
     */
    public void handleClick(RangeCalendarEntity clickEntity) {

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

    @Override
    public List<RangeCalendarEntity> getCheckedDates() {
        ArrayList<RangeCalendarEntity> checkedDates = new ArrayList<>();
        checkedDates.add(FIRST_CLICK_CALENDAR_ENTITY);
        checkedDates.add(SECOND_CLICK_CALENDAR_ENTITY);
        return checkedDates;
    }

    @Override
    public void reset() {
        FIRST_CLICK = false;
        SECOND_CLICK = false;
        FIRST_CLICK_CALENDAR_ENTITY = null;
        SECOND_CLICK_CALENDAR_ENTITY = null;

        for (List<RangeCalendarEntity> dates : mCalendarDates) {
            for (RangeCalendarEntity date : dates) {
                date.setStartCheckedDay(false);
                date.setRangedCheckedDay(false);
                date.setEndCheckedDay(false);
            }
        }
    }

    @Override
    public void setListData(List<List<RangeCalendarEntity>> dateList) {
        mCalendarDates = dateList;
    }

    // 重置操作
    private void resetDate(@NonNull RangeCalendarEntity clickEntity) {
        clickEntity.setStartCheckedDay(false);
        clickEntity.setEndCheckedDay(false);
        FIRST_CLICK = false;
        SECOND_CLICK = false;
        FIRST_CLICK_CALENDAR_ENTITY = null;
        SECOND_CLICK_CALENDAR_ENTITY = null;

        for (List<RangeCalendarEntity> dates : mCalendarDates) {
            for (RangeCalendarEntity date : dates) {
                date.setStartCheckedDay(false);
                date.setRangedCheckedDay(false);
                date.setEndCheckedDay(false);
            }
        }
    }

    // 标记区间的
    private void handleRange() {
        if (!SECOND_CLICK) {
            return;
        }
        for (List<RangeCalendarEntity> date : mCalendarDates) {
            for (RangeCalendarEntity rangeCalendarEntity : date) {
                if (rangeCalendarEntity.getTimeStamp() >= FIRST_CLICK_CALENDAR_ENTITY.getTimeStamp()
                        && rangeCalendarEntity.getTimeStamp() < SECOND_CLICK_CALENDAR_ENTITY.getTimeStamp()) {
                    rangeCalendarEntity.setRangedCheckedDay(true);
                }
            }
        }
    }
}
