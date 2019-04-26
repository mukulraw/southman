package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.sc.bigboss.bigboss.VideoGenralPOJO.Datum;
import com.sc.bigboss.bigboss.VideoGenralPOJO.GenralBean;
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

public class Genral extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    GenralAdapter adapter;

    List<Datum> list;

    ProgressBar bar;

    String catid;

    LinearLayout linearLayout;

    String base;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.genral, container, false);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        base = b.baseurl;

        catid = getArguments().getString("catid");

        list = new ArrayList<>();

        adapter = new GenralAdapter(getContext(), list);

        grid = view.findViewById(R.id.grid);

        linearLayout = view.findViewById(R.id.linear);

        manager = new GridLayoutManager(getContext(), 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        bar = view.findViewById(R.id.progress);

        return view;


    }

    public class GenralAdapter extends RecyclerView.Adapter<GenralAdapter.MyViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();

        public GenralAdapter(Context context, List<Datum> list) {

            this.context = context;

            this.list = list;

        }


        @NonNull
        @Override
        public GenralAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.genral_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GenralAdapter.MyViewHolder myViewHolder, int i) {

            final Datum item = list.get(i);

            myViewHolder.textView.setText(Html.fromHtml(item.getSmallDesc()).toString().trim());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();



            loader.displayImage(base + "bigboss/admin2/upload/videos/" + item.getThumbnail(), myViewHolder.imageView, options);

            myViewHolder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, Videoplayer.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("ph", item.getPhoneNo());
                    intent.putExtra("is", item.getWhatsOrderNow());
                    intent.putExtra("videourl", item.getVideoUrl());
                    intent.putExtra("des", item.getDescription());
                    intent.putExtra("code", item.getProductCode());
                    startActivity(intent);


                }
            });


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("iidd" , item.getVideoUrl());

                    Intent intent = new Intent(context, Videoplayer.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("ph", item.getPhoneNo());
                    intent.putExtra("is", item.getWhatsOrderNow());
                    intent.putExtra("videourl", item.getVideoUrl());
                    intent.putExtra("des", item.getDescription());
                    intent.putExtra("code", item.getProductCode());
                    startActivity(intent);

                }
            });

        }

        public void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            TextView textView;

            Button play;

            public MyViewHolder(@NonNull final View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.text);

                imageView = itemView.findViewById(R.id.image);
                play = itemView.findViewById(R.id.play);


            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<GenralBean> call = cr.genra(catid, SharePreferenceUtils.getInstance().getString("location"));

        call.enqueue(new Callback<GenralBean>() {
            @Override
            public void onResponse(Call<GenralBean> call, Response<GenralBean> response) {

                try {

                    if (Objects.equals(response.body().getStatus(), "1")) {

                        if (response.body().getData().size()>0){


                            linearLayout.setVisibility(View.GONE);

                        }else {
                            linearLayout.setVisibility(View.VISIBLE);
                        }

                        adapter.setgrid(response.body().getData());
                        linearLayout.setVisibility(View.GONE);


                    }
                    else {

                        linearLayout.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GenralBean> call, Throwable t) {

                linearLayout.setVisibility(View.VISIBLE);

                bar.setVisibility(View.GONE);


            }
        });

    }
}
