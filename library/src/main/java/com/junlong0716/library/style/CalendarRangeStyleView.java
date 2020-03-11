package com.junlong0716.library.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.junlong0716.library.RangeCalendarEntity;
import com.junlong0716.library.CalendarRangeView;

/**
 * @ClassName: CalendarSimpleView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 4:24 PM
 */
public class CalendarRangeStyleView extends CalendarRangeView {

    public CalendarRangeStyleView(Context context) {
        this(context, null);
    }

    public CalendarRangeStyleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarRangeStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawDayText(Canvas canvas, RangeCalendarEntity item, int x, int y) {
        Paint.FontMetrics metrics = mUnselectedDateTextPaint.getFontMetrics();
        int mTextBaseLine = (int) (mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2);

        float baselineY = mTextBaseLine + y;
        int centerX = x + mItemWidth / 2;

        // 是否起始时选中的样式
        // 是否是终止选中的样式
        if (item.isStartCheckedDay() || item.isEndCheckedDay()) {
            canvas.drawText(String.valueOf(item.getDay()), centerX, baselineY, mSelectedDateTextPaint);
        }

        // 不可用状态 包括 时间已经过去 还有 当天不能用
        else if (!item.isAvailable()){
            canvas.drawText(String.valueOf(item.getDay()), centerX, baselineY, mUnavailableDateTextPaint);
        }

        // 普通未选中的样式
        else{
            canvas.drawText(String.valueOf(item.getDay()), centerX, baselineY, mUnselectedDateTextPaint);
        }
    }

    @Override
    public void drawDaySelected(Canvas canvas, RangeCalendarEntity item, int x, int y) {

    }
}
