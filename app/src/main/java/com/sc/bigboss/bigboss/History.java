package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.voucherHistoryPOJO.Datum;
import com.sc.bigboss.bigboss.voucherHistoryPOJO.voucherHistoryBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class History extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progress;
    private ViewPager grid;
    GridLayoutManager manager;
    List<Datum> list;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        tabs = findViewById(R.id.tabs);
        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("History");


        tabs.addTab(tabs.newTab().setText("VOUCHER STORE"));
        tabs.addTab(tabs.newTab().setText("REDEEM STORE"));

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        grid.setAdapter(adapter);

        tabs.setupWithViewPager(grid);

        Objects.requireNonNull(tabs.getTabAt(0)).setText("VOUCHER STORE");
        Objects.requireNonNull(tabs.getTabAt(1)).setText("REDEEM STORE");

        //grid.setOffscreenPageLimit(1);


    }

    BroadcastReceiver singleReceiver;

    class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return new voucer();
            } else {
                return new redeem();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    public static class voucer extends Fragment {

        ProgressBar progress;
        RecyclerView grid;
        GridLayoutManager manager;
        List<Datum> list;
        CardAdapter adapter;
        TextView date;
        LinearLayout linear;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.rh_layout, container, false);

            grid = view.findViewById(R.id.grid);
            date = view.findViewById(R.id.date);
            progress = view.findViewById(R.id.progress);

            linear = view.findViewById(R.id.linear);
            list = new ArrayList<>();


            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            Log.d("dddd", formattedDate);

            date.setText("Date - " + formattedDate + " (click to change)");


            adapter = new CardAdapter(getActivity(), list);
            manager = new GridLayoutManager(getActivity(), 1);
            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);


            date.setOnClickListener(v -> {


                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMaxDate(now);

                ok.setOnClickListener(v1 -> {

                    int year = picker.getYear();
                    int month = picker.getMonth();
                    int day = picker.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = format.format(calendar.getTime());

                    dialog.dismiss();

                    date.setText("Date - " + strDate + " (click to change)");


                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getActivity().getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    progress.setVisibility(View.VISIBLE);

                    Call<voucherHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), strDate);

                    call1.enqueue(new Callback<voucherHistoryBean>() {
                        @Override
                        public void onResponse(Call<voucherHistoryBean> call, Response<voucherHistoryBean> response1) {

                            //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                            if (response1.body().getStatus().equals("1")) {
                                linear.setVisibility(View.GONE);
                            } else {
                                linear.setVisibility(View.VISIBLE);
                            }

                            adapter.setData(response1.body().getData());

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<voucherHistoryBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                });


            });

            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            progress.setVisibility(View.VISIBLE);

            Call<voucherHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

            call1.enqueue(new Callback<voucherHistoryBean>() {
                @Override
                public void onResponse(Call<voucherHistoryBean> call, Response<voucherHistoryBean> response1) {

                    //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                    if (Objects.requireNonNull(response1.body()).getStatus().equals("1")) {
                        linear.setVisibility(View.GONE);
                    } else {
                        linear.setVisibility(View.VISIBLE);
                    }

                    adapter.setData(response1.body().getData());

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<voucherHistoryBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            singleReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        progress.setVisibility(View.VISIBLE);

                        Call<voucherHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

                        call1.enqueue(new Callback<voucherHistoryBean>() {
                            @Override
                            public void onResponse(Call<voucherHistoryBean> call, Response<voucherHistoryBean> response1) {

                                //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                                if (Objects.requireNonNull(response1.body()).getStatus().equals("1")) {
                                    linear.setVisibility(View.GONE);
                                } else {
                                    linear.setVisibility(View.VISIBLE);
                                }

                                adapter.setData(response1.body().getData());

                                progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<voucherHistoryBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            };

            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(singleReceiver,
                    new IntentFilter("count"));

            return view;
        }


        BroadcastReceiver singleReceiver;

        class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

            List<Datum> list;
            final Context context;
LayoutInflater inflater;
            CardAdapter(Context context, List<Datum> list) {
                this.context = context;
                this.list = list;
            }

            void setData(List<Datum> list) {
                this.list = list;
                notifyDataSetChanged();
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.history_list_item1, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int i2) {

                Datum item = list.get(i2);


                holder.status.setText(item.getCreated());


                holder.type.setText("ORDER NO. - " + item.getId());
                holder.code.setText("Item - " + item.getProductTitle());
                holder.type.setTextColor(Color.parseColor("#009688"));

                holder.price.setText(Html.fromHtml("<font color=#000000>Paid</font> - Rs. " + item.getPrice()));

                holder.date.setVisibility(View.GONE);
                holder.price.setVisibility(View.VISIBLE);

                try {
                    holder.benefits.removeAllViews();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

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

                    holder.benefits.addView(view);

                }

                holder.paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (holder.benefits.getVisibility() == View.VISIBLE)
                        {
                            holder.benefits.setVisibility(View.GONE);
                        }
                        else
                        {
                            holder.benefits.setVisibility(View.VISIBLE);
                        }

                    }
                });

            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                LinearLayout benefits;
                final TextView code;
                final TextView date;
                final TextView type;
                final TextView status;
                final TextView price;
                final TextView paid;

                ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    code = itemView.findViewById(R.id.code);
                    date = itemView.findViewById(R.id.date);
                    type = itemView.findViewById(R.id.type);
                    status = itemView.findViewById(R.id.status);
                    price = itemView.findViewById(R.id.price);
                    paid = itemView.findViewById(R.id.paid);
                    benefits = itemView.findViewById(R.id.benefits);

                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(singleReceiver);

        }


    }

    public static class redeem extends Fragment {

        ProgressBar progress;
        RecyclerView grid;
        GridLayoutManager manager;
        List<com.sc.bigboss.bigboss.scratchCardPOJO.Datum> list;
        CardAdapter adapter;
        TextView date;
        LinearLayout linear;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.rh_layout, container, false);

            linear = view.findViewById(R.id.linear);
            grid = view.findViewById(R.id.grid);
            date = view.findViewById(R.id.date);
            progress = view.findViewById(R.id.progress);

            list = new ArrayList<>();


            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);

            Log.d("dddd", formattedDate);

            date.setText("Date - " + formattedDate + " (click to change)");


            adapter = new CardAdapter(getActivity(), list);
            manager = new GridLayoutManager(getActivity(), 1);
            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);

            date.setOnClickListener(v -> {


                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMaxDate(now);

                ok.setOnClickListener(v1 -> {

                    int year = picker.getYear();
                    int month = picker.getMonth();
                    int day = picker.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = format.format(calendar.getTime());

                    dialog.dismiss();

                    date.setText("Date - " + strDate + " (click to change)");


                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getActivity().getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    progress.setVisibility(View.VISIBLE);

                    Call<scratchCardBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), strDate);

                    call1.enqueue(new Callback<scratchCardBean>() {
                        @Override
                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                            //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                            if (response1.body().getStatus().equals("1")) {
                                linear.setVisibility(View.GONE);
                            } else {
                                linear.setVisibility(View.VISIBLE);
                            }
                            adapter.setData(response1.body().getData());

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<scratchCardBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                });


            });


            singleReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        progress.setVisibility(View.VISIBLE);

                        Call<scratchCardBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

                        call1.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                                //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                                if (Objects.requireNonNull(response1.body()).getStatus().equals("1")) {
                                    linear.setVisibility(View.GONE);
                                } else {
                                    linear.setVisibility(View.VISIBLE);
                                }
                                adapter.setData(response1.body().getData());

                                progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            };

            LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(singleReceiver,
                    new IntentFilter("count"));

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);


            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) Objects.requireNonNull(getActivity()).getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            progress.setVisibility(View.VISIBLE);

            Call<scratchCardBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

            call1.enqueue(new Callback<scratchCardBean>() {
                @Override
                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                    //Toast.makeText(History.this, String.valueOf(response1.body().getData().size()), Toast.LENGTH_SHORT).show();

                    if (Objects.requireNonNull(response1.body()).getStatus().equals("1")) {
                        linear.setVisibility(View.GONE);
                    } else {
                        linear.setVisibility(View.VISIBLE);
                    }
                    adapter.setData(response1.body().getData());

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        BroadcastReceiver singleReceiver;


        class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

            List<com.sc.bigboss.bigboss.scratchCardPOJO.Datum> list;
            final Context context;

            CardAdapter(Context context, List<com.sc.bigboss.bigboss.scratchCardPOJO.Datum> list) {
                this.context = context;
                this.list = list;
            }

            void setData(List<com.sc.bigboss.bigboss.scratchCardPOJO.Datum> list) {
                this.list = list;
                notifyDataSetChanged();
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.history_list_item2, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

                com.sc.bigboss.bigboss.scratchCardPOJO.Datum item = list.get(i);


                holder.date.setText(item.getCreated());

                holder.status.setText(item.getStatus());


                if (item.getGenerate().equals("no")) {

                    if (item.getStatus().equals("cancelled") || item.getStatus().equals("completed")) {
                        holder.text.setVisibility(View.GONE);
                        holder.generate.setVisibility(View.GONE);
                    } else {
                        holder.text.setVisibility(View.VISIBLE);
                        holder.generate.setVisibility(View.VISIBLE);
                    }

                } else {
                    holder.text.setVisibility(View.GONE);
                    holder.generate.setVisibility(View.GONE);
                }


                holder.generate.setOnClickListener(v -> {


                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.generate_dialog);
                    dialog.show();

                    Button ookk = dialog.findViewById(R.id.button2);
                    Button canc = dialog.findViewById(R.id.button4);

                    canc.setOnClickListener(v12 -> dialog.dismiss());

                    ookk.setOnClickListener(v1 -> {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<scratchCardBean> call = cr.generateBill(item.getId());

                        call.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                dialog.dismiss();

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                onResume();

                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {

                            }
                        });
                    });


                });


                switch (item.getText()) {
                    case "perks":
                        holder.type.setText("ORDER NO. - " + item.getId());
                        holder.code.setText("Item - " + item.getCode());
                        holder.type.setTextColor(Color.parseColor("#009688"));

                        holder.price.setText("Benefits - " + item.getPrice() + " credits");

                        float pr = Float.parseFloat(item.getPrice());
                        float pa = Float.parseFloat(item.getCashValue());

                        holder.paid.setText("Pending benefits - " + (pr - pa) + " credits");

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);

                        break;
                    case "cash":
                        if (item.getTableName().equals("")) {
                            holder.type.setText("ORDER NO. - " + item.getId());
                        } else {
                            holder.type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                        }

                        holder.code.setText("Shop - " + item.getClient());
                        holder.type.setTextColor(Color.parseColor("#689F38"));

                        holder.price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                        holder.paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                        if (item.getBillAmount().equals("")) {

                            holder.bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                        } else {

                            float c = Float.parseFloat(item.getCashRewards());
                            float s = Float.parseFloat(item.getScratchAmount());
                            float t = Float.parseFloat(item.getBillAmount());

                            holder.bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                        }

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);
                        break;
                    case "scratch":
                        if (item.getTableName().equals("")) {
                            holder.type.setText("ORDER NO. - " + item.getId());
                        } else {
                            holder.type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                        }
                        holder.code.setText("Shop - " + item.getClient());
                        holder.type.setTextColor(Color.parseColor("#689F38"));

                        holder.price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                        holder.paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                        if (item.getBillAmount().equals("")) {

                            holder.bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                        } else {

                            float c = Float.parseFloat(item.getCashRewards());
                            float s = Float.parseFloat(item.getScratchAmount());
                            float t = Float.parseFloat(item.getBillAmount());

                            holder.bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                        }

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                final TextView code;
                final TextView date;
                final TextView type;
                final TextView status;
                final TextView price;
                final TextView paid;
                final TextView bill;
                final TextView balance;
                final TextView text;
                final Button generate;

                ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    code = itemView.findViewById(R.id.code);
                    date = itemView.findViewById(R.id.date);
                    type = itemView.findViewById(R.id.type);
                    status = itemView.findViewById(R.id.status);
                    price = itemView.findViewById(R.id.price);
                    paid = itemView.findViewById(R.id.paid);

                    bill = itemView.findViewById(R.id.bill);
                    balance = itemView.findViewById(R.id.balance);
                    text = itemView.findViewById(R.id.text);
                    generate = itemView.findViewById(R.id.generate);


                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(singleReceiver);

        }

    }


}
