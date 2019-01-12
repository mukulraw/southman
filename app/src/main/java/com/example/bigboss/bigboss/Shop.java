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

import com.example.bigboss.bigboss.ShoptabPOJO.Datum;
import com.example.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.example.bigboss.bigboss.TabCategoryPOJO.TabBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

        Bean b = (Bean)getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<ShopBean> call = cr.sho();
        call.enqueue(new Callback<ShopBean>() {
            @Override
            public void onResponse(Call<ShopBean> call, Response<ShopBean> response) {

                for (int i = 0; i < response.body().getData().size(); i++) {

                    tab.addTab(tab.newTab().setText(response.body().getData().get(i).getName()));

                }

                adapter = new ShopAdapter(getChildFragmentManager() , response.body().getData());
                pager.setAdapter(adapter);
                tab.setupWithViewPager(pager);

                for (int i = 0; i < response.body().getData().size(); i++) {

                    tab.getTabAt(i).setText(response.body().getData().get(i).getName());

                }

            }

            @Override
            public void onFailure(Call<ShopBean> call, Throwable t) {

            }
        });



        return view;
    }


    public class ShopAdapter extends FragmentStatePagerAdapter {

        List<Datum>list = new ArrayList<>();

        public ShopAdapter(FragmentManager fm, List<Datum>list) {
            super(fm);

            this.list = list;
        }

        @Override
        public Fragment getItem(int i) {

          Till till = new Till();

          Bundle b = new Bundle();

          b.putString("Catid" , list.get(i).getId());

          till.setArguments(b);

            return till;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


}
