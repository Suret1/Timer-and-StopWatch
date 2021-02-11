package com.suret.stopwatchandtimer.ui.timer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shawnlin.numberpicker.NumberPicker;
import com.suret.stopwatchandtimer.R;

import java.util.Locale;

public class TimerFragment extends Fragment {

    private CountDownTimer countDownTimer;
    private TextView mTextViewCountDown;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private MediaPlayer spinEffect;
    private LinearLayout linearLayout;
    private Button start_btn, stop_btn;
    private View myView;
    private LinearLayout spinLayout;
    private int hour, minute, second;
    private boolean isPaused;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftMillis;
    private long mEndTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinEffect = MediaPlayer.create(getActivity(), R.raw.spin);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        hourPicker = view.findViewById(R.id.hours_picker);
        minutePicker = view.findViewById(R.id.minutes_picker);
        secondPicker = view.findViewById(R.id.seconds_picker);
        start_btn = view.findViewById(R.id.button);
        linearLayout = view.findViewById(R.id.linearLayout);
        stop_btn = view.findViewById(R.id.stop);
        spinLayout = view.findViewById(R.id.spinLayout);
        hourPicker.setOnValueChangedListener(hourValueChangeListener);

        minutePicker.setOnValueChangedListener(minuteValueChangeListener);

        secondPicker.setOnValueChangedListener(secondValueChangeListener);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.count_down_layout, null);

        mTextViewCountDown = myView.findViewById(R.id.text_view_countdown);
        linearLayout.addView(myView);
        myView.setVisibility(View.INVISIBLE);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }

            }
        });
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerRunning = false;
                isPaused = false;
                updateWatchInterface();
            }
        });
    }

    private void startTimer() {

        if (isPaused != true) {
            mStartTimeInMillis = (hour * 3600000) + (minute * 60000) + (second * 1000) + 1000;
            mTimeLeftMillis = mStartTimeInMillis;
        }
        mEndTime = System.currentTimeMillis() + mTimeLeftMillis;

        countDownTimer = new CountDownTimer(mTimeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                isPaused = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        start_btn.setText("Start");
        start_btn.setBackgroundResource(R.drawable.custom_start_button);
        start_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
        mTimerRunning = false;
        isPaused = true;
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            start_btn.setText("Pause");
            start_btn.setBackgroundResource(R.drawable.custom_start_button);
            start_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
            stop_btn.setVisibility(View.VISIBLE);
            myView.setVisibility(View.VISIBLE);
            spinLayout.setVisibility(View.GONE);
        } else {
            start_btn.setText("Start");
            start_btn.setBackgroundResource(R.drawable.custom_start_button);
            start_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
            stop_btn.setVisibility(View.INVISIBLE);
            myView.setVisibility(View.INVISIBLE);
            spinLayout.setVisibility(View.VISIBLE);
            countDownTimer.cancel();
        }
    }


    private NumberPicker.OnValueChangeListener hourValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            spinEffect.start();
            hour = newVal;
            buttonHideShowListener();
        }
    };
    private NumberPicker.OnValueChangeListener minuteValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            spinEffect.start();
            minute = newVal;
            buttonHideShowListener();
        }
    };
    private NumberPicker.OnValueChangeListener secondValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            spinEffect.start();
            second = newVal;
            buttonHideShowListener();
        }
    };

    private void buttonHideShowListener() {
        if (hour == 0 && minute == 0 && second == 0) {
            start_btn.setVisibility(View.INVISIBLE);
        } else {
            start_btn.setVisibility(View.VISIBLE);
        }
    }
}