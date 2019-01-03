package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

public class Play extends Fragment {

    Button submit;

    AutoScrollViewPager pager;

    CircleIndicator indicator;

    ImageAddapter adapter;

    TextView name , color  , price , size , proof , brand , nagtiable;

    EditText email , phone;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.play, container, false);


        submit = view.findViewById(R.id.submit );
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext() , Playitem.class);
                startActivity(i);

            }
        });


        pager = (AutoScrollViewPager)view. findViewById(R.id.pager);

        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(2000);
        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));


        indicator = view. findViewById(R.id.indicator);

        adapter = new ImageAddapter(getChildFragmentManager(), 3);

        pager.setAdapter(adapter);

        indicator.setViewPager(pager);

        name = view.findViewById(R.id.namee);

        brand = view.findViewById(R.id.brand);

        color = view.findViewById(R.id.color);

        price = view.findViewById(R.id.price);

        size = view.findViewById(R.id.size);

        nagtiable = view.findViewById(R.id.nagtiable);

        proof = view.findViewById(R.id.waterproof);

        email = view.findViewById(R.id.email);

        phone = view.findViewById(R.id.phone);




















        return view;
    }

    public class ImageAddapter extends FragmentStatePagerAdapter{


        public ImageAddapter(FragmentManager fm , int tab) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0){

                return new Image1();
            }else if (i == 1){

                return new Image2();
            }else if (i == 2){

                return new Image3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
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
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        pager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        pager.startAutoScroll();
    }




}
