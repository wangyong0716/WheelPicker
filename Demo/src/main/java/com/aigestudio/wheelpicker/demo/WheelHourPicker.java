package com.aigestudio.wheelpicker.demo;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aigestudio.wheelpicker.widget.IDigital;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class WheelHourPicker extends WheelCurvedPicker implements IDigital {
    private static final long HOUR_MILL_SECONDS = 3600000;
    public static final int DIGIT_TYPE_SINGLE = 1;
    public static final int DIGIT_TYPE_DOUBLE = 2;

    private List<String> mSingleHours = new ArrayList<>();
    private List<String> mDoubleHours = new ArrayList<>();

    private List<String> hours;
    private int mStartHour;

    private int mDigitType;

    public WheelHourPicker(Context context) {
        super(context);
        init();
    }

    public WheelHourPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setStartHour(0);
        setDigitType(DIGIT_TYPE_DOUBLE);
        super.setData(hours);
//        setSelectHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }

    public void setStartHour(int hour) {
        if (mStartHour == hour && mSingleHours.size() > 0) {
            return;
        }
        mStartHour = hour;
        mSingleHours.clear();
        mDoubleHours.clear();
        for (int i = hour; i < 24; i++) {
            String num = String.valueOf(i);
            mSingleHours.add(num);
            if (num.length() == 1) {
                num = "0" + num;
            }
            mDoubleHours.add(num);
        }
        if (mDigitType == DIGIT_TYPE_SINGLE) {
            hours = mSingleHours;
        } else {
            hours = mDoubleHours;
        }
        super.setData(hours);
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setSelectHour(int hour) {
        try {
            int startHour = Integer.parseInt(mSingleHours.get(0));
            int interval = hour - startHour;
            if (interval >= 0 && interval < 24 - startHour) {
                setItemIndex(interval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setDigitType(int type) {
        if (mDigitType == type && hours != null) {
            return;
        }
        this.mDigitType = type;
        if (type == DIGIT_TYPE_SINGLE) {
            hours = mSingleHours;
        } else {
            hours = mDoubleHours;
        }
        super.setData(hours);
    }

    /**
     * 获取选中小时从当天0刻起经历的毫秒数
     */
    public long getSelectMills(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mSingleHours.size()) {
            try {
                return Long.parseLong(mSingleHours.get(selectIndex)) * HOUR_MILL_SECONDS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean isCurrentHour(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mSingleHours.size()) {
            try {
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                return Integer.parseInt(mSingleHours.get(selectIndex)) == hour;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}