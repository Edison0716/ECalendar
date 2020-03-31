package com.junlong0716.calendarview;

import com.junlong0716.library.ICalendarStrategy;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/18 13:31
 * desc : 
 */
public class CalendarStrategyFactory implements ICalendarStrategyFactory {
    // 单例
    private static volatile CalendarStrategyFactory INSTANCE = null;
    private ICalendarStrategy mICalendarStrategy;

    public static CalendarStrategyFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (CalendarStrategyFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CalendarStrategyFactory();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public ICalendarStrategy createStrategy(boolean enableExtend) {
        if (enableExtend) {
            mICalendarStrategy = RangeCalendarExtendDateStrategy.getInstance();
            return mICalendarStrategy;
        } else {
            mICalendarStrategy = RangeCalendarDateStrategy.getInstance();
            return mICalendarStrategy;
        }
    }

    public ICalendarStrategy getICalendarStrategy() {
        return mICalendarStrategy;
    }
}
