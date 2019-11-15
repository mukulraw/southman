package com.sc.bigboss.bigboss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.cartPOJO.cartBean;
import com.sc.bigboss.bigboss.subCat3POJO.Datum;
import com.sc.bigboss.bigboss.subCat3POJO.subCat3Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SubCat2 extends AppCompatActivity {

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

    float per = 0;
    float min = 0;

    String cname;

    View bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat2);

        cd = new ConnectionDetector(SubCat2.this);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());
        bottom = findViewById(R.id.cart_bottom);
        bquantity = findViewById(R.id.textView7);
        btotal = findViewById(R.id.textView9);
        bproceed = findViewById(R.id.textView10);
        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(v -> {


            Intent i = new Intent(SubCat2.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(SubCat2.this , Perks.class);
            startActivity(intent);
        });


        title.setText(getIntent().getStringExtra("text"));
        catName = getIntent().getStringExtra("catname");
        client = getIntent().getStringExtra("client");

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

        search.setOnClickListener(v -> {


            Intent i = new Intent(SubCat2.this, Search.class);
            startActivity(i);
        });

        home.setOnClickListener(v -> {
            Intent i = new Intent(SubCat2.this, MainActivity.class);
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

            Log.d("skandas" , id);

            Call<subCat3Bean> call = cr.subCat3(id , SharePreferenceUtils.getInstance().getString("location") , client);

            call.enqueue(new Callback<subCat3Bean>() {
                @Override
                public void onResponse(Call<subCat3Bean> call, Response<subCat3Bean> response) {

                    if (Objects.equals(Objects.requireNonNull(response.body()).getStatus(), "1")) {

                        adapter.setgrid(response.body().getData());
                        linear.setVisibility(View.GONE);

                        per = Float.parseFloat(response.body().getClient().getPercentage());
                        cname = response.body().getClient().getId();

                        if (response.body().getClient().getMinimunBill().equals("0") || response.body().getClient().getMinimunBill() == null)
                        {
                            min = 0;
                        }
                        else
                        {
                            min = Float.parseFloat(response.body().getClient().getMinimunBill());
                        }

                    }
                    else
                    {
                        linear.setVisibility(View.VISIBLE);
                    }

                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<subCat3Bean> call, Throwable t) {

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

                Intent intent = new Intent(SubCat2.this , Cart.class);
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


        MAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;


        }

        String getSpace(int position)
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


/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/sub_cat1/" + item.getImageUrl(), myViewHolder.imageView, options);
*/

            Glide.with(context).load(base + "southman/admin2/upload/sub_cat1/" + item.getImageUrl()).into(myViewHolder.imageView);



            myViewHolder.itemView.setOnClickListener(v -> {


                    Intent i1 = new Intent(context, ProductList4.class);
                    i1.putExtra("id", item.getId());
                    i1.putExtra("text", item.getSubcatName());
                    i1.putExtra("catname", catName);
                    i1.putExtra("phone", item.getPhone());
                    i1.putExtra("client", client);
                    i1.putExtra("per", per);
                    i1.putExtra("min", min);
                    i1.putExtra("cname", cname);
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

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final ImageView imageView;

            // TextView name;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.tshirt);

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
