
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private final int purple = Color.rgb(155, 105, 255);
    private final int background = Color.rgb(9, 10, 18);

    private int dp(float value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable roundedBackground(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
        return shape;
    }

    private TextView makeText(String value, int size, int color) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);
        return text;
    }

    private EditText makeInput(String hint, int type) {
        EditText input = new EditText(this);
        input.setHint(hint);
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.GRAY);
        input.setTextSize(15);
        input.setSingleLine(true);
        input.setInputType(type);
        input.setPadding(dp(16), 0, dp(16), 0);
        input.setBackground(
            roundedBackground(Color.rgb(28, 28, 42), 10)
        );
        return input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(28), dp(35), dp(28), dp(30));
        layout.setBackgroundColor(background);

        TextView logo = makeText("ZYVORA", 34, purple);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        layout.addView(logo);

        TextView title = makeText("Create Account", 27, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams titleParams =
            new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = dp(35);
        layout.addView(title, titleParams);

        EditText fullName = makeInput(
            "Full name", InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );

        EditText username = makeInput(
            "Username", InputType.TYPE_CLASS_TEXT
        );

        EditText email = makeInput(
            "Email address", InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        EditText password = makeInput(
            "Password", InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        EditText confirmPassword = makeInput(
            "Confirm password", InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        addField(layout, fullName);
        addField(layout, username);
        addField(layout, email);
        addField(layout, password);
        addField(layout, confirmPassword);

        Button signup = new Button(this);
        signup.setText("CONTINUE");
        signup.setTextColor(Color.WHITE);
        signup.setTextSize(15);
        signup.setTypeface(null, Typeface.BOLD);
        signup.setAllCaps(false);
        signup.setBackground(roundedBackground(purple, 12));

        LinearLayout.LayoutParams buttonParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        buttonParams.topMargin = dp(28);
        layout.addView(signup, buttonParams);

        signup.setOnClickListener(v -> {
            String name = fullName.getText().toString().trim();
            String user = username.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString();
            String confirm = confirmPassword.getText().toString();

            if (name.isEmpty()) {
                fullName.setError("Enter your full name");
                return;
            }

            if (!user.matches("[a-zA-Z0-9_]{3,20}")) {
                username.setError("Use 3-20 letters, numbers or underscores");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.setError("Enter a valid email address");
                return;
            }

            if (pass.length() < 8) {
                password.setError("Password must be at least 8 characters");
                return;
            }

            if (!pass.equals(confirm)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }

            Intent intent = new Intent(
                RegisterActivity.this, OtpActivity.class
            );
            intent.putExtra("email", mail);
            intent.putExtra("full_name", name);
            intent.putExtra("username", user);
            intent.putExtra("password", pass);
            startActivity(intent);
        });

        setContentView(layout);
    }

    private void addField(LinearLayout layout, EditText field) {
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, dp(54));
        params.topMargin = dp(16);
        layout.addView(field, params);
    }
}

