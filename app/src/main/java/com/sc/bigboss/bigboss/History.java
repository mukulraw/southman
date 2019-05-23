package com.sc.bigboss.bigboss;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.Datum;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class History extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);

        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("History");

        list = new ArrayList<>();


        adapter = new CardAdapter(History.this, list);
        manager = new GridLayoutManager(History.this, 1);
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("asdsad", SharePreferenceUtils.getInstance().getString("userid"));



        progress.setVisibility(View.VISIBLE);

        Call<scratchCardBean> call1 = cr.getRedeemed(SharePreferenceUtils.getInstance().getString("userid"));

        call1.enqueue(new Callback<scratchCardBean>() {
            @Override
            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                adapter.setData(response1.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(History.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        List<Datum> list = new ArrayList<>();
        Context context;

        public CardAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.history_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.code.setText("Item - " + item.getCode());
            holder.date.setText(item.getCreated());

            holder.status.setText(item.getStatus());

            switch (item.getText()) {
                case "perks":
                    holder.type.setText("VOUCHER STORE - " + item.getId());

                    holder.price.setText("Price - " + item.getPrice());

                    float pr = Float.parseFloat(item.getPrice());
                    float pa = Float.parseFloat(item.getCashValue());

                    holder.paid.setText("Paid - " + String.valueOf(pr - pa));

                    holder.paid.setVisibility(View.VISIBLE);
                    holder.price.setVisibility(View.VISIBLE);

                    break;
                case "cash":
                    holder.type.setText("REDEEM STORE - " + item.getId());

                    holder.price.setText("Price - " + item.getPrice());

                    float pr1 = Float.parseFloat(item.getPrice());
                    float pa1 = Float.parseFloat(item.getCashValue());

                    holder.paid.setText("Paid - " + String.valueOf(pr1 - pa1));

                    holder.paid.setVisibility(View.VISIBLE);
                    holder.price.setVisibility(View.VISIBLE);

                    break;
                case "scratch":
                    holder.type.setText("SCRATCH CARD - " + item.getId());
                    holder.paid.setVisibility(View.GONE);
                    holder.price.setVisibility(View.GONE);

                    break;
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView code, date, type , status , price , paid;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                code = itemView.findViewById(R.id.code);
                date = itemView.findViewById(R.id.date);
                type = itemView.findViewById(R.id.type);
                status = itemView.findViewById(R.id.status);
                price = itemView.findViewById(R.id.price);
                paid = itemView.findViewById(R.id.paid);

            }
        }
    }


}
