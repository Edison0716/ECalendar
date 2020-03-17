package com.junlong0716.calendarview;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void monthOffSetTest() {
        int monthEndDiff = CalendarUtil.getDayOfMonthStartOffset(2020, Calendar.JANUARY,Calendar.SUNDAY);
        System.out.println(monthEndDiff);
    }
}