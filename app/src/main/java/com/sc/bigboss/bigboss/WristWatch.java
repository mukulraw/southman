package com.sc.bigboss.bigboss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.PlaySliderPOJO.PlayBean;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WristWatch extends AppCompatActivity {


    Toolbar toolbar;

    AutoScrollViewPager pager;

    ViewAdapter adapter;

    CircleIndicator indicator;

    String id , title;

    TextView tool;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrist_watch);


        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar);
        bar = findViewById(R.id.progress);
        tool = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        tool.setText(title);

        pager = (AutoScrollViewPager) findViewById(R.id.pager);

        //pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(14000);
        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        indicator = findViewById(R.id.indicator);



        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<PlayBean> call = cr.play(id , SharePreferenceUtils.getInstance().getString("location"  ));

        call.enqueue(new Callback<PlayBean>() {
            @Override
            public void onResponse(Call<PlayBean> call, Response<PlayBean> response) {



                adapter = new ViewAdapter(getSupportFragmentManager(), response.body().getThumb().get(0));

                pager.setAdapter(adapter);

                indicator.setViewPager(pager);


                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<PlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });




    }

    public class ViewAdapter extends FragmentStatePagerAdapter {

        List<String> tlist = new ArrayList<>();

        public ViewAdapter(FragmentManager fm, List<String> tlist) {
            super(fm);
            this.tlist = tlist;
        }

        @Override
        public Fragment getItem(int i) {


            Page1 frag = new Page1();
            Bundle b = new Bundle();
            b.putString("url" , tlist.get(i));

            frag.setArguments(b);
                return frag;

        }

        @Override
        public int getCount() {
            return tlist.size();
        }
    }


    /*public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
           *//* indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));*//*
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        pager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume
        pager.startAutoScroll();
    }
}
