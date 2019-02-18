package com.marcosalive;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.marcosalive.R.layout.activity_splashscreen);


        if (!isNetworkAvailable()) {
            //Create an alertdialog
            AlertDialog.Builder Checkbuilder = new AlertDialog.Builder(SplashScreenActivity.this);
            Checkbuilder.setMessage("Por favor conecte-se à internet!");
            //Builder Retry Button

            Checkbuilder.setNegativeButton("Sair", (dialog, which) -> finish());

            AlertDialog alert = Checkbuilder.create();
            alert.show();

        } else {
            if (isNetworkAvailable()) {

                Thread tr = new Thread() {
                    public void run() {
                        try {
                            progress();
                            sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                };
                tr.start();

            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null;

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private void progress() {
        ProgressBar progressbar = findViewById(com.marcosalive.R.id.progressBar);
        for (int progress = 0; progress < 100; progress += 1) {
            try {
                Thread.sleep(10);
                progressbar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(this, getString(com.marcosalive.R.string.Toast), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}