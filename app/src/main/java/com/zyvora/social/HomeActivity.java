
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.View;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.animation.ValueAnimator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

public class HomeActivity extends Activity {

    private final int purple = Color.rgb(155, 105, 255);
    private final int bg = Color.rgb(7, 7, 13);
    private final int white = Color.WHITE;

    private FrameLayout screenContainer;
    private LiquidIndicator indicator;
    private LinearLayout navigation;

    private View[] pages = new View[4];
    private String[] names = {
        "FEED", "FRIENDS", "ADD", "PROFILE"
    };

    private String[] symbols = {
        "F", "♡", "+", "P"
    };

    private int selected = 0;
    private ValueAnimator liquidAnimator;
    private boolean moving = false;

    private int dp(float value) {
        return (int) (value *
            getResources().getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable rounded(
        int color, int radius
    ) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    private TextView text(
        String value, int size, int color
    ) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);

        screenContainer = new FrameLayout(this);

        LinearLayout.LayoutParams screenParams =
            new LinearLayout.LayoutParams(-1, 0, 1);

        root.addView(screenContainer, screenParams);

        createPages();

        FrameLayout navFrame = new FrameLayout(this);

        LinearLayout.LayoutParams navParams =
            new LinearLayout.LayoutParams(-1, dp(82));

        navParams.setMargins(dp(14), dp(5), dp(14), dp(9));
        root.addView(navFrame, navParams);

        navFrame.setBackground(
            rounded(Color.rgb(24, 19, 36), 23)
        );

        indicator = new LiquidIndicator();

        navFrame.addView(indicator,
            new FrameLayout.LayoutParams(-1, -1));

        navigation = new LinearLayout(this);
        navigation.setOrientation(LinearLayout.HORIZONTAL);

        navFrame.addView(navigation,
            new FrameLayout.LayoutParams(-1, -1));

        for (int i = 0; i < 4; i++) {
            final int index = i;

            LinearLayout tab = createTab(i);

            LinearLayout.LayoutParams tabParams =
                new LinearLayout.LayoutParams(0, -1, 1);

            navigation.addView(tab, tabParams);

            tab.setOnClickListener(v -> selectTab(index));
        }

        setContentView(root);

        navigation.post(() ->
            indicator.setPosition(0)
        );
    }

    private void createPages() {
        String[] titles = {
            "Discover your world",
            "Your friends, your moments",
            "Create something amazing",
            "Your personal space"
        };

        String[] descriptions = {
            "FOR YOU  •  FOLLOWING  •  SPECIAL",
            "Videos and moments from your friends",
            "Share photos, videos and stories",
            "Your posts, memories and profile"
        };

        String[] icons = {
            "▶", "♡", "+", "◇"
        };

        for (int i = 0; i < 4; i++) {
            LinearLayout page = new LinearLayout(this);
            page.setOrientation(LinearLayout.VERTICAL);
            page.setGravity(Gravity.CENTER);
            page.setPadding(dp(24), dp(24), dp(24), dp(24));

            TextView icon = text(icons[i], 54, purple);
            page.addView(icon);

            TextView title = text(titles[i], 22, white);
            title.setTypeface(Typeface.DEFAULT_BOLD);

            LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(-1, -2);

            titleParams.topMargin = dp(20);
            page.addView(title, titleParams);

            TextView description =
                text(descriptions[i], 12, Color.LTGRAY);

            LinearLayout.LayoutParams descParams =
                new LinearLayout.LayoutParams(-1, -2);

            descParams.topMargin = dp(12);
            page.addView(description, descParams);

            pages[i] = page;

            FrameLayout.LayoutParams pageParams =
                new FrameLayout.LayoutParams(-1, -1);

            page.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
            screenContainer.addView(page, pageParams);
        }
    }

    private LinearLayout createTab(int index) {
        LinearLayout tab = new LinearLayout(this);
        tab.setOrientation(LinearLayout.VERTICAL);
        tab.setGravity(Gravity.CENTER);

        HexIcon icon = new HexIcon(symbols[index]);

        LinearLayout.LayoutParams iconParams =
            new LinearLayout.LayoutParams(dp(34), dp(34));

        tab.addView(icon, iconParams);

        TextView label = text(names[index], 9, white);
        label.setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams labelParams =
            new LinearLayout.LayoutParams(-1, -2);

        labelParams.topMargin = dp(3);
        tab.addView(label, labelParams);

        return tab;
    }

    private void selectTab(int next) {
        if (next == selected || moving) return;

        moving = true;

        int previous = selected;
        int direction = next > previous ? 1 : -1;

        View oldPage = pages[previous];
        View newPage = pages[next];

        newPage.setVisibility(View.VISIBLE);
        newPage.setTranslationX(
            direction * screenContainer.getWidth()
        );

        newPage.animate()
            .translationX(0)
            .setDuration(380)
            .setInterpolator(new DecelerateInterpolator())
            .start();

        oldPage.animate()
            .translationX(
                -direction * screenContainer.getWidth()
            )
            .setDuration(380)
            .setInterpolator(new DecelerateInterpolator())
            .withEndAction(() -> {
                oldPage.setVisibility(View.GONE);
                oldPage.setTranslationX(0);
                moving = false;
            })
            .start();

        animateLiquid(previous, next);
        selected = next;
    }

    private void animateLiquid(int from, int to) {
        if (liquidAnimator != null) {
            liquidAnimator.cancel();
        }

        liquidAnimator = ValueAnimator.ofFloat(from, to);
        liquidAnimator.setDuration(480);
        liquidAnimator.setInterpolator(
            new DecelerateInterpolator()
        );

        liquidAnimator.addUpdateListener(animation -> {
            indicator.setPosition(
                (float) animation.getAnimatedValue()
            );
        });

        liquidAnimator.start();
    }

    private class LiquidIndicator extends View {

        Paint paint = new Paint(3);
        float position = 0;

        LiquidIndicator() {
            super(HomeActivity.this);
            paint.setStyle(Paint.Style.FILL);
        }

        void setPosition(float value) {
            position = value;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float segment = getWidth() / 4f;
            float center = segment * (position + 0.5f);

            float width = segment * 0.78f;
            float height = dp(55);

            float left = center - width / 2;
            float right = center + width / 2;
            float top = (getHeight() - height) / 2;
            float bottom = top + height;

            Path path = new Path();

            path.moveTo(left + dp(12), top);
            path.lineTo(right - dp(12), top);

            path.cubicTo(
                right, top,
                right, top + dp(8),
                right, top + dp(17)
            );

            path.lineTo(right, bottom - dp(17));

            path.cubicTo(
                right, bottom - dp(5),
                right - dp(5), bottom,
                right - dp(17), bottom
            );

            path.lineTo(left + dp(17), bottom);

            path.cubicTo(
                left, bottom,
                left, bottom - dp(8),
                left, bottom - dp(17)
            );

            path.lineTo(left, top + dp(17));

            path.cubicTo(
                left, top + dp(5),
                left + dp(5), top,
                left + dp(12), top
            );

            path.close();

            paint.setColor(Color.argb(85, 155, 105, 255));
            canvas.drawPath(path, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(1));
            paint.setColor(Color.argb(180, 190, 155, 255));
            canvas.drawPath(path, paint);

            paint.setStyle(Paint.Style.FILL);
        }
    }

    private class HexIcon extends View {

        Paint paint = new Paint(3);
        String symbol;

        HexIcon(String value) {
            super(HomeActivity.this);
            symbol = value;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float w = getWidth();
            float h = getHeight();

            Path hex = new Path();

            hex.moveTo(w * .28f, h * .08f);
            hex.lineTo(w * .72f, h * .08f);
            hex.lineTo(w * .96f, h * .5f);
            hex.lineTo(w * .72f, h * .92f);
            hex.lineTo(w * .28f, h * .92f);
            hex.lineTo(w * .04f, h * .5f);
            hex.close();

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(1.5f));
            paint.setColor(purple);
            canvas.drawPath(hex, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(white);
            paint.setTextSize(dp(16));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextAlign(Paint.Align.CENTER);

            Paint.FontMetrics fm = paint.getFontMetrics();

            float y = h / 2 - (fm.ascent + fm.descent) / 2;

            canvas.drawText(symbol, w / 2, y, paint);
        }
    }
}

