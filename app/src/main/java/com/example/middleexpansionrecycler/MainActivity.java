package com.example.middleexpansionrecycler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.middleexpansionrecycler.adapter.MyRecyclerAdapter;
import com.example.middleexpansionrecycler.bean.ItemBean;
import com.example.middleexpansionrecycler.bean.WeatherBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private List<WeatherBean> mList = new ArrayList<>();
    private MyRecyclerAdapter mAdapter;
    private String data = "{\n" +
            "\"datas\": [{\n" +
            "\"title\": \"5月1日\",\n" +
            "\"link\": \"5月1日:晴\"\n" +
            "}, {\n" +
            "\"title\": \"5月2日\",\n" +
            "\"link\": \"5月2日:晴\"\n" +
            "}, {\n" +
            "\"title\": \"5月3日\",\n" +
            "\"link\": \"5月3日:阴\"\n" +
            "}, {\n" +
            "\"title\": \"5月4日\",\n" +
            "\"link\": \"5月4日:阴\"\n" +
            "}, {\n" +
            "\"title\": \"5月5日\",\n" +
            "\"link\": \"5月5日:雨\"\n" +
            "}, {\n" +
            "\"title\": \"5月6日\",\n" +
            "\"link\": \"5月6日:雨\"\n" +
            "}, {\n" +
            "\"title\": \"5月7日\",\n" +
            "\"link\": \"5月7日:大风\"\n" +
            "}, {\n" +
            "\"title\": \"5月8日\",\n" +
            "\"link\": \"5月8日:大风\"\n" +
            "}, {\n" +
            "\"title\": \"5月9日\",\n" +
            "\"link\": \"5月9日:多云转晴\"\n" +
            "}, {\n" +
            "\"title\": \"5月10日\",\n" +
            "\"link\": \"5月10日:多云转晴\"\n" +
            "}, {\n" +
            "\"title\": \"5月11日\",\n" +
            "\"link\": \"5月11日:晴转雨\"\n" +
            "}, {\n" +
            "\"title\": \"5月12日\",\n" +
            "\"link\": \"5月12日:晴转雨\"\n" +
            "}, {\n" +
            "\"title\": \"5月13日\",\n" +
            "\"link\": \"5月13日:雪\"\n" +
            "}, {\n" +
            "\"title\": \"5月14日\",\n" +
            "\"link\": \"5月14日:雪\"\n" +
            "}, {\n" +
            "\"title\": \"5月15日\",\n" +
            "\"link\": \"5月15日:沙尘暴\"\n" +
            "}, {\n" +
            "\"title\": \"5月16日\",\n" +
            "\"link\": \"5月16日:沙尘暴\"\n" +
            "}, {\n" +
            "\"title\": \"5月17日\",\n" +
            "\"link\": \"5月17日:晴转多云\"\n" +
            "}, {\n" +
            "\"title\": \"5月18日\",\n" +
            "\"link\": \"5月18日:晴转多云\"\n" +
            "}, {\n" +
            "\"title\": \"5月19日\",\n" +
            "\"link\": \"5月19日:台风\"\n" +
            "}, {\n" +
            "\"title\": \"5月20日\",\n" +
            "\"link\": \"5月20日:台风\"\n" +
            "}]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    //初始化数据
    private void initData() {
        Gson gson = new Gson();
        WeatherBean originBean = gson.fromJson(data, WeatherBean.class);
        if (originBean.datas != null){
            for (int i = 0; i < originBean.datas.size() - 1; i+= 2) {
                List<ItemBean> itemBeans = originBean.datas.subList(i, i + 2);
                WeatherBean bean = new WeatherBean();
                bean.datas = itemBeans;
                mList.add(bean);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecycler = findViewById(R.id.mRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecycler.setLayoutManager(layoutManager);
        if (mAdapter == null) {
            mAdapter = new MyRecyclerAdapter(this, mList);
            mRecycler.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
