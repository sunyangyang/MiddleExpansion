package com.example.middleexpansionrecycler.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.middleexpansionrecycler.R;
import com.example.middleexpansionrecycler.anim.ExpandableViewHoldersUtil;
import com.example.middleexpansionrecycler.bean.WeatherBean;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.WxArticleHolder> {

    private Context mContext;
    private List<WeatherBean> mList;

    public MyRecyclerAdapter(Context context, List<WeatherBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    ExpandableViewHoldersUtil.KeepOneH<WxArticleHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<>();

    @NonNull
    @Override
    public WxArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WxArticleHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WxArticleHolder holder, final int position) {
        final WeatherBean bean = mList.get(position);
        holder.bind(position, bean);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class WxArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {

        private WxArticleHolder mHolder;
        public final TextView mTitle;
        public final TextView mTitle1;
        public final LinearLayout mTopLayout; //折叠View
        public ViewPager mBottomLayout; //折叠View

        public
        WxArticleHolder(@NonNull View itemView) {
            super(itemView);
            mHolder = this;
            mTitle = itemView.findViewById(R.id.mTitle);
            mTitle1 = itemView.findViewById(R.id.mTitle1);
            mTopLayout = itemView.findViewById(R.id.mTopLayout);
            mBottomLayout = itemView.findViewById(R.id.bottom_view);
            mTitle.setOnClickListener(this);
            mTitle1.setOnClickListener(this);
        }

        //绑定数据
        public void bind(final int pos, final WeatherBean bean) {
            keepOne.bind(this,pos);
            mTitle.setText(Html.fromHtml(TextUtils.isEmpty(bean.datas.get(0).title) ? "暂无" : bean.datas.get(0).title)); //标题
            mTitle1.setText(Html.fromHtml(TextUtils.isEmpty(bean.datas.get(1).title) ? "暂无" : bean.datas.get(1).title)); //标题
            BottomViewPagerAdapter adapter = new BottomViewPagerAdapter(mContext, bean.datas);
            mHolder.mBottomLayout.setAdapter(adapter);
            mHolder.mBottomLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            mTitle.setSelected(true);
                            mTitle1.setSelected(false);
                            break;
                        case 1:
                            mTitle.setSelected(false);
                            mTitle1.setSelected(true);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        @Override
        public View getExpandView() {
            return mBottomLayout;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mTitle:
                    if (mHolder.mBottomLayout.getCurrentItem() == 0) {
                        keepOne.toggle(mHolder, true);
                    } else {
                        mHolder.mBottomLayout.setCurrentItem(0);
                        keepOne.toggle(mHolder, false);
                    }
                    break;
                case R.id.mTitle1:
                    if (mHolder.mBottomLayout.getCurrentItem() == 1) {
                        keepOne.toggle(mHolder, true);
                    } else {
                        mHolder.mBottomLayout.setCurrentItem(1);
                        keepOne.toggle(mHolder, false);
                    }
                    break;
            }
        }
    }

}
