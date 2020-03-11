package com.junlong0716.library;

import android.graphics.Canvas;

/**
 * @ClassName: IBaseView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:02 PM
 */
public interface ICalendarView {
    //
//    /**
//     * 绘制当前月份的文字颜色
//     * @param colorRes 颜色资源
//     */
//    void setCurrentMonthTextColor(int colorRes);
//
//    /**
//     * 绘制当前月份文字大小
//     * @param size 大小
//     */
//    void setCurrentMonthTextSize(float size);
//
//    /**
//     * 是否有日期描述 比如 日期对应的农历
//     * @return
//     */
//    boolean isHaveDateDes();

    void drawDayText(Canvas canvas, RangeCalendarEntity item);

    void drawDaySelected(Canvas canvas, RangeCalendarEntity item);


}
