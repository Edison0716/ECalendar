package com.junlong0716.library.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.junlong0716.library.Calendar;
import com.junlong0716.library.CalendarRangeView;

/**
 * @ClassName: CalendarSimpleView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 4:24 PM
 */
public class CalendarSimpleView extends CalendarRangeView {

    public CalendarSimpleView(Context context) {
        this(context, null);
    }

    public CalendarSimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawText(Canvas canvas, Calendar item, int x, int y, Paint currentMonthTextPaint) {
        Paint.FontMetrics metrics = currentMonthTextPaint.getFontMetrics();
        int mTextBaseLine = (int) (mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2);

        float baselineY = mTextBaseLine + y;
        int centerX = x + mItemWidth / 2;

        canvas.drawText(String.valueOf(item.getDay()), centerX, baselineY, currentMonthTextPaint);
    }

    @Override
    public void onActionDown(float x, float y) {

    }

    @Override
    public void onActionUp(float x, float y) {

    }
}
