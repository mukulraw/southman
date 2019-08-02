package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.appyvet.materialrangebar.RangeBar;
import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.TillCategory3POJO.ProductInfo;
import com.sc.bigboss.bigboss.TillCategory3POJO.ShopProductBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CollerTshirt extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView grid;

    private GridLayoutManager manager;

    private CollerAdapter adapeter;


    private List<ProductInfo> list;

    private List<ProductInfo> filteredList;

    private List<ProductInfo> sortedList;

    private String id;

    private ProgressBar bar;

    private TextView title;

    private TextView sort;
    private TextView filter;

    boolean isPrice = false, isSize = false;

    private boolean isFilter = false;

    private ImageView search;
    private ImageView home;

    private ConnectionDetector cd;

    private String catName;
    private String base;

    private LinearLayout linear;
    private LinearLayout bottom;

    private ImageView notification;
    private ImageView perks2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coller_tshirt);

        cd = new ConnectionDetector(CollerTshirt.this);

        id = getIntent().getStringExtra("id");
        catName = getIntent().getStringExtra("catname");

        Log.d("catname " , catName);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);
        bottom = findViewById(R.id.bottom);


        sort = findViewById(R.id.sort);
        filter = findViewById(R.id.filter);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(v -> {


            Intent i = new Intent(CollerTshirt.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(CollerTshirt.this , Perks.class);
            startActivity(intent);
        });

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());


        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        sortedList = new ArrayList<>();

        adapeter = new CollerAdapter(this, list);


        grid = findViewById(R.id.grid);

        title = findViewById(R.id.title);

        title.setText(getIntent().getStringExtra("text"));

        manager = new GridLayoutManager(this, 1);


        bar = findViewById(R.id.progress);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);


        if (catName.equals("till day sale")) {
            search.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.GONE);
        }


        search.setOnClickListener(v -> {


            Intent i = new Intent(CollerTshirt.this, Search.class);
            startActivity(i);
        });

        home.setOnClickListener(v -> {
            Intent i = new Intent(CollerTshirt.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
        });

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

            Log.d("asdasdasd" , id);
            Log.d("asdlosad" , SharePreferenceUtils.getInstance().getString("location"));

            String f;

            if (catName.equals("food & drinks"))
            {
                f = "food";
            }
            else
            {
                f = "asd";
            }

            Call<ShopProductBean> call = cr.shopproduct(id, SharePreferenceUtils.getInstance().getString("location") , f);

            call.enqueue(new Callback<ShopProductBean>() {
                @Override
                public void onResponse(Call<ShopProductBean> call, Response<ShopProductBean> response) {

                    assert response.body() != null;
                    if (response.body().getProductInfo().size() > 0)
                    {
                        list.clear();

                        list = response.body().getProductInfo();


                            Log.d("ssii" , String.valueOf(list.size()));

                            if (list.size() > 0)
                            {
                                bottom.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                bottom.setVisibility(View.GONE);
                            }


                            grid.setLayoutManager(manager);

                            grid.setAdapter(adapeter);
                            adapeter.setgrid(list);

                        linear.setVisibility(View.GONE);

                    }else
                    {
                        linear.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.GONE);

                    }



                    bar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<ShopProductBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);

                }
            });


        } else {

            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        sort.setOnClickListener(v -> {

            final Dialog dialog = new Dialog(CollerTshirt.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.sort_dialog_layout);
            dialog.setCancelable(true);
            dialog.show();

            final RadioGroup group = dialog.findViewById(R.id.group);
            TextView res = dialog.findViewById(R.id.reset);
            final TextView sor = dialog.findViewById(R.id.sort);

            res.setOnClickListener(v14 -> {

                if (isFilter) {

                    adapeter.setgrid(filteredList);


                } else {

                    adapeter.setgrid(list);


                }

                dialog.dismiss();

            });

            sor.setOnClickListener(v13 -> {

                int iidd = group.getCheckedRadioButtonId();

                if (iidd > -1) {

                    Log.d("sort", "1");

                    if (iidd == R.id.l2h) {

                        sortedList.clear();

                        Log.d("sort", "2");

                        if (isFilter) {
                            Log.d("sort", "3");
                            List<ProductInfo> fl = new ArrayList<>();
                            fl = filteredList;
                            sortedList.addAll(fl);
                        } else {
                            Log.d("sort", "4");
                            List<ProductInfo> fl = new ArrayList<>();
                            fl = list;
                            sortedList.addAll(fl);
                        }


                        Collections.sort(sortedList, (o1, o2) -> {
                            Log.d("sort", "5");
                            if (o2.getDiscountPrice() != null && o1.getDiscountPrice() != null) {
                                return Float.valueOf(o1.getDiscountPrice()).compareTo(Float.valueOf(o2.getDiscountPrice()));
                            } else if (o2.getDiscountPrice() == null && o1.getDiscountPrice() != null) {
                                return Float.valueOf(o1.getDiscountPrice()).compareTo(Float.valueOf(o2.getPrice()));
                            } else if (o2.getDiscountPrice() != null && o1.getDiscountPrice() == null) {
                                return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getDiscountPrice()));
                            } else {
                                return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                            }
                        });


                        Log.d("sort", "6");
                        adapeter.setgrid(sortedList);


                        dialog.dismiss();

                    } else if (iidd == R.id.h2l) {
                        Log.d("sort", "7");
                        sortedList.clear();

                        if (isFilter) {
                            Log.d("sort", "8");
                            sortedList.addAll(filteredList);
                        } else {
                            Log.d("sort", "9");
                            sortedList.addAll(list);
                        }


                        Collections.sort(sortedList, (o2, o1) -> {
                            Log.d("sort", "10");
                            if (o2.getDiscountPrice() != null && o1.getDiscountPrice() != null) {
                                return Float.valueOf(o1.getDiscountPrice()).compareTo(Float.valueOf(o2.getDiscountPrice()));
                            } else if (o2.getDiscountPrice() == null && o1.getDiscountPrice() != null) {
                                return Float.valueOf(o1.getDiscountPrice()).compareTo(Float.valueOf(o2.getPrice()));
                            } else if (o2.getDiscountPrice() != null && o1.getDiscountPrice() == null) {
                                return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getDiscountPrice()));
                            } else {
                                return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                            }
                        });

                        Log.d("sort", "12");
                        adapeter.setgrid(sortedList);


                        dialog.dismiss();

                    }

                } else {
                    Log.d("sort", "13");
                    Toast.makeText(CollerTshirt.this, "Please select a Sorting type", Toast.LENGTH_SHORT).show();
                }

            });

        });

        filter.setOnClickListener(v -> {

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

            range.setOnRangeBarChangeListener((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {

                min[0] = leftPinValue;
                max[0] = rightPinValue;

                prii.setText("Price: " + leftPinValue + " - " + rightPinValue);

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


            res.setOnClickListener(v12 -> {


                adapeter.setgrid(list);


                isFilter = false;
                dialog1.dismiss();

            });


            fil.setOnClickListener(v1 -> {

                List<String> fl = fadapter.getChecked();

                filteredList.clear();

                for (int i = 0; i < list.size(); i++) {

                    for (int j = 0; j < fl.size(); j++) {

                        if (list.get(i).getDiscountPrice() != null) {
                            if (fl.get(j).equals(list.get(i).getSize()) && Float.parseFloat(list.get(i).getPrice()) > Float.parseFloat(min[0]) && Float.parseFloat(list.get(i).getPrice()) <= Float.parseFloat(max[0])) {
                                filteredList.add(list.get(i));
                            }

                        } else {
                            if (fl.get(j).equals(list.get(i).getSize()) && Float.parseFloat(list.get(i).getDiscountPrice()) > Float.parseFloat(min[0]) && Float.parseFloat(list.get(i).getDiscountPrice()) <= Float.parseFloat(max[0])) {
                                filteredList.add(list.get(i));
                            }

                        }


                    }

                }


                adapeter.setgrid(filteredList);


                isFilter = true;
                dialog1.dismiss();

            });


        });

        count = findViewById(R.id.count);

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));


    }

    private BroadcastReceiver singleReceiver;
    private TextView count;


    public class CollerAdapter extends RecyclerView.Adapter<CollerAdapter.MyViewHolder> {

        final Context context;

        List<ProductInfo> list;

        CollerAdapter(Context context, List<ProductInfo> list) {

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

            if (item.getDiscountPrice() != null && !catName.equals("shop by shop"))
            {
                myViewHolder.prices.setText(Html.fromHtml("\u20B9" + item.getDiscountPrice() + "  <strike>\u20B9" + item.getPrice() + "</strike>"));
            }
            else
            {
                myViewHolder.prices.setText("\u20B9" + item.getPrice());
            }


            myViewHolder.color.setText(item.getColor());



/*
            if (item.getNegotiable().equals("no")){

                myViewHolder.negotiable.setText("No");

                myViewHolder.negotiable.setTextColor(Color.RED);


            }else {
                myViewHolder.negotiable.setText("Yes");

                myViewHolder.negotiable.setTextColor(Color.parseColor("#4CAF50"));

            }
*/


            /*if (catName.equals("shop by shop"))
            {
                myViewHolder.negititle.setVisibility(View.VISIBLE);
                myViewHolder.negotiable.setVisibility(View.VISIBLE);
            }
            else
            {*/
                myViewHolder.negititle.setVisibility(View.GONE);
                myViewHolder.negotiable.setVisibility(View.GONE);
            /*}*/


            /*DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/products/" + item.getProductImage(), myViewHolder.image, options);
*/

            Glide.with(context).load(base + "southman/admin2/upload/products/" + item.getProductImage()).into(myViewHolder.image);


            myViewHolder.itemView.setOnClickListener(v -> {

                Intent i1 = new Intent(context, SingleProduct.class);

                i1.putExtra("id", item.getId());
                i1.putExtra("catname", catName);
                i1.putExtra("text", item.getProductTitle());

                context.startActivity(i1);
            });

        }

        void setgrid(List<ProductInfo> list) {

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final TextView name;
            final TextView brand;
            final TextView size;
            final TextView prices;
            final TextView color;
            final TextView negotiable;
            final LinearLayout negititle;

            final ImageView image;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                brand = itemView.findViewById(R.id.brand);
                size = itemView.findViewById(R.id.size);
                prices = itemView.findViewById(R.id.price);
                color = itemView.findViewById(R.id.color);
                negotiable = itemView.findViewById(R.id.negotiable);
                image = itemView.findViewById(R.id.image);
                negititle = itemView.findViewById(R.id.negotitle);


            }
        }
    }


    public class CollerAdapter2 extends RecyclerView.Adapter<CollerAdapter2.MyViewHolder> {

        final Context context;

        List<ProductInfo> list;

        public CollerAdapter2(Context context, List<ProductInfo> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public CollerAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.coller_list_model2, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CollerAdapter2.MyViewHolder myViewHolder, int i) {


            final ProductInfo item = list.get(i);

            myViewHolder.name.setText(item.getProductTitle());


            String sub = item.getSubTitle();

            Log.d("sub" , sub);

            sub = sub.replace("<p>" , "");
            sub = sub.replace("</p>" , "<br>");


            StringBuilder sb = new StringBuilder(sub);

            sb.delete(sub.length() - 4 , sub.length());

            sub = sb.toString();

            Log.d("sub" , sub);



            //myViewHolder.brand.setText(Html.fromHtml(sub.replace("<p>" , "\n\n")));
            myViewHolder.brand.setText(Html.fromHtml(sub));


            if (item.getDiscountPrice() != null)
            {
                myViewHolder.price.setText(Html.fromHtml("\u20B9" + item.getDiscountPrice() + "  <strike>\u20B9" + item.getPrice() + "</strike>"));
            }
            else
            {
                myViewHolder.price.setText("\u20B9" + item.getPrice());
            }


            myViewHolder.color.setText(item.getProductCode());



/*


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/products/" + item.getProductImage(), myViewHolder.image, options);
*/


            Glide.with(context).load(base + "southman/admin2/upload/products/" + item.getProductImage()).into(myViewHolder.image);


            myViewHolder.order.setOnClickListener(view -> {

                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                mobile.setText(item.getPhoneNumber());

                code.setText(item.getProductCode());

                final String ph = item.getPhoneNumber();
                final String co = item.getProductCode();

                watshp.setOnClickListener(v -> {


                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + ph + "&text=Product Code : " + co));
                    startActivity(browserIntent);
                    dialog.dismiss();


                });

                call.setOnClickListener(v -> {


                    try {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+" + ph));
                        startActivity(intent);
                        dialog.dismiss();


                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                });

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

            final TextView name;
            final TextView brand;
            final TextView color;
            final TextView price;

            final ImageView image;

            final Button order;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                brand = itemView.findViewById(R.id.brand);
                order = itemView.findViewById(R.id.order);
                color = itemView.findViewById(R.id.color);
                image = itemView.findViewById(R.id.image);
                price = itemView.findViewById(R.id.price);


            }
        }
    }


    class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

        final Context context;

        List<String> flist;

        final List<String> checked = new ArrayList<>();

        FilterAdapter(Context context, List<String> flist) {
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

            viewHolder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {

                    checked.add(item);

                } else {

                    checked.remove(item);

                }

            });

        }

        List<String> getChecked() {
            return checked;
        }

        @Override
        public int getItemCount() {
            return flist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final CheckBox check;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                check = itemView.findViewById(R.id.check);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));

    }
}
