package com.example.simplecountdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SetTimer extends AppCompatActivity {
    public static final String EXTRA_TIME = "com.example.simplecountdowntimer.TIME";
    public static final String EXTRA_NAME = "com.example.simplecountdowntimer.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Tag", "SetTimer called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Log.i("Tag", "Got intent");
        String message = intent.getStringExtra(MainActivity.EXTRA_TIME);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Tag", "Finished");
    }

    public void loadTime (View view) {
        Log.i("Tag", "loadTime called");

        TextView name1 = findViewById(R.id.editText);
        TextView minutes1 = findViewById(R.id.editText2);
        TextView seconds1 = findViewById(R.id.editText3);
        String minutes2 = minutes1.getText().toString();
        String seconds2 = seconds1.getText().toString();
        String name2 = name1.getText().toString();

        long minutesInMilliseconds = 60000 * Integer.parseInt(minutes2);
        long secondsInMilliseconds = 1000 * Integer.parseInt(seconds2);
        long totalTimeInMilliseconds = minutesInMilliseconds + secondsInMilliseconds;
        Log.i("Tag", (totalTimeInMilliseconds / (1000 * 60 * 60)) % 24 + " hours");
        Log.i("Tag", (totalTimeInMilliseconds / 60000) % 60 + " minutes");
        Log.i("Tag", ((totalTimeInMilliseconds / 1000) % 60) + " seconds");
        Log.i("Tag", totalTimeInMilliseconds + " milliseconds");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TIME, totalTimeInMilliseconds);
        intent.putExtra(EXTRA_NAME, name2);

        startActivity(intent);
    }
}
