package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.annotation.Nullable;

/**
 * @ClassName: CalendarView
 * @Description: 基础日期
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:56 PM
 */
public abstract class CalendarRangeView extends CalendarBaseView implements ICalendarView {

    private Calendar mFirstClickDate, mSecondClickDate;

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
        if (mLineCount == 0 || mItems.isEmpty()) {
            return;
        }
        // 计算日期的index
        int indexItem = 0;
        // 计算格子的index
        int index = 0;

        for (int lineIndex = 0; lineIndex < mLineCount; ++lineIndex) {

            for (int rowIndex = 0; rowIndex < DAYS_COUNT_IN_WEEK; ++rowIndex) {

                // 若小于偏移量 则跳过
                if (mDayOfMonthStartOffset != 1 && index < DAYS_COUNT_IN_WEEK - mDayOfMonthStartOffset + 1) {
                    ++index;
                    continue;
                }

                // 大于总天数则跳出
                if (indexItem > mMonthDaysCount - 1) {
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
            performClick();
            // EdisonLi TODO 2020/3/9 按下时的效果
            onActionDown(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // EdisonLi TODO 2020/3/9 抬起时的效果
            onActionUp(event.getX(), event.getY());
        }

        return true;
    }

    private void drawSingleItem(Canvas canvas, Calendar item, int lineIndex, int rowIndex) {
        // 格子左上角的横坐标
        int x = rowIndex * mItemWidth;
        int y = lineIndex * mItemHeight;
        item.setLocationX(x);
        item.setLocationY(y);
        drawDayText(canvas, item, x, y, mUnSelectedDateTextPaint);
    }

    public void onActionDown(float x, float y) {
        calculateDateByLocation(x, y);
    }

    private void calculateDateByLocation(float x, float y) {
        int indexCol = (int) ((x - getPaddingLeft()) / mItemWidth);
        int indexRow = (int) ((y - getPaddingTop()) / mItemHeight);

        Log.d("INDEX_COL", indexCol + "");
        Log.d("INDEX_ROW", indexRow + "");
        if (indexCol < DAYS_COUNT_IN_WEEK - mDayOfMonthStartOffset + 1 && indexRow == 0){
            // 点击的时偏移量的格子
        }else {
            int checkedDate = indexCol + indexRow * DAYS_COUNT_IN_WEEK - (DAYS_COUNT_IN_WEEK - mDayOfMonthStartOffset);
            if (checkedDate <= mMonthDaysCount ){
                Toast.makeText(getContext(), checkedDate + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActionUp(float x, float y) {

    }
}
