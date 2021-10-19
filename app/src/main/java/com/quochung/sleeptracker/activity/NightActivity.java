package com.quochung.sleeptracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.quochung.sleeptracker.R;

public class NightActivity extends AppCompatActivity {

    private ConstraintLayout nightrootlayout;
    private SwipeListener swipeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightactivity);
        nightrootlayout = findViewById(R.id.nightrootlayout);
        new Handler().postDelayed(() -> {
            swipeListener = new SwipeListener(nightrootlayout);
        }, 500);
    }

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;


        SwipeListener(View view) {
            double threhold = 200;
            double velocity_threhold = 200;


            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();
                    try {
                        //if (Math.abs(xDiff) > Math.abs(yDiff)) {
                        //When x greater than y
                        //Check condition
                        if (Math.abs(yDiff) > threhold && Math.abs(velocityY) > velocity_threhold)
                        {
                            if (yDiff > 0) {
                                Log.d("DUMADUMADUAM" , "swipe down");
                            } else {
                                Log.d("DUMADUMADUAM" , "swipe up");

                                Intent intent = new Intent(NightActivity.this, StatActivity.class);
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