package com.junlong0716.library;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import static com.junlong0716.library.CalendarRangeView.RANGE_CALENDAR_CLASS_NAME;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CalendarUtil
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:13 PM
 */
public class CalendarUtil {

    private static final Calendar CALENDAR = Calendar.getInstance();

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取最大行数
     *
     * @param dayOfMonthStartOffset 1号开始的偏移量 从序号1开始计算
     * @param totalDaysInMonth      一个月有多少天
     * @return 行数
     */
    static int getMaxLines(int dayOfMonthStartOffset, int totalDaysInMonth) {

        Log.d("dayOfMonthStartOffset",dayOfMonthStartOffset+"");
        Log.d("dayOfMonthStartOffset1",totalDaysInMonth+"");
        //判断最大行数
        if (8- dayOfMonthStartOffset == 1 && totalDaysInMonth == 31) {
            return 6;
        } else if (8 - dayOfMonthStartOffset == 0 && totalDaysInMonth >= 30) {
            return 6;
        } else {
            return 5;
        }
    }

    /**
     * 计算一共需要绘制多少个格子 算上空格
     * @param dayOfMonthStartOffset 1号开始的偏移量 从序号1开始计算
     * @param totalDaysInMonth 一个月有多少天
     * @return 格子个数
     */
    public static int getTotalBlockInMonth(int dayOfMonthStartOffset, int totalDaysInMonth) {
        return getMaxLines(dayOfMonthStartOffset, totalDaysInMonth) * 7;
    }

    /**
     * 计算一共需要绘制多少个格子 算上空格
     * @param maxLines 行数
     * @return 格子个数
     */
    public static int getTotalBlockInMonth(int maxLines) {
        return maxLines * 7;
    }

    /**
     * 获取 每月 最后一天 所在的位置
     * @param year
     * @param month
     * @param weekStart
     * @return
     */
    public static int getDayOfMonthStartOffset(int year, int month, int weekStart) {
        return getDayOfMonthStartOffset(year, month, getMonthDaysCount(year, month), weekStart);
    }

    public static int getDayOfMonthStartOffset(int year, int month, int date, int weekStart) {
        int week = getWeekFormCalendar(year, month, date);
        if (weekStart == Calendar.SUNDAY) {
            return 7 - week;
        }
        if (weekStart == Calendar.MONDAY) {
            return week == 1 ? 0 : 7 - week + 1;
        }
        return week == 7 ? 6 : 7 - week - 1;
    }

    /**
     * 获取今天是星期几
     *
     * @param year  年
     * @param month 月
     * @param date  日
     * @return 星期
     */
    public static int getWeekFormCalendar(int year, int month, int date) {
        CALENDAR.set(year, month, date);
        return CALENDAR.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取某月的天数
     *
     * @param year  年
     * @param month 月
     * @return 某月的天数
     */
    public static int getMonthDaysCount(int year, int month) {
        int count = 0;
        //判断大月份
        if (month == Calendar.JANUARY || month == Calendar.MARCH || month == Calendar.MAY || month == Calendar.JULY
                || month == Calendar.AUGUST || month == Calendar.OCTOBER || month == Calendar.DECEMBER) {
            count = 31;
        }

        //判断小月
        if (month == Calendar.APRIL || month == Calendar.JUNE || month == Calendar.SEPTEMBER
                || month == Calendar.NOVEMBER) {
            count = 30;
        }

        //判断平年与闰年
        if (month == Calendar.FEBRUARY) {
            if (isLeapYear(year)) {
                count = 29;
            } else {
                count = 28;
            }
        }
        return count;
    }

    /**
     * 是否是闰年
     *
     * @param year year
     * @return 是否是闰年
     */
    static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * 判断月份是不是当前月份
     * @param month 月份
     */
    static boolean isCurrentMonth(int month) {
        return CALENDAR.get(Calendar.MONTH) == month;
    }

    static boolean isToday(int year, int month, int day) {
        return CALENDAR.get(Calendar.YEAR) == year && CALENDAR.get(Calendar.MONTH) == month
                && CALENDAR.get(Calendar.DATE) == day;
    }

    static boolean isPass(int year, int month, int day) {
        if (year < CALENDAR.get(Calendar.YEAR)) {
            return true;
        } else if (year > CALENDAR.get(Calendar.YEAR)) {
            return false;
        } else if (month < CALENDAR.get(Calendar.MONTH)) {
            return true;
        } else if (month > CALENDAR.get(Calendar.MONTH)) {
            return false;
        } else {
            return day < CALENDAR.get(Calendar.DATE);
        }
    }

    /**
     * 比较日期大小
     * @param reference 基准日期
     * @param compared 被比较日期
     * @return 被比较日期 是不是在 基准日期 之前
     */
    static boolean isPass(BaseCalendarEntity reference, BaseCalendarEntity compared) {
        Calendar referenceC = Calendar.getInstance();
        referenceC.set(Calendar.YEAR, reference.getYear());
        referenceC.set(Calendar.MONTH, reference.getMonth());
        referenceC.set(Calendar.DATE, reference.getDay());

        Calendar comparedC = Calendar.getInstance();
        comparedC.set(Calendar.YEAR, compared.getYear());
        comparedC.set(Calendar.MONTH, compared.getMonth());
        comparedC.set(Calendar.DATE, compared.getDay());

        return referenceC.getTime().getTime() > comparedC.getTime().getTime();
    }

    static List<? super BaseCalendarEntity> createDate(int year, int month,
            @NonNull List<? super BaseCalendarEntity> items, @NonNull Class clazz) {
        items.clear();
        if (checkInvalidateMonth(month)) {
            throw new IllegalArgumentException("非法月份!");
        }
        for (int i = 0; i < CalendarUtil.getMonthDaysCount(year, month); i++) {
            while (clazz != null) {
                if (clazz.getName().equals(RANGE_CALENDAR_CLASS_NAME)) {
                    RangeCalendarEntity rangeCalendarEntity = new RangeCalendarEntity(year, month, i + 1);
                    items.add(rangeCalendarEntity);

                    Log.d("okok","okok");
                    break;
                } else {
                    clazz = clazz.getSuperclass();
                }
            }
        }
        return items;
    }

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
     * 是否是非法月份
     * @param m 月份 使用Calendar 包的
     * @return 结果
     */
    static boolean checkInvalidateMonth(int m) {
        return m < 0 || m >= 12;
    }
}
