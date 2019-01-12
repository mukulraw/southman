package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopSingleProduct extends AppCompatActivity {

    Toolbar toolbar;

    Button order;

    TextView name , brand , color , size , negitable , price;

    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_single_product);


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

        name = findViewById(R.id.name);

        brand = findViewById(R.id.brand);

        color = findViewById(R.id.color);

        size = findViewById(R.id.size);

        price = findViewById(R.id.price);

        negitable = findViewById(R.id.nagtiable);

        imageView = findViewById(R.id.image);

        order = findViewById(R.id.order);


       /* DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage("" , imageView , options);

*/
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(ShopSingleProduct.this);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                watshp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                        dialog.dismiss();
                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
