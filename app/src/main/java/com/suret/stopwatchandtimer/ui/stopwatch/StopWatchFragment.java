package com.suret.stopwatchandtimer.ui.stopwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.suret.stopwatchandtimer.R;

import timerx.Stopwatch;
import timerx.StopwatchBuilder;
import timerx.TimeTickListener;

public class StopWatchFragment extends Fragment {
    private TextView stopwatchTV;
    private Button start_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stopwatchTV = view.findViewById(R.id.stopwatch_tv);
        start_btn = view.findViewById(R.id.start_stopwatch);

        Stopwatch stopwatch = new StopwatchBuilder()
                .startFormat("MM:SS:LLL")
                .onTick(time -> stopwatchTV.setText(time))
                .build();


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.start();
            }
        });

    }
}