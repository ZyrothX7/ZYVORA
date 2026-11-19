
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

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
        button.setBackground(roundedBackground(purple, 12));
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
        scroll.setVerticalScrollBarEnabled(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(28), dp(40), dp(28), dp(30));

        scroll.addView(layout);

        TextView logo = makeText("ZYVORA", 34, purple);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        layout.addView(logo);

        TextView title = makeText(
            "Create Account", 29, Color.WHITE
        );
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams titleParams =
            new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = dp(35);
        layout.addView(title, titleParams);

        TextView subtitle = makeText(
            "Join the ZYVORA community",
            14, Color.LTGRAY
        );
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams subParams =
            new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = dp(10);
        layout.addView(subtitle, subParams);

        int textType = InputType.TYPE_CLASS_TEXT;
        int passwordType = textType |
            InputType.TYPE_TEXT_VARIATION_PASSWORD;

        EditText name = makeInput("Full name", textType);
        EditText username = makeInput("Username", textType);

        EditText email = makeInput(
            "Email address",
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        EditText password = makeInput(
            "Password", passwordType
        );

        EditText confirm = makeInput(
            "Confirm password", passwordType
        );

        EditText[] fields = {
            name, username, email, password, confirm
        };

        for (EditText field : fields) {
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, dp(54));
            params.topMargin = dp(18);
            layout.addView(field, params);
        }

        Button create = new Button(this);
        create.setText("CREATE ACCOUNT");
        styleButton(create);

        LinearLayout.LayoutParams createParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        createParams.topMargin = dp(30);
        layout.addView(create, createParams);

        TextView login = makeText(
            "Already have an account? Log in",
            14, Color.WHITE
        );
        login.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams loginParams =
            new LinearLayout.LayoutParams(-1, dp(55));
        loginParams.topMargin = dp(15);
        layout.addView(login, loginParams);

        create.setOnClickListener(v -> {

            Toast.makeText(
                RegisterActivity.this,
                "BUTTON CLICKED!",
                Toast.LENGTH_SHORT
            ).show();

            if (name.getText().toString().trim().isEmpty()) {
                showError(name, "Enter your full name");
                return;
            }

            String userName =
                username.getText().toString().trim();

            if (userName.isEmpty()) {
                showError(username, "Choose a username");
                return;
            }

            if (!userName.matches("[a-zA-Z0-9_]{3,20}")) {
                showError(username,
                    "Use 3-20 letters, numbers or underscores");
                return;
            }

            String emailAddress =
                email.getText().toString().trim();

            if (emailAddress.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                showError(email, "Enter a valid email address");
                return;
            }

            String pass = password.getText().toString();
            String confirmPass = confirm.getText().toString();

            if (pass.length() < 8) {
                showError(password,
                    "Password must contain at least 8 characters");
                return;
            }

            if (!pass.equals(confirmPass)) {
                showError(confirm, "Passwords do not match");
                return;
            }

            Toast.makeText(
                RegisterActivity.this,
                "All checks passed! Registration is ready for the next step.",
                Toast.LENGTH_LONG
            ).show();
        });

        login.setOnClickListener(v -> finish());

        setContentView(scroll);
    }
}

