package com.junlong0716.calendarview;

import androidx.annotation.NonNull;
import com.junlong0716.library.ICalendarStrategy;
import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/18 13:37
 * desc :
 */
public final class RangeCalendarExtendDateStrategy implements ICalendarStrategy<RangeCalendarEntity> {
    // 单例
    private static volatile RangeCalendarExtendDateStrategy STRATEGY = null;
    @NonNull
    private List<List<RangeCalendarEntity>> mCalendarDates;

    public static RangeCalendarExtendDateStrategy getInstance() {
        if (STRATEGY == null) {
            synchronized (RangeCalendarExtendDateStrategy.class) {
                if (STRATEGY == null) {
                    STRATEGY = new RangeCalendarExtendDateStrategy();
                }
            }
        }
        return STRATEGY;
    }

    private RangeCalendarExtendDateStrategy() {

    }

    @Override
    public void handleClick(RangeCalendarEntity clickEntity) {

    }

    @Override
    public List<RangeCalendarEntity> getCheckedDates() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setListData(List<List<RangeCalendarEntity>> dateList) {
        mCalendarDates = dateList;
    }
}
