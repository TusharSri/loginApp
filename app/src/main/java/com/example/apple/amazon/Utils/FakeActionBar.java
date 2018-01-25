package com.example.apple.amazon.Utils;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.apple.amazon.R;

public class FakeActionBar extends FrameLayout{
    private final SymmetricProgressBar progressBar;

    public FakeActionBar(final Context context) {
        this(context, null);
    }

    public FakeActionBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FakeActionBar(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        final View view = LayoutInflater.from(context).inflate(R.layout.activity_login, null);

        this.addView(view);

        this.progressBar = (SymmetricProgressBar)view.findViewById(R.id.fake_action_bar_progress_bar);
    }

    public void startAnimation() {
        this.progressBar.startAnimation();
    }

    public void stopAnimation() {
        this.progressBar.stopAnimation();
    }
}
