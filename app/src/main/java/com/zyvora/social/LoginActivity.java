
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ScrollView;

public class LoginActivity extends Activity {

    int bg = Color.rgb(9, 10, 18);
    int purple = Color.rgb(155, 105, 255);
    int white = Color.WHITE;

    int dp(float value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    GradientDrawable roundedBackground(
        int color, int radius
    ) {
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

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(dp(28), dp(24), dp(28), dp(24));

        scroll.addView(layout);

        TextView logo = new TextView(this);
        logo.setText("ZYVORA");
        logo.setTextSize(34);
        logo.setTextColor(purple);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);

        layout.addView(logo);

        TextView title = new TextView(this);
        title.setText("Welcome Back");
        title.setTextSize(25);
        title.setTextColor(white);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams tp =
            new LinearLayout.LayoutParams(-1, -2);
        tp.topMargin = dp(55);
        layout.addView(title, tp);

        TextView subtitle = new TextView(this);
        subtitle.setText("Log in to continue to ZYVORA");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams stp =
            new LinearLayout.LayoutParams(-1, -2);
        stp.topMargin = dp(10);
        layout.addView(subtitle, stp);

        EditText username = new EditText(this);
        username.setHint("Email or username");
        username.setSingleLine(true);
        username.setTextColor(white);
        username.setHintTextColor(Color.GRAY);
        username.setPadding(dp(16), 0, dp(16), 0);
        username.setBackground(
            roundedBackground(Color.rgb(35, 32, 46), 9)
        );

        LinearLayout.LayoutParams up =
            new LinearLayout.LayoutParams(-1, dp(56));
        up.topMargin = dp(42);
        layout.addView(username, up);

        EditText password = new EditText(this);
        password.setHint("Password");
        password.setSingleLine(true);
        password.setInputType(
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );
        password.setTextColor(white);
        password.setHintTextColor(Color.GRAY);
        password.setPadding(dp(16), 0, dp(16), 0);
        password.setBackground(
            roundedBackground(Color.rgb(35, 32, 46), 9)
        );

        LinearLayout.LayoutParams pp =
            new LinearLayout.LayoutParams(-1, dp(56));
        pp.topMargin = dp(16);
        layout.addView(password, pp);

        TextView forgot = new TextView(this);
        forgot.setText("Forgot password?");
        forgot.setTextSize(13);
        forgot.setTextColor(purple);
        forgot.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams fp =
            new LinearLayout.LayoutParams(-1, -2);
        fp.topMargin = dp(14);
        layout.addView(forgot, fp);

        Button login = new Button(this);
        login.setText("LOG IN");
        login.setTextSize(14);
        login.setTextColor(white);
        login.setAllCaps(false);
        login.setPadding(0, 0, 0, 0);
        login.setMinHeight(0);
        login.setMinimumHeight(0);
        login.setBackground(
            roundedBackground(purple, 9)
        );

        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(-1, dp(54));
        lp.topMargin = dp(32);
        layout.addView(login, lp);

        TextView signup = new TextView(this);
        signup.setText("Don't have an account? Create account");
        signup.setTextSize(13);
        signup.setTextColor(white);
        signup.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams cp =
            new LinearLayout.LayoutParams(-1, -2);
        cp.topMargin = dp(28);
        layout.addView(signup, cp);

        setContentView(scroll);
    }
}

