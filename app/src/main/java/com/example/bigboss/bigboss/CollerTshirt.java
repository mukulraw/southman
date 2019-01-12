package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bigboss.bigboss.TillCategory3POJO.ProductInfo;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.example.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
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

public class CollerTshirt extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    CollerAdapter adapeter;

    List<ProductInfo> list;

    String id;

    ProgressBar bar;

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coller_tshirt);

        id = getIntent().getStringExtra("id");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        list = new ArrayList<>();

        adapeter = new CollerAdapter(this, list);

        grid = findViewById(R.id.grid);

        title = findViewById(R.id.title);

        title.setText(getIntent().getStringExtra("text"));

        manager = new GridLayoutManager(this, 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapeter);

        bar = findViewById(R.id.progress);

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<ShopProductBean> call = cr.shopproduct(id);

        call.enqueue(new Callback<ShopProductBean>() {
            @Override
            public void onResponse(Call<ShopProductBean> call, Response<ShopProductBean> response) {


                adapeter.setgrid(response.body().getProductInfo());
                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ShopProductBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


    }

    public class CollerAdapter extends RecyclerView.Adapter<CollerAdapter.MyViewHolder> {

        Context context;

        List<ProductInfo> list = new ArrayList<>();

        public CollerAdapter(Context context, List<ProductInfo> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public CollerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.coller_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CollerAdapter.MyViewHolder myViewHolder, int i) {


            final ProductInfo item = list.get(i);

            myViewHolder.name.setText(item.getProductTitle());
            myViewHolder.brand.setText(item.getBrand());
            myViewHolder.size.setText(item.getSize());
            myViewHolder.prices.setText(item.getPrice());
            myViewHolder.color.setText(item.getColor());
            myViewHolder.negotiable.setText(item.getNegotiable());


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getProductImage(), myViewHolder.image, options);


            myViewHolder. itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, SingleProduct.class);

                    i.putExtra("id"  , item.getId());

                    i.putExtra("text"  , item.getProductTitle());

                    context.startActivity(i);
                }
            });

        }

        public void setgrid(List<ProductInfo> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name, brand, size, prices, color, negotiable;

            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                brand = itemView.findViewById(R.id.brand);
                size = itemView.findViewById(R.id.size);
                prices = itemView.findViewById(R.id.price);
                color = itemView.findViewById(R.id.color);
                negotiable = itemView.findViewById(R.id.negotiable);
                image = itemView.findViewById(R.id.image);


            }
        }
    }


}
