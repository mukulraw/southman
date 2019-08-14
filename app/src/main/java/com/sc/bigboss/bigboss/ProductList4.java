package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.cartPOJO.cartBean;
import com.sc.bigboss.bigboss.vouchersPOJO.Datum;
import com.sc.bigboss.bigboss.vouchersPOJO.vouchersBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.dionsegijn.steppertouch.StepperTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProductList4 extends AppCompatActivity {


    private Toolbar toolbar;

    private RecyclerView grid;

    private GridLayoutManager manager;

    private MAdapter adapter;

    private List<Datum> list;

    private ProgressBar bar;

    private String id;

    private TextView title;

    private ImageView search;
    private ImageView home;

    private ConnectionDetector cd;

    private String catName;
    private String base;
    private String client;

    private LinearLayout linear;

    private ImageView notification;
    private ImageView perks2;

    TextView bquantity , btotal , bproceed;

    int amm = 0;

    View bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list4);

        cd = new ConnectionDetector(ProductList4.this);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);
        bottom = findViewById(R.id.cart_bottom);
        bquantity = findViewById(R.id.textView7);
        btotal = findViewById(R.id.textView9);
        bproceed = findViewById(R.id.textView10);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());



        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(v -> {


            Intent i = new Intent(ProductList4.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(ProductList4.this , Perks.class);
            startActivity(intent);
        });


        title.setText(getIntent().getStringExtra("text"));
        catName = getIntent().getStringExtra("catname");
        client = getIntent().getStringExtra("client");

        Log.d("catname" , catName);

        grid = findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new MAdapter(this, list);

        manager = new GridLayoutManager(getApplicationContext(), 1);



        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        bar = findViewById(R.id.progress);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);

        search.setOnClickListener(v -> {


            Intent i = new Intent(ProductList4.this, Search.class);
            startActivity(i);
        });

        home.setOnClickListener(v -> {
            Intent i = new Intent(ProductList4.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
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

            Log.d("id" , id);
            Log.d("location" , SharePreferenceUtils.getInstance().getString("location"));

            Call<vouchersBean> call = cr.getProd3(id , SharePreferenceUtils.getInstance().getString("location"));

            call.enqueue(new Callback<vouchersBean>() {
                @Override
                public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                    if (Objects.equals(Objects.requireNonNull(response.body()).getStatus(), "1")) {

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
                public void onFailure(Call<vouchersBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);

                }
            });


        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

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


        bproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductList4.this , Cart.class);
                intent.putExtra("client" , client);
                startActivity(intent);

                /*
                Intent intent = new Intent(ProductList4.this, WebViewActivity.class);
                intent.putExtra(AvenuesParams.ACCESS_CODE, "AVVG86GG67BT51GVTB");
                intent.putExtra(AvenuesParams.MERCHANT_ID, "225729");
                intent.putExtra(AvenuesParams.ORDER_ID, String.valueOf(System.currentTimeMillis()));
                intent.putExtra(AvenuesParams.CURRENCY, "INR");
                intent.putExtra(AvenuesParams.AMOUNT, String.valueOf(amm));
                //intent.putExtra(AvenuesParams.AMOUNT, "1");
                intent.putExtra("pid", SharePreferenceUtils.getInstance().getString("userid"));

                intent.putExtra(AvenuesParams.REDIRECT_URL, "https://mrtecks.com/southman/api/pay/ccavResponseHandler.php");
                intent.putExtra(AvenuesParams.CANCEL_URL, "https://mrtecks.com/southman/api/pay/ccavResponseHandler.php");
                intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://mrtecks.com/southman/api/pay/GetRSA.php");

                startActivity(intent);*/

            }
        });


    }

    private BroadcastReceiver singleReceiver;
    private TextView count;


    public class MAdapter extends RecyclerView.Adapter<MAdapter.MyViewHolder> {

        final Context context;

        List<Datum> list;

        LayoutInflater inflater;


        MAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;


        }



        @NonNull
        @Override
        public MAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.prod_list_model2, viewGroup, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MAdapter.MyViewHolder myViewHolder, int j) {


            final Datum item = list.get(j);

            myViewHolder.setIsRecyclable(false);

            for (int i = 0 ; i < item.getBenefits().size() ; i++)
            {

                View view = inflater.inflate(R.layout.benefit_layout , null);

                ImageView type = view.findViewById(R.id.textView4);
                TextView text = view.findViewById(R.id.textView6);

                if (item.getBenefits().get(i).getType().equals("CASH"))
                {
                    type.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_money));

                    text.setText("Open Cash Rewards worth \u20B9 " + item.getBenefits().get(i).getValue());
                }
                else
                {
                    type.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_card));
                    text.setText(item.getBenefits().get(i).getClient() + " Digital Coupon Cash worth \u20B9 " + item.getBenefits().get(i).getValue());
                }

                myViewHolder.benefits.addView(view);

            }


            myViewHolder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Dialog dialog = new Dialog(ProductList4.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.add_cart_dialog);
                    dialog.show();

                    StepperTouch stepperTouch  = dialog.findViewById(R.id.stepperTouch);
                    Button add = dialog.findViewById(R.id.button8);
                    ProgressBar progressBar = dialog.findViewById(R.id.progressBar2);



                    stepperTouch.setMinValue(1);
                    stepperTouch.setMaxValue(99);
                    stepperTouch.setSideTapEnabled(true);
                    stepperTouch.setCount(1);


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            progressBar.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();

                            base = b.baseurl;

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<vouchersBean> call = cr.addCart(SharePreferenceUtils.getInstance().getString("userid") , item.getPid() , String.valueOf(stepperTouch.getCount()), item.getPrice() , client);

                            call.enqueue(new Callback<vouchersBean>() {
                                @Override
                                public void onResponse(Call<vouchersBean> call, Response<vouchersBean> response) {

                                    if (response.body().getStatus().equals("1"))
                                    {
                                        loadCart();
                                        dialog.dismiss();
                                    }

                                    Toast.makeText(ProductList4.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<vouchersBean> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });


                        }
                    });


                }
            });


            myViewHolder.viewBenefits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (myViewHolder.benefits.getVisibility() == View.VISIBLE)
                    {
                        myViewHolder.benefits.setVisibility(View.GONE);
                    }
                    else
                    {
                        myViewHolder.benefits.setVisibility(View.VISIBLE);
                    }

                }
            });

/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/sub_cat1/" + item.getImageUrl(), myViewHolder.imageView, options);
*/

            Glide.with(context).load(base + "southman/admin2/upload/products3/" + item.getProductImage()).into(myViewHolder.imageView);





        }

        void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final ImageView imageView;

            LinearLayout benefits;

            Button viewBenefits;

            Button buy;
            // TextView name;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image);
                buy = itemView.findViewById(R.id.play);
                benefits = itemView.findViewById(R.id.benefits);
                viewBenefits = itemView.findViewById(R.id.view);

                //name = itemView.findViewById(R.id.name);


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


        loadCart();

    }

    void loadCart()
    {
        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        base = b.baseurl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<cartBean> call = cr.getCart(SharePreferenceUtils.getInstance().getString("userid") , client);
        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getData().size() > 0)
                {

                    amm = Integer.parseInt(response.body().getTotal());


                    bquantity.setText(response.body().getItems() + " Items");
                    btotal.setText("Total: Rs. " + response.body().getTotal());

                    bottom.setVisibility(View.VISIBLE);
                }
                else
                {
                    bottom.setVisibility(View.GONE);
                }

                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<cartBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });

    }

}
