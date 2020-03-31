package com.junlong0716.calendarview;

import com.junlong0716.library.ICalendarStrategy;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/18 13:32
 * desc : 策略工厂创建接口
 */
public interface ICalendarStrategyFactory {
    ICalendarStrategy createStrategy(boolean enableExtend);
}
