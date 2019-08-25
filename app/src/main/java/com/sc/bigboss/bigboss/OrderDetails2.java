package com.sc.bigboss.bigboss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.benefits3POJO.Datum;
import com.sc.bigboss.bigboss.benefits3POJO.benefits3Bean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrderDetails2 extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar bar;
    String base;
    TextView tid1 , status1 , cashdiscount , scratchcard , bill , balance;

    Button rewards;
    LinearLayout benefits;

    String t , s , c , sc , b , ba , oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);


        t = getIntent().getStringExtra("tid");
        s = getIntent().getStringExtra("status");
        c = getIntent().getStringExtra("cash");
        sc = getIntent().getStringExtra("scratch");
        b = getIntent().getStringExtra("bill");
        ba = getIntent().getStringExtra("balance");
        oid = getIntent().getStringExtra("order");


        toolbar = findViewById(R.id.toolbar3);
        bar = findViewById(R.id.progressBar3);
        tid1 = findViewById(R.id.tid);
        status1 = findViewById(R.id.status);
        cashdiscount = findViewById(R.id.cash_discount);
        scratchcard = findViewById(R.id.scratch_card);
        bill = findViewById(R.id.bill);
        balance = findViewById(R.id.balance);
        rewards = findViewById(R.id.textView41);
        benefits = findViewById(R.id.benefits);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Order Details");

        tid1.setText(t);
        status1.setText(s);
        cashdiscount.setText(c);
        scratchcard.setText(sc);
        bill.setText(b);
        balance.setText(ba);

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (benefits.getVisibility() == View.VISIBLE)
                {
                    benefits.setVisibility(View.GONE);
                }
                else
                {
                    benefits.setVisibility(View.VISIBLE);
                }

            }
        });

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<benefits3Bean> call = cr.getOrderHistory2(oid);

        call.enqueue(new Callback<benefits3Bean>() {
            @Override
            public void onResponse(Call<benefits3Bean> call, Response<benefits3Bean> response) {

                benefits.removeAllViews();

                for (int i = 0 ; i < response.body().getData().size() ; i++)
                {
                    Datum item = response.body().getData().get(i);

                    View view = getLayoutInflater().inflate(R.layout.benefit_layout , null);

                    ImageView type = view.findViewById(R.id.textView4);
                    TextView text = view.findViewById(R.id.textView6);



                    if (item.getType().equals("CASH"))
                    {
                        type.setImageDrawable(getResources().getDrawable(R.drawable.ic_money));
                        text.setText("Open Cash Rewards worth \u20B9 " + item.getValue());
                    }
                    else
                    {
                        type.setImageDrawable(getResources().getDrawable(R.drawable.ic_card));
                        text.setText(item.getClient() + " Digital Coupon Cash worth \u20B9 " + item.getValue());
                    }

                    benefits.addView(view);

                }


                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<benefits3Bean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });

    }
}
