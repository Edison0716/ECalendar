package com.junlong0716.library.style;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.junlong0716.library.CalendarUtil;
import com.junlong0716.library.RangeCalendarEntity;
import com.junlong0716.library.CalendarRangeView;

import java.util.Calendar;

/**
 * @ClassName: EHiCalendarRangeStyleView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 4:24 PM
 */
public class EHiCalendarRangeStyleView extends CalendarRangeView {

    public EHiCalendarRangeStyleView(Context context) {
        super(context);
    }

    public EHiCalendarRangeStyleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EHiCalendarRangeStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawDayText(Canvas canvas, RangeCalendarEntity item) {
        Paint.FontMetrics metrics = mUnselectedDateTextPaint.getFontMetrics();
        int mTextBaseLine = (int) (mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2);

        float baselineY = mTextBaseLine + item.getLocationY();
        int centerX = item.getLocationX() + mItemWidth / 2;

        // 是否起始时选中的样式
        // 是否是终止选中的样式
        if (item.isStartCheckedDay() || item.isEndCheckedDay()) {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY, mSelectedDateTextPaint);
        }

        // 不可用状态 包括 时间已经过去 还有 当天不能用
        else if (!item.isAvailable()) {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY, mUnavailableDateTextPaint);
        }

        // 普通未选中的样式
        else {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY, mUnselectedDateTextPaint);
        }

        // 文字与上部分的圆的距离
        int marginTop = 10;
        // 圆距离顶部的距离
        int paddingTop = (mItemHeight - CalendarUtil.dipToPx(getContext(), 32)) / 2;
        int desPointY = mItemHeight  + item.getLocationY();
        // 绘制描述文字
        if (!TextUtils.isEmpty(item.getDes()) && !item.isStartCheckedDay() && !item.isEndCheckedDay()){
            canvas.drawText(item.getDes(), centerX, desPointY, mDesTextPaint);
        }
        // 是取车
        else if (item.isStartCheckedDay() && item.isEndCheckedDay()){
            canvas.drawText("取/还车", centerX, desPointY, mDesTextPaint);
        }
        else if (item.isStartCheckedDay()){
            canvas.drawText("取车", centerX, desPointY, mDesTextPaint);
        }
        else if (item.isEndCheckedDay()){
            canvas.drawText("还车", centerX, desPointY, mDesTextPaint);
        }
    }

    private void checkToday(Canvas canvas, boolean today, String value, float x, float y, Paint p) {
        canvas.drawText(today ? "今" : value, x, y, p);
    }

    @Override
    public void drawDaySelected(Canvas canvas, RangeCalendarEntity item) {
        // 绘制范围内
        if (item.isRangedCheckedDay() && !item.isStartCheckedDay()) {

            if (CalendarUtil.getMonthDaysCount(item.getYear(),item.getMonth()) == item.getDay()){



            }else {
                int a = (mItemHeight - CalendarUtil.dipToPx(getContext(), 32)) / 2;
                // 画矩形阴影
                Rect rect = new Rect(item.getLocationX(), item.getLocationY() + a, item.getLocationX() + mItemWidth,
                        item.getLocationY() + mItemHeight - a);
                mSelectedDateBgPaint.setColor(Color.parseColor("#CCEEEE"));
                canvas.drawRect(rect, mSelectedDateBgPaint);
            }
        }
        // 绘制 起始点 终点
        else if (!(item.isStartCheckedDay() & item.isEndCheckedDay())) {
            // 为了与圆相切割
            int a = (mItemHeight - CalendarUtil.dipToPx(getContext(), 32)) / 2;

            Rect rect = null;

            if (item.isStartCheckedDay()) {
                // 画矩形阴影
                rect = new Rect(item.getLocationX() + (mItemWidth / 2), item.getLocationY() + a,
                        item.getLocationX() + mItemWidth,
                        item.getLocationY() + mItemHeight - a);
            } else if (item.isEndCheckedDay()) {
                rect = new Rect(item.getLocationX(), item.getLocationY() + a, item.getLocationX() + (mItemWidth / 2),
                        item.getLocationY() + mItemHeight - a);
            }

            if (rect == null) {
                return;
            }

            mSelectedDateBgPaint.setColor(Color.parseColor("#CCEEEE"));

            if (item.isEndCheckedDay() || (item.isStartCheckedDay() && item.isRangedCheckedDay())){
                canvas.drawRect(rect, mSelectedDateBgPaint);
            }

            drawCircle(item, canvas);
        } else {
            drawCircle(item, canvas);
        }
    }

    private void drawCircle(RangeCalendarEntity item, Canvas canvas) {
        // 选中的效果 这个为圆形
        // 计算圆心
        int rX = item.getLocationX() + mItemWidth / 2;
        int rY = item.getLocationY() + mItemHeight / 2;
        int r = CalendarUtil.dipToPx(getContext(), 16);
        mSelectedDateBgPaint.setColor(Color.parseColor("#29B7B7"));
        canvas.drawCircle(rX, rY, r, mSelectedDateBgPaint);
    }
}
