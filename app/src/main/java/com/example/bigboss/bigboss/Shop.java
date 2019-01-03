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
import android.widget.Toast;

public class Shop extends Fragment {

    TabLayout tab;

    ViewPager pager;

    ShopAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shop, container, false);

        pager = view.findViewById(R.id.pagerr);
        tab = view.findViewById(R.id.tab);

        tab.addTab(tab.newTab().setText("Till Day sale"));
        tab.addTab(tab.newTab().setText("Shop by Shop"));
        tab.addTab(tab.newTab().setText("Mens Wear Matching"));

        adapter = new ShopAdapter(getChildFragmentManager() , 3);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);

        tab.getTabAt(0).setText("Till Day sale");
        tab.getTabAt(1).setText("Shop by Shop");
        tab.getTabAt(2).setText("Mens Wear Matching");

        return view;
    }


    public class ShopAdapter extends FragmentStatePagerAdapter {

        public ShopAdapter(FragmentManager fm, int tab) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0) {

                return new Till();

            } else if (i == 1) {
                return new Shopby();


            }else if (i == 2){

                return new MeansWear();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
