
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.graphics.drawable.*;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.animation.ValueAnimator;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.widget.*;

public class SplashActivity extends Activity {

    private final int purple = Color.rgb(155, 105, 255);
    private final int red = Color.rgb(255, 45, 55);

    private ProgressBar progress;
    private TextView status;
    private ValueAnimator animator;
    private ConnectivityManager connectivity;
    private ConnectivityManager.NetworkCallback callback;

    private boolean loadingStarted = false;
    private boolean openedMain = false;
    private boolean screenActive = false;

    private int dp(float value) {
        return (int) (value *
            getResources().getDisplayMetrics().density + 0.5f);
    }

    private boolean hasInternet() {
        Network network = connectivity.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities caps =
            connectivity.getNetworkCapabilities(network);

        return caps != null &&
            caps.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) &&
            caps.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            );
    }

    private void setProgressColor(int color) {
        LayerDrawable layers = new LayerDrawable(
            new Drawable[] {
                new ColorDrawable(Color.WHITE),
                new ClipDrawable(
                    new ColorDrawable(color),
                    Gravity.LEFT,
                    ClipDrawable.HORIZONTAL
                )
            }
        );

        layers.setId(0, android.R.id.background);
        layers.setId(1, android.R.id.progress);
        progress.setProgressDrawable(layers);
    }

    private void stopAnimator() {
        if (animator != null) {
            ValueAnimator old = animator;
            animator = null;
            old.removeAllListeners();
            old.removeAllUpdateListeners();
            old.cancel();
        }
    }

    private void showOffline() {
        if (!screenActive || openedMain) return;

        loadingStarted = false;
        stopAnimator();

        setProgressColor(red);
        progress.setProgress(100);

        status.setText("NO INTERNET CONNECTION");
        status.setTextColor(red);
    }

    private void startLoading() {
        if (!screenActive || loadingStarted || openedMain) return;

        loadingStarted = true;

        setProgressColor(purple);
        progress.setProgress(0);

        status.setText("LOADING...");
        status.setTextColor(purple);

        animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(2200);

        animator.addUpdateListener(animation ->
            progress.setProgress(
                (int) animation.getAnimatedValue()
            )
        );

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator = null;

                if (!screenActive || openedMain) return;

                if (hasInternet()) {
                    openedMain = true;

                    startActivity(new Intent(
                        SplashActivity.this,
                        MainActivity.class
                    ));

                    finish();
                } else {
                    showOffline();
                }
            }
        });

        animator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);

        connectivity = (ConnectivityManager)
            getSystemService(CONNECTIVITY_SERVICE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.BLACK);
        layout.setPadding(dp(28), dp(24), dp(28), dp(24));

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.zyvora_splash);
        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LinearLayout.LayoutParams imageParams =
            new LinearLayout.LayoutParams(dp(300), dp(300));

        layout.addView(logo, imageParams);

        progress = new ProgressBar(
            this, null, android.R.attr.progressBarStyleHorizontal
        );

        progress.setMax(100);
        progress.setProgress(0);
        progress.setIndeterminate(false);
        setProgressColor(purple);

        LinearLayout.LayoutParams progressParams =
            new LinearLayout.LayoutParams(-1, dp(12));

        progressParams.topMargin = dp(45);
        layout.addView(progress, progressParams);

        status = new TextView(this);
        status.setText("CONNECTING...");
        status.setTextColor(purple);
        status.setTextSize(12);
        status.setGravity(Gravity.CENTER);
        status.setLetterSpacing(0.12f);

        LinearLayout.LayoutParams statusParams =
            new LinearLayout.LayoutParams(-1, -2);

        statusParams.topMargin = dp(18);
        layout.addView(status, statusParams);

        setContentView(layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        screenActive = true;

        NetworkRequest request =
            new NetworkRequest.Builder()
                .addCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                )
                .build();

        callback = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onAvailable(Network network) {
                runOnUiThread(() -> {
                    if (hasInternet()) {
                        startLoading();
                    } else {
                        showOffline();
                    }
                });
            }

            @Override
            public void onCapabilitiesChanged(
                Network network,
                NetworkCapabilities capabilities
            ) {
                runOnUiThread(() -> {
                    if (hasInternet()) {
                        startLoading();
                    } else {
                        showOffline();
                    }
                });
            }

            @Override
            public void onLost(Network network) {
                runOnUiThread(() -> {
                    if (!hasInternet()) showOffline();
                });
            }
        };

        connectivity.registerNetworkCallback(request, callback);

        if (hasInternet()) {
            startLoading();
        } else {
            showOffline();
        }
    }

    @Override
    protected void onStop() {
        screenActive = false;
        loadingStarted = false;

        stopAnimator();

        if (callback != null) {
            try {
                connectivity.unregisterNetworkCallback(callback);
            } catch (Exception ignored) {}

            callback = null;
        }

        super.onStop();
    }
}

