package com.example.simplecountdowntimer;

/**********************************************************************
*                                                                     *
*   From YouTube Video: https://www.youtube.com/watch?v=zmjfAcnosS0   *
*                                                                     *
***********************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TIME = "com.example.simplecountdowntimer.TIME";
    public static final String EXTRA_NAME = "com.example.simplecountdowntimer.NAME";

    private TextView countDownText;
    private TextView labelName;
    private TextView setRest;
    private Button countDownButton;
    private Button setReset;
    //private String timerLabel = "Timer 1";
   // private TextView timerLabel2;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0; //600,000 = 10 minutes
    private long resetTime;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelName = findViewById(R.id.labelName);
        setReset = findViewById(R.id.setReset);
        setReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer();
            }
        });
        countDownText = findViewById(R.id.countDownText);
        countDownButton = findViewById(R.id.countdown_button);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        // Sets the default name of the timer
        labelName.setText(("Timer 1"));

        // Updates variables with time and label set by user
        Intent intent =getIntent();
        timeLeftInMilliseconds = getIntent().getLongExtra(EXTRA_TIME, 0);
        resetTime = timeLeftInMilliseconds;
        labelName.setText(intent.getStringExtra(EXTRA_NAME));

        // Updates the Text box displaying time set by user
        updateTimer();
    }


    public void setTimer () {
        // If there is time left on the timer, RESET it
        if(timeLeftInMilliseconds > 0) {
            timeLeftInMilliseconds = resetTime;
            startStop();
            updateTimer();
        }
        // If no time left on the timer, go to SetTimer activity
        else {
            Intent intent = new Intent(this, SetTimer.class);
            startActivity(intent);
        }
    }


    public void startStop () {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        countDownButton.setText("PAUSE");
        setReset.setText("RESET");
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countDownButton.setText("START");
        timerRunning = false;
    }

    public void updateTimer() {
        int hours = (int) ((timeLeftInMilliseconds / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((timeLeftInMilliseconds / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60;
        String timeLeftText;

        timeLeftText = "" + hours;
        if(hours < 10) timeLeftText = "0" + hours;
        timeLeftText += ":";

        if(minutes < 10) timeLeftText += "0" + minutes;
        if(minutes >= 10) timeLeftText += minutes;
        timeLeftText += ":";

        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText((timeLeftText));
    }
}
