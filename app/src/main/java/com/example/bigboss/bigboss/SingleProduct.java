package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bigboss.bigboss.PlaySliderPOJO.PlayBean;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SingleProduct extends AppCompatActivity {

    Toolbar toolbar;

    Button order;

    TextView name, brand, color, size, negitable, price, title , details;

    ImageView imageView;

    ProgressBar bar;

    String id;

    ImageView search;

    String ph, co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        id = getIntent().getStringExtra("id");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        title = findViewById(R.id.title);

        details = findViewById(R.id.text);

        title.setText(getIntent().getStringExtra("text"));

        name = findViewById(R.id.name);

        brand = findViewById(R.id.brand);

        color = findViewById(R.id.color);

        size = findViewById(R.id.size);

        price = findViewById(R.id.price);

        negitable = findViewById(R.id.nagtiable);

        imageView = findViewById(R.id.image);

        order = findViewById(R.id.order);

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SingleProduct.this , Search.class);
                startActivity(i);
            }
        });


        bar = findViewById(R.id.progress);


        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<PlayBean> call = cr.play(id, SharePreferenceUtils.getInstance().getString("location"));

        call.enqueue(new Callback<PlayBean>() {
            @Override
            public void onResponse(Call<PlayBean> call, Response<PlayBean> response) {

                name.setText(response.body().getProductInfo().get(0).getProductTitle());

                brand.setText(response.body().getProductInfo().get(0).getBrand());

                color.setText(response.body().getProductInfo().get(0).getColor());

                size.setText(response.body().getProductInfo().get(0).getSize());

                price.setText(response.body().getProductInfo().get(0).getPrice());

                negitable.setText(response.body().getProductInfo().get(0).getNegotiable());

                ph = String.valueOf(response.body().getProductInfo().get(0).getPhoneNumber());
               co = String.valueOf(response.body().getProductInfo().get(0).getProductCode());

                details.setText(response.body().getProductInfo().get(0).getProductDetail());


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(response.body().getProductInfo().get(0).getProductImage(), imageView, options);

                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<PlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SingleProduct.this, WristWatch.class);
                i.putExtra("id", id);
                i.putExtra("text", title.getText().toString());
                startActivity(i);

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SingleProduct.this);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                mobile.setText(ph);

                code.setText(co);

                watshp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        try {

                            Uri uri = Uri.parse("smsto:" + ph);
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            sendIntent.setPackage("com.whatsapp");
                            startActivity(sendIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {


                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse(ph));
                            startActivity(i);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }
                });
            }
        });

    }
}
