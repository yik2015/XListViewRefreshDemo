package com.hema.www.xlistviewpullrefreshdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.hema.www.xlistviewpullrefreshdemo.widget.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{

    private ArrayList<String> items = new ArrayList<>();

    private ArrayAdapter<String> mAdapter;

    private Handler mHandler;
    private int index;
    private XListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geneItems();

        initView();
    }

    private void initView() {
        mHandler = new Handler();

        listView = (XListView) findViewById(R.id.listview);

        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        listView.setRefreshTime(getTime());

        listView.setAutoLoadEnable(false);
        listView.setPullLoadEnable(false);

        mAdapter = new ArrayAdapter<String>(this, R.layout.vw_list_item, items);
        listView.setAdapter(mAdapter);
    }

    private void geneItems() {
        for (int i = 1; i < 20; i++) {
            items.add("Test Item #" + index++);
        }
    }

    @Override
    public void onRefresh() {
        delayHandle();
    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(getTime());
    }

    @Override
    public void onLoadMore() {
        delayHandle();
    }

    private void delayHandle() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
