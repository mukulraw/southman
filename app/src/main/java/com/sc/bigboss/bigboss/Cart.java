package com.sc.bigboss.bigboss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.cartPOJO.Datum;
import com.sc.bigboss.bigboss.cartPOJO.cartBean;

import java.net.ConnectException;
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

public class Cart extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar bar;
    String base;
    TextView btotal , bproceed;

    int amm = 0;

    View bottom;

    CartAdapter adapter;

    GridLayoutManager manager;

    RecyclerView grid;

    List<Datum> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar3);
        bar = findViewById(R.id.progressBar3);
        bottom = findViewById(R.id.cart_bottom);
        btotal = findViewById(R.id.textView9);
        bproceed = findViewById(R.id.textView10);
        grid = findViewById(R.id.grid);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Cart");

        adapter = new CartAdapter(list , this);

        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCart();

    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
    {

        List<Datum> list = new ArrayList<>();
        Context context;
        LayoutInflater inflater;

        public CartAdapter(List<Datum> list , Context context)
        {
            this.context = context;
            this.list = list;
        }

        void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.prod_list_model4, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i1) {

            Datum item = list.get(i1);

            viewHolder.setIsRecyclable(false);

            viewHolder.subtitle.setText(Html.fromHtml(item.getSubTitle()));

            viewHolder.title.setText(item.getProductTitle());

            for (int i = 0 ; i < item.getBenefits().size() ; i++)
            {

                View view = inflater.inflate(R.layout.benefit_layout , null);

                TextView type = view.findViewById(R.id.textView4);
                TextView text = view.findViewById(R.id.textView6);

                type.setText(item.getBenefits().get(i).getType());

                if (item.getBenefits().get(i).getType().equals("CASH"))
                {
                    text.setText("Get Cash rewards worth Rs. " + item.getBenefits().get(i).getValue());
                }
                else
                {
                    text.setText("Get Scratch card for " + item.getBenefits().get(i).getClient() + " worth Rs. " + item.getBenefits().get(i).getValue());
                }

                viewHolder.benefits.addView(view);

            }

            viewHolder.viewBenefits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (viewHolder.benefits.getVisibility() == View.VISIBLE)
                    {
                        viewHolder.benefits.setVisibility(View.GONE);
                    }
                    else
                    {
                        viewHolder.benefits.setVisibility(View.VISIBLE);
                    }

                }
            });

/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "southman/admin2/upload/sub_cat1/" + item.getImageUrl(), myViewHolder.imageView, options);
*/

            Glide.with(context).load(base + "southman/admin2/upload/products3/" + item.getProductImage()).into(viewHolder.imageView);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;

            LinearLayout benefits;

            TextView title , subtitle , viewBenefits;

            StepperTouch buy;
            // TextView name;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image);
                buy = itemView.findViewById(R.id.play);
                title = itemView.findViewById(R.id.title);
                subtitle = itemView.findViewById(R.id.subtitle);
                benefits = itemView.findViewById(R.id.benefits);
                viewBenefits = itemView.findViewById(R.id.view);

                //name = itemView.findViewById(R.id.name);


            }
        }
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

        Call<cartBean> call = cr.getCart(SharePreferenceUtils.getInstance().getString("userid"));
        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getData().size() > 0)
                {


                    adapter.setgrid(response.body().getData());

                    amm = Integer.parseInt(response.body().getTotal());


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
