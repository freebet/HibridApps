package com.marcosalive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WebsiteActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks {

    private static final int PERMISSIONS = 123;
    private static final String TAG = "WebsiteActivity";

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
        setContentView(com.marcosalive.R.layout.activity_website);

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

        mXWalkView.loadUrl("https://marcosnunes.github.io/Portifolio/", null);

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
            android.app.AlertDialog.Builder Checkbuilder = new android.app.AlertDialog.Builder(WebsiteActivity.this);
            Checkbuilder.setMessage("Por favor conecte-se à internet!");
            android.app.AlertDialog alert = Checkbuilder.create();
            alert.show();
        } else {
            mXWalkView.loadUrl("https://marcosnunes.github.io/Portifolio/", null);
        }
    }

    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean hasWriteStoragePermissions() {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private boolean hasReadStoragePermissions() {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @AfterPermissionGranted(PERMISSIONS)
    public void permissionsTask() {
        if (hasLocationPermissions()) {
            // Have permission, do the thing!
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    PERMISSIONS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (hasWriteStoragePermissions()) {
            // Have permission, do the thing!
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_write_storage),
                    PERMISSIONS,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (hasReadStoragePermissions()) {
            // Have permission, do the thing!
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_write_storage),
                    PERMISSIONS,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        permissionsTask();
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
