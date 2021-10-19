package com.quochung.sleeptracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.quochung.sleeptracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayActivity extends AppCompatActivity {
    private ConstraintLayout rootlayout;
    private SwipeListener swipeListener;
    private TextView timeday;
    SharedPreferences tracking;
    SharedPreferences.Editor ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootlayout = findViewById(R.id.rootlayout);
        timeday = findViewById(R.id.timeday);
        Date currentdate = new Date();
        String stringDate = DateFormat.getDateInstance() .format(currentdate);
        timeday.setText(stringDate);
        swipeListener = new SwipeListener(rootlayout);

    }

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;


        SwipeListener(View view) {
            double threhold = 200;
            double velocity_threhold = 200;


            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    float yDiff = e2.getY() - e1.getY();
                    try {
                            if (Math.abs(yDiff) > threhold && Math.abs(velocityY) > velocity_threhold)
                            {
                                if (yDiff < 0) {
                                    //Swipe up, store date
                                    SharedPreferences tracking = getSharedPreferences("tracking", MODE_PRIVATE);
                                    ed = tracking.edit();
                                    Date storedate = new Date();
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");

                                    ed.putString("counting", formatter.format(storedate));
                                    ed.apply();



                                    Intent intent = new Intent(DayActivity.this, NightActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slideup, R.anim.slidedown);
                                }

                            }
                            return true;
                       // }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    return  true;
                }
            };


            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);

        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }
}