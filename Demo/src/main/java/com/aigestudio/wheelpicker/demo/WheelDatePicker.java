package com.aigestudio.wheelpicker.demo;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.view.WheelCurvedPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class WheelDatePicker extends WheelCurvedPicker {
    private static final long MILL_OFFSET = 28800000;//东八区时间偏移
    private static final long DAY_MILL_SECONDS = 86400000;
    private static final int DEFAULT_DAY_COUNT = 30;
    private Date mStartDate;
    private int mDayCount = DEFAULT_DAY_COUNT;
    private List<String> mDays = new ArrayList<>();
    private List<Date> mDates = new ArrayList<>();

    public WheelDatePicker(Context context) {
        super(context);
        init();
    }

    public WheelDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initDates();
        setCurrentDate();
    }

    private void initDates() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 E");
        Calendar calendar = Calendar.getInstance();
        if (mStartDate == null) {
            mStartDate = Calendar.getInstance().getTime();
        }
        calendar.setTime(mStartDate);
        mDayCount = mDayCount >= 1 ? mDayCount : DEFAULT_DAY_COUNT;
        for (int i = 0; i < mDayCount; i++) {
            mDates.add(calendar.getTime());
            mDays.add(formatter.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        super.setData(mDays);
    }


    private void setCurrentDate() {
        int index = setSelectDate(Calendar.getInstance().getTime());
        if (index >= 0 && index < mDayCount) {
            mDays.set(index, "今天");
        }
    }

    /**
     * 获取两个日期之间相隔天数
     *
     * @return
     */
    private int getIntervalDays(Date fromDay, Date toDay) {
        long fromMillSeconds = fromDay.getTime();
        long toMillSeconds = toDay.getTime();
        int intervalDays = (int) (((fromMillSeconds - fromMillSeconds % DAY_MILL_SECONDS) - (toMillSeconds - toMillSeconds % DAY_MILL_SECONDS)) / DAY_MILL_SECONDS);
        return intervalDays >= 0 ? intervalDays : -1;
    }

    public void setStartDate(Date startDate) {
        if (getIntervalDays(mStartDate, startDate) == 0) {
            return;
        }
        this.mStartDate = startDate;
        initDates();
    }

    public void setDayCount(int dayCount) {
        if (mDayCount == dayCount) {
            return;
        }
        this.mDayCount = dayCount;
        initDates();
    }

    public void setSelectIndex(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mDayCount) {
            setItemIndex(selectIndex);
        }
    }

    public int setSelectDate(Date date) {
        Date firstDay = mDates.get(0);
        int selectIndex;
        int interval = getIntervalDays(date, firstDay);

        if (interval < 0 || interval > mDayCount) {
            selectIndex = -1;
        } else {
            selectIndex = interval;
        }

        setSelectIndex(selectIndex);
        return selectIndex;
    }

    public Date getSelectDate(int selectIndex) {
        if (selectIndex < 0 || selectIndex > mDayCount) {
            return mDates.get(selectIndex);
        }
        return null;
    }

    /**
     * 获取指定位置日期当天第一毫秒值
     *
     * @param selectIndex
     * @return
     */
    public long getSelectMills(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mDayCount) {
            long time = mDates.get(selectIndex).getTime();
            return time - time % DAY_MILL_SECONDS - MILL_OFFSET;
        }
        return -1;
    }

    public boolean isCurrentDay(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mDayCount) {
            return getIntervalDays(mDates.get(selectIndex), new Date()) == 0;
        }
        return false;
    }

    public boolean isCurrentDay(long millSeconds) {
        long time = Calendar.getInstance().getTimeInMillis();
        long tMills = time - time % DAY_MILL_SECONDS - MILL_OFFSET;
        return tMills == millSeconds;
    }
}