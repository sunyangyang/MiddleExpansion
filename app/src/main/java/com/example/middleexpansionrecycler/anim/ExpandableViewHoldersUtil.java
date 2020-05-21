package com.example.middleexpansionrecycler.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

public class ExpandableViewHoldersUtil {

    public static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate, final int position, RecyclerView parent) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder, position, true, parent, expandView);
            if (animator == null) {
                return;
            }
            animator.start();
        } else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    public static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate,
                              final int previous, final int _opened, final RecyclerView.ViewHolder openHolder, final View openExpandView, final RecyclerView parent) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder, previous, false, parent, expandView);
            if (animator == null) {
                return;
            }
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) expandView.getLayoutParams();
                    layoutParams.topMargin = 0;
                    expandView.setLayoutParams(layoutParams);
                    if (previous !=_opened && openHolder != null) {
                        openH(openHolder, openExpandView, true, _opened, parent);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) expandView.getLayoutParams();
                    layoutParams.topMargin = 0;
                    expandView.setLayoutParams(layoutParams);
                    if (previous !=_opened && openHolder != null) {
                        openH(openHolder, openExpandView, true, _opened, parent);
                    }
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    public interface Expandable {
        View getExpandView();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;

        public void bind(VH holder, int pos) {
            RecyclerView parent = (RecyclerView) holder.itemView.getParent();
            if (pos == _opened)
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false, _opened, parent);
            else
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false, pos, _opened, null, null, parent);
        }


        public void toggle(VH holder, boolean isCurrentItem) {
            RecyclerView parent = (RecyclerView) holder.itemView.getParent();
            if (_opened == holder.getPosition()) {
                if (isCurrentItem) {
                    _opened = -1;
                    ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true, holder.getPosition(), -1, null, null, parent);
                }
            } else {
                int previous = _opened;
                _opened = holder.getPosition();

                final VH oldHolder = (VH) parent.findViewHolderForPosition(previous);
                if (oldHolder != null) {
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true, previous, _opened, holder, holder.getExpandView(), parent);
                } else {
                    ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true, _opened, parent);
                }
            }
        }
    }
}
