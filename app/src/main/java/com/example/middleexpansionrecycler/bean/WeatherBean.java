package com.example.middleexpansionrecycler.bean;

import androidx.annotation.NonNull;

import com.example.middleexpansionrecycler.bean.ItemBean;

import java.util.List;

public class WeatherBean {
    public List<ItemBean> datas;

    @NonNull
    @Override
    public String toString() {
        if (datas != null && !datas.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (ItemBean item:datas) {
                stringBuffer.append(item.toString());
            }
            return stringBuffer.toString();
        }
        return super.toString();
    }
}
