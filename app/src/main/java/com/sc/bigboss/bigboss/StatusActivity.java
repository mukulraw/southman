package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.cartPOJO.cartBean;
import com.sc.bigboss.bigboss.gPayPOJO.gPayBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.tarek360.instacapture.Instacapture;
import com.tarek360.instacapture.listener.SimpleScreenCapturingListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StatusActivity extends AppCompatActivity {

    TextView status , amount , client_name , date , order , tag , paid , tid;
    Button share;
    ImageButton back;
    ImageView gpay;

    ProgressBar progress;

    String amm , cli , sta , txn;

    RelativeLayout rateLayout;
    Button rate;

    String oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        amm = getIntent().getStringExtra("amount");
        cli = getIntent().getStringExtra("client");
        sta = getIntent().getStringExtra("status");
        txn = getIntent().getStringExtra("txn");

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
        rateLayout = findViewById(R.id.textView47);
        rate = findViewById(R.id.rate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (sta.equals("success"))
        {

            progress.setVisibility(View.VISIBLE);


            Log.d("successful" , "success");

            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<gPayBean> call = cr.buyVouchers(SharePreferenceUtils.getInstance().getString("userid") , cli , amm , txn);
            call.enqueue(new Callback<gPayBean>() {
                @Override
                public void onResponse(Call<gPayBean> call, Response<gPayBean> response) {

                   // image.setImageResource(R.drawable.success);
                   // text.setText("Voucher purchased successfully. Benefits will get added to your account.");

                    status.setText("Payment Successful");
                    amount.setText("\u20B9 " + response.body().getData().getAmount());
                    amount.setCompoundDrawablesWithIntrinsicBounds(null , getResources().getDrawable(R.drawable.ic_checked) , null , null);
                    client_name.setText(response.body().getData().getClient());
                    client_name.setVisibility(View.VISIBLE);
                    rateLayout.setVisibility(View.VISIBLE);
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

                    oid = response.body().getData().getId();

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<gPayBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        }else
        {
            status.setText("Payment Failed");
            amount.setText("\u20B9 " + amm);
            amount.setCompoundDrawablesWithIntrinsicBounds(null , getResources().getDrawable(R.drawable.ic_cancel) , null , null);
            back.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            amount.setVisibility(View.VISIBLE);
        }

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StatusActivity.this , OrderDetails.class);
                intent.putExtra("order" , oid);
                startActivity(intent);

            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(StatusActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.rate_dialog);
                dialog.show();

                TextView titll = dialog.findViewById(R.id.textView48);
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                Button submit = dialog.findViewById(R.id.button12);
                Button cancel = dialog.findViewById(R.id.button13);
                EditText feedback = dialog.findViewById(R.id.editText3);

                titll.setText("Order #" + txn);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        float rr = ratingBar.getRating();

                        if (rr > 0)
                        {


                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();


                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<scratchCardBean> call = cr.rate(oid , String.valueOf(rr) , SharePreferenceUtils.getInstance().getString("userid") , feedback.getText().toString());

                            call.enqueue(new Callback<scratchCardBean>() {
                                @Override
                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                    dialog.dismiss();
                                    Toast.makeText(StatusActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    finish();

                                }

                                @Override
                                public void onFailure(Call<scratchCardBean> call, Throwable t) {

                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(StatusActivity.this, "Please add a rating", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Instacapture.INSTANCE.capture(StatusActivity.this, new SimpleScreenCapturingListener() {
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


        /*switch (transStatus) {
            case "Transaction Successful!":


                //progress.setVisibility(View.VISIBLE);


                Log.d("successful" , "success");

                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<cartBean> call = cr.buyVouchers(SharePreferenceUtils.getInstance().getString("userid"));
                call.enqueue(new Callback<cartBean>() {
                    @Override
                    public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                        image.setImageResource(R.drawable.success);
                        text.setText("Voucher purchased successfully. Benefits will get added to your account.");

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<cartBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });





                //Toast.makeText(StatusActivity.this , response.body() , Toast.LENGTH_SHORT).show();

                break;
            case "Transaction Declined!":
                text.setText("Payment has been declined by your bank");
                image.setImageResource(R.drawable.failure);
                break;
            default:
                text.setText("Transaction has been cancelled");
                image.setImageResource(R.drawable.failure);
                break;
        }




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent = new Intent(StatusActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*//*
                finish();
            }
        });*/


    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(StatusActivity.this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }*/

}
