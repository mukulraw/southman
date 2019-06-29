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

import com.sc.bigboss.bigboss.TabCategoryPOJO.Datum;
import com.sc.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Videos extends Fragment {

    private TabLayout tab;

    private ViewPager pager;

    private VideoAddapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.videos , container , false);

        pager = view.findViewById(R.id.pager);
        tab = view.findViewById(R.id.tab);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

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


                for (int i = 0; i < Objects.requireNonNull(response.body()).getData().size(); i++) {

                    //tab.addTab(tab.newTab().setText(response.body().getData().get(i).getVideocatName()));
                    //tab.addTab(tab.newTab().setText(response.body().getData().get(i).getVideocatName()));
                    tab.addTab(tab.newTab().setCustomView(getCustomView(inflater , response.body().getData().get(i).getCatUrl() , response.body().getData().get(i).getVideocatName())));

                }

                adapter = new VideoAddapter(getChildFragmentManager() , response.body().getData());
                pager.setAdapter(adapter);
                tab.setupWithViewPager(pager);

                for (int i = 0; i < response.body().getData().size(); i++) {

                    Objects.requireNonNull(tab.getTabAt(i)).setCustomView(getCustomView(inflater , response.body().getData().get(i).getCatUrl() , response.body().getData().get(i).getVideocatName()));

                }


              /*  tab.getTabAt(0).setIcon(R.drawable.ic_film);
                tab.getTabAt(1).setIcon(R.drawable.ic_movie_symbol_of_video_camera);
                tab.getTabAt(2).setIcon(R.drawable.ic_boling_pot);
                tab.getTabAt(3).setIcon(R.drawable.ic_study);
*/


            }

            @Override
            public void onFailure(Call<TabBean> call, Throwable t) {

            }
        });

        return view;
    }

    private View getCustomView(LayoutInflater inflater, String url, String name)
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

    class VideoAddapter extends FragmentStatePagerAdapter {

        List<Datum>list;

        VideoAddapter(FragmentManager fm, List<Datum> list) {
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
