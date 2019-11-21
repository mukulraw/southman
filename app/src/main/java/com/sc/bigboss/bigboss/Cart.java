package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.cartPOJO.Datum;
import com.sc.bigboss.bigboss.cartPOJO.cartBean;
import com.sc.bigboss.bigboss.vouchersPOJO.vouchersBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Cart extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar bar;
    String base;
    TextView btotal, bproceed, clear;

    int amm = 0;

    View bottom;

    CartAdapter adapter;

    GridLayoutManager manager;

    RecyclerView grid;

    List<Datum> list;

    String client, txn;

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        client = getIntent().getStringExtra("client");

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar3);
        bar = findViewById(R.id.progressBar3);
        bottom = findViewById(R.id.cart_bottom);
        btotal = findViewById(R.id.textView9);
        bproceed = findViewById(R.id.textView10);
        grid = findViewById(R.id.grid);
        clear = findViewById(R.id.textView12);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Cart");

        adapter = new CartAdapter(list, this);

        manager = new GridLayoutManager(this, 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bar.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                base = b.baseurl;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<vouchersBean> call = cr.clearCart(SharePreferenceUtils.getInstance().getString("userid"));

                call.enqueue(new Callback<vouchersBean>() {
                    @Override
                    public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                        if (response.body().getStatus().equals("1")) {
                            finish();
                        }

                        Toast.makeText(Cart.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<vouchersBean> call, Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });


            }
        });

        bproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txn = String.valueOf(System.currentTimeMillis());
                Intent intent = new Intent(Cart.this, StatusActivity.class);
                intent.putExtra("client", client);
                intent.putExtra("amount", String.valueOf(amm));
                intent.putExtra("txn", txn);
                intent.putExtra("status", "success");
                startActivity(intent);


                /*try {

                    txn = String.valueOf(System.currentTimeMillis());

                    Uri uri = new Uri.Builder()
                            .scheme("upi")
                            .authority("pay")
                            .appendQueryParameter("pa", "southman@sbi")
                            .appendQueryParameter("pn", "South Man")
                            .appendQueryParameter("mc", "BCR2DN6T6WEP3JDV")
                            .appendQueryParameter("tr", txn)
                            .appendQueryParameter("tn", "Voucher Pay")
                            .appendQueryParameter("am", String.valueOf(amm))
                            .appendQueryParameter("cu", "INR")
                            .appendQueryParameter("url", "https://southman.in")
                            .build();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                    startActivityForResult(intent, TEZ_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Cart.this, "You don't have Google Pay app installed", Toast.LENGTH_SHORT).show();
                }*/




            }
        });

        loadCart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCart();

    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

        List<Datum> list = new ArrayList<>();
        Context context;
        LayoutInflater inflater;

        CartAdapter(List<Datum> list, Context context) {
            this.context = context;
            this.list = list;
        }

        void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.prod_list_model4, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i1) {

            Datum item = list.get(i1);

            //viewHolder.setIsRecyclable(false);

            viewHolder.benefits.removeAllViews();

            for (int i = 0; i < item.getBenefits().size(); i++) {

                View view = inflater.inflate(R.layout.benefit_layout, null);

                ImageView type = view.findViewById(R.id.textView4);
                TextView text = view.findViewById(R.id.textView6);


                if (item.getBenefits().get(i).getType().equals("CASH")) {
                    type.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_money));
                    text.setText("Open Cash Rewards worth \u20B9 " + item.getBenefits().get(i).getValue() + " x " + item.getQuantity());
                } else {
                    type.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_card));
                    text.setText(item.getBenefits().get(i).getClient() + " Digital Coupon Cash worth \u20B9 " + item.getBenefits().get(i).getValue() + " x " + item.getQuantity());
                }

                viewHolder.benefits.addView(view);

            }


            viewHolder.quantity.setText(item.getQuantity());

            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int q = Integer.parseInt(item.getQuantity());

                    if (q < 99) {

                        q++;

                        viewHolder.quantity.setText(String.valueOf(q));

                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        base = b.baseurl;

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<vouchersBean> call = cr.updateCart(item.getPid(), String.valueOf(q), item.getPrice());

                        call.enqueue(new Callback<vouchersBean>() {
                            @Override
                            public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                                if (response.body().getStatus().equals("1")) {
                                    loadCart();
                                }

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<vouchersBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    }


                }
            });


            viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int q = Integer.parseInt(item.getQuantity());

                    if (q > 1) {

                        q--;

                        viewHolder.quantity.setText(String.valueOf(q));

                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();

                        base = b.baseurl;

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<vouchersBean> call = cr.updateCart(item.getPid(), String.valueOf(q), item.getPrice());

                        call.enqueue(new Callback<vouchersBean>() {
                            @Override
                            public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                                if (response.body().getStatus().equals("1")) {
                                    loadCart();
                                }

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<vouchersBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    }


                }
            });

            //viewHolder.buy.setQuantity(Integer.parseInt(item.getQuantity()));

            viewHolder.viewBenefits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (viewHolder.benefits.getVisibility() == View.VISIBLE) {
                        viewHolder.benefits.setVisibility(View.GONE);
                    } else {
                        viewHolder.benefits.setVisibility(View.VISIBLE);
                    }

                }
            });

            /*viewHolder.buy.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
                @Override
                public void onQuantityChanged(int oldQuantity, int value, boolean programmatically) {

                    Log.d("value" , String.valueOf(value));

                    if (programmatically)
                    {

                    }

                }

                @Override
                public void onLimitReached() {

                }
            });*/



            /*viewHolder.buy.addStepCallback((i, b1) -> {


                if (b1)
                {

                }


            });*/


            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    bar.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    base = b.baseurl;

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<vouchersBean> call = cr.deleteCart(item.getPid());

                    call.enqueue(new Callback<vouchersBean>() {
                        @Override
                        public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                            if (response.body().getStatus().equals("1")) {
                                loadCart();
                            }

                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<vouchersBean> call, Throwable t) {
                            bar.setVisibility(View.GONE);
                            Log.d("error", t.toString());
                        }
                    });

                }
            });

/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/sub_cat1/" + item.getImageUrl(), myViewHolder.imageView, options);
*/

            Glide.with(context).load(base + "southman/admin2/upload/products3/" + item.getProductImage()).into(viewHolder.imageView);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            LinearLayout benefits;

            Button viewBenefits;

            ImageButton delete;

            Button add, remove;
            TextView quantity;

            //QuantityView buy;
            // TextView name;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image);
                //buy = itemView.findViewById(R.id.play);
                benefits = itemView.findViewById(R.id.benefits);
                viewBenefits = itemView.findViewById(R.id.view);
                delete = itemView.findViewById(R.id.delete);

                add = itemView.findViewById(R.id.increment);
                remove = itemView.findViewById(R.id.decrement);
                quantity = itemView.findViewById(R.id.display);

                //buy.setSideTapEnabled(true);

                //name = itemView.findViewById(R.id.name);


            }
        }
    }


    void loadCart() {
        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        base = b.baseurl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Log.d("userid", SharePreferenceUtils.getInstance().getString("userid"));
        Log.d("client", client);

        Call<cartBean> call = cr.getCart(SharePreferenceUtils.getInstance().getString("userid"), client);
        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getData().size() > 0) {


                    adapter.setgrid(response.body().getData());

                    amm = Integer.parseInt(response.body().getTotal());


                    btotal.setText("Total: Rs. " + response.body().getTotal());

                    bottom.setVisibility(View.VISIBLE);
                } else {
                    adapter.setgrid(response.body().getData());
                    bottom.setVisibility(View.GONE);
                    finish();
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<cartBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE && resultCode == RESULT_OK) {
            // Process based on the data in response.


            String res = data.getStringExtra("Status");

            Log.d("result", res);

            if (res.equals("SUCCESS")) {
                Intent intent = new Intent(Cart.this, StatusActivity.class);
                intent.putExtra("client", client);
                intent.putExtra("amount", String.valueOf(amm));
                intent.putExtra("txn", txn);
                intent.putExtra("status", "success");
                startActivity(intent);
            } else {
                Intent intent = new Intent(Cart.this, StatusActivity.class);
                intent.putExtra("client", client);
                intent.putExtra("amount", String.valueOf(amm));
                intent.putExtra("txn", txn);
                intent.putExtra("status", "failure");
                startActivity(intent);
            }


        }
    }


}
