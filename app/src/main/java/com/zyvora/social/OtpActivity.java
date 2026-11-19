
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OtpActivity extends Activity {

    private int purple = Color.rgb(155, 105, 255);
    private int background = Color.rgb(9, 10, 18);

    private int dp(float value) {
        return (int) (value * getResources()
            .getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable roundedBackground(
        int color, int radius
    ) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
        return shape;
    }

    private TextView makeText(
        String value, int size, int color
    ) {
        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String email = getIntent().getStringExtra("email");

        if (email == null || email.trim().isEmpty()) {
            email = "your email address";
        }

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(dp(28), dp(35), dp(28), dp(30));
        layout.setBackgroundColor(background);

        TextView logo = makeText(
            "ZYVORA", 34, purple
        );
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);

        layout.addView(logo);

        TextView title = makeText(
            "Verify Your Email", 27, Color.WHITE
        );
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams titleParams =
            new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = dp(65);
        layout.addView(title, titleParams);

        TextView description = makeText(
            "Enter the 8-digit verification code sent to",
            14, Color.LTGRAY
        );
        description.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams descParams =
            new LinearLayout.LayoutParams(-1, -2);
        descParams.topMargin = dp(18);
        layout.addView(description, descParams);

        TextView emailText = makeText(
            email, 14, purple
        );
        emailText.setGravity(Gravity.CENTER);
        emailText.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams emailParams =
            new LinearLayout.LayoutParams(-1, -2);
        emailParams.topMargin = dp(8);
        layout.addView(emailText, emailParams);

        EditText otp = new EditText(this);
        otp.setHint("8-digit code");
        otp.setTextColor(Color.WHITE);
        otp.setHintTextColor(Color.GRAY);
        otp.setTextSize(23);
        otp.setGravity(Gravity.CENTER);
        otp.setSingleLine(true);
        otp.setInputType(
            InputType.TYPE_CLASS_NUMBER
        );
        otp.setFilters(new InputFilter[] {
            new InputFilter.LengthFilter(8)
        });
        otp.setLetterSpacing(0.2f);
        otp.setBackground(
            roundedBackground(
                Color.rgb(28, 28, 42), 12
            )
        );
        otp.setPadding(dp(12), 0, dp(12), 0);

        LinearLayout.LayoutParams otpParams =
            new LinearLayout.LayoutParams(-1, dp(62));
        otpParams.topMargin = dp(38);
        layout.addView(otp, otpParams);

        Button verify = new Button(this);
        verify.setText("VERIFY EMAIL");
        verify.setTextColor(Color.WHITE);
        verify.setTextSize(15);
        verify.setTypeface(null, Typeface.BOLD);
        verify.setAllCaps(false);
        verify.setPadding(0, 0, 0, 0);
        verify.setMinHeight(0);
        verify.setMinimumHeight(0);
        verify.setBackground(
            roundedBackground(purple, 12)
        );

        LinearLayout.LayoutParams verifyParams =
            new LinearLayout.LayoutParams(-1, dp(54));
        verifyParams.topMargin = dp(28);
        layout.addView(verify, verifyParams);

        verify.setOnClickListener(v -> {
            String code = otp.getText().toString().trim();

            if (code.length() != 8) {
                otp.setError(
                    "Enter all 8 digits"
                );
                otp.requestFocus();
                return;
            }

            Toast.makeText(
                this,
                "Supabase verification will be connected next!",
                Toast.LENGTH_LONG
            ).show();
        });

        TextView resend = makeText(
            "Didn't receive a code? Resend code",
            14, purple
        );
        resend.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams resendParams =
            new LinearLayout.LayoutParams(-1, dp(55));
        resendParams.topMargin = dp(18);
        layout.addView(resend, resendParams);

        resend.setOnClickListener(v ->
            Toast.makeText(
                this,
                "Code resend will be connected next!",
                Toast.LENGTH_SHORT
            ).show()
        );

        setContentView(layout);
    }
}

