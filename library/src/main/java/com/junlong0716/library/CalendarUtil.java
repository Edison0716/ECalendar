package com.junlong0716.library;

import android.content.Context;

/**
 * @ClassName: CalendarUtil
 * @Description:
 * @Author: LiJunlong
 * @CreateDate: 2020/3/7 6:13 PM
 */
public class CalendarUtil {
    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
