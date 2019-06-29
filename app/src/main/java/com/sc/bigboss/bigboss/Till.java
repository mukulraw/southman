package com.sc.bigboss.bigboss;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.ShopTillPOJO.Datum;
import com.sc.bigboss.bigboss.ShopTillPOJO.TillBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Till extends Fragment {

    private RecyclerView grid;

    private GridLayoutManager manager;

    private TillAddapter adapter;

    private List<Datum> list;

    private String catid;

    private ProgressBar bar;

    private LinearLayout linearLayout;

    private String catName;
    private String base;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.till, container, false);

        catid = Objects.requireNonNull(getArguments()).getString("Catid");
        catName = getArguments().getString("catname");

        //Log.d("catname " , catName);

        list = new ArrayList<>();

        adapter = new TillAddapter(getContext(), list);

        grid = vi.findViewById(R.id.grid);
        linearLayout = vi.findViewById(R.id.linear);

        manager = new GridLayoutManager(getContext() , 3);

        grid.setAdapter(adapter);


        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                return Integer.parseInt(adapter.getSpace(i));

            }
        });


        grid.setLayoutManager(manager);

        bar = vi.findViewById(R.id.progress);


        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        base = b.baseurl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<TillBean> call = cr.till(catid , SharePreferenceUtils.getInstance().getString("location"));

        call.enqueue(new Callback<TillBean>() {
            @Override
            public void onResponse(Call<TillBean> call, Response<TillBean> response) {

                try {

                    if (Objects.equals(Objects.requireNonNull(response.body()).getStatus(), "1")) {

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
            public void onFailure(Call<TillBean> call, Throwable t) {

                linearLayout.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);



            }
        });

        return vi;


    }

    public class TillAddapter extends RecyclerView.Adapter<TillAddapter.MyViewHolder> {

        final Context context;

        List<Datum> list;


        TillAddapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public TillAddapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.till_list_model, viewGroup, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TillAddapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.setIsRecyclable(false);

            final Datum item = list.get(i);

            // myViewHolder.name.setText(item.getSubcatName());

/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/cat/" + item.getImageUrl(), myViewHolder.imageView, options);
*/


            Glide.with(context).load(base + "southman/admin2/upload/cat/" + item.getImageUrl()).into(myViewHolder.imageView);




            myViewHolder.itemView.setOnClickListener(v -> {

                Intent i1 = new Intent(context, MeansCategory.class);
                i1.putExtra("id", item.getId());
                i1.putExtra("text", item.getSubcatName());
                i1.putExtra("catname", catName);
                context.startActivity(i1);


            });

        }

        void setgrid(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();

        }

        String getSpace(int position)
        {
            return list.get(position).getSpace();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final ImageView imageView;


            MyViewHolder(@NonNull final View itemView) {
                super(itemView);


                // name = itemView.findViewById(R.id.name);
                imageView = itemView.findViewById(R.id.tshirt);


            }
        }
    }


}