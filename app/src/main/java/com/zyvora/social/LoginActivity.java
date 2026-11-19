
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private int purple = Color.rgb(155, 105, 255);
    private int background = Color.rgb(9, 10, 18);
    private int white = Color.WHITE;

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density);
    }

    private GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    private void showError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    private TextView createText(String value, int size, int color) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);
        text.setGravity(Gravity.CENTER);
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(dp(28), dp(30), dp(28), dp(30));
        layout.setBackgroundColor(background);

        TextView logo = createText("ZYVORA", 34, purple);
        logo.setTypeface(null, Typeface.BOLD);
        layout.addView(logo);

        TextView heading = createText("Welcome Back", 25, white);
        heading.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams headingParams =
                new LinearLayout.LayoutParams(-1, -2);
        headingParams.topMargin = dp(35);
        layout.addView(heading, headingParams);

        TextView subtitle =
                createText("Log in to continue your journey", 14,
                        Color.LTGRAY);

        LinearLayout.LayoutParams subtitleParams =
                new LinearLayout.LayoutParams(-1, -2);
        subtitleParams.topMargin = dp(8);
        layout.addView(subtitle, subtitleParams);

        EditText email = new EditText(this);
        email.setSingleLine(true);
        email.setHint("Email or Username");
        email.setTextColor(white);
        email.setHintTextColor(Color.GRAY);
        email.setPadding(dp(14), 0, dp(14), 0);
        email.setBackground(roundedBackground(
                Color.rgb(28, 29, 42), 9));

        LinearLayout.LayoutParams fieldParams =
                new LinearLayout.LayoutParams(-1, dp(54));
        fieldParams.topMargin = dp(45);
        layout.addView(email, fieldParams);

        EditText password = new EditText(this);
        password.setSingleLine(true);
        password.setHint("Password");
        password.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTextColor(white);
        password.setHintTextColor(Color.GRAY);
        password.setPadding(dp(14), 0, dp(14), 0);
        password.setBackground(roundedBackground(
                Color.rgb(28, 29, 42), 9));

        LinearLayout.LayoutParams passwordParams =
                new LinearLayout.LayoutParams(-1, dp(54));
        passwordParams.topMargin = dp(16);
        layout.addView(password, passwordParams);

        TextView forgot = createText(
                "Forgot Password?", 14, purple);
        forgot.setGravity(Gravity.RIGHT);

        LinearLayout.LayoutParams forgotParams =
                new LinearLayout.LayoutParams(-1, -2);
        forgotParams.topMargin = dp(14);
        layout.addView(forgot, forgotParams);

        forgot.setOnClickListener(v ->
                Toast.makeText(
                        LoginActivity.this,
                        "Password recovery will be available soon!",
                        Toast.LENGTH_SHORT
                ).show()
        );

        Button login = new Button(this);
        login.setText("LOG IN");
        login.setTextColor(white);
        login.setTextSize(15);
        login.setTypeface(null, Typeface.BOLD);
        login.setPadding(0, 0, 0, 0);
        login.setMinHeight(0);
        login.setMinimumHeight(0);
        login.setBackground(roundedBackground(purple, 9));

        LinearLayout.LayoutParams loginParams =
                new LinearLayout.LayoutParams(-1, dp(54));
        loginParams.topMargin = dp(30);
        layout.addView(login, loginParams);

        login.setOnClickListener(v -> {
            String emailOrUsername =
                    email.getText().toString().trim();

            String pass = password.getText().toString();

            if (emailOrUsername.isEmpty()) {
                showError(email,
                        "Enter your email or username");
                return;
            }

            if (emailOrUsername.contains("@") &&
                    !Patterns.EMAIL_ADDRESS.matcher(
                            emailOrUsername
                    ).matches()) {
                showError(email,
                        "Enter a valid email address");
                return;
            }

            if (pass.isEmpty()) {
                showError(password,
                        "Enter your password");
                return;
            }

            Toast.makeText(
                    LoginActivity.this,
                    "Login form is ready for authentication!",
                    Toast.LENGTH_LONG
            ).show();
        });

        TextView signup = createText(
                "Don't have an account? Create Account",
                14, purple);

        LinearLayout.LayoutParams signupParams =
                new LinearLayout.LayoutParams(-1, -2);
        signupParams.topMargin = dp(25);
        layout.addView(signup, signupParams);

        signup.setOnClickListener(v ->
                startActivity(new Intent(
                        LoginActivity.this,
                        RegisterActivity.class))
        );

        setContentView(layout);
    }
}

