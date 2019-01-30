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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.SearchPojo.Datum;
import com.example.bigboss.bigboss.SearchPojo.SearchBean;
import com.example.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
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

public class Search extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    GridLayoutManager manager;

    SearchAdapter adapter;

    List<Datum>list;

    ProgressBar bar;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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


        bar = findViewById(R.id.progress);
        search = findViewById(R.id.s);
        grid = findViewById(R.id.grid);

        list = new ArrayList<>();
        adapter = new SearchAdapter(this , list);

        manager = new GridLayoutManager(this , 1);
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        String ss = search.getText().toString();

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<SearchBean> call = cr.search(ss , SharePreferenceUtils.getInstance().getString("location"  ) );

        call.enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {

                if (Objects.equals(response.body().getStatus() , "1")){

                    adapter.setgrid(response.body().getData());

                }else {

                    Toast.makeText(Search.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });




    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.My>{

        Context context;

        List<Datum>list = new ArrayList<>();

        public SearchAdapter(Context context ,  List<Datum>list ){

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public SearchAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.search_list_model , viewGroup , false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchAdapter.My my, int i) {


            final Datum item = list.get(i);

            my.name.setText(item.getProductTitle());
            my.brand.setText(item.getBrand());
            my.size.setText(item.getSize());
            my.prices.setText(item.getPrice());
            my.color.setText(item.getColor());
            my.negotiable.setText(item.getNegotiable());


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getProductImage(), my.image, options);

            my.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(context , SingleProduct.class);

                    i.putExtra("id"  , item.getId());

                    i.putExtra("text"  , item.getProductTitle());
                    context.startActivity(i);
                }
            });


        }

        public void setgrid( List<Datum>list ){

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class My extends RecyclerView.ViewHolder {

            TextView name, brand, size, prices, color, negotiable;

            ImageView image;



            public My(@NonNull View itemView) {
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
