package com.exercise.app30day.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.exercise.app30day.R;

public class StepSeekBar extends LinearLayout {

    private Context context;

    private int numSteps;

    private int progress;

    private int progressTint;

    private int progressBackgroundTint;

    private float stepSpacing;

    private float stepRadius;

    public StepSeekBar(Context context) {
        super(context);
        initView(context, null);
    }

    public StepSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public StepSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StepSeekBar, 0, 0);

        try {
            numSteps = a.getInt(R.styleable.StepSeekBar_numSteps, 5);
            progress = a.getInt(R.styleable.StepSeekBar_progress, 0);
            progressTint = a.getColor(R.styleable.StepSeekBar_progressTint, Color.parseColor("#000000"));
            progressBackgroundTint = a.getColor(R.styleable.StepSeekBar_progressBackgroundTint, Color.parseColor("#F1F1F1"));
            stepSpacing = a.getDimension(R.styleable.StepSeekBar_stepSpacing, dpToPixel(3));
            stepRadius = a.getDimension(R.styleable.StepSeekBar_stepRadius, dpToPixel(20));
        }finally {
            a.recycle();
        }
        updateView();
        requestLayout();
    }

    private void updateView(){
        this.removeAllViews();
        for(int position = 0; position < numSteps; position++) {
            View view = getStepView(position);
            this.addView(view);
        }
    }

    @NonNull
    private View getStepView(int position) {
        View view = new View(context);
        view.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(position < progress ? progressTint : progressBackgroundTint);
        drawable.setCornerRadius(50);
        view.setBackground(drawable);
        if(position != numSteps - 1){
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.setMarginEnd((int) stepSpacing);
            view.setLayoutParams(params);
        }
        return view;
    }


    private int dpToPixel(int dp) {
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
        updateView();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        updateView();
    }

    public int getProgressTint() {
        return progressTint;
    }

    public void setProgressTint(int progressTint) {
        this.progressTint = progressTint;
        updateView();
    }

    public int getProgressBackgroundTint() {
        return progressBackgroundTint;
    }

    public void setProgressBackgroundTint(int progressBackgroundTint) {
        this.progressBackgroundTint = progressBackgroundTint;
        updateView();
    }

    public float getStepSpacing() {
        return stepSpacing;
    }

    public void setStepSpacing(float stepSpacing) {
        this.stepSpacing = stepSpacing;
        updateView();
    }

    public float getStepRadius() {
        return stepRadius;
    }

    public void setStepRadius(float stepRadius) {
        this.stepRadius = stepRadius;
        updateView();
    }
}
