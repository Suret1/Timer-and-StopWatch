package com.suret.stopwatchandtimer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.suret.stopwatchandtimer.ui.stopwatch.StopWatchFragment;
import com.suret.stopwatchandtimer.ui.timer.TimerFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar animatedBottomBar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        animatedBottomBar = findViewById(R.id.animated_bottom_bar);


        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.navigation_timer, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimerFragment()).commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.navigation_timer:
                        fragment = new TimerFragment();
                        break;
                    case R.id.navigation_stopwatch:
                        fragment = new StopWatchFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });


    }

}