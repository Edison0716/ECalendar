package com.junlong0716.library;

import android.graphics.Canvas;
import com.junlong0716.library.range.RangeCalendarEntity;
import java.util.List;

/**
 * @ClassName: IBaseView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:02 PM
 */
public interface ICalendarView {
    void drawDayText(Canvas canvas, RangeCalendarEntity item);

    void drawDaySelected(Canvas canvas, RangeCalendarEntity item);

    void createCalendarStrategy(List<List<RangeCalendarEntity>> calendarDates);
}
