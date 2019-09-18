package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.SearchPojo.Datum;
import com.sc.bigboss.bigboss.SearchPojo.SearchBean;
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

    private Toolbar toolbar;

    private RecyclerView grid;

    private GridLayoutManager manager;

    private SearchAdapter adapter;

    private List<Datum> list;

    private ProgressBar bar;

    private EditText search;

    private LinearLayout linear;

    private String base;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        bar = findViewById(R.id.progress);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        search = findViewById(R.id.s);

        linear = findViewById(R.id.linear);

        grid = findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new SearchAdapter(this, list);

        manager = new GridLayoutManager(this, 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String ss = search.getText().toString();

                bar.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<SearchBean> call = cr.search(ss, SharePreferenceUtils.getInstance().getString("location"));

                Log.d("location" ,SharePreferenceUtils.getInstance().getString("location") );

                call.enqueue(new Callback<SearchBean>() {
                    @Override
                    public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {

                        try {

                            if (Objects.equals(response.body().getStatus(), "1")) {

                                if (response.body().getData().size()>0){

                                    adapter.setgrid(response.body().getData());

                                    Log.d("response" , "response");
                                    linear.setVisibility(View.GONE);

                                }else {
                                    adapter.setgrid(response.body().getData());
                                    linear.setVisibility(View.VISIBLE);
                                }


                            }
                            else {
                                adapter.setgrid(response.body().getData());
                                linear.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                            Log.d("asdsad" , e.toString());
                        }


                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<SearchBean> call, Throwable t) {

                        Log.d("failure" , t.toString());
                        adapter.setgrid(new ArrayList<>());
                        linear.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);

                    }
                });

                return true;
            }
            return false;
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String ss = s.toString();

                if (ss.length() > 0)
                {
                    bar.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<SearchBean> call = cr.search(ss, SharePreferenceUtils.getInstance().getString("location"));

                    Log.d("location" ,SharePreferenceUtils.getInstance().getString("location") );

                    call.enqueue(new Callback<SearchBean>() {
                        @Override
                        public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {

                            try {

                                if (Objects.equals(Objects.requireNonNull(response.body()).getStatus(), "1")) {

                                    if (response.body().getData().size()>0){

                                        adapter.setgrid(response.body().getData());

                                        Log.d("response" , "response");
                                        linear.setVisibility(View.GONE);

                                    }else {
                                        adapter.setgrid(response.body().getData());
                                        linear.setVisibility(View.VISIBLE);
                                    }


                                }
                                else {
                                    adapter.setgrid(response.body().getData());
                                    linear.setVisibility(View.VISIBLE);
                                }

                            } catch (Exception e) {

                                e.printStackTrace();
                            }


                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<SearchBean> call, Throwable t) {

                            adapter.setgrid(new ArrayList<>());
                            linear.setVisibility(View.VISIBLE);
                            bar.setVisibility(View.GONE);

                        }
                    });
                }
                else
                {
                    adapter.setgrid(new ArrayList<>());
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.My> {

        final Context context;

        List<Datum> list;

        SearchAdapter(Context context, List<Datum> list) {

            this.context = context;

            this.list = list;

        }


        @NonNull
        @Override
        public SearchAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.coller_list_model, viewGroup, false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchAdapter.My my, int i) {


            final Datum item = list.get(i);

            my.name.setText(item.getProductTitle());
            my.brand.setText(item.getBrand());
            my.size.setText(item.getSize());

            if (item.getDiscountPrice() != null)
            {
                my.prices.setText(Html.fromHtml("\u20B9" + item.getDiscountPrice() + "  <strike>\u20B9" + item.getPrice() + "</strike>"));
            }
            else
            {
                my.prices.setText("\u20B9" + item.getPrice());
            }
           // my.prices.setText(item.getPrice());
            my.color.setText(item.getColor());
            /*if (item.getNegotiable().equals("no")){

                my.negotiable.setText("No");

                my.negotiable.setTextColor(Color.RED);


            }else {
                my.negotiable.setText("Yes");

                my.negotiable.setTextColor(Color.parseColor("#4CAF50"));

            }*/



            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            base = "http://southman.in/";


            loader.displayImage("http://southman.in/southman/admin2/upload/products/" + item.getProductImage(), my.image, options);

            my.itemView.setOnClickListener(v -> {

                Intent i1 = new Intent(context, SingleProduct.class);

                i1.putExtra("id", item.getId());

                i1.putExtra("text", item.getProductTitle());

                context.startActivity(i1);
            });


        }

        void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class My extends RecyclerView.ViewHolder {

            final TextView name;
            final TextView brand;
            final TextView size;
            final TextView prices;
            final TextView color;
            final TextView negotiable;
            final ImageView image;


            My(@NonNull View itemView) {
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
