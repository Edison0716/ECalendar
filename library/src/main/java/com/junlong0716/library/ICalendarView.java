package com.junlong0716.library;

import android.graphics.Canvas;

/**
 * @ClassName: IBaseView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:02 PM
 */
public interface ICalendarView {
    void drawDayText(Canvas canvas, RangeCalendarEntity item);

    void drawDaySelected(Canvas canvas, RangeCalendarEntity item);
}
