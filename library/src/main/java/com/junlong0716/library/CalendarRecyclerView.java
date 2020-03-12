package com.junlong0716.library;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.junlong0716.library.style.EHiCalendarRangeStyleView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/12 16:19
 * desc : 
 */
public class CalendarRecyclerView extends RecyclerView {

    private final List<Integer> mYearMonthList = new ArrayList<>(12);

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
        setAdapter(new CalendarAdapter(getContext()));
    }

    private static class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapterHolder> {

        private Context mContext;

        CalendarAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public CalendarAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            return new CalendarAdapterHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CalendarAdapterHolder holder, int position) {
            Log.d("H", "ok");
            holder.mEHiCalendarRangeStyleView.setDate(2020, 3, holder.mEHiCalendarRangeStyleView.getClass());
        }

        @Override
        public int getItemCount() {
            return 12;
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
