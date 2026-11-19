
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
import android.content.Intent;

public class RegisterActivity extends Activity {

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
        layout.setPadding(28, 40, 28, 30);

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
        titleParams.topMargin = 35;
        layout.addView(title, titleParams);

        TextView subtitle = makeText(
            "Join the ZYVORA community",
            14, Color.LTGRAY
        );
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams subParams =
            new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = 10;
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
        EditText password = makeInput("Password", passwordType);
        EditText confirm = makeInput(
            "Confirm password", passwordType
        );

        EditText[] fields = {
            name, username, email, password, confirm
        };

        for (EditText field : fields) {
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, 54);
            params.topMargin = 18;
            layout.addView(field, params);
        }

        Button create = new Button(this);
        create.setText("CREATE ACCOUNT");
        create.setTextColor(Color.WHITE);
        create.setTextSize(15);
        create.setAllCaps(false);
        create.setBackground(roundedBackground(purple, 12));

        LinearLayout.LayoutParams createParams =
            new LinearLayout.LayoutParams(-1, 54);
        createParams.topMargin = 30;
        layout.addView(create, createParams);

        TextView login = makeText(
            "Already have an account? Log in",
            14, Color.WHITE
        );
        login.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams loginParams =
            new LinearLayout.LayoutParams(-1, 55);
        loginParams.topMargin = 15;
        layout.addView(login, loginParams);

        login.setOnClickListener(v -> finish());

        setContentView(scroll);
    }
}

