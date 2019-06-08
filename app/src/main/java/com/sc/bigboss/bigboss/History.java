package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
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

import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.Datum;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    ViewPager grid;
    GridLayoutManager manager;
    List<Datum> list;
    TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        tabs = findViewById(R.id.tabs);
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


        tabs.addTab(tabs.newTab().setText("VOUCHER STORE"));
        tabs.addTab(tabs.newTab().setText("REDEEM STORE"));

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        grid.setAdapter(adapter);

        tabs.setupWithViewPager(grid);

        tabs.getTabAt(0).setText("VOUCHER STORE");
        tabs.getTabAt(1).setText("REDEEM STORE");

        //grid.setOffscreenPageLimit(1);


    }

    BroadcastReceiver singleReceiver;

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
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


            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.date_dialog);
                    dialog.show();


                    DatePicker picker = dialog.findViewById(R.id.date);
                    Button ok = dialog.findViewById(R.id.ok);

                    long now = System.currentTimeMillis() - 1000;
                    picker.setMaxDate(now);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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

                            Call<scratchCardBean> call1 = cr.getRedeemed(SharePreferenceUtils.getInstance().getString("userid"), strDate);

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


                        }
                    });


                }
            });

            progress.setVisibility(View.VISIBLE);

            Bean b = (Bean) getActivity().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            progress.setVisibility(View.VISIBLE);

            Call<scratchCardBean> call1 = cr.getRedeemed(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

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

            singleReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent.getAction().equals("count")) {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        progress.setVisibility(View.VISIBLE);

                        Call<scratchCardBean> call1 = cr.getRedeemed(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

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
                    }

                }
            };

            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(singleReceiver,
                    new IntentFilter("count"));

            return view;
        }


        BroadcastReceiver singleReceiver;

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


                holder.date.setText(item.getCreated());

                holder.status.setText(item.getStatus());

                switch (item.getText()) {
                    case "perks":
                        holder.type.setText("ORDER NO. - " + item.getId());
                        holder.code.setText("Item - " + item.getCode());
                        holder.type.setTextColor(Color.parseColor("#009688"));

                        holder.price.setText(Html.fromHtml("<font color=#000000>Benefits</font> - " + item.getPrice() + " credits"));

                        float pr = Float.parseFloat(item.getPrice());
                        float pa = Float.parseFloat(item.getCashValue());

                        holder.paid.setText(Html.fromHtml("<font color=#000000>Pending benefits</font> - " + String.valueOf(pr - pa) + " credits"));

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);

                        break;
                    case "cash":
                        holder.type.setText("ORDER NO. - " + item.getId());
                        holder.code.setText("Shop - " + item.getClient());
                        holder.type.setTextColor(Color.parseColor("#689F38"));

                        holder.price.setText("Price - Rs." + item.getPrice());

//                    float pr1 = Float.parseFloat(item.getPrice());
                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);

                        break;
                    case "scratch":
                        holder.type.setText("ORDER NO. - " + item.getId());

                        holder.type.setTextColor(Color.parseColor("#F9A825"));

                        holder.price.setText("Benefits - " + item.getCashValue() + " credits");

                        holder.paid.setVisibility(View.GONE);
                        //holder.price.setVisibility(View.GONE);

                        break;
                }

            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView code, date, type, status, price, paid;

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

        @Override
        public void onDestroy() {
            super.onDestroy();

            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(singleReceiver);

        }


    }

    public static class redeem extends Fragment {

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

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.date_dialog);
                    dialog.show();


                    DatePicker picker = dialog.findViewById(R.id.date);
                    Button ok = dialog.findViewById(R.id.ok);

                    long now = System.currentTimeMillis() - 1000;
                    picker.setMaxDate(now);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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


                        }
                    });


                }
            });




            singleReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent.getAction().equals("count")) {
                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();

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
                    }

                }
            };

            LocalBroadcastManager.getInstance(getContext()).registerReceiver(singleReceiver,
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

            Bean b = (Bean) getActivity().getApplicationContext();

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

        }

        BroadcastReceiver singleReceiver;


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
                View view = inflater.inflate(R.layout.history_list_item2, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

                Datum item = list.get(i);


                holder.date.setText(item.getCreated());

                holder.status.setText(item.getStatus());


                if (item.getGenerate().equals("no"))
                {

                    if (item.getStatus().equals("cancelled") || item.getStatus().equals("completed"))
                    {
                        holder.text.setVisibility(View.GONE);
                        holder.generate.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.text.setVisibility(View.VISIBLE);
                        holder.generate.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    holder.text.setVisibility(View.GONE);
                    holder.generate.setVisibility(View.GONE);
                }


                holder.generate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.generate_dialog);
                        dialog.show();

                        Button ookk = dialog.findViewById(R.id.button2);
                        Button canc = dialog.findViewById(R.id.button4);

                        canc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        ookk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                            }
                        });





                    }
                });


                switch (item.getText()) {
                    case "perks":
                        holder.type.setText("ORDER NO. - " + item.getId());
                        holder.code.setText("Item - " + item.getCode());
                        holder.type.setTextColor(Color.parseColor("#009688"));

                        holder.price.setText("Benefits - " + item.getPrice() + " credits");

                        float pr = Float.parseFloat(item.getPrice());
                        float pa = Float.parseFloat(item.getCashValue());

                        holder.paid.setText("Pending benefits - " + String.valueOf(pr - pa) + " credits");

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);

                        break;
                    case "cash":
                        holder.type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
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
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));


                        }

                        holder.paid.setVisibility(View.VISIBLE);
                        holder.price.setVisibility(View.VISIBLE);
                        break;
                    case "scratch":
                        holder.type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
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
                            holder.balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));


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

                TextView code, date, type, status, price, paid, bill, balance , text;
                Button generate;

                public ViewHolder(@NonNull View itemView) {
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

            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(singleReceiver);

        }

    }


}
