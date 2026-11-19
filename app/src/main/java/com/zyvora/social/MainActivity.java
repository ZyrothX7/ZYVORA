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

public class MainActivity extends Activity {

    int bg = Color.rgb(9, 10, 18);
    int purple = Color.rgb(155, 105, 255);
    int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(35, 30, 35, 30);
        layout.setBackgroundColor(bg);

        TextView logo = new TextView(this);
        logo.setText("ZYVORA");
        logo.setTextSize(38);
        logo.setTextColor(purple);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);

        layout.addView(logo);

        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR WORLD. YOUR CONNECTION.");
        subtitle.setTextSize(11);
        subtitle.setTextColor(white);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setLetterSpacing(0.15f);

        LinearLayout.LayoutParams sp =
            new LinearLayout.LayoutParams(-1, -2);
        sp.topMargin = 15;
        layout.addView(subtitle, sp);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to ZYVORA");
        welcome.setTextSize(23);
        welcome.setTextColor(white);
        welcome.setTypeface(Typeface.DEFAULT_BOLD);
        welcome.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams wp =
            new LinearLayout.LayoutParams(-1, -2);
        wp.topMargin = 90;
        layout.addView(welcome, wp);

        TextView description = new TextView(this);
        description.setText(
            "Share your moments.\\nConnect with the world."
        );
        description.setTextSize(15);
        description.setTextColor(Color.LTGRAY);
        description.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams dp =
            new LinearLayout.LayoutParams(-1, -2);
        dp.topMargin = 15;
        layout.addView(description, dp);

        Button login = new Button(this);
        login.setText("LOG IN");
        login.setTextColor(white);
        login.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(purple)
        );

        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(-1, 55);
        lp.topMargin = 65;
        layout.addView(login, lp);

        Button signup = new Button(this);
        signup.setText("CREATE ACCOUNT");
        signup.setTextColor(white);
        signup.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(40, 35, 55)
            )
        );

        LinearLayout.LayoutParams cp =
            new LinearLayout.LayoutParams(-1, 55);
        cp.topMargin = 15;
        layout.addView(signup, cp);

        setContentView(layout);
    }
}
