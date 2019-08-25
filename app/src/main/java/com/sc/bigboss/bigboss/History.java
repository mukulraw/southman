package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.vHistoryPOJO.Datum;
import com.sc.bigboss.bigboss.vHistoryPOJO.vHistoryBean;
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

                    Call<vHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), strDate);

                    call1.enqueue(new Callback<vHistoryBean>() {
                        @Override
                        public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                        public void onFailure(Call<vHistoryBean> call, Throwable t) {
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

            Call<vHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

            call1.enqueue(new Callback<vHistoryBean>() {
                @Override
                public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                public void onFailure(Call<vHistoryBean> call, Throwable t) {
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

                        Call<vHistoryBean> call1 = cr.getRedeemed21(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

                        call1.enqueue(new Callback<vHistoryBean>() {
                            @Override
                            public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                            public void onFailure(Call<vHistoryBean> call, Throwable t) {
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


                holder.type.setText(item.getTxn());
                holder.code.setText("Paid \u20B9 " + item.getAmount() + " to " + item.getClient());
                holder.type.setTextColor(Color.parseColor("#009688"));

                holder.date.setVisibility(View.GONE);
                //holder.price.setVisibility(View.VISIBLE);



                holder.paid.setText("completed");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context , StatusActivity2.class);
                        intent.putExtra("id" , item.getId());
                        context.startActivity(intent);

                    }
                });


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

                ViewHolder(@NonNull View itemView) {
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

            LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(singleReceiver);

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

                    Call<vHistoryBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), strDate);

                    call1.enqueue(new Callback<vHistoryBean>() {
                        @Override
                        public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                        public void onFailure(Call<vHistoryBean> call, Throwable t) {
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

                        Call<vHistoryBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

                        call1.enqueue(new Callback<vHistoryBean>() {
                            @Override
                            public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                            public void onFailure(Call<vHistoryBean> call, Throwable t) {
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

            Call<vHistoryBean> call1 = cr.getRedeemed2(SharePreferenceUtils.getInstance().getString("userid"), formattedDate);

            call1.enqueue(new Callback<vHistoryBean>() {
                @Override
                public void onResponse(Call<vHistoryBean> call, Response<vHistoryBean> response1) {

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
                public void onFailure(Call<vHistoryBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        BroadcastReceiver singleReceiver;


        class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

            List<Datum> list;
            final Context context;

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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.history_list_item1, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

                Datum item = list.get(i);


                holder.status.setText(item.getCreated());


                holder.type.setText(item.getTxn());
                holder.code.setText("Paid \u20B9 " + item.getAmount() + " to " + item.getClient());
                holder.type.setTextColor(Color.parseColor("#009688"));

                holder.date.setVisibility(View.GONE);
                //holder.price.setVisibility(View.VISIBLE);

                if (item.getMode().equals("GPAY"))
                {
                    holder.type.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_google_pay_mark_800_gray , 0 , 0 , 0);
                }
                else
                {
                    holder.type.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_money2 , 0 , 0 , 0);
                }


                holder.paid.setText(item.getStatus());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context , StatusActivity5.class);
                        intent.putExtra("id" , item.getId());
                        context.startActivity(intent);

                    }
                });
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

                ViewHolder(@NonNull View itemView) {
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

            LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(singleReceiver);

        }

    }


}
