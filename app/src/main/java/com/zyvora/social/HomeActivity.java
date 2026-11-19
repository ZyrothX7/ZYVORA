
package com.zyvora.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Canvas;
import android.graphics.RectF;
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
    private HexIcon[] icons = new HexIcon[4];
    private TextView[] labels = new TextView[4];

    private String[] names = {
        "FEED", "FRIENDS", "ADD", "PROFILE"
    };

    private int selected = 0;
    private ValueAnimator liquidAnimator;
    private boolean moving = false;

    private int dp(float value) {
        return (int) (value *
            getResources().getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable glassBackground() {
        GradientDrawable d = new GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            new int[] {
                Color.rgb(49, 37, 70),
                Color.rgb(23, 18, 34),
                Color.rgb(13, 12, 21)
            }
        );
        d.setCornerRadius(dp(25));
        d.setStroke(dp(1), Color.argb(100, 190, 155, 255));
        return d;
    }

    private TextView text(String value, int size, int color) {
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

        root.addView(screenContainer,
            new LinearLayout.LayoutParams(-1, 0, 1));

        createPages();

        FrameLayout navFrame = new FrameLayout(this);

        LinearLayout.LayoutParams navParams =
            new LinearLayout.LayoutParams(-1, dp(82));

        navParams.setMargins(dp(14), dp(5), dp(14), dp(9));
        root.addView(navFrame, navParams);

        navFrame.setBackground(glassBackground());
        navFrame.setElevation(dp(12));
        navFrame.setClipChildren(false);
        navFrame.setClipToPadding(false);

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

            navigation.addView(tab,
                new LinearLayout.LayoutParams(0, -1, 1));

            tab.setOnClickListener(v -> selectTab(index));
        }

        setContentView(root);

        navigation.post(() -> {
            indicator.setPosition(0);
            updateTabStyles();
        });
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

        String[] pageIcons = {"▶", "♡", "+", "◇"};

        for (int i = 0; i < 4; i++) {
            LinearLayout page = new LinearLayout(this);
            page.setOrientation(LinearLayout.VERTICAL);
            page.setGravity(Gravity.CENTER);
            page.setPadding(dp(24), dp(24), dp(24), dp(24));

            page.addView(text(pageIcons[i], 54, purple));

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

        icons[index] = new HexIcon(index);

        tab.addView(icons[index],
            new LinearLayout.LayoutParams(dp(35), dp(35)));

        labels[index] = text(names[index], 9, white);
        labels[index].setTypeface(Typeface.DEFAULT_BOLD);

        LinearLayout.LayoutParams labelParams =
            new LinearLayout.LayoutParams(-1, -2);
        labelParams.topMargin = dp(3);
        tab.addView(labels[index], labelParams);

        return tab;
    }

    private void updateTabStyles() {
        for (int i = 0; i < 4; i++) {
            boolean active = i == selected;
            icons[i].setActive(active);

            labels[i].setTextColor(
                active ? Color.rgb(210, 185, 255) : white);
        }
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
            direction * screenContainer.getWidth());

        newPage.animate()
            .translationX(0)
            .setDuration(420)
            .setInterpolator(new DecelerateInterpolator())
            .start();

        oldPage.animate()
            .translationX(-direction * screenContainer.getWidth())
            .setDuration(420)
            .setInterpolator(new DecelerateInterpolator())
            .withEndAction(() -> {
                oldPage.setVisibility(View.GONE);
                oldPage.setTranslationX(0);
                moving = false;
            })
            .start();

        animateLiquid(previous, next);

        selected = next;
        updateTabStyles();
    }

    private void animateLiquid(int from, int to) {
        if (liquidAnimator != null) liquidAnimator.cancel();

        liquidAnimator = ValueAnimator.ofFloat(0f, 1f);
        liquidAnimator.setDuration(420);
        liquidAnimator.setInterpolator(new DecelerateInterpolator());

        liquidAnimator.addUpdateListener(animation -> {
            float t = (float) animation.getAnimatedValue();
            float position = from + (to - from) * t;
            float stretch = 1f +
                (float) Math.sin(Math.PI * t) * 0.55f;

            indicator.setLiquid(position, stretch);
        });

        liquidAnimator.start();
    }

    private class LiquidIndicator extends View {
        Paint paint = new Paint(3);
        float position = 0;
        float stretch = 1f;

        LiquidIndicator() {
            super(HomeActivity.this);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        void setPosition(float value) {
            position = value;
            invalidate();
        }

        void setLiquid(float value, float amount) {
            position = value;
            stretch = amount;
            invalidate();
        }

        private Path makePath(float left, float top,
            float right, float bottom, float radius) {

            Path path = new Path();
            RectF rect = new RectF(left, top, right, bottom);
            path.addRoundRect(rect, radius, radius, Path.Direction.CW);
            return path;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float segment = getWidth() / 4f;
            float center = segment * (position + 0.5f);
            float width = segment * 0.78f * stretch;
            float height = dp(56);

            float left = center - width / 2;
            float right = center + width / 2;
            float top = (getHeight() - height) / 2;
            float bottom = top + height;
            float radius = dp(19);

            Path path = makePath(left, top, right, bottom, radius);

            paint.clearShadowLayer();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(7));
            paint.setColor(Color.argb(25, 170, 120, 255));
            canvas.drawPath(path, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setShader(new LinearGradient(
                left, top, left, bottom,
                new int[] {
                    Color.argb(115, 190, 145, 255),
                    Color.argb(75, 120, 75, 190),
                    Color.argb(105, 65, 40, 110)
                },
                null, Shader.TileMode.CLAMP));

            canvas.drawPath(path, paint);
            paint.setShader(null);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(1.2f));
            paint.setColor(Color.argb(210, 205, 175, 255));
            canvas.drawPath(path, paint);

            paint.setStrokeWidth(dp(1));
            paint.setColor(Color.argb(105, 255, 255, 255));

            Path reflection = new Path();
            reflection.moveTo(left + radius, top + dp(3));
            reflection.lineTo(right - radius, top + dp(3));
            canvas.drawPath(reflection, paint);

            paint.setStrokeWidth(dp(2));
            paint.setColor(Color.argb(85, 180, 120, 255));

            canvas.drawLine(left + radius, bottom - dp(3),
                right - radius, bottom - dp(3), paint);

            paint.setStyle(Paint.Style.FILL);
        }
    }

    private class HexIcon extends View {
        Paint paint = new Paint(3);
        int type;
        boolean active = false;

        HexIcon(int value) {
            super(HomeActivity.this);
            type = value;
        }

        void setActive(boolean value) {
            active = value;
            invalidate();
        }

        private void line(Canvas canvas, float x1, float y1,
            float x2, float y2) {
            canvas.drawLine(x1, y1, x2, y2, paint);
        }

        private void circle(Canvas canvas, float x, float y, float r) {
            canvas.drawCircle(x, y, r, paint);
        }

        private void drawSymbol(Canvas canvas, float w, float h) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(2));
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(active ? white : Color.rgb(190, 155, 255));

            float cx = w / 2f;
            float cy = h / 2f;

            if (type == 0) {
                // FEED: outlined play symbol.
                Path play = new Path();
                play.moveTo(cx - dp(5), cy - dp(8));
                play.lineTo(cx + dp(8), cy);
                play.lineTo(cx - dp(5), cy + dp(8));
                play.close();
                canvas.drawPath(play, paint);

            } else if (type == 1) {
                // FRIENDS: two connected user silhouettes.
                circle(canvas, cx - dp(5), cy - dp(4), dp(3));
                circle(canvas, cx + dp(5), cy - dp(4), dp(3));

                Path people = new Path();
                people.moveTo(cx - dp(11), cy + dp(8));
                people.quadTo(cx - dp(11), cy + dp(1),
                    cx - dp(5), cy + dp(1));
                people.quadTo(cx, cy + dp(1),
                    cx, cy + dp(8));

                people.moveTo(cx, cy + dp(8));
                people.quadTo(cx, cy + dp(1),
                    cx + dp(5), cy + dp(1));
                people.quadTo(cx + dp(11), cy + dp(1),
                    cx + dp(11), cy + dp(8));

                canvas.drawPath(people, paint);

                line(canvas, cx - dp(2), cy - dp(4),
                    cx + dp(2), cy - dp(4));

            } else if (type == 2) {
                // ADD: geometric plus.
                line(canvas, cx - dp(8), cy,
                    cx + dp(8), cy);
                line(canvas, cx, cy - dp(8),
                    cx, cy + dp(8));

            } else {
                // PROFILE: head and shoulders.
                circle(canvas, cx, cy - dp(5), dp(4));

                Path shoulders = new Path();
                shoulders.moveTo(cx - dp(10), cy + dp(9));
                shoulders.quadTo(cx - dp(9), cy,
                    cx, cy);
                shoulders.quadTo(cx + dp(9), cy,
                    cx + dp(10), cy + dp(9));

                canvas.drawPath(shoulders, paint);
            }
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

            if (active) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(dp(5));
                paint.setColor(Color.argb(35, 175, 115, 255));
                canvas.drawPath(hex, paint);
            }

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(active
                ? Color.argb(85, 155, 105, 255)
                : Color.argb(20, 255, 255, 255));

            canvas.drawPath(hex, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(active ? dp(2) : dp(1.3f));
            paint.setColor(active
                ? Color.rgb(210, 180, 255) : purple);

            canvas.drawPath(hex, paint);

            drawSymbol(canvas, w, h);
        }
    }
}

