package com.junlong0716.calendarview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.junlong0716.library.BaseCalendarEntity;

public class MainActivity extends AppCompatActivity implements EHiCalendarRangeStyleView.OnCheckedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final EHiCalendarRangeStyleView calendarRangeStyleView = findViewById(R.id.simpleView);
//
//        calendarRangeStyleView.setDate(2020, Calendar.MARCH, calendarRangeStyleView.getClass());
//
//        calendarRangeStyleView.setOnCheckedListener(new OnCheckedListener() {
//            @Override
//            public void onDaySelectedListener(BaseCalendarEntity item) {
//
//            }
//        });
//
//        final LinearLayout linearLayout = findViewById(R.id.container);
//
//        for (int i = 0; i < 11; i++) {
//            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            EHiCalendarRangeStyleView eHiCalendarRangeStyleView = new EHiCalendarRangeStyleView(this);
//            eHiCalendarRangeStyleView.setLayoutParams(layoutParams);
//            eHiCalendarRangeStyleView.setDate(2020, i, eHiCalendarRangeStyleView.getClass());
//            eHiCalendarRangeStyleView.setOnCheckedListener(this);
//            linearLayout.addView(eHiCalendarRangeStyleView);
//        }

    }

    @Override
    public void onDaySelectedListener(BaseCalendarEntity item) {

    }
}
