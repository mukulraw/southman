package com.example.bigboss.bigboss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Videos extends Fragment {

    TabLayout tab;

    ViewPager pager;

    VideoAddapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.videos , container , false);

        pager = view.findViewById(R.id.pager);
        tab = view.findViewById(R.id.tab);

        tab.addTab(tab.newTab().setText("Genral"));
        tab.addTab(tab.newTab().setText("Entertainment"));
        tab.addTab(tab.newTab().setText("Steam"));


        adapter = new VideoAddapter(getChildFragmentManager() , 3);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);

        tab.getTabAt(0).setText("Genral");
        tab.getTabAt(1).setText("Entertainment");
        tab.getTabAt(2).setText("Steam");

        return view;
    }

    public class VideoAddapter extends FragmentStatePagerAdapter {

        public VideoAddapter(FragmentManager fm, int tab) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0) {

                return new Genral();

            } else if (i == 1) {
                return new Entertainment();


            }else if (i == 2){

                return new Steam();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;






        }
    }

}
