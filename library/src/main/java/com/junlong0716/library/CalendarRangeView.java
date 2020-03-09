package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import android.view.MotionEvent;
import androidx.annotation.Nullable;

/**
 * @ClassName: CalendarView
 * @Description: 基础日期
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:56 PM
 */
public abstract class CalendarRangeView extends CalendarBaseView implements ICalendarView {

    public CalendarRangeView(Context context) {
        this(context, null);
    }

    public CalendarRangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarRangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawCalendarDate(Canvas canvas) {
        // 偏移量 该月上一个月分的结束的位置
        int dayOfMonthStartOffset;
        // 如果是一月份 则 找上一年的12月份
        if (mMonth == java.util.Calendar.JANUARY) {
            dayOfMonthStartOffset = CalendarUtil
                    .getDayOfMonthStartOffset(mYear - 1, java.util.Calendar.DECEMBER, mWeekStart);
        } else {
            dayOfMonthStartOffset = CalendarUtil.getDayOfMonthStartOffset(mYear, mMonth - 1, mWeekStart);
        }
        Log.d("count-offset", dayOfMonthStartOffset + "");
        int monthDaysCount = CalendarUtil.getMonthDaysCount(mYear, mMonth);
        mLineCount = CalendarUtil.getMaxLines(dayOfMonthStartOffset, monthDaysCount);
        if (mLineCount == 0 || mItems.isEmpty()) {
            return;
        }
        // 计算一共需要绘制多少个格子 算上空格
        int totalDrawCount = mLineCount * 7;

        // 计算日期的index
        int indexItem = 0;
        // 计算格子的index
        int index = 0;

        for (int lineIndex = 0; lineIndex < mLineCount; ++lineIndex) {

            for (int rowIndex = 0; rowIndex < 7; ++rowIndex) {

                // 若小于偏移量 则跳过
                if (dayOfMonthStartOffset != 1 && index < 7 - dayOfMonthStartOffset + 1) {
                    ++index;
                    continue;
                }

                // 大于总天数则跳出
                if (indexItem > monthDaysCount - 1) {
                    break;
                }

                // 绘制单元块
                drawSingleItem(canvas, mItems.get(indexItem), lineIndex, rowIndex);
                ++indexItem;
                ++index;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // EdisonLi TODO 2020/3/9 按下时的效果
            onActionDown(getX(), getY());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // EdisonLi TODO 2020/3/9 抬起时的效果
            onActionUp(getX(), getY());
            return false;
        } else {
            return false;
        }
    }

    private void drawSingleItem(Canvas canvas, Calendar item, int lineIndex, int rowIndex) {
        // 格子左上角的横坐标
        int x = rowIndex * mItemWidth;
        int y = lineIndex * mItemHeight;
        drawText(canvas, item, x, y, mCurrentMonthTextPaint);
    }
}
