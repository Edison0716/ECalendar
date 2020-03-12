package com.junlong0716.calendarview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.junlong0716.library.BaseCalendarEntity;
import com.junlong0716.library.CalendarBaseView.OnCheckedListener;
import com.junlong0716.library.RangeCalendarEntity;
import com.junlong0716.library.RangeCalendarViewDelegate;
import com.junlong0716.library.style.CalendarRangeStyleView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CalendarRangeStyleView calendarRangeStyleView = findViewById(R.id.simpleView);

        calendarRangeStyleView.setDate(2020, Calendar.MARCH, calendarRangeStyleView.getClass());

        calendarRangeStyleView.setOnCheckedListener(new OnCheckedListener() {
            @Override
            public void onDaySelectedListener(BaseCalendarEntity item) {

            }
        });
    }
}
