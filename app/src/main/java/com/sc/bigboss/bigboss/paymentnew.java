package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sc.bigboss.bigboss.createOrderPOJO.createOrderBean;
import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.xuexiang.xqrcode.decoding.Intents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class paymentnew extends Fragment {

    RecyclerView grid;
    GridAdapter adapter;
    GridLayoutManager manager;
    FloatingActionButton next;
    EditText amount;

    private String catName;
    private String client;
    private String bann;
    String id;

    ProgressBar progress;
    Button location;
    ImageView banner;

    String lat, lng;
    boolean orderCreated = false;

    String oid, ttiidd, tbill;

    List<Scratch> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paymentnew, container, false);

        list = new ArrayList<>();

        catName = getArguments().getString("text");
        client = getArguments().getString("client");
        bann = getArguments().getString("banner");
        id = getArguments().getString("id");

        Log.d("catname", getArguments().getString("id"));

        grid = view.findViewById(R.id.grid);
        next = view.findViewById(R.id.floatingActionButton2);
        amount = view.findViewById(R.id.editTextNumber);
        progress = view.findViewById(R.id.progressBar9);
        location = view.findViewById(R.id.button5);
        banner = view.findViewById(R.id.imageView16);

        adapter = new GridAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext(), 2);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        Glide.with(this).load(bann).into(banner);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = amount.getText().toString();

                if (a.length() > 0) {
                    float aa = Float.parseFloat(a);
                    if (aa > 0) {

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Log.d("userid", SharePreferenceUtils.getInstance().getString("userid"));
                        Log.d("client", client);
                        Log.d("amount", String.valueOf(aa));
                        Log.d("txn", String.valueOf(System.currentTimeMillis()));


                        Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, String.valueOf(aa), String.valueOf(System.currentTimeMillis()));
                        call.enqueue(new Callback<createOrderBean>() {
                            @Override
                            public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                if (response.body().getStatus().equals("1")) {
                                    orderCreated = true;
                                    amount.setText("");
                                    onResume();
                                }

                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_SHORT).show();
                }

                //Intent intent = new Intent(getContext(), Summary.class);
                //startActivity(intent);

            }
        });

        return view;
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
        Context context;
        List<Scratch> list = new ArrayList<>();

        public GridAdapter(Context context, List<Scratch> list) {
            this.context = context;
            this.list = list;
        }

        void setData(List<Scratch> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.rewards_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setIsRecyclable(false);

            Scratch item = list.get(position);

            holder.amount.setText("₹ " + item.getCashValue());
            holder.expiry.setText("Expired on " + item.getExpiry());

            if (item.getType().equals("red"))
            {
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.light_red));
                holder.reward.setBackground(context.getResources().getDrawable(R.drawable.red));
            }
            else
            {
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.light_blue));
                holder.reward.setBackground(context.getResources().getDrawable(R.drawable.blue));
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CardView card;
            ImageView reward;
            TextView amount , expiry;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                card = itemView.findViewById(R.id.card);
                reward = itemView.findViewById(R.id.imageView17);
                amount = itemView.findViewById(R.id.textView61);
                expiry = itemView.findViewById(R.id.textView62);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPerks();
    }

    private void loadPerks() {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("asdsad", android_id);

        Call<getPerksBean> call = cr.getPerks2(SharePreferenceUtils.getInstance().getString("userid"), client);


        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {


                lat = response.body().getClient().getLatitude();
                lng = response.body().getClient().getLongitude();

                location.setText("GET LOCATION");

                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String label = catName;
                        String uriBegin = "geo:" + lat + "," + lng;
                        String query = lat + "," + lng + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                        Uri uri = Uri.parse(uriString);

                        //String ll = "geo:" + lat + "," + lng + "(" + catName + ")";

                        //Uri gmmIntentUri = Uri.parse(ll);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                    }
                });

                adapter.setData(response.body().getScratch());

                if (response.body().getOrder().getId() != null) {
                    orderCreated = true;

                    Order item = response.body().getOrder();

                    oid = item.getId();
                    ttiidd = item.getTxn();


                    float ca = Float.parseFloat(item.getRed());
                    float sc = Float.parseFloat(item.getBlue());
                    float tb = Float.parseFloat(item.getAmount());

                    float nb = tb - (ca + sc);

                    tbill = String.valueOf(nb);

                    Intent intent = new Intent(getContext(), Summary.class);
                    intent.putExtra("client", client);
                    startActivity(intent);

                } else {

                    orderCreated = false;

                    oid = "";
                    ttiidd = "";
                    tbill = "0";
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }


}
