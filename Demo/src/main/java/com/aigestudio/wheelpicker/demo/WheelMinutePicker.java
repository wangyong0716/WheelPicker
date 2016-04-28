package com.aigestudio.wheelpicker.demo;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aigestudio.wheelpicker.widget.IDigital;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class WheelMinutePicker extends WheelCurvedPicker implements IDigital {
    private static final long MINUTE_MILL_SECONDS = 60000;
    public static final int DIGIT_TYPE_SINGLE = 1;
    public static final int DIGIT_TYPE_DOUBLE = 2;

    private List<String> mSingleMinutes = new ArrayList<>();
    private List<String> mDoubleMinutes = new ArrayList<>();

    private List<String> minutes;

    private int mStartMinute;

    private int mDigitType;

    public WheelMinutePicker(Context context) {
        super(context);
        init();
    }

    public WheelMinutePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setStartMinute(0);
        setDigitType(DIGIT_TYPE_DOUBLE);
        super.setData(minutes);
//        setSelectMinute(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public void setStartMinute(int minute) {
        if (mStartMinute == minute && mSingleMinutes.size() > 0) {
            return;
        }
        mStartMinute = minute;
        mSingleMinutes.clear();
        mDoubleMinutes.clear();
        for (int i = minute; i < 60; i++) {
            String num = String.valueOf(i);
            mSingleMinutes.add(num);
            if (num.length() == 1) {
                num = "0" + num;
            }
            mDoubleMinutes.add(num);
        }
        if (mDigitType == DIGIT_TYPE_SINGLE) {
            minutes = mSingleMinutes;
        } else {
            minutes = mDoubleMinutes;
        }
        super.setData(minutes);
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setSelectMinute(int minute) {
        try {
            int startMinute = Integer.parseInt(mSingleMinutes.get(0));
            int interval = minute - startMinute;
            if (interval >= 0 && interval < 60 - startMinute) {
                setItemIndex(interval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDigitType(int type) {
        if (mDigitType == type) {
            return;
        }
        mDigitType = type;
        if (type == DIGIT_TYPE_SINGLE) {
            minutes = mSingleMinutes;
        } else {
            minutes = mDoubleMinutes;
        }
        super.setData(minutes);
    }

    /**
     * 获取从当前小时0秒起经历的毫秒数
     *
     * @param selectIndex
     * @return
     */
    public long getSelectMills(int selectIndex) {
        if (selectIndex >= 0 && selectIndex < mSingleMinutes.size()) {
            try {
                return Long.parseLong(mSingleMinutes.get(selectIndex)) * MINUTE_MILL_SECONDS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}