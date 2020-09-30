package com.junlong0716.library;

import android.graphics.Canvas;
import java.util.List;

/**
 * @ClassName: IBaseView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:02 PM
 */
public interface ICalendarView<T extends BaseCalendarEntity> {

    /**
     * 绘制文字
     * @param canvas 画板
     * @param item 数据
     */
    void drawDayText(Canvas canvas, T item);

    /**
     * 绘制选中得
     * @param canvas 画板
     * @param item 数据
     */
    void drawDaySelected(Canvas canvas, T item);


    /**
     * 日历策略
     * @param calendarDates 画板
     */
    void createCalendarStrategy(List<List<T>> calendarDates);
}
