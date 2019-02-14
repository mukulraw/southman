package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.appyvet.materialrangebar.RangeBar;
import com.example.bigboss.bigboss.TillCategory3POJO.ProductInfo;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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

    List<ProductInfo> filteredList;

    List<ProductInfo> sortedList;

    String id;

    ProgressBar bar;

    TextView title;

    TextView sort, filter;

    boolean isPrice = false, isSize = false;

    boolean isFilter = false;

    ImageView search;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coller_tshirt);

        cd = new ConnectionDetector(CollerTshirt.this);

        id = getIntent().getStringExtra("id");

        toolbar = findViewById(R.id.toolbar);


        sort = findViewById(R.id.sort);
        filter = findViewById(R.id.filter);


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
        filteredList = new ArrayList<>();
        sortedList = new ArrayList<>();

        adapeter = new CollerAdapter(this, list);

        grid = findViewById(R.id.grid);

        title = findViewById(R.id.title);

        title.setText(getIntent().getStringExtra("text"));

        manager = new GridLayoutManager(this, 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapeter);

        bar = findViewById(R.id.progress);

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(CollerTshirt.this, Search.class);
                startActivity(i);
            }
        });

        if (cd.isConnectingToInternet()) {

            bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<ShopProductBean> call = cr.shopproduct(id, SharePreferenceUtils.getInstance().getString("location"));

            call.enqueue(new Callback<ShopProductBean>() {
                @Override
                public void onResponse(Call<ShopProductBean> call, Response<ShopProductBean> response) {

                    list.clear();

                    list = response.body().getProductInfo();

                    adapeter.setgrid(list);
                    bar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<ShopProductBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);

                }
            });


        } else {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(CollerTshirt.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sort_dialog_layout);
                dialog.setCancelable(true);
                dialog.show();

                final RadioGroup group = dialog.findViewById(R.id.group);
                TextView res = dialog.findViewById(R.id.reset);
                final TextView sor = dialog.findViewById(R.id.sort);

                res.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isFilter) {
                            adapeter.setgrid(filteredList);
                        } else {
                            adapeter.setgrid(list);
                        }

                        dialog.dismiss();

                    }
                });

                sor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int iidd = group.getCheckedRadioButtonId();

                        if (iidd > -1) {

                            if (iidd == R.id.l2h) {

                                sortedList.clear();

                                if (isFilter) {
                                    sortedList = filteredList;
                                } else {
                                    sortedList = list;
                                }


                                Collections.sort(sortedList, new Comparator<ProductInfo>() {
                                    @Override
                                    public int compare(ProductInfo o1, ProductInfo o2) {
                                        return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                                    }
                                });

                                adapeter.setgrid(sortedList);
                                dialog.dismiss();

                            } else if (iidd == R.id.h2l) {
                                sortedList.clear();

                                if (isFilter) {
                                    sortedList = filteredList;
                                } else {
                                    sortedList = list;
                                }


                                Collections.sort(sortedList, new Comparator<ProductInfo>() {
                                    @Override
                                    public int compare(ProductInfo o2, ProductInfo o1) {
                                        return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                                    }
                                });

                                adapeter.setgrid(sortedList);
                                dialog.dismiss();

                            }

                        } else {
                            Toast.makeText(CollerTshirt.this, "Please select a Sorting type", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1 = new Dialog(CollerTshirt.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.price_filter_dialog);
                dialog1.setCancelable(true);
                dialog1.show();


                final TextView prii = dialog1.findViewById(R.id.prii);
                RangeBar range = dialog1.findViewById(R.id.range);
                RecyclerView fgrid = dialog1.findViewById(R.id.grid);
                GridLayoutManager fmanager = new GridLayoutManager(CollerTshirt.this, 1);
                TextView fil = dialog1.findViewById(R.id.filter);
                TextView res = dialog1.findViewById(R.id.reset);

                final String[] min = {"50"};
                final String[] max = {"5000"};

                range.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                        min[0] = leftPinValue;
                        max[0] = rightPinValue;

                        prii.setText("Price: " + leftPinValue + " - " + rightPinValue);

                    }
                });


                List<String> flist = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    flist.add(list.get(i).getSize());
                }

                Log.d("flist", TextUtils.join(",", flist));

                HashSet<String> hashSet = new HashSet<>();
                hashSet.addAll(flist);
                flist.clear();
                flist.addAll(hashSet);


                final FilterAdapter fadapter = new FilterAdapter(CollerTshirt.this, flist);
                fgrid.setAdapter(fadapter);
                fgrid.setLayoutManager(fmanager);


                res.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adapeter.setgrid(list);
                        isFilter = false;
                        dialog1.dismiss();

                    }
                });


                fil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        List<String> fl = fadapter.getChecked();

                        filteredList.clear();

                        for (int i = 0; i < list.size(); i++) {

                            for (int j = 0; j < fl.size(); j++) {

                                if (fl.get(j).equals(list.get(i).getSize()) && Float.parseFloat(list.get(i).getPrice()) > Float.parseFloat(min[0]) && Float.parseFloat(list.get(i).getPrice()) < Float.parseFloat(max[0])) {
                                    filteredList.add(list.get(i));
                                }

                            }

                        }

                        adapeter.setgrid(filteredList);
                        isFilter = true;
                        dialog1.dismiss();

                    }
                });


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

            if (item.getDiscountPrice() != null)
            {

                myViewHolder.prices.setText(Html.fromHtml("<strike>\u20B9" + item.getDiscountPrice() + "</strike>  \u20B9" + item.getPrice()));
            }
            else
            {
                myViewHolder.prices.setText("\u20B9" + item.getPrice());
            }


            myViewHolder.color.setText(item.getColor());



            if (item.getNegotiable().equals("no")){

                myViewHolder.negotiable.setText("No");

                myViewHolder.negotiable.setTextColor(Color.RED);


            }else {
                myViewHolder.negotiable.setText("Yes");

                myViewHolder.negotiable.setTextColor(Color.parseColor("#4CAF50"));

            }

            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(item.getProductImage(), myViewHolder.image, options);


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, SingleProduct.class);

                    i.putExtra("id", item.getId());

                    i.putExtra("text", item.getProductTitle());

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


    class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

        Context context;

        List<String> flist = new ArrayList<>();

        List<String> checked = new ArrayList<>();

        public FilterAdapter(Context context, List<String> flist) {
            this.context = context;
            this.flist = flist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.filter_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            viewHolder.setIsRecyclable(false);

            final String item = flist.get(i);

            viewHolder.check.setText(item);

            viewHolder.check.setChecked(true);

            checked.add(item);

            viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        checked.add(item);

                    } else {

                        checked.remove(item);

                    }

                }
            });

        }

        public List<String> getChecked() {
            return checked;
        }

        @Override
        public int getItemCount() {
            return flist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CheckBox check;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                check = itemView.findViewById(R.id.check);

            }
        }
    }


}
