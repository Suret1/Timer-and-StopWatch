package com.suret.stopwatchandtimer.ui.stopwatch;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suret.stopwatchandtimer.R;
import com.suret.stopwatchandtimer.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

import timerx.Stopwatch;
import timerx.StopwatchBuilder;

public class StopWatchFragment extends Fragment {
    private Stopwatch stopwatch;
    private TextView stopwatchTV;
    private Button start_btn, reset_timer_btn;
    private boolean isPause;
    private boolean mTimeRunning;
    private LinearLayout linearLayout;
    private int checkAnimate = 0;
    private int resultCount;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> result = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_resultTimes);
        stopwatchTV = view.findViewById(R.id.stopwatch_tv);
        start_btn = view.findViewById(R.id.start_stopwatch);
        reset_timer_btn = view.findViewById(R.id.reset_timer_btn);
        linearLayout = view.findViewById(R.id.linearLayout);
        stopwatch = new StopwatchBuilder()
                .startFormat("MM:SS:LLL")
                .onTick(time -> stopwatchTV.setText(time))
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        start_btn.setOnClickListener(v -> {
            if (mTimeRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
        reset_timer_btn.setOnClickListener(v -> {
            if (mTimeRunning) {
                result.add(stopwatchTV.getText().toString());
                Collections.sort(result, Collections.reverseOrder());
                resultCount = result.size();
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), result);
                recyclerView.setAdapter(recyclerViewAdapter);
                resultCount++;
                startAnimate();
            } else {
                resetTimer();
            }
        });

    }

    private void startAnimate() {
        if (checkAnimate == 0) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fragment_open_enter);
            linearLayout.startAnimation(animation);
            linearLayout.setGravity(Gravity.LEFT);
        }
        checkAnimate = 1;
    }

    private void startTimer() {
        stopwatch.start();
        mTimeRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        stopwatch.stop();
        isPause = false;
        mTimeRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        reset_timer_btn.setVisibility(View.INVISIBLE);
        checkAnimate = 0;
        resultCount = 1;
        stopwatch.reset();
        stopwatchTV.setText(stopwatch.getFormattedStartTime());
        mTimeRunning = false;
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fragment_open_enter);
        linearLayout.startAnimation(animation);
        linearLayout.setGravity(Gravity.CENTER);
        result.clear();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void updateWatchInterface() {
        if (mTimeRunning) {
            start_btn.setText("Pause");
            start_btn.setBackgroundResource(R.drawable.button_bg);
            start_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
            reset_timer_btn.setVisibility(View.VISIBLE);
            reset_timer_btn.setText("Add");
            reset_timer_btn.setBackgroundResource(R.drawable.button_bg);
            reset_timer_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag, 0, 0, 0);

        } else {
            start_btn.setText("Start");
            start_btn.setBackgroundResource(R.drawable.button_bg);
            start_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
            reset_timer_btn.setVisibility(View.VISIBLE);
            reset_timer_btn.setText("Reset");
            reset_timer_btn.setBackgroundResource(R.drawable.button_bg);
            reset_timer_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop, 0, 0, 0);

        }

    }
}