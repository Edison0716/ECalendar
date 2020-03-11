package com.junlong0716.calendarview;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.junlong0716.library.BaseCalendarEntity;
import com.junlong0716.library.RangeCalendarEntity;
import com.junlong0716.library.CalendarBaseView.OnCheckedListener;
import com.junlong0716.library.style.CalendarRangeStyleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarRangeStyleView calendarRangeStyleView = findViewById(R.id.simpleView);

        calendarRangeStyleView.setDate(2020, Calendar.MARCH, calendarRangeStyleView.getClass());

        calendarRangeStyleView.setOnCheckedListener(new OnCheckedListener() {
            @Override
            public void onDaySelectedListener(BaseCalendarEntity item) {
                if (item instanceof RangeCalendarEntity) {
                    Toast.makeText(MainActivity.this, item.isAvailable() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
