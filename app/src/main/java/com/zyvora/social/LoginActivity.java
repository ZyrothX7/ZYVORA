
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.text.InputType;
import android.util.Patterns;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    int purple = Color.rgb(155, 105, 255);
    int background = Color.rgb(9, 10, 18);

    int dp(float value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
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
        input.setPadding(dp(18), 0, dp(18), 0);
        input.setBackground(roundedBackground(
            Color.rgb(28, 28, 42), 12
        ));
        return input;
    }

    void styleButton(Button button) {
        button.setTextColor(Color.WHITE);
        button.setTextSize(15);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setPadding(0, 0, 0, 0);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setMinWidth(0);
        button.setMinimumWidth(0);
        button.setBackground(
            roundedBackground(purple, 12)
        );
    }

    void showError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(background);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(28), dp(55), dp(28), dp(30));

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
        titleParams.topMargin = dp(55);
        layout.addView(title, titleParams);

        TextView subtitle = makeText(
            "Log in to continue your journey",
            14, Color.LTGRAY
        );
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams subParams =
            new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = dp(10);
        layout.addView(subtitle, subParams);

        EditText email = makeInput(
            "Email or username",
            InputType.TYPE_CLASS_TEXT
        );

        LinearLayout.LayoutParams inputParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        inputParams.topMargin = dp(45);
        layout.addView(email, inputParams);

        EditText password = makeInput(
            "Password",
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        LinearLayout.LayoutParams passParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        passParams.topMargin = dp(18);
        layout.addView(password, passParams);

        TextView forgot = makeText(
            "Forgot password?", 14, purple
        );
        forgot.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams forgotParams =
            new LinearLayout.LayoutParams(-1, -2);
        forgotParams.topMargin = dp(18);
        layout.addView(forgot, forgotParams);

        Button login = new Button(this);
        login.setText("LOG IN");
        styleButton(login);

        LinearLayout.LayoutParams loginParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        loginParams.topMargin = dp(35);
        layout.addView(login, loginParams);

        TextView signup = makeText(
            "Don't have an account? Create account",
            14, Color.WHITE
        );
        signup.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams signupParams =
            new LinearLayout.LayoutParams(-1, dp(55));
        signupParams.topMargin = dp(20);
        layout.addView(signup, signupParams);

        login.setOnClickListener(v -> {

            String emailOrUsername =
                email.getText().toString().trim();

            String pass = password.getText().toString();

            if (emailOrUsername.isEmpty()) {
                showError(email, "Enter your email or username");
                return;
            }

            if (emailOrUsername.contains("@") &&
                !Patterns.EMAIL_ADDRESS.matcher(
                    emailOrUsername
                ).matches()) {
                showError(email, "Enter a valid email address");
                return;
            }

            if (pass.isEmpty()) {
                showError(password, "Enter your password");
                return;
            }

            Toast.makeText(
                LoginActivity.this,
                "Login form is ready for authentication!",
                Toast.LENGTH_LONG
            ).show();
        });

        signup.setOnClickListener(v -> {
            startActivity(new Intent(
                LoginActivity.this,
                RegisterActivity.class
            ));
        });

        forgot.setOnClickListener(v -> {
            Toast.makeText(
                LoginActivity.this,
                "Password recovery will be available soon!",
                Toast.LENGTH_SHORT
            ).show();
        });

        setContentView(scroll);
    }
}

