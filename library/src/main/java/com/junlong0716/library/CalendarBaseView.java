package com.junlong0716.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
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

    public static final int DAYS_COUNT_IN_WEEK = 7;
    // 默认颜色值
    public static final int DEFAULT_TEXT_SIZE = 14;
    // 当前月份的画笔
    protected final Paint mUnselectedDateTextPaint = new Paint();
    // 选中的日期画笔
    protected final Paint mSelectedDateTextPaint = new Paint();
    // 不可用的日期画笔
    protected final Paint mUnavailableDateTextPaint = new Paint();
    // 不是当月的画笔
    protected final Paint mOtherMonthTextPaint = new Paint();
    // 日历数据
    protected final List<? super BaseCalendarEntity> mItems = new ArrayList<>(31);
    // 每一项日期的宽度 高度
    protected int mItemWidth;
    // 默认 高度 等于 宽度 一个正方形
    protected int mItemHeight = -1;
    // 一个月份总共的行数  不是5行 就是6行 因为有日期偏移格子
    protected int mLineCount;
    // 设置当前日历显示的年 月
    protected int mYear;
    protected int mMonth;
    // 从周几开始计算起始点
    protected int mWeekStart = java.util.Calendar.SUNDAY;
    // 是否是当前的月份
    protected boolean mIsCurrentMonth = false;
    // 计算偏移量
    protected int mDayOfMonthStartOffset;
    // 该月一共有多少天
    protected int mMonthDaysCount;
    // 一共有多少个格子  不是35个  就是 42 个
    protected int mTotalBlocksInMonth;

    @Nullable
    protected OnCheckedListener mOnCheckedListener;

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

    // 初始化 画笔
    private void initPaint(Context context) {

        mUnselectedDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mUnselectedDateTextPaint.setAntiAlias(true);
        mUnselectedDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mUnselectedDateTextPaint.setFakeBoldText(true);
        mUnselectedDateTextPaint.setColor(Color.parseColor("#333333"));

        mSelectedDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mSelectedDateTextPaint.setAntiAlias(true);
        mSelectedDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedDateTextPaint.setFakeBoldText(true);
        mSelectedDateTextPaint.setColor(Color.BLACK);

        mOtherMonthTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mOtherMonthTextPaint.setAntiAlias(true);
        mOtherMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mOtherMonthTextPaint.setFakeBoldText(true);
        mOtherMonthTextPaint.setColor(Color.BLACK);

        mUnavailableDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mUnavailableDateTextPaint.setAntiAlias(true);
        mUnavailableDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mUnavailableDateTextPaint.setFakeBoldText(true);
        mUnavailableDateTextPaint.setColor(Color.parseColor("#999999"));
    }

    public void setDate(@NonNull List<? extends BaseCalendarEntity> items) {
        if (items.isEmpty()) {
            return;
        }

        int year = items.get(0).getYear();
        int month = items.get(0).getMonth();

        mIsCurrentMonth = CalendarUtil.isCurrentMonth(month);

        mYear = year;
        mMonth = month;

        mItems.clear();
        mItems.addAll(items);

        requestLayout();
    }

    /**
     * 设置数据
     * @param year 年
     * @param month 月
     */
    public void setDate(int year, int month, Class clazz) {
        mYear = year;
        mMonth = month;
        CalendarUtil.createDate(mYear, mMonth, mItems, clazz);
        mIsCurrentMonth = CalendarUtil.isCurrentMonth(mMonth);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            calculateOffset();
            int realHeight = getPaddingTop() + getPaddingBottom() + mLineCount * mItemHeight;
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(realHeight, MeasureSpec.AT_MOST));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 计算偏移量
     */
    public void calculateOffset() {
        // 偏移量 该月上一个月分的结束的位置
        // 如果是一月份 则 找上一年的12月份
        if (mMonth == java.util.Calendar.JANUARY) {
            mDayOfMonthStartOffset = CalendarUtil
                    .getDayOfMonthStartOffset(mYear - 1, java.util.Calendar.DECEMBER, mWeekStart);
        } else {
            mDayOfMonthStartOffset = CalendarUtil.getDayOfMonthStartOffset(mYear, mMonth - 1, mWeekStart);
        }
        mMonthDaysCount = CalendarUtil.getMonthDaysCount(mYear, mMonth);
        mLineCount = CalendarUtil.getMaxLines(mDayOfMonthStartOffset, mMonthDaysCount);
        mTotalBlocksInMonth = CalendarUtil.getTotalBlockInMonth(mLineCount);
        // 计算一个格子的宽度
        mItemWidth = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        // 这里设置高度与宽度相等
        if (mItemHeight <= 0) {
            mItemHeight = mItemWidth;
        } else {
            // EdisonLi TODO 2020/3/10 自定义Item高度
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制基础文字
        drawCalendarDate(canvas);
    }

    abstract void drawCalendarDate(Canvas canvas);

    public void setOnCheckedListener(@Nullable OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public interface OnCheckedListener {

        void onDaySelectedListener(BaseCalendarEntity item);
    }
}
