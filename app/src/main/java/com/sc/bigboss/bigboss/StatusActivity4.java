package com.sc.bigboss.bigboss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.onlinePayPOJO.Data;
import com.sc.bigboss.bigboss.onlinePayPOJO.onlinePayBean;
import com.tarek360.instacapture.Instacapture;
import com.tarek360.instacapture.listener.SimpleScreenCapturingListener;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StatusActivity4 extends AppCompatActivity {

    TextView status , amount , client_name , date , tag , paid , tid;
    Button share;
    ImageButton back;
    ImageView gpay;

    CardView order;

    ProgressBar progress;

    String id , pid , sta , amm;

    TextView tid1 , status1 , cashdiscount , scratchcard , bill , balance;

    String oid;

    Dialog dialog;
    ImageView rewars;

    private BroadcastReceiver singleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status4);

        id = getIntent().getStringExtra("id");
        pid = getIntent().getStringExtra("pid");
        sta = getIntent().getStringExtra("sta");
        amm = getIntent().getStringExtra("amount");

        status = findViewById(R.id.textView14);
        amount = findViewById(R.id.textView16);
        client_name = findViewById(R.id.textView29);
        date = findViewById(R.id.textView30);
        order = findViewById(R.id.textView33);
        share = findViewById(R.id.button3);
        tag = findViewById(R.id.textView32);
        back = findViewById(R.id.imageButton);
        paid = findViewById(R.id.textView17);
        progress = findViewById(R.id.progressBar5);
        tid = findViewById(R.id.textView34);
        gpay = findViewById(R.id.textView35);
        rewars = findViewById(R.id.reward);

        tid1 = findViewById(R.id.tid);
        status1 = findViewById(R.id.status);
        cashdiscount = findViewById(R.id.cash_discount);
        scratchcard = findViewById(R.id.scratch_card);
        bill = findViewById(R.id.bill);
        balance = findViewById(R.id.balance);

        Glide.with(this).load(R.drawable.giphy).into(rewars);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pay_cash_dialog);
        dialog.show();


        TextView ppay = dialog.findViewById(R.id.textView42);

        ppay.setText(Html.fromHtml("Please pay " + amm + " to the cashier"));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Instacapture.INSTANCE.capture(StatusActivity4.this , new SimpleScreenCapturingListener() {
                    @Override
                    public void onCaptureComplete(Bitmap bitmap) {
                        //Your code here..

                        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap , tid.getText().toString() , null);
                        Uri bitmapUri = Uri.parse(bitmapPath);

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/png");
                        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                        startActivity(Intent.createChooser(intent, "Share"));


                    }
                });

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StatusActivity4.this , OrderDetails2.class);
                intent.putExtra("order" , oid);
                intent.putExtra("tid" , tid1.getText().toString());
                intent.putExtra("status" , status1.getText().toString());
                intent.putExtra("cash" , cashdiscount.getText().toString());
                intent.putExtra("scratch" , scratchcard.getText().toString());
                intent.putExtra("bill" , bill.getText().toString());
                intent.putExtra("balance" , balance.getText().toString());
                startActivity(intent);

            }
        });

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<onlinePayBean> call = cr.cashPay(id , pid , SharePreferenceUtils.getInstance().getString("userid"));
        call.enqueue(new Callback<onlinePayBean>() {
            @Override
            public void onResponse(Call<onlinePayBean> call, Response<onlinePayBean> response) {


                Toast.makeText(StatusActivity4.this, response.body().getMessage() , Toast.LENGTH_SHORT).show();

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<onlinePayBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);
                    Call<onlinePayBean> call = cr.getSingleOrder2(id);

                    call.enqueue(new Callback<onlinePayBean>() {
                        @Override
                        public void onResponse(Call<onlinePayBean> call, Response<onlinePayBean> response) {

                            Data item = response.body().getData();

                            if (item.getStatus().equals("completed"))
                            {

                                status.setText("Payment Successful");
                                amount.setText(Html.fromHtml(amm));
                                amount.setCompoundDrawablesWithIntrinsicBounds(null , getResources().getDrawable(R.drawable.ic_checked) , null , null);
                                client_name.setText(response.body().getData().getClient());
                                client_name.setVisibility(View.VISIBLE);
                                date.setText(response.body().getData().getCreated());
                                back.setVisibility(View.VISIBLE);
                                paid.setVisibility(View.VISIBLE);
                                status.setVisibility(View.VISIBLE);
                                amount.setVisibility(View.VISIBLE);
                                tag.setVisibility(View.VISIBLE);
                                order.setVisibility(View.VISIBLE);
                                date.setVisibility(View.VISIBLE);
                                tid.setText("TXN ID - " + response.body().getData().getTxn());
                                tid.setVisibility(View.VISIBLE);
                                gpay.setVisibility(View.VISIBLE);

                                float ca = Float.parseFloat(item.getCash());
                                float sc = Float.parseFloat(item.getScratch());
                                float tb = Float.parseFloat(item.getAmount());

                                float nb = tb - (ca + sc);

                                tid1.setText("TXN ID - " + item.getTxn());
                                status1.setText(item.getStatus());
                                cashdiscount.setText("Cash Discount - \u20B9 " + item.getCash());
                                scratchcard.setText("Scratch Discount - \u20B9 " + item.getScratch());
                                bill.setText("Total Bill - \u20B9 " + item.getAmount());
                                balance.setText("Balance Pay - \u20B9 " + String.valueOf(nb));


                                oid = response.body().getData().getId();

                                dialog.dismiss();

                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<onlinePayBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }

}
