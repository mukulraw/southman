package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.ShopTillPOJO.Datum;
import com.example.bigboss.bigboss.ShopTillPOJO.TillBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Shopby extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    ShopByAdapter adapter;

    List<Datum> list;

    String catid , location;

    ProgressBar bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.till, container, false);


        catid = getArguments().getString("catid");



        list = new ArrayList<>();

        adapter = new ShopByAdapter(getContext(), list);

        manager = new GridLayoutManager(getContext(), 3);

        grid = view.findViewById(R.id.grid);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        bar = view.findViewById(R.id.progress);

        return view;
    }


    public class ShopByAdapter extends RecyclerView.Adapter<ShopByAdapter.MyViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();

        public ShopByAdapter(Context context, List<Datum> list) {

            this.list = list;
            this.context = context;

        }


        @NonNull
        @Override
        public ShopByAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.till_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopByAdapter.MyViewHolder myViewHolder, int i) {

            final Datum item = list.get(i);
            myViewHolder.textView.setText(item.getSubcatName());

         myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(getContext(), MeansCategory.class);
                    i.putExtra("id" , item.getId());
                    context.startActivity(i);
                }
            });


        }

        public void setgrid(List<Datum> list) {

            this.list = list;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                imageView = itemView.findViewById(R.id.tshirt);
                textView = itemView.findViewById(R.id.name);


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

        Call<TillBean> call = cr.till(catid ,  SharePreferenceUtils.getInstance().getString("location"));

        call.enqueue(new Callback<TillBean>() {
            @Override
            public void onResponse(Call<TillBean> call, Response<TillBean> response) {

                if (Objects.equals(response.body().getStatus(), "1")) {

                    adapter.setgrid(response.body().getData());

                } else {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TillBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });
    }
}

