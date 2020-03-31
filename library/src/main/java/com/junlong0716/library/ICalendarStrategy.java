package com.junlong0716.library;

import java.util.List;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/03/16 15:20
 * desc : 
 */
public interface ICalendarStrategy<T extends BaseCalendarEntity> {
    /**
     * @param clickEntity 点击的日期
     */
    void handleClick(T clickEntity);

    /**
     * 返回选中的那个日期
     */
    List<T> getCheckedDates();


    void reset();

    /**
     * 设置整个外部列表集合
     * @param dateList 外部列表集合
     */
    void setListData(List<List<T>> dateList);
}
