package com.marcosalive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private FrameLayout frameLayout;

    private XWalkView mXWalkView;

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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.marcosalive.R.layout.activity_main);

        BottomNavigationView navigation = findViewById(com.marcosalive.R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        frameLayout = findViewById(com.marcosalive.R.id.frameLayout);
        progressbar = findViewById(com.marcosalive.R.id.progressBar);

        progressbar.setMax(100);

        mXWalkView = findViewById(com.marcosalive.R.id.xwalkview);

        mXWalkView.getSettings().setAllowContentAccess(true);
        mXWalkView.getSettings().setAllowFileAccess(true);
        mXWalkView.getSettings().setDomStorageEnabled(true);
        mXWalkView.getSettings().setAllowFileAccessFromFileURLs(true);
        mXWalkView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mXWalkView.getSettings().setJavaScriptEnabled(true);
        mXWalkView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mXWalkView.getSettings().setSaveFormData(true);
        mXWalkView.getSettings().getCacheMode();
        mXWalkView.getSettings().setSupportMultipleWindows(true);
        mXWalkView.getSettings().setDatabaseEnabled(true);
        mXWalkView.getSettings().setLoadsImagesAutomatically(true);
        mXWalkView.getSettings().setDomStorageEnabled(true);

        mXWalkView.loadUrl("https://marcosnunes.github.io/MarcosAlive/", null);

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);


        mXWalkView.setResourceClient(new XWalkResourceClient(mXWalkView) {
            public void onProgressChanged(XWalkView view, int progress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressbar.setProgress(progress);

                setTitle("Carregando...");

                if (progress == 100) {
                    frameLayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view, progress);
            }
        });

        progressbar.setProgress(0);

        if (!haveNetworkConnection()) {
            android.app.AlertDialog.Builder Checkbuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            Checkbuilder.setMessage("Por favor conecte-se à internet!");
            android.app.AlertDialog alert = Checkbuilder.create();
            alert.show();
        } else {
            mXWalkView.loadUrl("https://marcosnunes.github.io/MarcosAlive/", null);
        }
    }

    @Override
    public void onBackPressed() {
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, com.marcosalive.R.style.AppTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
        builder.setMessage("Deseja encerrar?")
                .setCancelable(false)
                .setPositiveButton("sim", (dialog, id) -> finish())
                .setNegativeButton("Não", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mXWalkView != null) {
            mXWalkView.pauseTimers();
            mXWalkView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mXWalkView != null) {
            mXWalkView.resumeTimers();
            mXWalkView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mXWalkView != null) {
            mXWalkView.onDestroy();
        }
    }

    private boolean haveNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null;

    }
}
