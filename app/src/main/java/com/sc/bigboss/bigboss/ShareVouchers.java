package com.sc.bigboss.bigboss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ShareVouchers extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar bar;
    GridLayoutManager manager;
    String id;
    RecyclerView grid;
    private CardAdapter adapter;
    private List<Scratch> list;
    String client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_vouchers);
        list = new ArrayList<>();
        id = getIntent().getStringExtra("id");
        client = getIntent().getStringExtra("client");

        toolbar = findViewById(R.id.toolbar3);
        bar = findViewById(R.id.progressBar3);
        grid = findViewById(R.id.grid);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Send Voucher");



        adapter = new CardAdapter(this, list);

        manager = new GridLayoutManager(this, 2);

        /*manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                return Integer.parseInt(adapter.getSpace(i));

            }
        });*/

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


        bar.setVisibility(View.VISIBLE);

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

        Call<getPerksBean> call = cr.getPerks2(SharePreferenceUtils.getInstance().getString("userid"), client);


        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {





                if (Objects.requireNonNull(response.body()).getScratch().size() > 0) {
                    adapter.setData(response.body().getScratch());

                } else {
                    adapter.setData(response.body().getScratch());
                    Toast.makeText(ShareVouchers.this, "No Business Voucher found", Toast.LENGTH_SHORT).show();
                }





                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });


    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        List<Scratch> list;
        final Context context;

        CardAdapter(Context context, List<Scratch> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Scratch> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.scratch_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Scratch item = list.get(i);

            holder.text.setText(item.getText());

            holder.enable.setVisibility(View.VISIBLE);
            holder.disable.setVisibility(View.GONE);

            holder.text2.setText("You have got \u20B9 " + item.getCashValue());

            holder.expiry.setText("will expire on " + item.getExpiry());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog1 = new Dialog(context);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setCancelable(false);
                    dialog1.setContentView(R.layout.share_dialog);
                    dialog1.show();

                    Button shareNow = dialog1.findViewById(R.id.button7);
                    Button cancel = dialog1.findViewById(R.id.button9);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            dialog1.dismiss();

                        }
                    });

                    shareNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            bar.setVisibility(View.VISIBLE);

                            Bean b = (Bean) context.getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<scratchCardBean> call = cr.send(SharePreferenceUtils.getInstance().getString("userid"), item.getId(), id);

                            call.enqueue(new Callback<scratchCardBean>() {
                                @Override
                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                    dialog1.dismiss();

                                    Toast.makeText(ShareVouchers.this , response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    finish();

                                    bar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                    bar.setVisibility(View.GONE);
                                }
                            });


                        }
                    });

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView text, text2, expiry;
            RelativeLayout enable, disable;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                enable = itemView.findViewById(R.id.enable);
                disable = itemView.findViewById(R.id.disable);
                text2 = itemView.findViewById(R.id.text2);
                expiry = itemView.findViewById(R.id.expiry);
            }
        }
    }

}
