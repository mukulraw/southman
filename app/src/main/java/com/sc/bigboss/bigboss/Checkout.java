package com.sc.bigboss.bigboss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.onlinePayPOJO.Data;
import com.sc.bigboss.bigboss.onlinePayPOJO.onlinePayBean;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Checkout extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager grid;
    TextView restaurantName, txn, totalBill, redVoucher, blueVoucher, balancePay, status;

    ProgressBar progress;

    String client;
    boolean orderCreated = false;

    String oid, ttiidd, tbill;

    Button paid;
    String baa;

    String id, pid, sta, amm, txn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        client = getIntent().getStringExtra("client");

        id = getIntent().getStringExtra("id");
        pid = getIntent().getStringExtra("pid");
        sta = getIntent().getStringExtra("sta");
        amm = getIntent().getStringExtra("amount");

        toolbar = findViewById(R.id.toolbar5);
        grid = findViewById(R.id.grid);
        restaurantName = findViewById(R.id.textView58);
        txn = findViewById(R.id.textView66);
        totalBill = findViewById(R.id.textView71);
        redVoucher = findViewById(R.id.textView72);
        blueVoucher = findViewById(R.id.textView75);
        balancePay = findViewById(R.id.textView79);
        status = findViewById(R.id.textView80);
        progress = findViewById(R.id.progressBar9);
        paid = findViewById(R.id.button14);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        GridAdapter adapter = new GridAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        grid.setAdapter(adapter);

        try {
            if (sta.equals("success")) {

                progress.setVisibility(View.VISIBLE);


                Log.d("successful", "success");

                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .client(okHttpClient)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Log.d("id", id);
                Log.d("pid", pid);


                Call<onlinePayBean> call = cr.onlinePay(id, pid, SharePreferenceUtils.getInstance().getString("userid"));
                call.enqueue(new Callback<onlinePayBean>() {
                    @Override
                    public void onResponse(Call<onlinePayBean> call, Response<onlinePayBean> response) {


                        Data item = response.body().getData();
                        // image.setImageResource(R.drawable.success);
                        // text.setText("Voucher purchased successfully. Benefits will get added to your account.");

                        status.setText("Status - Paid");
                        restaurantName.setText("Paid " + response.body().getData().getClient());
                        txn.setText(response.body().getData().getCreated());
                        toolbar.setTitle("TXN ID - " + response.body().getData().getTxn());
                        //txn.setText("TXN ID - " + response.body().getData().getTxn());

                        paid.setText(Html.fromHtml("PAID " + amm));

                        float ca = Float.parseFloat(item.getRed());
                        float sc = Float.parseFloat(item.getBlue());
                        float tb = Float.parseFloat(item.getAmount());

                        float nb = tb - (ca + sc);

                    /*if (nb == 0)
                    {

                        paid.setText("You got this order for free");
                        gpay.setVisibility(View.GONE);

                    }
                    else
                    {
                        paid.setText("Paid via");
                        gpay.setVisibility(View.VISIBLE);
                    }*/

                        txn1 = item.getTxn();

                        redVoucher.setText("\u20B9 " + item.getRed());
                        blueVoucher.setText("\u20B9 " + item.getBlue());
                        totalBill.setText("\u20B9 " + item.getAmount());
                        balancePay.setText("\u20B9 " + String.valueOf(nb));


                        oid = response.body().getData().getId();

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<onlinePayBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            } else {
            /*status.setText("Payment Failed");
            amount.setText(Html.fromHtml(amm));
            amount.setCompoundDrawablesWithIntrinsicBounds(null , getResources().getDrawable(R.drawable.ic_cancel) , null , null);
            back.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            amount.setVisibility(View.VISIBLE);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static class GridAdapter extends FragmentStatePagerAdapter
    {

        public GridAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new page();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class page extends Fragment
    {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.rewards_list_model2 , container , false);



            return view;

        }
    }


}