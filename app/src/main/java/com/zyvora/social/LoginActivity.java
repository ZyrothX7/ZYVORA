
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
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

    int purple = Color.rgb(155, 105, 255);
    int background = Color.rgb(9, 10, 18);

    GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(radius);
        return shape;
    }

    TextView makeText(String text, int size, int color) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);
        return view;
    }

    EditText makeInput(String hint, int type) {
        EditText input = new EditText(this);
        input.setHint(hint);
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.GRAY);
        input.setSingleLine(true);
        input.setInputType(type);
        input.setPadding(18, 0, 18, 0);
        input.setBackground(roundedBackground(
            Color.rgb(28, 28, 42), 12
        ));
        return input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(background);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(28, 55, 28, 30);

        scroll.addView(layout);

        TextView logo = makeText("ZYVORA", 34, purple);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        layout.addView(logo);

        TextView title = makeText("Welcome Back", 29, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams titleParams =
            new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = 55;
        layout.addView(title, titleParams);

        TextView subtitle = makeText(
            "Log in to continue your journey",
            14, Color.LTGRAY
        );
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subParams =
            new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = 10;
        layout.addView(subtitle, subParams);

        EditText email = makeInput(
            "Email or username",
            InputType.TYPE_CLASS_TEXT
        );
        LinearLayout.LayoutParams inputParams =
            new LinearLayout.LayoutParams(-1, 54);
        inputParams.topMargin = 45;
        layout.addView(email, inputParams);

        EditText password = makeInput(
            "Password",
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );
        LinearLayout.LayoutParams passParams =
            new LinearLayout.LayoutParams(-1, 54);
        passParams.topMargin = 18;
        layout.addView(password, passParams);

        TextView forgot = makeText(
            "Forgot password?", 14, purple
        );
        forgot.setGravity(Gravity.RIGHT);
        LinearLayout.LayoutParams forgotParams =
            new LinearLayout.LayoutParams(-1, -2);
        forgotParams.topMargin = 18;
        layout.addView(forgot, forgotParams);

        Button login = new Button(this);
        login.setText("LOG IN");
        login.setTextColor(Color.WHITE);
        login.setTextSize(15);
        login.setAllCaps(false);
        login.setBackground(roundedBackground(purple, 12));
        LinearLayout.LayoutParams loginParams =
            new LinearLayout.LayoutParams(-1, 54);
        loginParams.topMargin = 35;
        layout.addView(login, loginParams);

        TextView signup = makeText(
            "Don't have an account? Create account",
            14, Color.WHITE
        );
        signup.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams signupParams =
            new LinearLayout.LayoutParams(-1, 55);
        signupParams.topMargin = 20;
        layout.addView(signup, signupParams);

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(
                LoginActivity.this,
                RegisterActivity.class
            );
            startActivity(intent);
        });

        setContentView(scroll);
    }
}

