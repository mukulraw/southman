package com.sc.bigboss.bigboss;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.sc.bigboss.bigboss.ShoptabPOJO.Datum;
import com.sc.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

        Call<ShopBean> call = cr.sho( SharePreferenceUtils.getInstance().getString("location"));
        call.enqueue(new Callback<ShopBean>() {
            @Override
            public void onResponse(Call<ShopBean> call, Response<ShopBean> response) {

                try {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        tab.addTab(tab.newTab().setCustomView(getCustomView(inflater , response.body().getData().get(i).getCatUrl() , response.body().getData().get(i).getName())));

                    }

                    adapter = new ShopAdapter(getChildFragmentManager() , response.body().getData());
                    pager.setAdapter(adapter);
                    tab.setupWithViewPager(pager);

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        tab.getTabAt(i).setCustomView(getCustomView(inflater , response.body().getData().get(i).getCatUrl() , response.body().getData().get(i).getName()));

                    }


                }catch (Exception e){

                    e.printStackTrace();
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

            if (list.get(i).getMainCat().equals("shop"))
            {
                Till till = new Till();

                Bundle b = new Bundle();

                b.putString("Catid" , list.get(i).getId());
                b.putString("catname" , list.get(i).getName().toLowerCase());


                till.setArguments(b);

                return till;
            }
            else if (list.get(i).getName().toLowerCase().equals("mens wear matching"))
            {
                MeansWear till = new MeansWear();

                Bundle b = new Bundle();

                b.putString("Catid" , list.get(i).getId());
                b.putString("type" , "men");

                till.setArguments(b);

                return till;
            }
            else
            {
                MeansWear till = new MeansWear();

                Bundle b = new Bundle();

                b.putString("Catid" , list.get(i).getId());
                b.putString("type" , "women");

                till.setArguments(b);

                return till;
            }


        }

        @Override
        public int getCount() {
            return list.size();
        }
    }


    View getCustomView(LayoutInflater inflater , String url , String name)
    {
        View view = inflater.inflate(R.layout.tabs_layout , null);

        TextView tname = view.findViewById(R.id.textView3);

        ImageView timage = view.findViewById(R.id.imageView);

        tname.setText(name);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(url , timage , options);

        return view;
    }


}
