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

        Log.d("asdsad", android_id);

        Call<getPerksBean> call = cr.getPerks(android_id);
        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {

                if (response.body().getStatus().equals("1")) {

                    progress.setVisibility(View.VISIBLE);

                    Call<scratchCardBean> call1 = cr.getRedeemed(response.body().getData().get(0).getId());

                    call1.enqueue(new Callback<scratchCardBean>() {
                        @Override
                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                            adapter = new CardAdapter(History.this , response1.body().getData());
                            manager = new GridLayoutManager(History.this , 2);
                            grid.setAdapter(adapter);
                            grid.setLayoutManager(manager);

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<scratchCardBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });



    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        List<Datum> list = new ArrayList<>();
        Context context;

        public CardAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.history_list_item , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.code.setText(item.getCashValue());
            holder.date.setText(item.getCreated());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView code , date;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                code = itemView.findViewById(R.id.code);
                date = itemView.findViewById(R.id.date);

            }
        }
    }



}
