package com.sc.bigboss.bigboss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Summary extends AppCompatActivity implements PaymentStatusListener {

    Toolbar toolbar;
    RecyclerView grid;
    GridAdapter adapter;
    GridLayoutManager manager;
    TextView restaurantName, txn, totalBill, redVoucher, blueVoucher, balancePay, status;

    ProgressBar progress;

    String client;
    boolean orderCreated = false;

    String oid, ttiidd, tbill , asset;

    Button pay, delete;
    String baa;
    List<Scratch> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        list = new ArrayList<>();

        client = getIntent().getStringExtra("client");

        toolbar = findViewById(R.id.toolbar5);
        grid = findViewById(R.id.grid);
        restaurantName = findViewById(R.id.textView58);
        txn = findViewById(R.id.textView66);
        totalBill = findViewById(R.id.textView71);
        redVoucher = findViewById(R.id.textView72);
        blueVoucher = findViewById(R.id.textView75);
        balancePay = findViewById(R.id.textView79);
        status = findViewById(R.id.textView80);
        progress = findViewById(R.id.progressBar9);
        pay = findViewById(R.id.button6);
        delete = findViewById(R.id.button14);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new GridAdapter(Summary.this, list);
        manager = new GridLayoutManager(Summary.this, 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                float tb = Float.parseFloat(tbill);

                if (tb > 0) {

                    Dialog dialog = new Dialog(Summary.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.payment_mode_dialog);
                    dialog.show();

                    Button gpay = dialog.findViewById(R.id.button7);
                    Button cash = dialog.findViewById(R.id.button9);

                    gpay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.d("TransactionSuccess", "TransactionSuccess");
                            Intent intent = new Intent(Summary.this, Checkout.class);
                            intent.putExtra("id", oid);
                            intent.putExtra("pid", client);
                            intent.putExtra("sta", "success");
                            intent.putExtra("amount", baa);
                            startActivity(intent);
                            finish();

                            /*dialog.dismiss();

                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(Summary.this)
                                    .setPayeeVpa("southman@sbi")
                                    .setPayeeName("South Man")
                                    .setTransactionId(ttiidd)
                                    .setTransactionRefId(ttiidd)
                                    //.setPayeeMerchantCode("BCR2DN6T6WEP3JDV")
                                    .setDescription("Payment Store Order")
                                    .setAmount(String.valueOf(tbill))
                                    .build();

                            Dialog dialog1 = new Dialog(Summary.this);
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog1.setCancelable(true);
                            dialog1.setContentView(R.layout.upi_select_dialog);
                            dialog1.show();

                            ImageView phonepe = dialog1.findViewById(R.id.imageView2);
                            ImageView googlepay = dialog1.findViewById(R.id.imageView5);
                            ImageView bhim = dialog1.findViewById(R.id.imageView6);
                            ImageView paytm = dialog1.findViewById(R.id.imageView9);


                            phonepe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.PHONE_PE);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(Summary.this);

                                }
                            });

                            googlepay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(Summary.this);

                                }
                            });


                            bhim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(Summary.this);

                                }
                            });


                            paytm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.PAYTM);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(Summary.this);

                                }
                            });*/


                        }
                    });

                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                            Intent intent = new Intent(Summary.this, StatusActivity4.class);
                            intent.putExtra("id", oid);
                            intent.putExtra("pid", client);
                            intent.putExtra("sta", "success");
                            intent.putExtra("amount", baa);
                            intent.putExtra("tid", ttiidd);
                            startActivity(intent);


                        }
                    });

                } else {

                    Dialog dialog = new Dialog(Summary.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.free_dialog);
                    dialog.show();

                    Button proceed = dialog.findViewById(R.id.button7);
                    Button cancel = dialog.findViewById(R.id.button9);

                    proceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                            Intent intent = new Intent(Summary.this, StatusActivity6.class);
                            intent.putExtra("id", oid);
                            intent.putExtra("pid", client);
                            intent.putExtra("sta", "success");
                            intent.putExtra("amount", baa);
                            startActivity(intent);

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });

                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(Summary.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.delete_dialog);
                dialog.show();


                Button ok = dialog.findViewById(R.id.button2);
                Button cancel = dialog.findViewById(R.id.button4);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getApplicationContext();


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<scratchCardBean> call = cr.cancelOrder(oid);

                        call.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                Toast.makeText(Summary.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                                progress.setVisibility(View.GONE);

                                //loadPerks();

                                finish();

                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }
                });

            }
        });

    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Log.d("TransactionDetails", transactionDetails.toString());
    }

    @Override
    public void onTransactionSuccess() {
        Log.d("TransactionSuccess", "TransactionSuccess");
        Intent intent = new Intent(Summary.this, StatusActivity3.class);
        intent.putExtra("id", oid);
        intent.putExtra("pid", client);
        intent.putExtra("sta", "success");
        intent.putExtra("amount", baa);
        startActivity(intent);
    }

    @Override
    public void onTransactionSubmitted() {
        Log.d("onTransactionSubmitted", "onTransactionSubmitted");
    }

    @Override
    public void onTransactionFailed() {
        Log.d("onTransactionFailed", "onTransactionFailed");
        Intent intent = new Intent(Summary.this, StatusActivity3.class);
        intent.putExtra("id", oid);
        intent.putExtra("pid", client);
        intent.putExtra("amount", baa);
        intent.putExtra("sta", "failure");
        startActivity(intent);
    }

    @Override
    public void onTransactionCancelled() {
        Log.d("onTransactionCancelled", "onTransactionCancelled");
    }

    @Override
    public void onAppNotFound() {
        Log.d("onAppNotFound", "onAppNotFound");
        Toast.makeText(Summary.this, "App Not Found", Toast.LENGTH_SHORT).show();
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
            View view = inflater.inflate(R.layout.redeem_rewards_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setIsRecyclable(false);

            Scratch item = list.get(position);

            holder.value.setText("₹ " + item.getCashValue());
            holder.expiry.setText("Expires on " + item.getExpiry());

            float bp = Float.parseFloat(tbill);
            float cv = Float.parseFloat(item.getCashValue());

            float uv = 0;

            if (item.getType().equals("red"))
            {
                holder.title.setText("Red Voucher");
                holder.title.setTextColor(Color.parseColor("#FF8370"));
                holder.reward.setBackground(context.getResources().getDrawable(R.drawable.red));

                if (bp > 0)
                {
                    if (bp >= cv)
                    {
                        uv = cv;
                    }
                    else
                    {
                        uv = bp - cv;
                    }
                }
                else
                {
                    uv = 0;
                }

            }
            else
            {

                if (bp > 0)
                {
                    if (bp >= cv)
                    {
                        uv = cv;
                    }
                    else
                    {
                        uv = bp - cv;
                    }
                }
                else
                {
                    uv = 0;
                }

                holder.title.setText("Blue Voucher");
                holder.title.setTextColor(Color.parseColor("#0087A2"));
                holder.reward.setBackground(context.getResources().getDrawable(R.drawable.blue));
            }

            //tbill

            holder.usable.setText("₹ " + uv);
            holder.use.setText("USE ₹ " + uv);



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView reward;
            TextView title , expiry , value , usable;
            Button use;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                reward = itemView.findViewById(R.id.imageView18);
                title = itemView.findViewById(R.id.textView81);
                expiry = itemView.findViewById(R.id.textView86);
                value = itemView.findViewById(R.id.textView84);
                usable = itemView.findViewById(R.id.textView85);
                use = itemView.findViewById(R.id.button15);

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


                if (response.body().getOrder().getId() != null) {
                    orderCreated = true;

                    Order item = response.body().getOrder();

                    oid = item.getId();
                    ttiidd = item.getTxn();

                    toolbar.setTitle("TXN ID: " + item.getTxn());

                    restaurantName.setText("Paying " + response.body().getClient().getId());
                    txn.setText("TXN ID: " + item.getTxn());
                    totalBill.setText("₹ " + item.getAmount());
                    redVoucher.setText("₹ " + item.getRed());
                    blueVoucher.setText("₹ " + item.getBlue());


                    float ca = Float.parseFloat(item.getRed());
                    float sc = Float.parseFloat(item.getBlue());
                    float tb = Float.parseFloat(item.getAmount());

                    float nb = tb - (ca + sc);

                    tbill = String.valueOf(nb);

                    baa = "<font color='#2D9A3A'>\u20B9 " + nb + "</font>  <font color='#FF8370'><strike>\u20B9 " + item.getAmount() + "</strike></font>";

                    balancePay.setText("₹ " + tbill);
                    pay.setText("PAY ₹ " + tbill);


                } else {

                    orderCreated = false;

                    oid = "";
                    ttiidd = "";
                    tbill = "0";
                }

                adapter.setData(response.body().getScratch());

                asset = response.body().getClient().getAsset();

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

}