package com.sc.bigboss.bigboss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.sharePOJO.Datum;
import com.sc.bigboss.bigboss.sharePOJO.shareBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class share extends Fragment {

    RecyclerView grid;
    ProgressBar progress;
    GridLayoutManager manager;
    String client;
    private String bann;
    private String catName;
    private String id;
    List<Datum> list;
    ShareAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share, container, false);

        list = new ArrayList<>();

        catName = getArguments().getString("text");
        client = getArguments().getString("client");
        bann = getArguments().getString("banner");
        id = getArguments().getString("id");

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);

        adapter = new ShareAdapter(getActivity(), list);
        manager = new GridLayoutManager(getActivity(), 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    loadData();
                }

            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(singleReceiver);

    }

    private BroadcastReceiver singleReceiver;

    @Override
    public void onResume() {
        super.onResume();

        loadData();

    }

    void loadData() {


        Bean b = (Bean) getActivity().getApplicationContext();

        progress.setVisibility(View.VISIBLE);

        String base = b.baseurl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<shareBean> call = cr.getShare(client);


        call.enqueue(new Callback<shareBean>() {
            @Override
            public void onResponse(Call<shareBean> call, Response<shareBean> response) {

                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<shareBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
        Context context;
        List<Datum> list;

        ShareAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.share_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Datum item = list.get(position);

            if (item.getType().equals("SHARE")) {
                holder.text.setText(item.getSenderName() + " has shared a Business Voucher worth \u20B9 " + item.getCashValue());
            } else {
                holder.text.setText(item.getReceiverName() + " has requested a Business Voucher");
            }

            if (item.getStatus().equals("1")) {
                holder.text2.setVisibility(View.VISIBLE);

                if (item.getType().equals("SHARE")) {
                    holder.text2.setText("Received by " + item.getReceiverName());
                } else {
                    holder.text2.setText("Sent by " + item.getSenderName());
                }

            } else {

                holder.text2.setVisibility(View.GONE);

            }

            if (item.getStatus().equals("0")) {
                holder.button.setVisibility(View.VISIBLE);

                if (item.getType().equals("SHARE")) {
                    holder.button.setText("RECEIVE");
                } else {
                    holder.button.setText("SEND");
                }

            } else {

                holder.button.setVisibility(View.GONE);

            }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (item.getType().equals("SHARE")) {


                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) context.getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<scratchCardBean> call = cr.receive(SharePreferenceUtils.getInstance().getString("userid") , item.getId());

                        call.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                loadData();
                                progress.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


                    } else {
                        holder.button.setText("SEND");
                    }


                }
            });

            holder.date.setText(item.getCreated());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView text, text2, date;
            Button button;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.text);
                text2 = itemView.findViewById(R.id.text2);
                date = itemView.findViewById(R.id.date);
                button = itemView.findViewById(R.id.button);

            }
        }
    }

}
