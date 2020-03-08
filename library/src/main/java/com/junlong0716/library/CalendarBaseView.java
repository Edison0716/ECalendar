package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: CalendarBaseView
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/8 10:41 AM
 */
public abstract class CalendarBaseView extends View {
    // 默认颜色值
    public static final int DEFAULT_TEXT_SIZE = 14;
    // 当前月份的画笔
    protected final Paint mCurrentMonthTextPaint = new Paint();
    // 其他月份的画笔
    protected final Paint mOtherMonthTextPaint = new Paint();
    // 日历数据
    protected final List<Calendar> mItems = new ArrayList<>(31);

    // 每一项日期的宽度 高度
    protected int mItemWidth;
    protected int mItemHeight;
    // 一个月份总共的行数  不是5行 就是6行 因为有日期偏移格子
    protected int mLineCount;
    // 设置当前日历显示的年 月
    protected int mYear;
    protected int mMonth;
    // 从周几开始计算起始点
    protected int mWeekStart = java.util.Calendar.SUNDAY;


    public CalendarBaseView(Context context) {
        this(context, null);
    }

    public CalendarBaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);

    }

    private void initPaint(Context context) {
        mCurrentMonthTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mCurrentMonthTextPaint.setAntiAlias(true);
        mCurrentMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mCurrentMonthTextPaint.setFakeBoldText(true);
        mCurrentMonthTextPaint.setColor(Color.BLACK);
    }


    public void setDate(int year, int month, List<Calendar> items) {
        mYear = year;
        mMonth = month;
        mItems.clear();
        mItems.addAll(items);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCalendarDate(canvas);
    }

    abstract void drawCalendarDate(Canvas canvas);
}
