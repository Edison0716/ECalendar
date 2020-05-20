package com.junlong0716.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.junlong0716.library.BaseCommonCalendarView;
import com.junlong0716.library.CalendarUtil;
import java.util.List;

/**
 * @ClassName: EHiCalendarRangeStyleView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 4:24 PM
 */
public class EHiRangeBaseCalendarView extends BaseCommonCalendarView<RangeCalendarEntity> {

    public EHiRangeBaseCalendarView(Context context) {
        super(context);
    }

    public EHiRangeBaseCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EHiRangeBaseCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void drawDayText(Canvas canvas, RangeCalendarEntity item) {
        // 文字居中
        Paint.FontMetrics metrics = mUnselectedDateTextPaint.getFontMetrics();
        int mTextBaseLine = (int) (mItemHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2);

        float baselineY = mTextBaseLine + item.getLocationY();
        int centerX = item.getLocationX() + mItemWidth / 2;

        // 是否起始时选中的样式
        // 是否是终止选中的样式
        if (item.isStartCheckedDay() || item.isEndCheckedDay()) {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY,
                    mSelectedDateTextPaint);
        }

        // 不可用状态 包括 时间已经过去 还有 当天不能用
        else if (!item.isAvailable()) {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY,
                    mUnavailableDateTextPaint);
        }

        // 普通未选中的样式
        else {
            checkToday(canvas, item.isToday(), String.valueOf(item.getDay()), centerX, baselineY,
                    mUnselectedDateTextPaint);
        }

        // 文字与上部分的圆的距离
        int marginTop = 10;
        // 圆距离顶部的距离
        int paddingTop = (mItemHeight - CalendarUtil.dipToPx(getContext(), 32)) / 2;
        int desPointY = mItemHeight + item.getLocationY() - CalendarUtil.dipToPx(getContext(),3);
        // 绘制描述文字
        if (!TextUtils.isEmpty(item.getDes()) && !item.isStartCheckedDay() && !item.isEndCheckedDay()) {
            canvas.drawText(item.getDes(), centerX, desPointY, mDesTextPaint);
        }
        // 是取车
        else if (item.isStartCheckedDay() && item.isEndCheckedDay()) {
            canvas.drawText("取/还车", centerX, desPointY, mDesTextPaint);
        } else if (item.isStartCheckedDay()) {
            canvas.drawText("取车", centerX, desPointY, mDesTextPaint);
        } else if (item.isEndCheckedDay()) {
            canvas.drawText("还车", centerX, desPointY, mDesTextPaint);
        }
    }

    private void checkToday(Canvas canvas, boolean today, String value, float x, float y, Paint p) {
        canvas.drawText(today ? "今" : value, x, y, p);
    }

    @Override
    public void drawDaySelected(Canvas canvas, RangeCalendarEntity item) {

        int halfWidth = mItemWidth / 2;
        int halfHeight = mItemHeight / 2;
        // 画圆所需
        int rX = item.getLocationX() + halfWidth;
        int rY = item.getLocationY() + halfHeight;
        int r = CalendarUtil.dipToPx(getContext(), 16);

        int r2 = CalendarUtil.dipToPx(getContext(), 32);

        // 为了与圆相切割
        int circleOutsideParentTop = (mItemHeight - r2) / 2;

        // 画矩形所需
        int top = item.getLocationY() + circleOutsideParentTop;
        int bottom = item.getLocationY() + mItemHeight - circleOutsideParentTop;

        // 绘制范围内
        if (item.isRangedCheckedDay() && !item.isStartCheckedDay()) {
            if (CalendarUtil.getMonthDaysCount(item.getYear(), item.getMonth()) == item.getDay()) {
                drawCircle(rX, rY, r, canvas, R.color.calendar_ranged_color);
                drawRect(canvas, item.getLocationX(), top, item.getLocationX() + halfWidth, bottom,
                        R.color.calendar_ranged_color);
            } else if (1 == item.getDay()) {
                drawCircle(rX, rY, r, canvas, R.color.calendar_ranged_color);
                drawRect(canvas, item.getLocationX() + halfWidth, top, item.getLocationX() + mItemWidth, bottom,
                        R.color.calendar_ranged_color);
            } else {
                // 画矩形阴影
                drawRect(canvas, item.getLocationX(), top, item.getLocationX() + mItemWidth, bottom,
                        R.color.calendar_ranged_color);
            }
        }
        // 绘制 起始点 终点
        else if (item.isStartCheckedDay() && item.isEndCheckedDay()) {
            drawCircle(rX, rY, r, canvas, R.color.calendar_checked_color);
        }
        // 绘制 起始点
        else if (item.isStartCheckedDay()) {
            // 范围选择
            if (item.isRangedCheckedDay()) {
                drawRect(canvas, item.getLocationX() + halfWidth, top, item.getLocationX() + mItemWidth, bottom, R.color.calendar_ranged_color);
            }
            drawCircle(rX, rY, r, canvas, R.color.calendar_checked_color);
        }
        // 绘制终止点
        else if (item.isEndCheckedDay()) {
            drawRect(canvas, item.getLocationX(), top, item.getLocationX() + halfWidth, bottom, R.color.calendar_ranged_color);
            drawCircle(rX, rY, r, canvas, R.color.calendar_checked_color);
        }
    }

    @Override
    public void createCalendarStrategy(List<List<RangeCalendarEntity>> calendarDates) {
        // 创建策略类
        mICalendarStrategy = CalendarStrategyFactory.getInstance().createStrategy(false);
        mICalendarStrategy.setListData(calendarDates);
    }

    /**
     * 画圆形
     * @param rX 圆心坐标
     * @param rY 圆心坐标
     * @param r  半径
     * @param canvas 画板
     * @param color 颜色
     */
    private void drawCircle(int rX, int rY, int r, Canvas canvas, int color) {
        // 选中的效果 这个为圆形
        // 计算圆心
        mSelectedDateBgPaint.setColor(ContextCompat.getColor(getContext(), color));
        canvas.drawCircle(rX, rY, r, mSelectedDateBgPaint);
    }

    /**
     * 绘制圆形
     * @param canvas 画板
     * @param left 左
     * @param top 上
     * @param right 右
     * @param bottom 下
     * @param color 画笔颜色
     */
    private void drawRect(Canvas canvas, int left, int top, int right, int bottom, int color) {
        Rect rect = new Rect();
        rect.set(left, top, right, bottom);
        mSelectedDateBgPaint.setColor(ContextCompat.getColor(getContext(), color));
        canvas.drawRect(rect, mSelectedDateBgPaint);
    }
}
