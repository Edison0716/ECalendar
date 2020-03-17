package com.junlong0716.calendarview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.junlong0716.calendarview.CalendarAdapter.CalendarAdapterHolder;
import com.junlong0716.library.CalendarBaseView.OnCheckedListener;
import com.junlong0716.library.range.RangeCalendarEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/13 14:01
 * desc : 
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapterHolder> {

    private final List<List<RangeCalendarEntity>> mYearMonthList = new ArrayList<>(12);
    private Context mContext;
    private DaySelectedCallback mDaySelectedCallback;

    CalendarAdapter(Context context, DaySelectedCallback daySelectedCallback) {
        mContext = context;
        this.mDaySelectedCallback = daySelectedCallback;
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
    public void onBindViewHolder(@NonNull CalendarAdapterHolder holder, final int position) {
        holder.mEHiCalendarRangeStyleView.setDate(mYearMonthList.get(position));
        holder.mEHiCalendarRangeStyleView.createCalendarStrategy(mYearMonthList);
        holder.mEHiCalendarRangeStyleView.setOnCheckedListener(new OnCheckedListener() {
            @Override
            public void onDaySelectedListener(List checkedDates) {
                notifyDataSetChanged();

                for (List<RangeCalendarEntity> rangeCalendarEntities : mYearMonthList) {
                    for (RangeCalendarEntity rangeCalendarEntity : rangeCalendarEntities) {
                        Log.d("IS_CHECKED", rangeCalendarEntity.isRangedCheckedDay() + "");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mYearMonthList.size();
    }

    public interface DaySelectedCallback {

        void onDaySelectedListener();
    }

    static class CalendarAdapterHolder extends ViewHolder {

        private EHiCalendarRangeStyleView mEHiCalendarRangeStyleView;

        CalendarAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mEHiCalendarRangeStyleView = itemView.findViewById(R.id.calendar);
        }
    }
}




