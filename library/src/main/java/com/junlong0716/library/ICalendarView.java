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
    void drawDayText(Canvas canvas, T item);

    void drawDaySelected(Canvas canvas, T item);

    void createCalendarStrategy(List<List<T>> calendarDates);
}
