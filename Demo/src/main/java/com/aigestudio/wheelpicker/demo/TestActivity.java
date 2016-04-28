package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelDayPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/26.
 */
public class TestActivity extends Activity {
    private WheelDayPicker mWdp;
    private WheelTimePicker mWtp;
    private WheelDatePicker mWdtp;
    private TimeChooser mTc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mWdp = (WheelDayPicker) findViewById(R.id.wdp_date);
        mWtp = (WheelTimePicker) findViewById(R.id.wtp_time);
        mWdtp = (WheelDatePicker) findViewById(R.id.wdp_d);
        mTc = (TimeChooser) findViewById(R.id.tc_timer);
        mTc.setTimeChangeListener(new TimeChooser.TimeChangeListener() {
            @Override
            public void onChanged(Date date) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
                String da = formatter.format(date);
//                Toast.makeText(TestActivity.this, da, Toast.LENGTH_LONG).show();
            }
        });
    }
}
