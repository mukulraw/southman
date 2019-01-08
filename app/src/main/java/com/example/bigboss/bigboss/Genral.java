package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bigboss.bigboss.VideoGenralPOJO.Datum;
import com.example.bigboss.bigboss.VideoGenralPOJO.GenralBean;
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

   List<Datum>list;

    ProgressBar bar;

    String catid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.genral, container, false);

        catid = getArguments().getString("catid");



        list = new ArrayList<>();

        adapter = new GenralAdapter(getContext(), list);

        grid = view.findViewById(R.id.grid);

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

            Datum item = list.get(i);

            myViewHolder.textView.setText(item.getDescription());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getThumbnail(), myViewHolder.imageView, options);


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

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.text);

                imageView = itemView.findViewById(R.id.image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, Videoplayer.class);
                        context.startActivity(i);

                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<GenralBean> call = cr.genra(catid);

        call.enqueue(new Callback<GenralBean>() {
            @Override
            public void onResponse(Call<GenralBean> call, Response<GenralBean> response) {


                if (Objects.equals(response.body().getStatus() , "1")){


                    adapter.setgrid(response.body().getData());

                }
                else
                {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GenralBean> call, Throwable t) {


                bar.setVisibility(View.GONE);


            }
        });

    }
}
