package com.origamitecnologia.appmodel.control;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class AnimationUtils {

    private static final int ANIMATE_DURATION = 700;
    private static final Interpolator ANIMATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    private final static Rect mTmpRect = new Rect();


    public static void fadeOutToBottom(final View v, View main) {
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        v.animate()
                .translationYBy(main.getHeight() / 2)
                .alpha(0)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener( new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    public static void undoFadeOutToBottom(final View v) {
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        v.animate()
                .translationY(0)
                .alpha(1)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    public static void stickTo(final View v, View viewToStickTo, ViewGroup main) {
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        v.getDrawingRect(mTmpRect);
        main.offsetDescendantRectToMyCoords(v, mTmpRect);

        v.animate()
                .translationY(viewToStickTo.getHeight() - mTmpRect.top)
                .alpha(1)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    public static void undoStickTo(final View v, ViewGroup main) {
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        v.animate()
                .y(main.getHeight())
                .alpha(0)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }


    public static void focusOn(final View v, View focusView, ViewGroup main) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        //Pega a posição da view que você quer
        focusView.getDrawingRect(mTmpRect);

        //Traduz a posição da view no seu layout atual para a posição dessa mesma view num layout "ancestral, root"
        main.offsetDescendantRectToMyCoords(focusView, mTmpRect);

        v.animate()
                .translationY(-mTmpRect.top)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    public static void undoFocusOn(final View v) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        v.animate()
                .translationY(0)
                .setDuration(ANIMATE_DURATION)
                .setInterpolator(ANIMATE_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setLayerType(View.LAYER_TYPE_NONE, null);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

}
