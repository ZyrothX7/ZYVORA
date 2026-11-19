
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

    private final int purple = Color.rgb(155, 105, 255);
    private final int background = Color.rgb(9, 10, 18);
    private final int white = Color.WHITE;
    private final int secondary = Color.rgb(175, 175, 190);

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density);
    }

    private GradientDrawable roundedBackground(
            int color, int radius) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));
        return drawable;
    }

    private TextView createText(
            String value, int size, int color) {

        TextView text = new TextView(this);
        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(background);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);

        // PROFILE BANNER
        LinearLayout banner = new LinearLayout(this);
        banner.setOrientation(LinearLayout.VERTICAL);
        banner.setPadding(dp(20), dp(8), dp(20), dp(16));
        banner.setGravity(Gravity.BOTTOM);
        banner.setBackground(roundedBackground(
                Color.rgb(78, 53, 125), 0));

        LinearLayout.LayoutParams bannerParams =
                new LinearLayout.LayoutParams(-1, dp(175));

        // TOP RIGHT EDIT ICON
        LinearLayout editRow = new LinearLayout(this);
        editRow.setOrientation(LinearLayout.HORIZONTAL);
        editRow.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        TextView edit = createText("⚒︎", 30, white);
        edit.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams editParams =
                new LinearLayout.LayoutParams(dp(42), dp(42));

        editRow.addView(edit, editParams);

        LinearLayout.LayoutParams editRowParams =
                new LinearLayout.LayoutParams(-1, dp(42));
        editRowParams.bottomMargin = dp(12);

        banner.addView(editRow, editRowParams);

        edit.setOnClickListener(v ->
                Toast.makeText(
                        ProfileActivity.this,
                        "Edit Profile coming soon!",
                        Toast.LENGTH_SHORT
                ).show()
        );

        // DISPLAY NAME
        TextView displayName =
                createText("Your Display Name", 25, white);
        displayName.setTypeface(null, Typeface.BOLD);

        banner.addView(displayName);

        // USERNAME
        TextView username =
                createText("@yourusername", 14,
                        Color.rgb(225, 210, 255));

        LinearLayout.LayoutParams usernameParams =
                new LinearLayout.LayoutParams(-1, -2);
        usernameParams.topMargin = dp(4);

        banner.addView(username, usernameParams);

        main.addView(banner, bannerParams);

        // PROFILE INFORMATION
        LinearLayout profileInfo = new LinearLayout(this);
        profileInfo.setOrientation(LinearLayout.VERTICAL);
        profileInfo.setPadding(
                dp(20), dp(18), dp(20), dp(20));

        // PROFILE PICTURE
        ImageView profilePicture = new ImageView(this);
        profilePicture.setImageResource(
                android.R.drawable.ic_menu_myplaces);
        profilePicture.setScaleType(
                ImageView.ScaleType.CENTER_INSIDE);
        profilePicture.setPadding(dp(16), dp(16), dp(16), dp(16));
        profilePicture.setBackground(
                roundedBackground(
                        Color.rgb(55, 43, 78), 100));

        LinearLayout.LayoutParams pictureParams =
                new LinearLayout.LayoutParams(
                        dp(82), dp(82));

        profileInfo.addView(
                profilePicture, pictureParams);

        // BIO
        TextView bio = createText(
                "Add your bio here...",
                15, secondary);

        LinearLayout.LayoutParams bioParams =
                new LinearLayout.LayoutParams(-1, -2);
        bioParams.topMargin = dp(18);

        profileInfo.addView(bio, bioParams);

        main.addView(profileInfo);

        scrollView.addView(main);
        setContentView(scrollView);
    }
}

