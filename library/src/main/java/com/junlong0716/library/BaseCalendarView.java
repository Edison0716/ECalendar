package com.junlong0716.library;

import android.content.Context;
import android.content.res.TypedArray;
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
public abstract class BaseCalendarView extends View {

    public static final int DAYS_COUNT_IN_WEEK = 7;
    // 默认颜色值
    public static final int DEFAULT_TEXT_SIZE = 16;
    // 当前月份的画笔
    protected final Paint mUnselectedDateTextPaint = new Paint();
    // 选中的日期画笔
    protected final Paint mSelectedDateTextPaint = new Paint();
    // 不可用的日期画笔
    protected final Paint mUnavailableDateTextPaint = new Paint();
    // 选中的背景样式画笔
    protected final Paint mSelectedDateBgPaint = new Paint();
    // 不是当月的画笔
    protected final Paint mOtherMonthTextPaint = new Paint();
    // 日期描述的画笔
    protected final Paint mDesTextPaint = new Paint();

    /**
     *  日历数据
     */
    protected final List<? super BaseCalendarEntity> mItems = new ArrayList<>(31);
    /**
     * 每一项日期的宽度 高度
     */
    protected int mItemWidth;
    /**
     * 默认 高度 等于 宽度 一个正方形
     */
    protected int mItemHeight = -1;
    /**
     *  一个月份总共的行数  不是5行 就是6行 因为有日期偏移格子
     */
    protected int mLineCount;
    /**
     * 设置当前日历显示的年 月
     */
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    /**
     * 从周几开始计算起始点
     */
    protected int mWeekStart = java.util.Calendar.SUNDAY;
    /**
     * 是否是当前的月份
     */
    protected boolean mIsCurrentMonth = false;
    /**
     * 计算偏移量
     */
    protected int mDayOfMonthStartOffset;
    /**
     * 该月一共有多少天
     */
    protected int mMonthDaysCount;
    /**
     * 一共有多少个格子  不是35个  就是 42 个
     */
    protected int mTotalBlocksInMonth;

    /**
     * 策略类
     */
    @Nullable
    protected ICalendarStrategy mICalendarStrategy;

    @Nullable
    protected OnCheckedListener mOnCheckedListener;

    public BaseCalendarView(Context context) {
        this(context, null);
    }

    public BaseCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化 画笔
     * @param context 上下文
     * @param attrs
     */
    private void initPaint(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseCalendarView);

        try {
            mItemHeight = (int) typedArray.getDimension(R.styleable.BaseCalendarView_date_height,-1f);
        } finally {
            typedArray.recycle();
        }

        mUnselectedDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mUnselectedDateTextPaint.setAntiAlias(true);
        mUnselectedDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mUnselectedDateTextPaint.setColor(Color.parseColor("#333333"));

        mSelectedDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mSelectedDateTextPaint.setAntiAlias(true);
        mSelectedDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedDateTextPaint.setColor(Color.WHITE);

        mOtherMonthTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mOtherMonthTextPaint.setAntiAlias(true);
        mOtherMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mOtherMonthTextPaint.setColor(Color.BLACK);

        mUnavailableDateTextPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mUnavailableDateTextPaint.setAntiAlias(true);
        mUnavailableDateTextPaint.setTextAlign(Paint.Align.CENTER);
        mUnavailableDateTextPaint.setColor(Color.parseColor("#999999"));

        mSelectedDateBgPaint.setTextSize(CalendarUtil.dipToPx(context, DEFAULT_TEXT_SIZE));
        mSelectedDateBgPaint.setAntiAlias(true);
        mSelectedDateBgPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedDateBgPaint.setColor(Color.parseColor("#29B7B7"));

        mDesTextPaint.setTextSize(CalendarUtil.dipToPx(context, 12));
        mDesTextPaint.setAntiAlias(true);
        mDesTextPaint.setTextAlign(Paint.Align.CENTER);
        mDesTextPaint.setColor(Color.parseColor("#666666"));
    }

    public void setDate(@NonNull List<? extends BaseCalendarEntity> items) {
        if (items.isEmpty()) {
            return;
        }
        int year = items.get(0).getYear();
        int month = items.get(0).getMonth();
        int day = items.get(0).getDay();

        mIsCurrentMonth = CalendarUtil.isCurrentMonth(month);
        mYear = year;
        mMonth = month;
        mDay = day;
        mItems.clear();
        mItems.addAll(items);
        calculateOffset();
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
        CalendarUtil.createDate(mYear, mMonth, mItems);
        mIsCurrentMonth = CalendarUtil.isCurrentMonth(mMonth);
        calculateOffset();
        requestLayout();
    }

    /**
     * 获取日期数据
     */
    @Nullable
    public List<? super BaseCalendarEntity> getDate() {
        return mItems;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED
                || heightMode == MeasureSpec.EXACTLY) {
            int realHeight = getPaddingTop() + getPaddingBottom() + mLineCount * mItemHeight;
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(realHeight, MeasureSpec.EXACTLY));
            // 计算一个格子的宽度
            mItemWidth = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        }
    }

    /**
     * 计算偏移量
     */
    public void calculateOffset() {
        if (mItems.isEmpty()) {
            return;
        }
        // 这里设置高度与宽度相等
        if (mItemHeight <= 0) {
            mItemHeight = CalendarUtil.dipToPx(getContext(), 60);
        }
        mMonthDaysCount = CalendarUtil.getMonthDaysCount(mYear, mMonth);
        // 偏移量
        mDayOfMonthStartOffset = CalendarUtil.getDayOfMonthStartOffset(mYear, mMonth, mDay, mWeekStart);
        mLineCount = CalendarUtil.getMaxLines(mDayOfMonthStartOffset, mMonthDaysCount);
        mTotalBlocksInMonth = CalendarUtil.getTotalBlockInMonth(mLineCount);
        // 残月
        if (mItems.size() != mMonthDaysCount) {
            // 一个月的总计时间 减去当前的日期
            mMonthDaysCount = mMonthDaysCount - mDay + 1;
            // 取整获取多少行
            int lineIndex = (mMonthDaysCount + mDayOfMonthStartOffset) / DAYS_COUNT_IN_WEEK;
            // 有余数直接加一行
            if ((mMonthDaysCount + mDayOfMonthStartOffset) % DAYS_COUNT_IN_WEEK > 0) {
                lineIndex += 1;
            }
            // 一共得行数
            mLineCount = lineIndex;
        }
    }

    /**
     * 设置日期高度
     * @param dateHeight 每一个的日期的高度
     */
    public void setDateHeight(int dateHeight){
        mItemHeight = CalendarUtil.dipToPx(getContext(), dateHeight);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制基础文字
        drawCalendarDate(canvas);
    }

    protected abstract void drawCalendarDate(Canvas canvas);

    public void setOnCheckedListener(@Nullable OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public interface OnCheckedListener {

        void onDaySelectedListener(List checkedDates);
    }
}
