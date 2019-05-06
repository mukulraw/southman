package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.TillSubCategory2.Datum;
import com.sc.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
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

public class MeansCategory extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    MAdapter adapter;

    List<Datum> list;

    ProgressBar bar;

    String id;

    TextView title;

    ImageView search , home;

    ConnectionDetector cd;

    String catName , base;

    LinearLayout linear;


    ImageView notification, perks2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_category);

        cd = new ConnectionDetector(MeansCategory.this);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MeansCategory.this, Notification.class);
                startActivity(i);
            }
        });

        perks2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MeansCategory.this , Perks.class);
                startActivity(intent);
            }
        });

        title.setText(getIntent().getStringExtra("text"));
        catName = getIntent().getStringExtra("catname");

        Log.d("catname" , catName);

        grid = findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new MAdapter(this, list);

        manager = new GridLayoutManager(getApplicationContext(), 3);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                return Integer.parseInt(adapter.getSpace(i));

            }
        });

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        bar = findViewById(R.id.progress);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MeansCategory.this, Search.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MeansCategory.this, MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });

        id = getIntent().getStringExtra("id");

        if (cd.isConnectingToInternet()) {

            bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            base = b.baseurl;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<TillSubCatBean> call = cr.tillcat2(id , SharePreferenceUtils.getInstance().getString("location"));

            call.enqueue(new Callback<TillSubCatBean>() {
                @Override
                public void onResponse(Call<TillSubCatBean> call, Response<TillSubCatBean> response) {

                    if (Objects.equals(response.body().getStatus(), "1")) {

                        adapter.setgrid(response.body().getData());
                        linear.setVisibility(View.GONE);
                    }
                    else
                    {
                        linear.setVisibility(View.VISIBLE);
                    }

                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<TillSubCatBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);

                }
            });


        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    public class MAdapter extends RecyclerView.Adapter<MAdapter.MyViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();


        public MAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;


        }

        public String getSpace(int position)
        {
            return list.get(position).getSpace();
        }

        @NonNull
        @Override
        public MAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.category_list_model, viewGroup, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MAdapter.MyViewHolder myViewHolder, int i) {


            final Datum item = list.get(i);

          //  myViewHolder.name.setText(item.getSubcatName());


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "bigboss/admin2/upload/sub_cat/" + item.getImageUrl(), myViewHolder.imageView, options);


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (catName.equals("vouchers store") || catName.equals("redeem store"))
                    {
                        Intent i = new Intent(context, SubCat2.class);
                        i.putExtra("id", item.getId());
                        i.putExtra("text", item.getSubcatName());
                        i.putExtra("catname", catName);
                        context.startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(context, CollerTshirt.class);
                        i.putExtra("id", item.getId());
                        i.putExtra("text", item.getSubcatName());
                        i.putExtra("catname", catName);
                        context.startActivity(i);
                    }



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

           // TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.tshirt);

                //name = itemView.findViewById(R.id.name);


            }
        }
    }


}
