package com.aigestudio.wheelpicker.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.core.IWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCrossPicker;
import com.aigestudio.wheelpicker.widget.IDigital;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class TimeChooser extends LinearLayout implements IWheelPicker, IDigital {
    private static final int TYPE_DATE = 1;
    private static final int TYPE_HOUR = 2;
    private static final int TYPE_MINUTE = 3;

    private WheelDatePicker mPickerDate;
    private WheelHourPicker mPickerHour;
    private WheelMinutePicker mPickerMinute;

    protected TimeChangeListener mListener;

    protected int mStateDate, mStateHour, mStateMinute;

    private long mDayMills, mHourMills, mMinuteMills;

    public TimeChooser(Context context) {
        this(context, null);
    }

    public TimeChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        int padding = getResources().getDimensionPixelSize(com.aigestudio.wheelpicker.R.dimen.WheelPadding);
        int padding2x = padding * 2;


        LayoutParams llParams = new LayoutParams(-2, -2);
        mPickerDate = new WheelDatePicker(getContext());
        mPickerHour = new WheelHourPicker(getContext());
        mPickerMinute = new WheelMinutePicker(getContext());
        mPickerDate.setPadding(0, padding, padding2x, padding);
        mPickerHour.setPadding(0, padding, padding2x, padding);
        mPickerMinute.setPadding(0, padding, padding2x, padding);

        addView(mPickerDate, llParams);
        addView(mPickerHour, llParams);
        addView(mPickerMinute, llParams);

        initListener(mPickerDate, TYPE_DATE);
        initListener(mPickerHour, TYPE_HOUR);
        initListener(mPickerMinute, TYPE_MINUTE);
    }

    private void initListener(final WheelCrossPicker picker, final int type) {
        picker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                switch (type) {
                    case TYPE_DATE:
                        if (mPickerDate.isCurrentDay(index)) {
                            mPickerHour.setStartHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                        } else {
                            mPickerHour.setStartHour(0);
                        }
                        mDayMills = mPickerDate.getSelectMills(index);
                        break;
                    case TYPE_HOUR:
                        mHourMills = mPickerHour.getSelectMills(index);
                        if (mPickerDate.isCurrentDay(mDayMills) && mPickerHour.isCurrentHour(index)) {
                            mPickerMinute.setStartMinute(Calendar.getInstance().get(Calendar.MINUTE));
                        } else {
                            mPickerMinute.setStartMinute(0);
                        }
                        break;
                    case TYPE_MINUTE:
                        mMinuteMills = mPickerMinute.getSelectMills(index);
                        break;
                    default:
                        break;
                }
                if (null != mListener)
                    mListener.onChanged(new Date(mDayMills + mHourMills + mMinuteMills));
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                switch (type) {
                    case TYPE_DATE:
                        mStateDate = state;
                        break;
                    case TYPE_HOUR:
                        mStateHour = state;
                        break;
                    case TYPE_MINUTE:
                        mStateMinute = state;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentTime(int hour, int minute) {
        mPickerDate.setSelectDate(new Date());
        mPickerHour.setSelectHour(hour);
        mPickerMinute.setSelectMinute(minute);
    }

    public void setTimeChangeListener(TimeChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener listener) {
    }

    private void checkState(AbstractWheelPicker.OnWheelChangeListener listener) {
        if (mStateDate == AbstractWheelPicker.SCROLL_STATE_IDLE &&
                mStateHour == AbstractWheelPicker.SCROLL_STATE_IDLE
                && mStateMinute == AbstractWheelPicker.SCROLL_STATE_IDLE) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_IDLE);
        }
        if (mStateDate == AbstractWheelPicker.SCROLL_STATE_SCROLLING ||
                mStateHour == AbstractWheelPicker.SCROLL_STATE_SCROLLING ||
                mStateMinute == AbstractWheelPicker.SCROLL_STATE_SCROLLING) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_SCROLLING);
        }
        if (mStateDate + mStateHour + mStateMinute == 1) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_DRAGGING);
        }
    }

    @Override
    public void setItemIndex(int index) {

    }

    @Override
    public void setItemSpace(int space) {
        mPickerDate.setItemSpace(space);
        mPickerHour.setItemSpace(space);
        mPickerMinute.setItemSpace(space);
    }

    @Override
    public void setItemCount(int count) {

    }

    @Override
    public void setTextColor(int color) {
        mPickerDate.setTextColor(color);
        mPickerHour.setTextColor(color);
        mPickerMinute.setTextColor(color);
    }

    @Override
    public void setTextSize(int size) {
        mPickerDate.setTextSize(size);
        mPickerHour.setTextSize(size);
        mPickerMinute.setTextSize(size);
    }

    @Override
    public void clearCache() {
        mPickerDate.clearCache();
        mPickerHour.clearCache();
        mPickerMinute.clearCache();
    }

    @Override
    public void setCurrentTextColor(int color) {
        mPickerDate.setCurrentTextColor(color);
        mPickerHour.setCurrentTextColor(color);
        mPickerMinute.setCurrentTextColor(color);
    }

    @Override
    public void setWheelDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        mPickerDate.setWheelDecor(ignorePadding, decor);
        mPickerHour.setWheelDecor(ignorePadding, decor);
        mPickerMinute.setWheelDecor(ignorePadding, decor);
    }

    @Override
    public void setDigitType(int type) {
        mPickerHour.setDigitType(type);
        mPickerMinute.setDigitType(type);
    }

    public interface TimeChangeListener {
        void onChanged(Date date);
    }
}
