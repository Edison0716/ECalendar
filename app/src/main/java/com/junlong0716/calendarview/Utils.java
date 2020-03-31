package com.junlong0716.calendarview;

import androidx.annotation.NonNull;
import com.junlong0716.library.BaseCalendarEntity;
import com.junlong0716.library.CalendarUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/18 14:02
 * desc : 
 */
public class Utils {
    static List<? extends BaseCalendarEntity> checkedRangeStartDate(int day,
            @NonNull List<? extends BaseCalendarEntity> items) {
        for (Object item : items) {
            if (item instanceof RangeCalendarEntity) {
                if (((RangeCalendarEntity) item).getDay() == day) {
                    ((RangeCalendarEntity) item).setStartCheckedDay(true);
                } else {
                    ((RangeCalendarEntity) item).setStartCheckedDay(false);
                }
            }
        }
        return items;
    }

    static List<? extends BaseCalendarEntity> checkedRangeEndDate(int day,
            @NonNull List<? extends BaseCalendarEntity> items) {
        for (Object item : items) {
            if (item instanceof RangeCalendarEntity) {
                if (((RangeCalendarEntity) item).getDay() == day) {
                    ((RangeCalendarEntity) item).setEndCheckedDay(true);
                } else {
                    ((RangeCalendarEntity) item).setEndCheckedDay(false);
                }
            }
        }
        return items;
    }

    /**
     * 创建月份实体
     * @param year 年
     * @param month 月
     * @param range 创建范围
     * @return 实体
     */
    public static List<List<RangeCalendarEntity>> createDate(int year, int month, int range) {
        List<List<RangeCalendarEntity>> createDates = new ArrayList<>();

        while (range > 0) {
            int monthCount = CalendarUtil.getMonthDaysCount(year, month);
            List<RangeCalendarEntity> monthDates = new ArrayList<>(monthCount);

            for (int i = 0; i < monthCount; ++i) {
                monthDates.add(new RangeCalendarEntity(year, month, i + 1));
            }

            createDates.add(monthDates);

            // 跨年
            if (month == Calendar.DECEMBER) {
                year++;
                month = Calendar.JANUARY;
            } else {
                month++;
            }

            range--;
        }

        return createDates;
    }

    /**
     * 创建日期实体
     * @param timeStamp 时间戳
     * @param extend 延后时间
     * @return 实体
     */
    public static List<List<RangeCalendarEntity>> createRangeDate(long timeStamp, int extend) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return createDate(year, month, extend);
    }

    /**
     * 创建范围日期数据
     * @param startTimeStamp 起始日期
     * @param endTimeStamp 终止日期
     * @param extend 向后延期日期
     * @return 数据
     */
    public static List<List<RangeCalendarEntity>> createRangeDate(long startTimeStamp, long endTimeStamp, int extend) {

        // 起始日期
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTimeStamp);
        int startMonth = startCalendar.get(Calendar.MONTH);
        int startYear = startCalendar.get(Calendar.YEAR);
        int startDay = startCalendar.get(Calendar.DATE);

        // 终止日期
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(endTimeStamp);
        int endMonth = endCalendar.get(Calendar.MONTH);
        int endYear = endCalendar.get(Calendar.YEAR);
        int endDay = endCalendar.get(Calendar.DATE);

        List<List<RangeCalendarEntity>> createDates = new ArrayList<>();

        // 判断是否跨年
        if (endYear > startYear) {
            createDate(startYear, startMonth, Calendar.DECEMBER, createDates);
            createDate(endYear, Calendar.JANUARY, endMonth, createDates);
        } else {
            createDate(startYear, startMonth, endMonth, createDates);
        }

        // 标记区间
        for (List<RangeCalendarEntity> dates : createDates) {
            for (RangeCalendarEntity date : dates) {
                if (date.getTimeStamp() > startTimeStamp && date.getTimeStamp() < endTimeStamp) {
                    date.setRangedCheckedDay(true);
                }
                if (date.getYear() == startYear && date.getMonth() == startMonth && date.getDay() == startDay) {
                    date.setStartCheckedDay(true);
                } else if (date.getYear() == endYear && date.getMonth() == endMonth && date.getDay() == endDay) {
                    date.setEndCheckedDay(true);
                }
            }
        }

        // 创建延后的月份
        if (endMonth == Calendar.DECEMBER) {
            createDates.addAll(createDate(endYear + 1, Calendar.JANUARY, extend));
        } else {
            createDates.addAll(createDate(endYear, endMonth + 1, extend));
        }

        return createDates;
    }

    private static List<List<RangeCalendarEntity>> createDate(int year, int startMonth, int endMonth,
            List<List<RangeCalendarEntity>> dates) {
        // 遍历从该月到12月份
        for (int i = startMonth; i <= endMonth; i++) {
            // 计算该月有几天
            int monthCount = CalendarUtil.getMonthDaysCount(year, i);
            List<RangeCalendarEntity> date = new ArrayList<>(monthCount);
            // 遍历日
            for (int j = 0; j < monthCount; ++j) {
                date.add(new RangeCalendarEntity(year, i, j + 1));
            }
            dates.add(date);
        }
        return dates;
    }
}
