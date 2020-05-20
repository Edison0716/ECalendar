package com.junlong0716.calendarview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.junlong0716.library.CalendarUtil;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/12 16:19
 * desc :
 */
public class CalendarRecyclerView extends RecyclerView implements CalendarAdapter.DaySelectedCallback {

    private CalendarAdapter mCalendarAdapter;

    public CalendarRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.WHITE);
        setLayoutManager(new LinearLayoutManager(getContext()));
        mCalendarAdapter = new CalendarAdapter(getContext(), this);

            long startTimeStamp = CalendarUtil.Date2TimeStamp(2020, Calendar.MONTH, 11);
            long endTimeStamp = CalendarUtil.Date2TimeStamp(2020, Calendar.MONTH, 21);

        List<List<RangeCalendarEntity>> date = Utils.createDate(2020, Calendar.MARCH, 1);
        List<List<RangeCalendarEntity>> dates = Utils.createRangeDate(startTimeStamp, endTimeStamp, 6);
        setAdapter(mCalendarAdapter);

//        for (List<RangeCalendarEntity> oneMonthDate : dates) {
//            Iterator<RangeCalendarEntity> inter = oneMonthDate.iterator();
//            while (inter.hasNext()) {
//                if (!inter.next().isAvailable()) {
//                    inter.remove();
//                }
//            }
//        }

        mCalendarAdapter.setData(dates);
        setHasFixedSize(true);
    }

    @Override
    public void onDaySelectedListener() {
        mCalendarAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 日历策略数据重置
        CalendarStrategyFactory.getInstance().getICalendarStrategy().reset();
    }
}
