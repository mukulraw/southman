package com.sc.bigboss.bigboss;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Notification extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView grid;
    private List<notiBean> list;
    private GridLayoutManager manager;
    private NotiAdapter adapter;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        SharePreferenceUtils.getInstance().saveInt("count" , 0);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progress);
        manager = new GridLayoutManager(this, 1);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Notification");

        progress.setVisibility(View.VISIBLE);


        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<List<notiBean>> call1 = cr.getNoti(SharePreferenceUtils.getInstance().getString("userid"));

        call1.enqueue(new Callback<List<notiBean>>() {
            @Override
            public void onResponse(Call<List<notiBean>> call, Response<List<notiBean>> response1) {

                adapter = new NotiAdapter(Notification.this, response1.body());
                manager = new GridLayoutManager(Notification.this, 1);
                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<notiBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {
        final Context context;
        List<notiBean> list;

        NotiAdapter(Context context, List<notiBean> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.history_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            notiBean item = list.get(i);

            viewHolder.type.setVisibility(View.GONE);
            viewHolder.layout.setVisibility(View.GONE);
            viewHolder.code.setText(item.getText());
            viewHolder.date.setText(item.getTime());

        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView code;
            final TextView date;
            final TextView type;
            final RelativeLayout layout;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                layout = itemView.findViewById(R.id.layout);

                code = itemView.findViewById(R.id.code);
                date = itemView.findViewById(R.id.date);
                type = itemView.findViewById(R.id.type);
            }
        }
    }

}
