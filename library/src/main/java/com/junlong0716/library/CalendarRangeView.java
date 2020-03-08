package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @ClassName: CalendarView
 * @Description: 基础日期
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:56 PM
 */
public abstract class CalendarRangeView extends CalendarBaseView{

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

        // 偏移量
        int dayOfMonthStartOffset = CalendarUtil.getDayOfMonthStartOffset(mYear, mMonth - 1, mWeekStart);
        int monthDaysCount = CalendarUtil.getMonthDaysCount(mYear, mMonth);
        mLineCount = CalendarUtil.getMaxLines(dayOfMonthStartOffset, monthDaysCount);

        if (mLineCount == 0 || mItems.isEmpty()) return;


        Log.d("count",monthDaysCount+"");
        // 计算一个格子的宽度
        mItemWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        mItemHeight = mItemWidth;
        // 计算一共需要绘制多少个格子 算上空格
        int totalDrawCount = mLineCount * 7;

        // 计算格子的index
        int indexItem = 0;


        for (int lineIndex = 0; lineIndex < mLineCount; ++lineIndex) {

            for (int rowIndex = 0; rowIndex < 7; ++rowIndex) {

                // 若小于偏移量 则跳过
//                if (indexItem < dayOfMonthStartOffset) {
//                    continue;
//                }


                // 大于总天数则跳出
                if (indexItem > monthDaysCount - 1) {
                    break;
                }

                drawSingleItem(canvas, mItems.get(indexItem), lineIndex, rowIndex);

                ++indexItem;
            }
        }
    }

    private void drawSingleItem(Canvas canvas, Calendar item, int lineIndex, int rowIndex) {
        // 格子左上角的横坐标
        int x = rowIndex * mItemWidth;
        int y = lineIndex * mItemHeight;


        drawText(canvas, item, x, y,mCurrentMonthTextPaint);
    }


    public abstract void drawText(Canvas canvas, Calendar item, int x, int y, Paint currentMonthTextPaint);
}
