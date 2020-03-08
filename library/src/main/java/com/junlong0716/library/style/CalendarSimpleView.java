package com.junlong0716.library.style;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.junlong0716.library.Calendar;
import com.junlong0716.library.CalendarRangeView;
import com.junlong0716.library.ICalendarView;

/**
 * @ClassName: CalendarSimpleView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 4:24 PM
 */
public class CalendarSimpleView extends CalendarRangeView implements ICalendarView {


    public CalendarSimpleView(Context context) {
        this(context,null);
    }

    public CalendarSimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLineCount = 6;
    }

    @Override
    public void drawText(Canvas canvas, Calendar item, int x, int y) {
        Log.d("drawText","drawTexg");
    }
}
