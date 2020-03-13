package com.junlong0716.calendarview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.junlong0716.library.BaseCalendarEntity;
import com.junlong0716.library.CalendarBaseView.OnCheckedListener;
import com.junlong0716.library.CalendarUtil;
import com.junlong0716.library.RangeCalendarEntity;
import com.junlong0716.library.RangeCalendarViewDelegate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/12 16:19
 * desc :
 */
public class CalendarRecyclerView extends RecyclerView {

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
        mCalendarAdapter = new CalendarAdapter(getContext());
        List<List<RangeCalendarEntity>> dates = CalendarUtil.createDate(2020, 2, 1);
        setAdapter(mCalendarAdapter);
        RangeCalendarViewDelegate.setDates(dates);
        mCalendarAdapter.setData(dates);
    }

    private static class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapterHolder> {
        private final List<List<RangeCalendarEntity>> mYearMonthList = new ArrayList<>(12);
        private Context mContext;

        CalendarAdapter(Context context) {
            mContext = context;
        }

        public void setData(List<List<RangeCalendarEntity>> yearMonthList) {
            mYearMonthList.clear();
            mYearMonthList.addAll(yearMonthList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CalendarAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            return new CalendarAdapterHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CalendarAdapterHolder holder, int position) {
            holder.mEHiCalendarRangeStyleView.setDate(mYearMonthList.get(position));


            holder.mEHiCalendarRangeStyleView.setOnCheckedListener(new OnCheckedListener() {
                @Override
                public void onDaySelectedListener(BaseCalendarEntity item) {
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mYearMonthList.size();
        }
    }

    private static class CalendarAdapterHolder extends ViewHolder {

        private EHiCalendarRangeStyleView mEHiCalendarRangeStyleView;

        CalendarAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mEHiCalendarRangeStyleView = itemView.findViewById(R.id.calendar);
        }
    }
}
