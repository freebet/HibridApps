package com.greatestfastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        /**
         ImageView imageView = findViewById(R.id.imageView);
         final Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.mytransition2);
         imageView.startAnimation(animation);

         imageView = findViewById(R.id.imageView1);
         final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.mytransition1);
         imageView.startAnimation(animation_1);
         */

        Thread tr = new Thread() {
            public void run() {
                try {
                    progress();
                    sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                    finish();
                }
            }
        };
        tr.start();
    }


    private void progress() {
        ProgressBar progressbar = findViewById(R.id.progressBar);
        for (int progress = 0; progress < 100; progress += 1) {
            try {
                Thread.sleep(10);
                progressbar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}