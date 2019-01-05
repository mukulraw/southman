package com.example.bigboss.bigboss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

public class WristWatch extends AppCompatActivity {


    Toolbar toolbar;

    AutoScrollViewPager pager;

    ViewAdapter adapter;

    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrist_watch);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });


        pager = (AutoScrollViewPager) findViewById(R.id.pager);

        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(2000);
        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));


        indicator = findViewById(R.id.indicator);

        adapter = new ViewAdapter(getSupportFragmentManager(), 5);

        pager.setAdapter(adapter);

        indicator.setViewPager(pager);


    }

    public class ViewAdapter extends FragmentStatePagerAdapter {


        public ViewAdapter(FragmentManager fm, int list) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {


            if (i == 0) {
                return new Page1();

            } else {
                if (i == 1) {
                    return new page2();

                } else {
                    if (i == 2) {
                        return new page3();

                    } else if (i == 3) {
                        return new page4();

                    }
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
           /* indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));*/
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

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
