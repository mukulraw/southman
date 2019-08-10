package com.sc.bigboss.bigboss;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.cartPOJO.cartBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StatusActivity extends AppCompatActivity {
    /*String transStatus;
    ImageView image;
    TextView text;
    Button ok;
    ProgressBar progress;
    String pid;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //pid = getIntent().getStringExtra("pid");

       /* transStatus = getIntent().getStringExtra("transStatus");
        image = findViewById(R.id.imageView7);
        text = findViewById(R.id.textView19);
        ok = findViewById(R.id.button13);
        progress = findViewById(R.id.progressBar4);*/


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
