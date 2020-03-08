package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

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
        if (mLineCount == 0) return;
        // 计算一个格子的宽度
        mItemWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        // 计算一共需要绘制多少个格子 算上空格
        int totalDrawCount = mLineCount * 7;
        int indexItem = 0;

        for (int lineIndex = 0; lineIndex < mLineCount; ++lineIndex) {
            for (int rowIndex = 0; rowIndex < 7; ++rowIndex) {
                drawSingleItem(canvas, mItems.get(indexItem), mLineCount, rowIndex);
                ++indexItem;
            }
        }
    }

    private void drawSingleItem(Canvas canvas, Calendar item, int mLineCount, int rowIndex) {
        // 格子左上角的横坐标
        int x = rowIndex * mItemWidth;
        int y = mLineCount * mItemHeight;
        drawText(canvas, item, x, y);
    }
}
