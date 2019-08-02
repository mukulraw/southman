package com.sc.bigboss.bigboss;

import android.app.Dialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class ShopSingleProduct extends AppCompatActivity {

    private Toolbar toolbar;

    private Button order;

    private TextView name;
    private TextView brand;
    private TextView color;
    private TextView size;
    private TextView negitable;
    private TextView price;

    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_single_product);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());

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
        order.setOnClickListener(v -> {

            final Dialog dialog = new Dialog(ShopSingleProduct.this);

            dialog.setContentView(R.layout.dialog);

            dialog.setCancelable(true);

            dialog.show();

            TextView code = dialog.findViewById(R.id.code);

            TextView mobile = dialog.findViewById(R.id.mobile);

            Button watshp = dialog.findViewById(R.id.whatsapp);

            Button call = dialog.findViewById(R.id.call);

            watshp.setOnClickListener(v12 -> {

                finish();
                dialog.dismiss();
            });

            call.setOnClickListener(v1 -> {

                finish();
                dialog.dismiss();
            });
        });

    }
}
