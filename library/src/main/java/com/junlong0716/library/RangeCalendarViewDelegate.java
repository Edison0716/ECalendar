package com.junlong0716.library;

import java.util.List;

/**
 * @ClassName: CalendarViewDelegate
 * @Description:
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

    /**
     * 处理点击事件
     * @param clickEntity 选中的日期
     */
    static void handleClick(RangeCalendarEntity clickEntity, List<? super BaseCalendarEntity> dates) {
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
                resetDate(clickEntity, dates);
                // 继续走一遍
                handleClick(clickEntity, dates);
            }
        } else {
            // 第三次点击
            resetDate(clickEntity, dates);
            // 继续走一遍
            handleClick(clickEntity, dates);
        }
    }

    // 重置操作
    private static void resetDate(RangeCalendarEntity clickEntity, List<? super BaseCalendarEntity> dates) {
        clickEntity.setStartCheckedDay(false);
        clickEntity.setEndCheckedDay(false);
        FIRST_CLICK = false;
        SECOND_CLICK = false;
        FIRST_CLICK_CALENDAR_ENTITY = null;
        SECOND_CLICK_CALENDAR_ENTITY = null;

        for (Object date : dates) {
            if (date instanceof RangeCalendarEntity) {
                ((RangeCalendarEntity) date).setStartCheckedDay(false);
                ((RangeCalendarEntity) date).setEndCheckedDay(false);
                ((RangeCalendarEntity) date).setRangedCheckedDay(false);
            }
        }
    }

    // 标记区间的
    public static void handleRange(RangeCalendarEntity item, List<? super BaseCalendarEntity> dates) {
        // 如果是第二次点击 并且 第二次点击的日期与第一次点击的日期不是同一个日期 则 不进行标记区间
        if (RangeCalendarViewDelegate.SECOND_CLICK && item != RangeCalendarViewDelegate.FIRST_CLICK_CALENDAR_ENTITY) {
            for (Object date : dates) {
                if (date instanceof RangeCalendarEntity) {
                    // 找到 起始点 与 终止点的范围区间
                    if (!CalendarUtil.isPass(FIRST_CLICK_CALENDAR_ENTITY, (RangeCalendarEntity) date) && CalendarUtil
                            .isPass(SECOND_CLICK_CALENDAR_ENTITY, (RangeCalendarEntity) date)) {
                        ((RangeCalendarEntity) date).setRangedCheckedDay(true);
                    }
                }
            }
        }
    }
}
