
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    int bg = Color.rgb(9, 10, 18);
    int purple = Color.rgb(155, 105, 255);
    int white = Color.WHITE;

    int dp(float value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(bg);
        scroll.setVerticalScrollBarEnabled(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(dp(28), dp(24), dp(28), dp(24));

        scroll.addView(layout, new ScrollView.LayoutParams(
            -1, -1
        ));

        // LOGO
        TextView logo = new TextView(this);
        logo.setText("ZYVORA");
        logo.setTextSize(38);
        logo.setTextColor(purple);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);

        layout.addView(logo);

        // SUBTITLE
        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR WORLD. YOUR CONNECTION.");
        subtitle.setTextSize(11);
        subtitle.setTextColor(white);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setLetterSpacing(0.15f);

        LinearLayout.LayoutParams sp =
            new LinearLayout.LayoutParams(-1, -2);
        sp.topMargin = dp(12);
        layout.addView(subtitle, sp);

        // WELCOME
        TextView welcome = new TextView(this);
        welcome.setText("Welcome to ZYVORA");
        welcome.setTextSize(23);
        welcome.setTextColor(white);
        welcome.setTypeface(Typeface.DEFAULT_BOLD);
        welcome.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams wp =
            new LinearLayout.LayoutParams(-1, -2);
        wp.topMargin = dp(58);
        layout.addView(welcome, wp);

        // DESCRIPTION
        TextView description = new TextView(this);
        description.setText(
            "Share your moments.\nConnect with the world."
        );
        description.setTextSize(15);
        description.setTextColor(Color.LTGRAY);
        description.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams dpParams =
            new LinearLayout.LayoutParams(-1, -2);
        dpParams.topMargin = dp(12);
        layout.addView(description, dpParams);

        // LOGIN BUTTON
        Button login = new Button(this);
        login.setText("LOG IN");
        login.setTextSize(14);
        login.setTextColor(white);
        login.setAllCaps(false);
        login.setGravity(Gravity.CENTER);
        login.setPadding(0, 0, 0, 0);
        login.setMinHeight(0);
        login.setMinimumHeight(0);
        login.setBackground(
            roundedBackground(purple, 9)
        );

        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(-1, dp(54));
        lp.topMargin = dp(42);
        layout.addView(login, lp);

        // CREATE ACCOUNT BUTTON
        Button signup = new Button(this);
        signup.setText("CREATE ACCOUNT");
        signup.setTextSize(14);
        signup.setTextColor(white);
        signup.setAllCaps(false);
        signup.setGravity(Gravity.CENTER);
        signup.setPadding(0, 0, 0, 0);
        signup.setMinHeight(0);
        signup.setMinimumHeight(0);
        signup.setBackground(
            roundedBackground(Color.rgb(40, 35, 55), 9)
        );

        LinearLayout.LayoutParams cp =
            new LinearLayout.LayoutParams(-1, dp(54));
        cp.topMargin = dp(14);
        layout.addView(signup, cp);

        setContentView(scroll);
    }
}
