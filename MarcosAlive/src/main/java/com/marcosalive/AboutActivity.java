package com.marcosalive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;


public class AboutActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case com.marcosalive.R.id.navigation_home:
                Intent intent0 = new Intent(this, MainActivity.class);
                startActivity(intent0);
                finish();
                return true;
            case com.marcosalive.R.id.navigation_website:
                Intent intent1 = new Intent(this, WebsiteActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case com.marcosalive.R.id.navigation_notifications:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                finish();
                return true;
        }
        return false;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.marcosalive.R.layout.activity_about);


        BottomNavigationView navigation = findViewById(com.marcosalive.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        builder.setMessage("Deseja encerrar?")
                .setCancelable(false)
                .setPositiveButton("sim", (dialog, id) -> finish())
                .setNegativeButton("Não", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
