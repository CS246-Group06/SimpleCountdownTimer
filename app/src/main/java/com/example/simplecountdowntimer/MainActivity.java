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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.simplecountdowntimer.MESSAGE";
    private TextView countDownText;
    private Button countDownButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 600000; //600,000 = 10 minutes
    private boolean timerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownText = findViewById(R.id.countdown_text);
        countDownButton = findViewById(R.id.countdown_button);

        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        updateTimer();
    }

    public void setTimer (View view) {
        Intent intent = new Intent(this, SetTimer.class);
        startActivity(intent);
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


        timeLeftText = "" + minutes;
        if(minutes < 10) timeLeftText = "0" + minutes;
        timeLeftText += ":";
        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;



        countDownText.setText((timeLeftText));
    }
}
