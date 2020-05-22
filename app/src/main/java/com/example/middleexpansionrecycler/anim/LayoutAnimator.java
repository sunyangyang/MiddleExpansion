package com.example.middleexpansionrecycler.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

public class LayoutAnimator {

    public static class LayoutHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private final View _view;
        private final int _transY;
        private final int _start;
        private final int _disparity;
        private final View _parent;
        private final View _expandView;

        public LayoutHeightUpdateListener(View view, int transY, int start, int disparity, View parent, View expandView) {
            _view = view;
            _transY = transY;
            _start = start;
            _disparity = disparity;
            _parent = parent;
            _expandView = expandView;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final ViewGroup.LayoutParams lp = _view.getLayoutParams();
            lp.height = (int) animation.getAnimatedValue();

            float closePercentage = Math.abs(1.f - Math.abs(lp.height - _start) * 1.0f / _disparity);
            float openPercentage = Math.abs((lp.height - _start) * 1.0f / _disparity);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) _parent.getLayoutParams();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) _expandView.getLayoutParams();
            if (_transY > 0) {//关闭
                params.topMargin = (int) (-_transY * closePercentage);
                layoutParams.topMargin = (int) (-_transY * openPercentage);
            } else {//打开
                params.topMargin = (int) (_transY * openPercentage);
                layoutParams.topMargin = (int) (_transY * closePercentage);
            }

            _expandView.setLayoutParams(layoutParams);
            _parent.setLayoutParams(params);
            _view.setLayoutParams(lp);
        }
    }

    public static Animator ofHeight(View view, int start, int end, int transY, View parent, View expandView) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new LayoutHeightUpdateListener(view, transY, start, Math.abs(end - start), parent, expandView));
        return animator;
    }
}
