package com.example.middleexpansionrecycler.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.middleexpansionrecycler.anim.LayoutAnimator;
import com.example.middleexpansionrecycler.utils.Utils;

public class ViewHolderAnimator {

    public static class ViewHolderAnimatorListener extends AnimatorListenerAdapter {
        private final RecyclerView.ViewHolder _holder;

        public ViewHolderAnimatorListener(RecyclerView.ViewHolder holder) {
            _holder = holder;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            _holder.setIsRecyclable(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            _holder.setIsRecyclable(true);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            _holder.setIsRecyclable(true);
        }
    }

    /**
     * 避免多次出发动画之后微小的差异滚雪球一样变大
     */
    public static class LayoutParamsAnimatorListener extends AnimatorListenerAdapter {
        private final View _view;
        private final int _paramsWidth;
        private final int _paramsHeight;

        public LayoutParamsAnimatorListener(View view, int paramsWidth, int paramsHeight) {
            _view = view;
            _paramsWidth = paramsWidth;
            _paramsHeight = paramsHeight;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            final ViewGroup.LayoutParams params = _view.getLayoutParams();
            params.width = _paramsWidth;
            params.height = _paramsHeight;
            _view.setLayoutParams(params);
        }
    }

    public static Animator ofItemViewHeight(RecyclerView.ViewHolder holder, int position, boolean isOpen, RecyclerView parent, View expandView) {
        int start = holder.itemView.getMeasuredHeight();
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = holder.itemView.getMeasuredHeight();
        int transY = isOpen ? -position * Utils.dip2px(holder.itemView.getContext(), 70) : position * Utils.dip2px(holder.itemView.getContext(), 70);
        final Animator animator = LayoutAnimator.ofHeight(holder.itemView, start, end, transY, parent, expandView);
        animator.addListener(new ViewHolderAnimatorListener(holder));

        animator.addListener(new LayoutParamsAnimatorListener(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return animator;
    }

}
