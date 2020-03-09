package com.junlong0716.calendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.junlong0716.library.Calendar;
import com.junlong0716.library.CalendarUtil;
import com.junlong0716.library.style.CalendarSimpleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarSimpleView simpleView = findViewById(R.id.simpleView);

        List<Calendar> dates = new ArrayList<>();

        for (int i = 0; i < CalendarUtil.getMonthDaysCount(2020, java.util.Calendar.JANUARY); i++) {
            Calendar calendar = new Calendar();
            calendar.setDay(i + 1);
            dates.add(calendar);
        }

        simpleView.setDate(2020, java.util.Calendar.JANUARY, dates);
    }
}
