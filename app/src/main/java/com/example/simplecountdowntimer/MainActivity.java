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
    private TextView setRest;
    private TextView editlabel;
    private Button countDownButton;
    private Button setReset;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private long resetTime;
    private boolean timerRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Tag", "onCreate");
        editlabel = findViewById(R.id.labelName);
        setReset = findViewById(R.id.setReset);
        countDownText = findViewById(R.id.countDownText2);
        countDownButton = findViewById(R.id.countdown_button);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        // Updates variables with time and label set by user
        Intent intent =getIntent();
        timeLeftInMilliseconds = getIntent().getLongExtra(EXTRA_TIME, 0);
        resetTime = timeLeftInMilliseconds;
        editlabel.setText(intent.getStringExtra(EXTRA_NAME));

        // Updates the Text box displaying time set by user
        updateTimer();
        Log.i("Tag", "initial timer update done");
    }


    public void setTimer(View view) {
        Log.i("Tag", "setTimer called");
        // If there is time left on the timer, RESET it
        // and continue to run.
        if(timerRunning) {
            Log.i("Tag", "IF ran");
            startStop();
            timeLeftInMilliseconds = resetTime;
            updateTimer();
            setReset.setText("SET");
        }
        // If there is time left on the timer, but timer has been
        // paused, .
        else if(!timerRunning && (timeLeftInMilliseconds != resetTime)) {
            Log.i("Tag", "first ELSE IF ran");
            timeLeftInMilliseconds = resetTime;
            updateTimer();
            setReset.setText("SET");
        }
        // If no time left on the timer, go to SetTimer activity
        else if(!timerRunning && timeLeftInMilliseconds == resetTime){
            Log.i("Tag", "second ELSE IF ran");
            Intent intent = new Intent(this, SetTimer.class);
            startActivity(intent);
        }
        else {
            Log.i("Tag", "ELSE ran");
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
