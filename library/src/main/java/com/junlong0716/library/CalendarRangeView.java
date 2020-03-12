package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
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

    public static final String RANGE_CALENDAR_CLASS_NAME = CalendarRangeView.class.getName();

    private int mCheckedDay;

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

                // 格子左上角的横坐标
                int x = rowIndex * mItemWidth;
                int y = lineIndex * mItemHeight;

                RangeCalendarEntity item = (RangeCalendarEntity) mItems.get(indexItem);

                // 记录一下每一个item 可绘制区域
                item.setLocationX(x);
                item.setLocationY(y);

                // 绘制背景
                drawDaySelected(canvas, item);

                // 绘制单元块
                drawDayText(canvas, item);

                ++indexItem;
                ++index;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
            // EdisonLi 2020/3/9 按下时的效果
            onActionDown(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // EdisonLi 2020/3/9 抬起时的效果
            boolean isChecked = onActionUp(event.getX(), event.getY());
            if (isChecked) {
                handleClick();
            }
            return isChecked;
        }
        return true;
    }

    private void handleClick() {
        if (mOnCheckedListener != null) {
            for (Object item : mItems) {
                if (item instanceof RangeCalendarEntity) {
                    if (((RangeCalendarEntity) item).getDay() == mCheckedDay) {
                        if (((RangeCalendarEntity) item).isAvailable()) {
                            RangeCalendarViewDelegate.handleClick((RangeCalendarEntity) item, mItems);
                            mOnCheckedListener.onDaySelectedListener((RangeCalendarEntity) item);
                        }
                        invalidate();
                        break;
                    }
                }
            }
        }
    }

    public void onActionDown(float x, float y) {
        mCheckedDay = calculateDateByLocation(x, y);
        Log.d("checked", mCheckedDay + "");
    }

    private int calculateDateByLocation(float x, float y) {
        int indexCol = (int) ((x - getPaddingLeft()) / mItemWidth);
        int indexRow = (int) ((y - getPaddingTop()) / mItemHeight);

        if (mDayOfMonthStartOffset <= 1) {
            // 没有偏移量
            int checkedDate = indexCol + indexRow * DAYS_COUNT_IN_WEEK + 1;
            if (checkedDate <= mMonthDaysCount) {
                return checkedDate;
            }
        } else if (indexCol < DAYS_COUNT_IN_WEEK - mDayOfMonthStartOffset + 1 && indexRow == 0) {
            // 点击的时偏移量的格子 do nothing

        } else {
            // 有偏移量 要减去偏移量
            int checkedDate = indexCol + indexRow * DAYS_COUNT_IN_WEEK - (DAYS_COUNT_IN_WEEK - mDayOfMonthStartOffset);
            if (checkedDate <= mMonthDaysCount) {
                return checkedDate;
            }
        }

        return -1;
    }

    public boolean onActionUp(float x, float y) {
        return calculateDateByLocation(x, y) != -1 && calculateDateByLocation(x, y) == mCheckedDay;
    }
}
