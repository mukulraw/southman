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

import com.example.bigboss.bigboss.TabCategoryPOJO.Datum;
import com.example.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.example.bigboss.bigboss.VideoGenralPOJO.GenralBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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




        Bean b = (Bean)getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<TabBean> call = cr.tabbean();
        call.enqueue(new Callback<TabBean>() {
            @Override
            public void onResponse(Call<TabBean> call, Response<TabBean> response) {


                for (int i = 0; i < response.body().getData().size(); i++) {

                    tab.addTab(tab.newTab().setText(response.body().getData().get(i).getVideocatName()));

                }

                adapter = new VideoAddapter(getChildFragmentManager() , response.body().getData());
                pager.setAdapter(adapter);
                tab.setupWithViewPager(pager);

                for (int i = 0; i < response.body().getData().size(); i++) {

                    tab.getTabAt(i).setText(response.body().getData().get(i).getVideocatName());

                }
            }

            @Override
            public void onFailure(Call<TabBean> call, Throwable t) {

            }
        });









        return view;
    }
    public class VideoAddapter extends FragmentStatePagerAdapter {

        List<Datum>list = new ArrayList<>();

        public VideoAddapter(FragmentManager fm, List<Datum>list) {
            super(fm);

            this.list = list;
        }

        @Override
        public Fragment getItem(int i) {

                Genral frag = new Genral();

                Bundle b = new Bundle();
                b.putString("catid" , list.get(i).getId());
                frag.setArguments(b);

                return frag;

        }

        @Override
        public int getCount() {
            return list.size();



        }
    }

}
