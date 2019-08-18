package com.diol.widget.materialprogressbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MaterialPercentageProgressBar extends RelativeLayout {

    //Views
    private View emptyTrack;
    private View filledTrack;
    private View indicator;
    private List<View> dots;
    private TextView tvIndicator;

    //Characteristics
    private int emptyTrackColor = R.color.grey;
    private int filledTrackColor = R.color.filledTrackColor;
    private float trackHeight = getResources().getDimension(R.dimen.trackHeight);

    private int indicatorColor = R.color.filledTrackColor;
    private float indicatorSize = getResources().getDimension(R.dimen.indicatorSize);

    private int steps = getResources().getInteger(R.integer.steps);
    private int dotsColor = R.color.darkGrey;
    private float dotsSize = getResources().getDimension(R.dimen.dotsSize);
    private int textColor = R.color.white;
    private int currentStep = 1;

    private TypedArray xmlAttrs;

    Context context;


    public MaterialPercentageProgressBar(Context context) {
        super(context);
    }

    public MaterialPercentageProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialPercentageProgressBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        setUpProps(context, attrs);

        emptyTrack = createEmptyTrack(context);
        filledTrack = createFilledTrack(context);
        indicator = createIndicator(context);


        addView(emptyTrack);
        addView(filledTrack);
        addView(indicator);

        Utils.doOnceOnGlobalLayoutOfView(this, new Runnable() {
            @Override
            public void run() {
                dots = createDots(context);

                for(View dot: dots) addView(dot);
            }
        });
    }

    private void setUpProps(Context context, AttributeSet attrs)
    {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        xmlAttrs = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MaterialPercentageProgressBar,
                0, 0);

        emptyTrackColor = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_emptyTrackColor,emptyTrackColor);
        filledTrackColor = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_filledTrackColor,filledTrackColor);
        indicatorColor = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_indicatorColor,indicatorColor);
        dotsColor = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_dotsColor, dotsColor);
        textColor = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_textColor, textColor);

        steps = xmlAttrs.getResourceId(R.styleable.MaterialPercentageProgressBar_steps, steps);


        xmlAttrs.recycle();
    }

    private View createEmptyTrack(Context context)
    {
        View track = new View(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)trackHeight);
        params.setMargins((int)(indicatorSize/2.0f), 0, (int)(indicatorSize/2.0f), 0);
        params.addRule(ALIGN_PARENT_BOTTOM);
        track.setBackgroundColor(ContextCompat.getColor(context, emptyTrackColor));
        track.setId(generateViewId());
        track.setLayoutParams(params);

        return track;

    }

    private View createFilledTrack(Context context)
    {
        View track = new View(context);

        track.setPivotX(0);
        LayoutParams params = new LayoutParams(1, (int)trackHeight);
        params.setMargins((int)(indicatorSize/2.0f), 0, (int)(indicatorSize/2.0f), 0);
        params.addRule(ALIGN_PARENT_BOTTOM);
        track.setBackgroundColor(ContextCompat.getColor(context, filledTrackColor));
        track.setId(generateViewId());
        track.setLayoutParams(params);

        return track;

    }

    private List<View> createDots(Context context)
    {
        List<View> dots = new ArrayList<>();

        for(int i =0; i < steps; ++i)
        {

            View dot = new View(context);

            LayoutParams params = new LayoutParams((int)dotsSize, (int)dotsSize);
            params.addRule(ALIGN_PARENT_BOTTOM);
            params.addRule(ALIGN_PARENT_LEFT);

            float margin = emptyTrack.getMeasuredWidth()/(float)(steps-1);


            params.setMargins(i*(int)margin+((int)((indicatorSize/2.0f)-(dotsSize/2.0f))), 0, 0, 0);


            ShapeDrawable oval = new ShapeDrawable(new OvalShape());
            oval.setIntrinsicHeight ((int)dotsSize);
            oval.setIntrinsicWidth ((int)dotsSize);
            oval.getPaint ().setColor (ContextCompat.getColor(context, dotsColor));
            dot.setBackground(oval);
            dot.setId(generateViewId());
            dot.setLayoutParams(params);

            dots.add(dot);
        }



        return dots;
    }

    private View createIndicator(Context context)
    {

        RelativeLayout rlIndicator = new RelativeLayout(context);

        LayoutParams params = new LayoutParams((int)indicatorSize, (int)indicatorSize);
        params.addRule(RelativeLayout.ABOVE, emptyTrack.getId());
        params.addRule(ALIGN_PARENT_LEFT);
        params.setMargins(0,0,0, (int) getResources().getDimension(R.dimen.indicatorBotMargin));
        rlIndicator.setLayoutParams(params);

        ImageView ivIndicatorIcon = new ImageView(context);
        Drawable icon  = getResources().getDrawable(R.drawable.ic_indicator);
        Utils.setDrawableColorFilter(icon, ContextCompat.getColor(context, indicatorColor));
        ivIndicatorIcon.setBackground(icon);
        LayoutParams ivParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ivIndicatorIcon.setLayoutParams(ivParams);

        tvIndicator = new TextView(context);
        tvIndicator.setText("0%");
        tvIndicator.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.indicatorTextSize));
        tvIndicator.setTextColor(ContextCompat.getColor(context, textColor));
        LayoutParams tvParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.addRule(CENTER_HORIZONTAL);
        tvParams.setMargins(0, 25, 0, 0);
        tvIndicator.setLayoutParams(tvParams);


        rlIndicator.addView(ivIndicatorIcon);
        rlIndicator.addView(tvIndicator);

        return rlIndicator;


    }


    public void fillTo(final int step)
    {
        if(step == currentStep) return;

        Utils.runAfter(500, new Runnable() {
            @Override
            public void run() {
                int minstep = Math.min(step, steps);

                final float target = dots.get(minstep-1).getX() - dots.get(0).getX();

                final float start = (float)filledTrack.getMeasuredWidth()/(float)emptyTrack.getMeasuredWidth();



                final float scale = target/filledTrack.getMeasuredWidth();

                ObjectAnimator fillBar = ObjectAnimator.ofFloat(filledTrack,"scaleX",scale);
                fillBar.setDuration(getResources().getInteger(R.integer.fill_duration));
                fillBar.setAutoCancel(true);
                fillBar.setInterpolator(new DecelerateInterpolator(2));
                fillBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int percent = (int)(100.0f*animation.getAnimatedFraction());
                        tvIndicator.setText((int)(1+start+(scale/emptyTrack.getMeasuredWidth()-start)*percent)+"%");

                    }
                });
                fillBar.start();

                currentStep = step;

                ObjectAnimator moveIndicator = ObjectAnimator.ofFloat(indicator, "x", target);
                moveIndicator.setDuration(getResources().getInteger(R.integer.fill_duration));
                moveIndicator.setAutoCancel(true);
                moveIndicator.setInterpolator(new DecelerateInterpolator(2));
                moveIndicator.start();
            }
        });





    }

}
