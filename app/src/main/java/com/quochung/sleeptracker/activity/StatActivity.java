package com.quochung.sleeptracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.progresviews.ProgressWheel;
import com.quochung.sleeptracker.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatActivity extends AppCompatActivity {
    SharedPreferences tracking;
    SharedPreferences.Editor ed;
    ProgressWheel progressWheel;
    TextView datestat, wenttosleep, wakeup;
    Button backtohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        initUI();
        statset();

        backtohome = findViewById(R.id.backtohome);
        backtohome.setOnClickListener(view -> {
            Intent intent = new Intent(StatActivity.this, DayActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideup, R.anim.slidedown);
        });










    }

    private void statset() {
        SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);
        ed = tracking.edit();
        Date curdate = new Date();
        datestat.setText(DateFormat.getDateInstance().format(curdate));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        SimpleDateFormat displaytime = new SimpleDateFormat("HH:mm:ss");
        try {
            Date storedate = formatter.parse(tracking.getString("counting", formatter.format(curdate)));
            long different = curdate.getTime() - storedate.getTime();
            long hour = different/3600000;
            hour = hour/1000;
            //Progress max is 360
            int progress = Math.round(hour*45);

            //Percent max is 100
            String percent = String.valueOf(Math.round(progress/3.6));


            progressWheel.setStepCountText(percent + "%");
            progressWheel.setPercentage(progress);


            wenttosleep.setText(displaytime.format(storedate));
            wakeup.setText(displaytime.format(curdate));

            ed.remove("counting");
            ed.apply();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }




    private void initUI() {
        progressWheel = findViewById(R.id.wheelprogress);
        datestat = findViewById(R.id.datestat);
        wenttosleep = findViewById(R.id.wenttosleep);
        wakeup = findViewById(R.id.wakeup);




    }




}