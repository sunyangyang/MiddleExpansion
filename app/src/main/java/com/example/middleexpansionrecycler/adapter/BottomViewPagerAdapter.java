package com.example.middleexpansionrecycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.middleexpansionrecycler.bean.ItemBean;
import com.example.middleexpansionrecycler.R;

import java.util.ArrayList;
import java.util.List;

public class BottomViewPagerAdapter extends PagerAdapter {

    public List<ItemBean> mList = new ArrayList<>();
    private Context mContext;

    private View.OnClickListener onClickListener;

    public BottomViewPagerAdapter(final Context context, List<ItemBean> list) {
        mContext = context;
        mList.addAll(list);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null && v.getTag() instanceof Integer) {
                    int position = (int) v.getTag();
                    String link = mList.get(position).link;
                    Toast.makeText(context, "link : " + link, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_item, null, false);
        TextView textView = view.findViewById(R.id.bottom_name);
        textView.setText(mList.get(position).link);
        textView.setTag(position);
        textView.setOnClickListener(onClickListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeAllViews();
    }
}
