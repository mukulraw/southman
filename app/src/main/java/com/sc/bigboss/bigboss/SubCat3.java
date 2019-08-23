package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.ScratchTextView;
import com.sc.bigboss.bigboss.createOrderPOJO.createOrderBean;
import com.sc.bigboss.bigboss.getPerksPOJO.Datum;
import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.pendingOrderPOJO.Data;
import com.sc.bigboss.bigboss.pendingOrderPOJO.pendingOrderBean;

import com.sc.bigboss.bigboss.usersPOJO.usersBean;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SubCat3 extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView grid;

    private GridLayoutManager manager;

    private CardAdapter adapter;

    private List<Scratch> list;

    private ProgressBar bar;

    private String id;

    private TextView title;

    private ImageView search;
    private ImageView home;

    private ConnectionDetector cd;

    private String catName;
    private String base;
    private String client;

    private LinearLayout linear;

    private ImageView notification;
    private ImageView perks2;

    private Uri uri;
    private File file;

    String ph;
    String co;
    private String bann;

    private TextView perks;

    private String p;

    private String pho = "";
    private String tex = "";

    private String phone;

    private Button upload;

    private ImageView banner;

    private String tab = "";
    private String amo = "0";
    private String scr = "0";
    private String cid = "";

    private String take = "no";


    Button createOrder;
    TextView currentOrder;
    LinearLayout hide;

    boolean orderCreated = false;



    TextView billAmount , tid , status , cashdiscount , scratchcard , bill , balance;


    Button confirmandpay , deleteorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat4);

        cd = new ConnectionDetector(SubCat3.this);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);

        billAmount = findViewById(R.id.bill_amount);
        tid = findViewById(R.id.tid);
        status = findViewById(R.id.status);
        cashdiscount = findViewById(R.id.cash_discount);
        scratchcard = findViewById(R.id.scratch_card);
        bill = findViewById(R.id.bill);
        balance = findViewById(R.id.balance);
        confirmandpay = findViewById(R.id.ok);
        deleteorder = findViewById(R.id.cancel);

        banner = findViewById(R.id.banner);

        perks = findViewById(R.id.perks);
        upload = findViewById(R.id.upload);

        createOrder = findViewById(R.id.create);
        currentOrder = findViewById(R.id.current);
        hide = findViewById(R.id.hide);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());

        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(v -> {


            Intent i = new Intent(SubCat3.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(SubCat3.this, Perks.class);
            startActivity(intent);
        });


        title.setText(getIntent().getStringExtra("text"));
        catName = getIntent().getStringExtra("catname");
        client = getIntent().getStringExtra("client");
        bann = getIntent().getStringExtra("banner");

        Log.d("catname", catName);

        grid = findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new CardAdapter(this, list);

        manager = new GridLayoutManager(getApplicationContext(), 3);

        /*manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                return Integer.parseInt(adapter.getSpace(i));

            }
        });*/

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        bar = findViewById(R.id.progress);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);

        search.setOnClickListener(v -> {


            Intent i = new Intent(SubCat3.this, Search.class);
            startActivity(i);
        });

        home.setOnClickListener(v -> {
            Intent i = new Intent(SubCat3.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
        });

        id = getIntent().getStringExtra("id");

        Glide.with(this).load(bann).into(banner);

        if (cd.isConnectingToInternet()) {

            //bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            base = b.baseurl;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        /*upload.setOnClickListener(v -> {


            String c = p;

            float cc = 0;

            try {
                cc = Float.parseFloat(c);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (cc > 0)
            {
                bar.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                base = b.baseurl;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                Call<tablebean> call = cr.getTables(client);

                call.enqueue(new Callback<tablebean>() {
                    @Override
                    public void onResponse(Call<tablebean> call, Response<tablebean> response) {


                        String type = response.body().getMessage();


                        if (type.equals("dining")) {
                            take = "no";


                            if (response.body().getData().size() > 0) {




                                Dialog dialog = new Dialog(SubCat3.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.table_dialog);
                                dialog.show();


                                List<String> names = new ArrayList<>();

                                Spinner spinner = dialog.findViewById(R.id.spinner);
                                EditText amount = dialog.findViewById(R.id.amount);
                                Button submit = dialog.findViewById(R.id.submit);

                                names.add("Select table");

                                names.addAll(response.body().getData());


                                ArrayAdapter<String> adapter = new ArrayAdapter<>(SubCat3.this,
                                        R.layout.spinner_item, names);

                                spinner.setAdapter(adapter);


                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if (position > 0) {
                                            tab = names.get(position);
                                        } else {
                                            tab = "";
                                        }


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                submit.setOnClickListener(v123 -> {

                                    String a = amount.getText().toString();
                                    String c17 = p;

                                    float aa = 0, cc17 = 0;

                                    try {

                                        aa = Float.parseFloat(a);
                                        cc17 = Float.parseFloat(c17);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    if (tab.length() > 0) {

                                        if (aa > 0 && aa <= cc17) {


                                            Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, tab);

                                            call1.enqueue(new Callback<pendingOrderBean>() {
                                                @Override
                                                public void onResponse(Call<pendingOrderBean> call28, Response<pendingOrderBean> response114) {


                                                    if (response114.body().getStatus().equals("1")) {

                                                        dialog.dismiss();

                                                        Dialog dialog1 = new Dialog(SubCat3.this);
                                                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog1.setCancelable(true);
                                                        dialog1.setContentView(R.layout.pending_order_dialog);
                                                        dialog1.show();

                                                        TextView code = dialog1.findViewById(R.id.code);
                                                        TextView type17 = dialog1.findViewById(R.id.type);
                                                        TextView status = dialog1.findViewById(R.id.status);
                                                        TextView price = dialog1.findViewById(R.id.price);
                                                        TextView paid = dialog1.findViewById(R.id.paid);

                                                        TextView bill = dialog1.findViewById(R.id.bill);
                                                        TextView balance = dialog1.findViewById(R.id.balance);

                                                        Button ok = dialog1.findViewById(R.id.ok);
                                                        Button cancel = dialog1.findViewById(R.id.cancel);


                                                        cancel.setOnClickListener(v122 -> dialog1.dismiss());

                                                        Data item = response114.body().getData();

                                                        TextView text = dialog1.findViewById(R.id.text);

                                                        if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                            cancel.setVisibility(View.VISIBLE);
                                                            ok.setVisibility(View.VISIBLE);
                                                            text.setText("Update this order?");
                                                        } else {
                                                            cancel.setVisibility(View.GONE);
                                                            ok.setVisibility(View.GONE);
                                                            text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                        }


                                                        ok.setOnClickListener(v121 -> {


                                                            Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call37, Response<scratchCardBean> response113) {

                                                                    Toast.makeText(SubCat3.this, response113.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call37, Throwable t) {

                                                                }
                                                            });


                                                        });


                                                        status.setText(item.getStatus());

                                                        switch (item.getText()) {
                                                            case "perks":
                                                                type17.setText("ORDER NO. - " + item.getId());
                                                                code.setText("Item - " + item.getCode());
                                                                type17.setTextColor(Color.parseColor("#009688"));

                                                                price.setText("Benefits - " + item.getPrice() + " credits");

                                                                float pr = Float.parseFloat(item.getPrice());
                                                                float pa = Float.parseFloat(item.getCashValue());

                                                                paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);

                                                                break;
                                                            case "cash":
                                                                if (item.getTableName().equals("")) {
                                                                    type17.setText("ORDER NO. - " + item.getId());
                                                                } else {
                                                                    type17.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item.getClient());
                                                                type17.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c17 = Float.parseFloat(item.getCashRewards());
                                                                    float s = Float.parseFloat(item.getScratchAmount());
                                                                    float t = Float.parseFloat(item.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c17 + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                            case "scratch":
                                                                if (item.getTableName().equals("")) {
                                                                    type17.setText("ORDER NO. - " + item.getId());
                                                                } else {
                                                                    type17.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item.getClient());
                                                                type17.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c17 = Float.parseFloat(item.getCashRewards());
                                                                    float s = Float.parseFloat(item.getScratchAmount());
                                                                    float t = Float.parseFloat(item.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c17 + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                        }


                                                    } else {
                                                        amo = a;
                                                        scr = "0";

                                                        pho = phone;
                                                        tex = "";

                                                        dialog.dismiss();

                                                        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                        File newdir = new File(dir);
                                                        try {
                                                            newdir.mkdirs();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                        String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                        file = new File(fil);
                                                        try {
                                                            file.createNewFile();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                        Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                        getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                        getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                        startActivityForResult(getpic, 1);
                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<pendingOrderBean> call28, Throwable t) {

                                                }
                                            });


                                        } else {
                                            Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(SubCat3.this, "Pelase select a table", Toast.LENGTH_SHORT).show();
                                    }


                                });


                            } else {


                                Dialog dialog2 = new Dialog(SubCat3.this);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setCancelable(true);
                                dialog2.setContentView(R.layout.amount_dialog);
                                dialog2.show();


                                EditText am = dialog2.findViewById(R.id.name);
                                Button sub = dialog2.findViewById(R.id.submit);

                                sub.setOnClickListener(v120 -> {

                                    String a = am.getText().toString();
                                    String c16 = p;

                                    float aa = 0, cc16 = 0;

                                    try {

                                        aa = Float.parseFloat(a);
                                        cc16 = Float.parseFloat(c16);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (aa > 0 && aa <= cc16) {


                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call27, Response<pendingOrderBean> response112) {


                                                if (response112.body().getStatus().equals("1")) {

                                                    dialog2.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type16 = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(v119 -> dialog1.dismiss());

                                                    Data item = response112.body().getData();

                                                    TextView text = dialog1.findViewById(R.id.text);

                                                    if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                        cancel.setVisibility(View.VISIBLE);
                                                        ok.setVisibility(View.VISIBLE);
                                                        text.setText("Update this order?");
                                                    } else {
                                                        cancel.setVisibility(View.GONE);
                                                        ok.setVisibility(View.GONE);
                                                        text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                    }

                                                    ok.setOnClickListener(v118 -> {


                                                        Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                            @Override
                                                            public void onResponse(Call<scratchCardBean> call36, Response<scratchCardBean> response111) {

                                                                Toast.makeText(SubCat3.this, response111.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                dialog1.dismiss();

                                                                loadPerks();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<scratchCardBean> call36, Throwable t) {

                                                            }
                                                        });


                                                    });


                                                    status.setText(item.getStatus());

                                                    switch (item.getText()) {
                                                        case "perks":
                                                            type16.setText("ORDER NO. - " + item.getId());
                                                            code.setText("Item - " + item.getCode());
                                                            type16.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item.getPrice());
                                                            float pa = Float.parseFloat(item.getCashValue());

                                                            paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            if (item.getTableName().equals("")) {
                                                                type16.setText("ORDER NO. - " + item.getId());
                                                            } else {
                                                                type16.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item.getClient());
                                                            type16.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c16 = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c16 + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            if (item.getTableName().equals("")) {
                                                                type16.setText("ORDER NO. - " + item.getId());
                                                            } else {
                                                                type16.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item.getClient());
                                                            type16.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c16 = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c16 + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                } else {
                                                    amo = a;
                                                    tab = "";
                                                    scr = "0";

                                                    dialog2.dismiss();

                                                    pho = phone;
                                                    tex = "";

                                                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                    File newdir = new File(dir);
                                                    try {
                                                        newdir.mkdirs();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }


                                                    String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                    file = new File(fil);
                                                    try {
                                                        file.createNewFile();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                    Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                    getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    startActivityForResult(getpic, 1);
                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<pendingOrderBean> call27, Throwable t) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

                                });


                            }


                        }
                        else if (type.equals("take_away")) {
                            take = "yes";

                            Dialog dialog2 = new Dialog(SubCat3.this);
                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog2.setCancelable(true);
                            dialog2.setContentView(R.layout.amount_dialog);
                            dialog2.show();


                            EditText am = dialog2.findViewById(R.id.name);
                            Button sub = dialog2.findViewById(R.id.submit);

                            sub.setOnClickListener(v117 -> {

                                String a = am.getText().toString();
                                String c15 = p;

                                float aa = 0, cc15 = 0;

                                try {

                                    aa = Float.parseFloat(a);
                                    cc15 = Float.parseFloat(c15);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (aa > 0 && aa <= cc15) {


                                    Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                    call1.enqueue(new Callback<pendingOrderBean>() {
                                        @Override
                                        public void onResponse(Call<pendingOrderBean> call26, Response<pendingOrderBean> response110) {


                                            if (response110.body().getStatus().equals("1")) {

                                                dialog2.dismiss();

                                                Dialog dialog1 = new Dialog(SubCat3.this);
                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialog1.setCancelable(true);
                                                dialog1.setContentView(R.layout.pending_order_dialog);
                                                dialog1.show();

                                                TextView code = dialog1.findViewById(R.id.code);
                                                TextView type15 = dialog1.findViewById(R.id.type);
                                                TextView status = dialog1.findViewById(R.id.status);
                                                TextView price = dialog1.findViewById(R.id.price);
                                                TextView paid = dialog1.findViewById(R.id.paid);

                                                TextView bill = dialog1.findViewById(R.id.bill);
                                                TextView balance = dialog1.findViewById(R.id.balance);

                                                Button ok = dialog1.findViewById(R.id.ok);
                                                Button cancel = dialog1.findViewById(R.id.cancel);

                                                cancel.setOnClickListener(v116 -> dialog1.dismiss());

                                                Data item = response110.body().getData();

                                                TextView text = dialog1.findViewById(R.id.text);

                                                if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                    cancel.setVisibility(View.VISIBLE);
                                                    ok.setVisibility(View.VISIBLE);
                                                    text.setText("Update this order?");
                                                } else {
                                                    cancel.setVisibility(View.GONE);
                                                    ok.setVisibility(View.GONE);
                                                    text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                }

                                                ok.setOnClickListener(v115 -> {


                                                    Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                    call2.enqueue(new Callback<scratchCardBean>() {
                                                        @Override
                                                        public void onResponse(Call<scratchCardBean> call35, Response<scratchCardBean> response19) {

                                                            Toast.makeText(SubCat3.this, response19.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            dialog1.dismiss();

                                                            loadPerks();

                                                        }

                                                        @Override
                                                        public void onFailure(Call<scratchCardBean> call35, Throwable t) {

                                                        }
                                                    });


                                                });


                                                status.setText(item.getStatus());

                                                switch (item.getText()) {
                                                    case "perks":
                                                        type15.setText("ORDER NO. - " + item.getId());
                                                        code.setText("Item - " + item.getCode());
                                                        type15.setTextColor(Color.parseColor("#009688"));

                                                        price.setText("Benefits - " + item.getPrice() + " credits");

                                                        float pr = Float.parseFloat(item.getPrice());
                                                        float pa = Float.parseFloat(item.getCashValue());

                                                        paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);

                                                        break;
                                                    case "cash":
                                                        if (item.getTableName().equals("")) {
                                                            type15.setText("ORDER NO. - " + item.getId());
                                                        } else {
                                                            type15.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                        }
                                                        code.setText("Shop - " + item.getClient());
                                                        type15.setTextColor(Color.parseColor("#689F38"));

                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                        if (item.getStatus().equals("pending")) {

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                        } else {

                                                            float c15 = Float.parseFloat(item.getCashRewards());
                                                            float s = Float.parseFloat(item.getScratchAmount());
                                                            float t = Float.parseFloat(item.getBillAmount());

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c15 + s))));


                                                        }

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);
                                                        break;
                                                    case "scratch":
                                                        if (item.getTableName().equals("")) {
                                                            type15.setText("ORDER NO. - " + item.getId());
                                                        } else {
                                                            type15.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                        }
                                                        code.setText("Shop - " + item.getClient());
                                                        type15.setTextColor(Color.parseColor("#689F38"));

                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                        if (item.getStatus().equals("pending")) {

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                        } else {

                                                            float c15 = Float.parseFloat(item.getCashRewards());
                                                            float s = Float.parseFloat(item.getScratchAmount());
                                                            float t = Float.parseFloat(item.getBillAmount());

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c15 + s))));


                                                        }

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);
                                                        break;
                                                }


                                            } else {
                                                amo = a;
                                                tab = "";
                                                scr = "0";

                                                dialog2.dismiss();

                                                pho = phone;
                                                tex = "";

                                                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                File newdir = new File(dir);
                                                try {
                                                    newdir.mkdirs();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                file = new File(fil);
                                                try {
                                                    file.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                startActivityForResult(getpic, 1);
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<pendingOrderBean> call26, Throwable t) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                }

                            });

                        }
                        else if (type.equals("both")) {


                            Dialog dialog4 = new Dialog(SubCat3.this);
                            dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog4.setCancelable(true);
                            dialog4.setContentView(R.layout.take_dialog);
                            dialog4.show();

                            Button di = dialog4.findViewById(R.id.button5);
                            Button ta = dialog4.findViewById(R.id.button6);


                            di.setOnClickListener(v114 -> {

                                dialog4.dismiss();
                                take = "no";


                                if (response.body().getData().size() > 0) {


                                    Dialog dialog = new Dialog(SubCat3.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(true);
                                    dialog.setContentView(R.layout.table_dialog);
                                    dialog.show();


                                    List<String> names = new ArrayList<>();

                                    Spinner spinner = dialog.findViewById(R.id.spinner);
                                    EditText amount = dialog.findViewById(R.id.amount);
                                    Button submit = dialog.findViewById(R.id.submit);

                                    names.add("Select table");

                                    names.addAll(response.body().getData());


                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SubCat3.this,
                                            R.layout.spinner_item, names);

                                    spinner.setAdapter(adapter);


                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            if (position > 0) {
                                                tab = names.get(position);
                                            } else {
                                                tab = "";
                                            }


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    submit.setOnClickListener(v113 -> {

                                        String a = amount.getText().toString();
                                        String c14 = p;

                                        float aa = 0, cc14 = 0;

                                        try {

                                            aa = Float.parseFloat(a);
                                            cc14 = Float.parseFloat(c14);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        if (tab.length() > 0) {

                                            if (aa > 0 && aa <= cc14) {


                                                Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, tab);

                                                call1.enqueue(new Callback<pendingOrderBean>() {
                                                    @Override
                                                    public void onResponse(Call<pendingOrderBean> call25, Response<pendingOrderBean> response18) {


                                                        if (response18.body().getStatus().equals("1")) {

                                                            dialog.dismiss();

                                                            Dialog dialog1 = new Dialog(SubCat3.this);
                                                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                            dialog1.setCancelable(true);
                                                            dialog1.setContentView(R.layout.pending_order_dialog);
                                                            dialog1.show();

                                                            TextView code = dialog1.findViewById(R.id.code);
                                                            TextView type14 = dialog1.findViewById(R.id.type);
                                                            TextView status = dialog1.findViewById(R.id.status);
                                                            TextView price = dialog1.findViewById(R.id.price);
                                                            TextView paid = dialog1.findViewById(R.id.paid);

                                                            TextView bill = dialog1.findViewById(R.id.bill);
                                                            TextView balance = dialog1.findViewById(R.id.balance);

                                                            Button ok = dialog1.findViewById(R.id.ok);
                                                            Button cancel = dialog1.findViewById(R.id.cancel);


                                                            cancel.setOnClickListener(v112 -> dialog1.dismiss());

                                                            Data item = response18.body().getData();

                                                            TextView text = dialog1.findViewById(R.id.text);

                                                            if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                                cancel.setVisibility(View.VISIBLE);
                                                                ok.setVisibility(View.VISIBLE);
                                                                text.setText("Update this order?");
                                                            } else {
                                                                cancel.setVisibility(View.GONE);
                                                                ok.setVisibility(View.GONE);
                                                                text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                            }


                                                            ok.setOnClickListener(v111 -> {


                                                                Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                                call2.enqueue(new Callback<scratchCardBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<scratchCardBean> call34, Response<scratchCardBean> response17) {

                                                                        Toast.makeText(SubCat3.this, response17.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                        dialog1.dismiss();

                                                                        loadPerks();

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<scratchCardBean> call34, Throwable t) {

                                                                    }
                                                                });


                                                            });


                                                            status.setText(item.getStatus());

                                                            switch (item.getText()) {
                                                                case "perks":
                                                                    type14.setText("ORDER NO. - " + item.getId());
                                                                    code.setText("Item - " + item.getCode());
                                                                    type14.setTextColor(Color.parseColor("#009688"));

                                                                    price.setText("Benefits - " + item.getPrice() + " credits");

                                                                    float pr = Float.parseFloat(item.getPrice());
                                                                    float pa = Float.parseFloat(item.getCashValue());

                                                                    paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);

                                                                    break;
                                                                case "cash":
                                                                    if (item.getTableName().equals("")) {
                                                                        type14.setText("ORDER NO. - " + item.getId());
                                                                    } else {
                                                                        type14.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item.getClient());
                                                                    type14.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c14 = Float.parseFloat(item.getCashRewards());
                                                                        float s = Float.parseFloat(item.getScratchAmount());
                                                                        float t = Float.parseFloat(item.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c14 + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                                case "scratch":
                                                                    if (item.getTableName().equals("")) {
                                                                        type14.setText("ORDER NO. - " + item.getId());
                                                                    } else {
                                                                        type14.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item.getClient());
                                                                    type14.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c14 = Float.parseFloat(item.getCashRewards());
                                                                        float s = Float.parseFloat(item.getScratchAmount());
                                                                        float t = Float.parseFloat(item.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c14 + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                            }


                                                        } else {
                                                            amo = a;
                                                            scr = "0";

                                                            pho = phone;
                                                            tex = "";

                                                            dialog.dismiss();

                                                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                            File newdir = new File(dir);
                                                            try {
                                                                newdir.mkdirs();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }


                                                            String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                            file = new File(fil);
                                                            try {
                                                                file.createNewFile();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                            startActivityForResult(getpic, 1);
                                                        }


                                                    }

                                                    @Override
                                                    public void onFailure(Call<pendingOrderBean> call25, Throwable t) {

                                                    }
                                                });


                                            } else {
                                                Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(SubCat3.this, "Pelase select a table", Toast.LENGTH_SHORT).show();
                                        }


                                    });


                                } else {


                                    Dialog dialog2 = new Dialog(SubCat3.this);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setCancelable(true);
                                    dialog2.setContentView(R.layout.amount_dialog);
                                    dialog2.show();


                                    EditText am = dialog2.findViewById(R.id.name);
                                    Button sub = dialog2.findViewById(R.id.submit);

                                    sub.setOnClickListener(v110 -> {

                                        String a = am.getText().toString();
                                        String c13 = p;

                                        float aa = 0, cc13 = 0;

                                        try {

                                            aa = Float.parseFloat(a);
                                            cc13 = Float.parseFloat(c13);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (aa > 0 && aa <= cc13) {


                                            Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                            call1.enqueue(new Callback<pendingOrderBean>() {
                                                @Override
                                                public void onResponse(Call<pendingOrderBean> call24, Response<pendingOrderBean> response16) {


                                                    if (response16.body().getStatus().equals("1")) {

                                                        dialog2.dismiss();

                                                        Dialog dialog1 = new Dialog(SubCat3.this);
                                                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog1.setCancelable(true);
                                                        dialog1.setContentView(R.layout.pending_order_dialog);
                                                        dialog1.show();

                                                        TextView code = dialog1.findViewById(R.id.code);
                                                        TextView type13 = dialog1.findViewById(R.id.type);
                                                        TextView status = dialog1.findViewById(R.id.status);
                                                        TextView price = dialog1.findViewById(R.id.price);
                                                        TextView paid = dialog1.findViewById(R.id.paid);

                                                        TextView bill = dialog1.findViewById(R.id.bill);
                                                        TextView balance = dialog1.findViewById(R.id.balance);

                                                        Button ok = dialog1.findViewById(R.id.ok);
                                                        Button cancel = dialog1.findViewById(R.id.cancel);

                                                        cancel.setOnClickListener(v19 -> dialog1.dismiss());

                                                        Data item = response16.body().getData();

                                                        TextView text = dialog1.findViewById(R.id.text);

                                                        if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                            cancel.setVisibility(View.VISIBLE);
                                                            ok.setVisibility(View.VISIBLE);
                                                            text.setText("Update this order?");
                                                        } else {
                                                            cancel.setVisibility(View.GONE);
                                                            ok.setVisibility(View.GONE);
                                                            text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                        }

                                                        ok.setOnClickListener(v18 -> {


                                                            Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call33, Response<scratchCardBean> response15) {

                                                                    Toast.makeText(SubCat3.this, response15.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call33, Throwable t) {

                                                                }
                                                            });


                                                        });


                                                        status.setText(item.getStatus());

                                                        switch (item.getText()) {
                                                            case "perks":
                                                                type13.setText("ORDER NO. - " + item.getId());
                                                                code.setText("Item - " + item.getCode());
                                                                type13.setTextColor(Color.parseColor("#009688"));

                                                                price.setText("Benefits - " + item.getPrice() + " credits");

                                                                float pr = Float.parseFloat(item.getPrice());
                                                                float pa = Float.parseFloat(item.getCashValue());

                                                                paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);

                                                                break;
                                                            case "cash":
                                                                if (item.getTableName().equals("")) {
                                                                    type13.setText("ORDER NO. - " + item.getId());
                                                                } else {
                                                                    type13.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item.getClient());
                                                                type13.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c13 = Float.parseFloat(item.getCashRewards());
                                                                    float s = Float.parseFloat(item.getScratchAmount());
                                                                    float t = Float.parseFloat(item.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c13 + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                            case "scratch":
                                                                if (item.getTableName().equals("")) {
                                                                    type13.setText("ORDER NO. - " + item.getId());
                                                                } else {
                                                                    type13.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item.getClient());
                                                                type13.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c13 = Float.parseFloat(item.getCashRewards());
                                                                    float s = Float.parseFloat(item.getScratchAmount());
                                                                    float t = Float.parseFloat(item.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c13 + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                        }


                                                    } else {
                                                        amo = a;
                                                        tab = "";
                                                        scr = "0";

                                                        dialog2.dismiss();

                                                        pho = phone;
                                                        tex = "";

                                                        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                        File newdir = new File(dir);
                                                        try {
                                                            newdir.mkdirs();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                        String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                        file = new File(fil);
                                                        try {
                                                            file.createNewFile();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                        Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                        getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                        getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                        startActivityForResult(getpic, 1);
                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<pendingOrderBean> call24, Throwable t) {

                                                }
                                            });


                                        } else {
                                            Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                        }

                                    });


                                }


                            });


                            ta.setOnClickListener(v17 -> {

                                dialog4.dismiss();

                                take = "yes";

                                Dialog dialog2 = new Dialog(SubCat3.this);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setCancelable(true);
                                dialog2.setContentView(R.layout.amount_dialog);
                                dialog2.show();


                                EditText am = dialog2.findViewById(R.id.name);
                                Button sub = dialog2.findViewById(R.id.submit);

                                sub.setOnClickListener(v16 -> {

                                    String a = am.getText().toString();
                                    String c12 = p;

                                    float aa = 0, cc12 = 0;

                                    try {

                                        aa = Float.parseFloat(a);
                                        cc12 = Float.parseFloat(c12);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (aa > 0 && aa <= cc12) {


                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call23, Response<pendingOrderBean> response14) {


                                                if (response14.body().getStatus().equals("1")) {

                                                    dialog2.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type12 = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(v15 -> dialog1.dismiss());

                                                    Data item = response14.body().getData();

                                                    TextView text = dialog1.findViewById(R.id.text);

                                                    if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                        cancel.setVisibility(View.VISIBLE);
                                                        ok.setVisibility(View.VISIBLE);
                                                        text.setText("Update this order?");
                                                    } else {
                                                        cancel.setVisibility(View.GONE);
                                                        ok.setVisibility(View.GONE);
                                                        text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                    }

                                                    ok.setOnClickListener(v14 -> {


                                                        Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                            @Override
                                                            public void onResponse(Call<scratchCardBean> call32, Response<scratchCardBean> response13) {

                                                                Toast.makeText(SubCat3.this, response13.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                dialog1.dismiss();

                                                                loadPerks();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<scratchCardBean> call32, Throwable t) {

                                                            }
                                                        });


                                                    });


                                                    status.setText(item.getStatus());

                                                    switch (item.getText()) {
                                                        case "perks":
                                                            type12.setText("ORDER NO. - " + item.getId());
                                                            code.setText("Item - " + item.getCode());
                                                            type12.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item.getPrice());
                                                            float pa = Float.parseFloat(item.getCashValue());

                                                            paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            if (item.getTableName().equals("")) {
                                                                type12.setText("ORDER NO. - " + item.getId());
                                                            } else {
                                                                type12.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item.getClient());
                                                            type12.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c12 = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c12 + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            if (item.getTableName().equals("")) {
                                                                type12.setText("ORDER NO. - " + item.getId());
                                                            } else {
                                                                type12.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item.getClient());
                                                            type12.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c12 = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c12 + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                } else {
                                                    amo = a;
                                                    tab = "";
                                                    scr = "0";

                                                    dialog2.dismiss();

                                                    pho = phone;
                                                    tex = "";

                                                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                    File newdir = new File(dir);
                                                    try {
                                                        newdir.mkdirs();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }


                                                    String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                    file = new File(fil);
                                                    try {
                                                        file.createNewFile();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                    Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                    getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    startActivityForResult(getpic, 1);
                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<pendingOrderBean> call23, Throwable t) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

                                });

                            });



                        }
                        else {
                            take = "no";
                            Dialog dialog2 = new Dialog(SubCat3.this);
                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog2.setCancelable(true);
                            dialog2.setContentView(R.layout.amount_dialog);
                            dialog2.show();


                            EditText am = dialog2.findViewById(R.id.name);
                            Button sub = dialog2.findViewById(R.id.submit);

                            sub.setOnClickListener(v13 -> {

                                String a = am.getText().toString();
                                String c1 = p;

                                float aa = 0, cc1 = 0;

                                try {

                                    aa = Float.parseFloat(a);
                                    cc1 = Float.parseFloat(c1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (aa > 0 && aa <= cc1) {


                                    Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                    call1.enqueue(new Callback<pendingOrderBean>() {
                                        @Override
                                        public void onResponse(Call<pendingOrderBean> call22, Response<pendingOrderBean> response12) {


                                            if (response12.body().getStatus().equals("1")) {

                                                dialog2.dismiss();

                                                Dialog dialog1 = new Dialog(SubCat3.this);
                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialog1.setCancelable(true);
                                                dialog1.setContentView(R.layout.pending_order_dialog);
                                                dialog1.show();

                                                TextView code = dialog1.findViewById(R.id.code);
                                                TextView type1 = dialog1.findViewById(R.id.type);
                                                TextView status = dialog1.findViewById(R.id.status);
                                                TextView price = dialog1.findViewById(R.id.price);
                                                TextView paid = dialog1.findViewById(R.id.paid);

                                                TextView bill = dialog1.findViewById(R.id.bill);
                                                TextView balance = dialog1.findViewById(R.id.balance);

                                                Button ok = dialog1.findViewById(R.id.ok);
                                                Button cancel = dialog1.findViewById(R.id.cancel);

                                                cancel.setOnClickListener(v12 -> dialog1.dismiss());

                                                Data item = response12.body().getData();

                                                TextView text = dialog1.findViewById(R.id.text);

                                                if (item.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                    cancel.setVisibility(View.VISIBLE);
                                                    ok.setVisibility(View.VISIBLE);
                                                    text.setText("Update this order?");
                                                } else {
                                                    cancel.setVisibility(View.GONE);
                                                    ok.setVisibility(View.GONE);
                                                    text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                }

                                                ok.setOnClickListener(v1 -> {


                                                    Call<scratchCardBean> call2 = cr.updateOrder(item.getId(), a, "0");
                                                    call2.enqueue(new Callback<scratchCardBean>() {
                                                        @Override
                                                        public void onResponse(Call<scratchCardBean> call3, Response<scratchCardBean> response1) {

                                                            Toast.makeText(SubCat3.this, response1.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            dialog1.dismiss();

                                                            loadPerks();

                                                        }

                                                        @Override
                                                        public void onFailure(Call<scratchCardBean> call3, Throwable t) {

                                                        }
                                                    });


                                                });


                                                status.setText(item.getStatus());

                                                switch (item.getText()) {
                                                    case "perks":
                                                        type1.setText("ORDER NO. - " + item.getId());
                                                        code.setText("Item - " + item.getCode());
                                                        type1.setTextColor(Color.parseColor("#009688"));

                                                        price.setText("Benefits - " + item.getPrice() + " credits");

                                                        float pr = Float.parseFloat(item.getPrice());
                                                        float pa = Float.parseFloat(item.getCashValue());

                                                        paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);

                                                        break;
                                                    case "cash":
                                                        if (item.getTableName().equals("")) {
                                                            type1.setText("ORDER NO. - " + item.getId());
                                                        } else {
                                                            type1.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                        }
                                                        code.setText("Shop - " + item.getClient());
                                                        type1.setTextColor(Color.parseColor("#689F38"));

                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                        if (item.getStatus().equals("pending")) {

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                        } else {

                                                            float c1 = Float.parseFloat(item.getCashRewards());
                                                            float s = Float.parseFloat(item.getScratchAmount());
                                                            float t = Float.parseFloat(item.getBillAmount());

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c1 + s))));


                                                        }

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);
                                                        break;
                                                    case "scratch":
                                                        if (item.getTableName().equals("")) {
                                                            type1.setText("ORDER NO. - " + item.getId());
                                                        } else {
                                                            type1.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                        }
                                                        code.setText("Shop - " + item.getClient());
                                                        type1.setTextColor(Color.parseColor("#689F38"));

                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                        if (item.getStatus().equals("pending")) {

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                        } else {

                                                            float c1 = Float.parseFloat(item.getCashRewards());
                                                            float s = Float.parseFloat(item.getScratchAmount());
                                                            float t = Float.parseFloat(item.getBillAmount());

                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c1 + s))));


                                                        }

                                                        paid.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);
                                                        break;
                                                }


                                            } else {
                                                amo = a;
                                                tab = "";
                                                scr = "0";

                                                dialog2.dismiss();

                                                pho = phone;
                                                tex = "";

                                                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                File newdir = new File(dir);
                                                try {
                                                    newdir.mkdirs();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                file = new File(fil);
                                                try {
                                                    file.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                startActivityForResult(getpic, 1);
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<pendingOrderBean> call22, Throwable t) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }


                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<tablebean> call, Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });
            }
            else
            {
                Toast.makeText(SubCat3.this, "You dont have enough cash rewards to redeem", Toast.LENGTH_SHORT).show();
            }





        });*/


        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!SharePreferenceUtils.getInstance().getBoolean("createOrder")) {
                    Dialog dialog2 = new Dialog(SubCat3.this);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.bill_amount_proceed_dialog);
                    dialog2.setCancelable(false);
                    dialog2.show();

                    Button enterBillAmount = dialog2.findViewById(R.id.button7);
                    Button cancel = dialog2.findViewById(R.id.button9);

                    CheckBox check = dialog2.findViewById(R.id.checkBox);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog2.dismiss();
                        }
                    });


                    enterBillAmount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog2.dismiss();

                            if (check.isChecked()) {
                                SharePreferenceUtils.getInstance().saveBoolean("createOrder", true);
                            }

                            Dialog dialog3 = new Dialog(SubCat3.this);
                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog3.setCancelable(false);
                            dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                            dialog3.show();


                            EditText amount = dialog3.findViewById(R.id.editText);
                            Button confirm = dialog3.findViewById(R.id.button10);
                            Button cancel = dialog3.findViewById(R.id.button11);
                            ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog3.dismiss();

                                }
                            });


                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    String aa = amount.getText().toString();

                                    if (aa.length() > 0) {

                                        pbar.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getApplicationContext();


                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                        call.enqueue(new Callback<createOrderBean>() {
                                            @Override
                                            public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                if (response.body().getStatus().equals("1")) {
                                                    orderCreated = true;
                                                    dialog3.dismiss();
                                                    onResume();
                                                }

                                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                pbar.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                pbar.setVisibility(View.GONE);
                                            }
                                        });

                                    } else {
                                        Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });


                        }
                    });


                } else {

                    Dialog dialog3 = new Dialog(SubCat3.this);
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setCancelable(false);
                    dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                    dialog3.show();


                    EditText amount = dialog3.findViewById(R.id.editText);
                    Button confirm = dialog3.findViewById(R.id.button10);
                    Button cancel = dialog3.findViewById(R.id.button11);
                    ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog3.dismiss();

                        }
                    });


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            String aa = amount.getText().toString();

                            if (aa.length() > 0) {

                                pbar.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getApplicationContext();


                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                call.enqueue(new Callback<createOrderBean>() {
                                    @Override
                                    public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                        if (response.body().getStatus().equals("1")) {
                                            orderCreated = true;
                                            dialog3.dismiss();
                                            onResume();
                                        }

                                        Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        pbar.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<createOrderBean> call, Throwable t) {
                                        pbar.setVisibility(View.GONE);
                                    }
                                });

                            } else {
                                Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!orderCreated) {

                    Dialog dialog1 = new Dialog(SubCat3.this);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setCancelable(false);
                    dialog1.setContentView(R.layout.create_order_info_dialog);
                    dialog1.show();

                    Button createOrderNow = dialog1.findViewById(R.id.button7);
                    Button cancel = dialog1.findViewById(R.id.button9);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog1.dismiss();

                        }
                    });

                    createOrderNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog1.dismiss();

                            if (!SharePreferenceUtils.getInstance().getBoolean("createOrder")) {
                                Dialog dialog2 = new Dialog(SubCat3.this);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setContentView(R.layout.bill_amount_proceed_dialog);
                                dialog2.setCancelable(false);
                                dialog2.show();

                                Button enterBillAmount = dialog2.findViewById(R.id.button7);
                                Button cancel = dialog2.findViewById(R.id.button9);

                                CheckBox check = dialog2.findViewById(R.id.checkBox);

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog2.dismiss();
                                    }
                                });


                                enterBillAmount.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog2.dismiss();

                                        if (check.isChecked()) {
                                            SharePreferenceUtils.getInstance().saveBoolean("createOrder", true);
                                        }

                                        Dialog dialog3 = new Dialog(SubCat3.this);
                                        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog3.setCancelable(false);
                                        dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                                        dialog3.show();


                                        EditText amount = dialog3.findViewById(R.id.editText);
                                        Button confirm = dialog3.findViewById(R.id.button10);
                                        Button cancel = dialog3.findViewById(R.id.button11);
                                        ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                dialog3.dismiss();

                                            }
                                        });


                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                String aa = amount.getText().toString();

                                                if (aa.length() > 0) {

                                                    pbar.setVisibility(View.VISIBLE);

                                                    Bean b = (Bean) getApplicationContext();


                                                    Retrofit retrofit = new Retrofit.Builder()
                                                            .baseUrl(b.baseurl)
                                                            .addConverterFactory(ScalarsConverterFactory.create())
                                                            .addConverterFactory(GsonConverterFactory.create())
                                                            .build();

                                                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                    Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                                    call.enqueue(new Callback<createOrderBean>() {
                                                        @Override
                                                        public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                            if (response.body().getStatus().equals("1")) {
                                                                orderCreated = true;
                                                                dialog3.dismiss();
                                                                onResume();
                                                            }

                                                            Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            pbar.setVisibility(View.GONE);

                                                        }

                                                        @Override
                                                        public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                            pbar.setVisibility(View.GONE);
                                                        }
                                                    });

                                                } else {
                                                    Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });


                                    }
                                });


                            } else {

                                Dialog dialog3 = new Dialog(SubCat3.this);
                                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog3.setCancelable(false);
                                dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                                dialog3.show();


                                EditText amount = dialog3.findViewById(R.id.editText);
                                Button confirm = dialog3.findViewById(R.id.button10);
                                Button cancel = dialog3.findViewById(R.id.button11);
                                ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog3.dismiss();

                                    }
                                });


                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        String aa = amount.getText().toString();

                                        if (aa.length() > 0) {

                                            pbar.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) getApplicationContext();


                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseurl)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                            Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                            call.enqueue(new Callback<createOrderBean>() {
                                                @Override
                                                public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                    if (response.body().getStatus().equals("1")) {
                                                        orderCreated = true;
                                                        dialog3.dismiss();
                                                        onResume();
                                                    }

                                                    Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                    pbar.setVisibility(View.GONE);

                                                }

                                                @Override
                                                public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                    pbar.setVisibility(View.GONE);
                                                }
                                            });

                                        } else {
                                            Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });

                            }

                        }
                    });

                } else {

                }

            }
        });


        count = findViewById(R.id.count);

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    loadPerks();
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (orderCreated)
                {
                    if (hide.getVisibility() == View.GONE) {
                        hide.setVisibility(View.VISIBLE);
                    } else {
                        hide.setVisibility(View.GONE);
                    }
                }



            }
        });

    }

    private BroadcastReceiver singleReceiver;
    private TextView count;


    private void loadPerks() {

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

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
                    perks.setText("Cash reward remaining : " + response.body().getData().get(0).getCashRewards());
                    p = response.body().getData().get(0).getCashRewards();

                }

                if (Objects.requireNonNull(response.body()).getScratch().size() > 0) {
                    adapter = new CardAdapter(SubCat3.this, response.body().getScratch());
                    manager = new GridLayoutManager(SubCat3.this, 2);
                    grid.setAdapter(adapter);
                    grid.setLayoutManager(manager);
                    linear.setVisibility(View.GONE);

                } else {
                    adapter = new CardAdapter(SubCat3.this, response.body().getScratch());
                    manager = new GridLayoutManager(SubCat3.this, 2);
                    grid.setAdapter(adapter);
                    grid.setLayoutManager(manager);
                    linear.setVisibility(View.VISIBLE);
                }


                if (response.body().getOrder().getId() != null)
                {
                    orderCreated = true;

                    Order item = response.body().getOrder();

                    float ca = Float.parseFloat(item.getCash());
                    float sc = Float.parseFloat(item.getScratch());
                    float tb = Float.parseFloat(item.getAmount());

                    float nb = tb - (ca + sc);

                    billAmount.setText(Html.fromHtml("\u20B9 " + String.valueOf(nb) + " <strike>\u20B9 " + item.getAmount() + "</strike>"));

                    createOrder.setVisibility(View.GONE);
                    billAmount.setVisibility(View.VISIBLE);
                    hide.setVisibility(View.VISIBLE);

                    tid.setText("TXN ID - " + item.getTxn());
                    status.setText(item.getStatus());
                    cashdiscount.setText("Cash Discount - \u20B9 " + item.getCash());
                    scratchcard.setText("Scratch Discount - \u20B9 " + item.getScratch());
                    bill.setText("Total Bill - \u20B9 " + item.getAmount());
                    balance.setText("Balance Pay - \u20B9 " + String.valueOf(nb));

                }
                else
                {
                    createOrder.setVisibility(View.VISIBLE);
                    billAmount.setVisibility(View.GONE);
                    hide.setVisibility(View.GONE);
                }


                bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                bar.setVisibility(View.GONE);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        loadPerks();

        count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));


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

            holder.itemView.setOnClickListener(v -> {


                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.scratch_dialog);
                dialog.show();

                ScratchTextView scratch = dialog.findViewById(R.id.scratch);
                Button share = dialog.findViewById(R.id.share);
                Button transfer = dialog.findViewById(R.id.transfer);


                transfer.setOnClickListener(v127 -> transfer.setOnClickListener(v126 -> {

                    dialog.dismiss();

                    Dialog dialog14 = new Dialog(SubCat3.this);
                    dialog14.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog14.setCancelable(true);
                    dialog14.setContentView(R.layout.transfer_layout2);
                    dialog14.show();


                    List<String> ids = new ArrayList<>();
                    List<String> names = new ArrayList<>();
                    final String[] id = new String[1];

                    SearchableSpinner spinner = dialog14.findViewById(R.id.spinner);
                    EditText amount = dialog14.findViewById(R.id.amount);
                    Button submit = dialog14.findViewById(R.id.submit);

                    spinner.setTitle("Select user");
                    spinner.setPositiveButton("OK");

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<usersBean> call = cr.getUsers();
                    call.enqueue(new Callback<usersBean>() {
                        @Override
                        public void onResponse(Call<usersBean> call, Response<usersBean> response) {


                            for (int i1 = 0; i1 < response.body().getData().size(); i1++) {
                                ids.add(response.body().getData().get(i1).getId());
                                names.add(response.body().getData().get(i1).getName() + "_" + response.body().getData().get(i1).getDeviceId());
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SubCat3.this,
                                    R.layout.spinner_item, names);

                            spinner.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<usersBean> call, Throwable t) {

                        }
                    });


                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {

                            id[0] = ids.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    submit.setOnClickListener(v125 -> {

                        String a = amount.getText().toString();
                        String c = item.getCashValue();

                        float aa = 0, cc = 0;

                        try {

                            aa = Float.parseFloat(a);
                            cc = Float.parseFloat(c);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (aa > 0 && aa <= cc) {


                            Call<usersBean> call1 = cr.transfer2(item.getId(), id[0], String.valueOf(aa));

                            call1.enqueue(new Callback<usersBean>() {
                                @Override
                                public void onResponse(Call<usersBean> call29, Response<usersBean> response) {

                                    dialog14.dismiss();
                                    Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    onResume();
                                }

                                @Override
                                public void onFailure(Call<usersBean> call29, Throwable t) {

                                }
                            });


                        } else {
                            Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                        }

                    });


                }));


                scratch.setText("You have got Rs." + item.getCashValue());

                scratch.setRevealListener(new ScratchTextView.IRevealListener() {
                    @Override
                    public void onRevealed(ScratchTextView tv) {

                        share.setVisibility(View.VISIBLE);
                        transfer.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {

                    }
                });

                share.setOnClickListener(v124 -> {

                    dialog.dismiss();

                    if (!orderCreated) {

                        Dialog dialog1 = new Dialog(SubCat3.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setCancelable(false);
                        dialog1.setContentView(R.layout.create_order_info_dialog);
                        dialog1.show();

                        Button createOrderNow = dialog1.findViewById(R.id.button7);
                        Button cancel = dialog1.findViewById(R.id.button9);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog1.dismiss();

                            }
                        });

                        createOrderNow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog1.dismiss();

                                if (!SharePreferenceUtils.getInstance().getBoolean("createOrder")) {
                                    Dialog dialog2 = new Dialog(SubCat3.this);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.bill_amount_proceed_dialog);
                                    dialog2.setCancelable(false);
                                    dialog2.show();

                                    Button enterBillAmount = dialog2.findViewById(R.id.button7);
                                    Button cancel = dialog2.findViewById(R.id.button9);
                                    CheckBox check = dialog2.findViewById(R.id.checkBox);
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog2.dismiss();
                                        }
                                    });


                                    enterBillAmount.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog2.dismiss();
                                            if (check.isChecked()) {
                                                SharePreferenceUtils.getInstance().saveBoolean("createOrder", true);
                                            }

                                            Dialog dialog3 = new Dialog(SubCat3.this);
                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog3.setCancelable(false);
                                            dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                                            dialog3.show();


                                            EditText amount = dialog3.findViewById(R.id.editText);
                                            Button confirm = dialog3.findViewById(R.id.button10);
                                            Button cancel = dialog3.findViewById(R.id.button11);
                                            ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                                            cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    dialog3.dismiss();

                                                }
                                            });


                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {


                                                    String aa = amount.getText().toString();

                                                    if (aa.length() > 0) {

                                                        pbar.setVisibility(View.VISIBLE);

                                                        Bean b = (Bean) getApplicationContext();


                                                        Retrofit retrofit = new Retrofit.Builder()
                                                                .baseUrl(b.baseurl)
                                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                                .addConverterFactory(GsonConverterFactory.create())
                                                                .build();

                                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                        Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                                        call.enqueue(new Callback<createOrderBean>() {
                                                            @Override
                                                            public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                                if (response.body().getStatus().equals("1")) {
                                                                    orderCreated = true;
                                                                    dialog3.dismiss();
                                                                }

                                                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                pbar.setVisibility(View.GONE);

                                                            }

                                                            @Override
                                                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                                pbar.setVisibility(View.GONE);
                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                    }


                                                }
                                            });


                                        }
                                    });


                                } else {

                                    Dialog dialog3 = new Dialog(SubCat3.this);
                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog3.setCancelable(false);
                                    dialog3.setContentView(R.layout.enter_bill_amount_dialog);
                                    dialog3.show();


                                    EditText amount = dialog3.findViewById(R.id.editText);
                                    Button confirm = dialog3.findViewById(R.id.button10);
                                    Button cancel = dialog3.findViewById(R.id.button11);
                                    ProgressBar pbar = dialog3.findViewById(R.id.progressBar6);

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            dialog3.dismiss();

                                        }
                                    });


                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            String aa = amount.getText().toString();

                                            if (aa.length() > 0) {

                                                pbar.setVisibility(View.VISIBLE);

                                                Bean b = (Bean) getApplicationContext();


                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.baseurl)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, aa, String.valueOf(System.currentTimeMillis()));
                                                call.enqueue(new Callback<createOrderBean>() {
                                                    @Override
                                                    public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                        if (response.body().getStatus().equals("1")) {
                                                            orderCreated = true;
                                                            dialog3.dismiss();
                                                        }

                                                        Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                        pbar.setVisibility(View.GONE);

                                                    }

                                                    @Override
                                                    public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                        pbar.setVisibility(View.GONE);
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });

                                }

                            }
                        });

                    } else {

                    }








                    /*bar.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    base = b.baseurl;

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<tablebean> call = cr.getTables(client);

                    call.enqueue(new Callback<tablebean>() {
                        @Override
                        public void onResponse(Call<tablebean> call, Response<tablebean> response) {


                            String type = response.body().getMessage();



*//*

                            if (type.equals("dining")) {
                                take = "no";
                                if (response.body().getData().size() > 0) {


                                    Dialog dialog13 = new Dialog(SubCat3.this);
                                    dialog13.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog13.setCancelable(true);
                                    dialog13.setContentView(R.layout.table_dialog2);
                                    dialog13.show();


                                    List<String> names = new ArrayList<>();

                                    Spinner spinner = dialog13.findViewById(R.id.spinner);
                                    EditText amount = dialog13.findViewById(R.id.amount);
                                    Button submit = dialog13.findViewById(R.id.submit);

                                    names.add("Select table");

                                    names.addAll(response.body().getData());


                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SubCat3.this,
                                            R.layout.spinner_item, names);

                                    spinner.setAdapter(adapter);


                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            if (position > 0) {
                                                tab = names.get(position);
                                            } else {
                                                tab = "";
                                            }


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    submit.setOnClickListener(v123 -> {


                                        String a = amount.getText().toString();
                                        String c = item.getCashValue();

                                        float aa = 0, cc = 0;

                                        try {

                                            aa = Float.parseFloat(a);
                                            cc = Float.parseFloat(c);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        if (tab.length() > 0) {
                                            if (aa > 0 && aa <= cc) {


                                                Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, tab);

                                                call1.enqueue(new Callback<pendingOrderBean>() {
                                                    @Override
                                                    public void onResponse(Call<pendingOrderBean> call28, Response<pendingOrderBean> response114) {


                                                        if (response114.body().getStatus().equals("1")) {

                                                            dialog13.dismiss();

                                                            Dialog dialog1 = new Dialog(SubCat3.this);
                                                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                            dialog1.setCancelable(true);
                                                            dialog1.setContentView(R.layout.pending_order_dialog);
                                                            dialog1.show();

                                                            TextView code = dialog1.findViewById(R.id.code);
                                                            TextView type17 = dialog1.findViewById(R.id.type);
                                                            TextView status = dialog1.findViewById(R.id.status);
                                                            TextView price = dialog1.findViewById(R.id.price);
                                                            TextView paid = dialog1.findViewById(R.id.paid);

                                                            TextView bill = dialog1.findViewById(R.id.bill);
                                                            TextView balance = dialog1.findViewById(R.id.balance);

                                                            Button ok = dialog1.findViewById(R.id.ok);
                                                            Button cancel = dialog1.findViewById(R.id.cancel);

                                                            cancel.setOnClickListener(v122 -> dialog1.dismiss());

                                                            Data item2 = response114.body().getData();

                                                            TextView text = dialog1.findViewById(R.id.text);

                                                            if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                                cancel.setVisibility(View.VISIBLE);
                                                                ok.setVisibility(View.VISIBLE);
                                                                text.setText("Update this order?");
                                                            } else {
                                                                cancel.setVisibility(View.GONE);
                                                                ok.setVisibility(View.GONE);
                                                                text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                            }

                                                            ok.setOnClickListener(v121 -> {


                                                                Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                                call2.enqueue(new Callback<scratchCardBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<scratchCardBean> call37, Response<scratchCardBean> response113) {

                                                                        Toast.makeText(SubCat3.this, response113.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                        dialog1.dismiss();

                                                                        loadPerks();

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<scratchCardBean> call37, Throwable t) {

                                                                    }
                                                                });


                                                            });


                                                            status.setText(item2.getStatus());

                                                            switch (item2.getText()) {
                                                                case "perks":
                                                                    type17.setText("ORDER NO. - " + item2.getId());
                                                                    code.setText("Item - " + item2.getCode());
                                                                    type17.setTextColor(Color.parseColor("#009688"));

                                                                    price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                    float pr = Float.parseFloat(item2.getPrice());
                                                                    float pa = Float.parseFloat(item2.getCashValue());

                                                                    paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);

                                                                    break;
                                                                case "cash":
                                                                    if (item2.getTableName().equals("")) {
                                                                        type17.setText("ORDER NO. - " + item2.getId());
                                                                    } else {
                                                                        type17.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item2.getClient());
                                                                    type17.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item2.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c = Float.parseFloat(item2.getCashRewards());
                                                                        float s = Float.parseFloat(item2.getScratchAmount());
                                                                        float t = Float.parseFloat(item2.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                                case "scratch":
                                                                    if (item2.getTableName().equals("")) {
                                                                        type17.setText("ORDER NO. - " + item2.getId());
                                                                    } else {
                                                                        type17.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item2.getClient());
                                                                    type17.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item2.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c = Float.parseFloat(item2.getCashRewards());
                                                                        float s = Float.parseFloat(item2.getScratchAmount());
                                                                        float t = Float.parseFloat(item2.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                            }


                                                        } else {
                                                            amo = "0";
                                                            scr = a;
                                                            cid = item.getId();

                                                            pho = phone;
                                                            tex = "";

                                                            dialog13.dismiss();

                                                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                            File newdir = new File(dir);
                                                            try {
                                                                newdir.mkdirs();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }


                                                            String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                            file = new File(fil);
                                                            try {
                                                                file.createNewFile();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                            startActivityForResult(getpic, 2);

                                                        }


                                                    }

                                                    @Override
                                                    public void onFailure(Call<pendingOrderBean> call28, Throwable t) {

                                                    }
                                                });


                                            } else {
                                                Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(SubCat3.this, "Please select a table", Toast.LENGTH_SHORT).show();
                                        }


                                    });


                                } else {


                                    Dialog dialog2 = new Dialog(SubCat3.this);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setCancelable(true);
                                    dialog2.setContentView(R.layout.amount_dialog);
                                    dialog2.show();


                                    EditText am = dialog2.findViewById(R.id.name);
                                    Button sub = dialog2.findViewById(R.id.submit);

                                    sub.setOnClickListener(v120 -> {

                                        String a = am.getText().toString();
                                        String c = item.getCashValue();

                                        float aa = 0, cc = 0;

                                        try {

                                            aa = Float.parseFloat(a);
                                            cc = Float.parseFloat(c);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (aa > 0 && aa <= cc) {


                                            Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                            call1.enqueue(new Callback<pendingOrderBean>() {
                                                @Override
                                                public void onResponse(Call<pendingOrderBean> call27, Response<pendingOrderBean> response112) {


                                                    if (response112.body().getStatus().equals("1")) {

                                                        dialog2.dismiss();
                                                        dialog.dismiss();

                                                        Dialog dialog1 = new Dialog(SubCat3.this);
                                                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog1.setCancelable(true);
                                                        dialog1.setContentView(R.layout.pending_order_dialog);
                                                        dialog1.show();

                                                        TextView code = dialog1.findViewById(R.id.code);
                                                        TextView type16 = dialog1.findViewById(R.id.type);
                                                        TextView status = dialog1.findViewById(R.id.status);
                                                        TextView price = dialog1.findViewById(R.id.price);
                                                        TextView paid = dialog1.findViewById(R.id.paid);

                                                        TextView bill = dialog1.findViewById(R.id.bill);
                                                        TextView balance = dialog1.findViewById(R.id.balance);

                                                        Button ok = dialog1.findViewById(R.id.ok);
                                                        Button cancel = dialog1.findViewById(R.id.cancel);

                                                        cancel.setOnClickListener(v119 -> dialog1.dismiss());

                                                        Data item2 = response112.body().getData();

                                                        TextView text = dialog1.findViewById(R.id.text);

                                                        if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                            cancel.setVisibility(View.VISIBLE);
                                                            ok.setVisibility(View.VISIBLE);
                                                            text.setText("Update this order?");
                                                        } else {
                                                            cancel.setVisibility(View.GONE);
                                                            ok.setVisibility(View.GONE);
                                                            text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                        }

                                                        ok.setOnClickListener(v118 -> {


                                                            Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call36, Response<scratchCardBean> response111) {

                                                                    Toast.makeText(SubCat3.this, response111.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call36, Throwable t) {

                                                                }
                                                            });


                                                        });


                                                        status.setText(item2.getStatus());

                                                        switch (item2.getText()) {
                                                            case "perks":
                                                                type16.setText("ORDER NO. - " + item2.getId());
                                                                code.setText("Item - " + item2.getCode());
                                                                type16.setTextColor(Color.parseColor("#009688"));

                                                                price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                float pr = Float.parseFloat(item2.getPrice());
                                                                float pa = Float.parseFloat(item2.getCashValue());

                                                                paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);

                                                                break;
                                                            case "cash":
                                                                if (item2.getTableName().equals("")) {
                                                                    type16.setText("ORDER NO. - " + item2.getId());
                                                                } else {
                                                                    type16.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item2.getClient());
                                                                type16.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item2.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c = Float.parseFloat(item2.getCashRewards());
                                                                    float s = Float.parseFloat(item2.getScratchAmount());
                                                                    float t = Float.parseFloat(item2.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                            case "scratch":
                                                                if (item2.getTableName().equals("")) {
                                                                    type16.setText("ORDER NO. - " + item2.getId());
                                                                } else {
                                                                    type16.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item2.getClient());
                                                                type16.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item2.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c = Float.parseFloat(item2.getCashRewards());
                                                                    float s = Float.parseFloat(item2.getScratchAmount());
                                                                    float t = Float.parseFloat(item2.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                        }


                                                    } else {
                                                        amo = "0";
                                                        tab = "";
                                                        scr = a;

                                                        cid = item.getId();

                                                        dialog2.dismiss();

                                                        pho = phone;
                                                        tex = "";

                                                        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                        File newdir = new File(dir);
                                                        try {
                                                            newdir.mkdirs();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                        String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                        file = new File(fil);
                                                        try {
                                                            file.createNewFile();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                        Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                        getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                        getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                        startActivityForResult(getpic, 2);

                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<pendingOrderBean> call27, Throwable t) {

                                                }
                                            });


                                        } else {
                                            Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                        }

                                    });


                                }


                            }
                            else if (type.equals("take_away")) {
                                take = "yes";

                                Dialog dialog2 = new Dialog(SubCat3.this);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setCancelable(true);
                                dialog2.setContentView(R.layout.amount_dialog);
                                dialog2.show();


                                EditText am = dialog2.findViewById(R.id.name);
                                Button sub = dialog2.findViewById(R.id.submit);

                                sub.setOnClickListener(v117 -> {

                                    String a = am.getText().toString();
                                    String c = item.getCashValue();

                                    float aa = 0, cc = 0;

                                    try {

                                        aa = Float.parseFloat(a);
                                        cc = Float.parseFloat(c);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (aa > 0 && aa <= cc) {


                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call26, Response<pendingOrderBean> response110) {


                                                if (response110.body().getStatus().equals("1")) {

                                                    dialog2.dismiss();
                                                    dialog.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type15 = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(v116 -> dialog1.dismiss());

                                                    Data item2 = response110.body().getData();

                                                    TextView text = dialog1.findViewById(R.id.text);

                                                    if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                        cancel.setVisibility(View.VISIBLE);
                                                        ok.setVisibility(View.VISIBLE);
                                                        text.setText("Update this order?");
                                                    } else {
                                                        cancel.setVisibility(View.GONE);
                                                        ok.setVisibility(View.GONE);
                                                        text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                    }

                                                    ok.setOnClickListener(v115 -> {


                                                        Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                            @Override
                                                            public void onResponse(Call<scratchCardBean> call35, Response<scratchCardBean> response19) {

                                                                Toast.makeText(SubCat3.this, response19.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                dialog1.dismiss();

                                                                loadPerks();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<scratchCardBean> call35, Throwable t) {

                                                            }
                                                        });


                                                    });


                                                    status.setText(item2.getStatus());

                                                    switch (item2.getText()) {
                                                        case "perks":
                                                            type15.setText("ORDER NO. - " + item2.getId());
                                                            code.setText("Item - " + item2.getCode());
                                                            type15.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item2.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item2.getPrice());
                                                            float pa = Float.parseFloat(item2.getCashValue());

                                                            paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            if (item2.getTableName().equals("")) {
                                                                type15.setText("ORDER NO. - " + item2.getId());
                                                            } else {
                                                                type15.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item2.getClient());
                                                            type15.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item2.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c = Float.parseFloat(item2.getCashRewards());
                                                                float s = Float.parseFloat(item2.getScratchAmount());
                                                                float t = Float.parseFloat(item2.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            if (item2.getTableName().equals("")) {
                                                                type15.setText("ORDER NO. - " + item2.getId());
                                                            } else {
                                                                type15.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item2.getClient());
                                                            type15.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item2.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c = Float.parseFloat(item2.getCashRewards());
                                                                float s = Float.parseFloat(item2.getScratchAmount());
                                                                float t = Float.parseFloat(item2.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                } else {
                                                    amo = "0";
                                                    tab = "";
                                                    scr = a;

                                                    cid = item.getId();

                                                    dialog2.dismiss();

                                                    pho = phone;
                                                    tex = "";

                                                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                    File newdir = new File(dir);
                                                    try {
                                                        newdir.mkdirs();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }


                                                    String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                    file = new File(fil);
                                                    try {
                                                        file.createNewFile();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                    Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                    getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    startActivityForResult(getpic, 2);

                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<pendingOrderBean> call26, Throwable t) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

                                });

                            }
                            else if (type.equals("both")) {
                                Dialog dialog4 = new Dialog(SubCat3.this);
                                dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog4.setCancelable(true);
                                dialog4.setContentView(R.layout.take_dialog);
                                dialog4.show();

                                Button di = dialog4.findViewById(R.id.button5);
                                Button ta = dialog4.findViewById(R.id.button6);


                                di.setOnClickListener(v114 -> {
                                    dialog4.dismiss();
                                    take = "no";


                                    if (response.body().getData().size() > 0) {


                                        Dialog dialog12 = new Dialog(SubCat3.this);
                                        dialog12.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog12.setCancelable(true);
                                        dialog12.setContentView(R.layout.table_dialog2);
                                        dialog12.show();


                                        List<String> names = new ArrayList<>();

                                        Spinner spinner = dialog12.findViewById(R.id.spinner);
                                        EditText amount = dialog12.findViewById(R.id.amount);
                                        Button submit = dialog12.findViewById(R.id.submit);

                                        names.add("Select table");

                                        names.addAll(response.body().getData());


                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SubCat3.this,
                                                R.layout.spinner_item, names);

                                        spinner.setAdapter(adapter);


                                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                if (position > 0) {
                                                    tab = names.get(position);
                                                } else {
                                                    tab = "";
                                                }


                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                        submit.setOnClickListener(v113 -> {


                                            String a = amount.getText().toString();
                                            String c = item.getCashValue();

                                            float aa = 0, cc = 0;

                                            try {

                                                aa = Float.parseFloat(a);
                                                cc = Float.parseFloat(c);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            if (tab.length() > 0) {
                                                if (aa > 0 && aa <= cc) {


                                                    Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, tab);

                                                    call1.enqueue(new Callback<pendingOrderBean>() {
                                                        @Override
                                                        public void onResponse(Call<pendingOrderBean> call25, Response<pendingOrderBean> response18) {


                                                            if (response18.body().getStatus().equals("1")) {

                                                                dialog12.dismiss();

                                                                Dialog dialog1 = new Dialog(SubCat3.this);
                                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setCancelable(true);
                                                                dialog1.setContentView(R.layout.pending_order_dialog);
                                                                dialog1.show();

                                                                TextView code = dialog1.findViewById(R.id.code);
                                                                TextView type14 = dialog1.findViewById(R.id.type);
                                                                TextView status = dialog1.findViewById(R.id.status);
                                                                TextView price = dialog1.findViewById(R.id.price);
                                                                TextView paid = dialog1.findViewById(R.id.paid);

                                                                TextView bill = dialog1.findViewById(R.id.bill);
                                                                TextView balance = dialog1.findViewById(R.id.balance);

                                                                Button ok = dialog1.findViewById(R.id.ok);
                                                                Button cancel = dialog1.findViewById(R.id.cancel);

                                                                cancel.setOnClickListener(v112 -> dialog1.dismiss());

                                                                Data item2 = response18.body().getData();

                                                                TextView text = dialog1.findViewById(R.id.text);

                                                                if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                                    cancel.setVisibility(View.VISIBLE);
                                                                    ok.setVisibility(View.VISIBLE);
                                                                    text.setText("Update this order?");
                                                                } else {
                                                                    cancel.setVisibility(View.GONE);
                                                                    ok.setVisibility(View.GONE);
                                                                    text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                                }

                                                                ok.setOnClickListener(v111 -> {


                                                                    Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                                    call2.enqueue(new Callback<scratchCardBean>() {
                                                                        @Override
                                                                        public void onResponse(Call<scratchCardBean> call34, Response<scratchCardBean> response17) {

                                                                            Toast.makeText(SubCat3.this, response17.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                            dialog1.dismiss();

                                                                            loadPerks();

                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<scratchCardBean> call34, Throwable t) {

                                                                        }
                                                                    });


                                                                });


                                                                status.setText(item2.getStatus());

                                                                switch (item2.getText()) {
                                                                    case "perks":
                                                                        type14.setText("ORDER NO. - " + item2.getId());
                                                                        code.setText("Item - " + item2.getCode());
                                                                        type14.setTextColor(Color.parseColor("#009688"));

                                                                        price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                        float pr = Float.parseFloat(item2.getPrice());
                                                                        float pa = Float.parseFloat(item2.getCashValue());

                                                                        paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);

                                                                        break;
                                                                    case "cash":
                                                                        if (item2.getTableName().equals("")) {
                                                                            type14.setText("ORDER NO. - " + item2.getId());
                                                                        } else {
                                                                            type14.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        }
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type14.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending")) {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        } else {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                    case "scratch":
                                                                        if (item2.getTableName().equals("")) {
                                                                            type14.setText("ORDER NO. - " + item2.getId());
                                                                        } else {
                                                                            type14.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        }
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type14.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending")) {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        } else {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                }


                                                            } else {
                                                                amo = "0";
                                                                scr = a;
                                                                cid = item.getId();

                                                                pho = phone;
                                                                tex = "";

                                                                dialog12.dismiss();

                                                                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                                File newdir = new File(dir);
                                                                try {
                                                                    newdir.mkdirs();
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }


                                                                String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                                file = new File(fil);
                                                                try {
                                                                    file.createNewFile();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                                Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                                getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                                startActivityForResult(getpic, 2);

                                                            }


                                                        }

                                                        @Override
                                                        public void onFailure(Call<pendingOrderBean> call25, Throwable t) {

                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(SubCat3.this, "Please select a table", Toast.LENGTH_SHORT).show();
                                            }


                                        });


                                    } else {


                                        Dialog dialog2 = new Dialog(SubCat3.this);
                                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog2.setCancelable(true);
                                        dialog2.setContentView(R.layout.amount_dialog);
                                        dialog2.show();


                                        EditText am = dialog2.findViewById(R.id.name);
                                        Button sub = dialog2.findViewById(R.id.submit);

                                        sub.setOnClickListener(v110 -> {

                                            String a = am.getText().toString();
                                            String c = item.getCashValue();

                                            float aa = 0, cc = 0;

                                            try {

                                                aa = Float.parseFloat(a);
                                                cc = Float.parseFloat(c);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            if (aa > 0 && aa <= cc) {


                                                Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                                call1.enqueue(new Callback<pendingOrderBean>() {
                                                    @Override
                                                    public void onResponse(Call<pendingOrderBean> call24, Response<pendingOrderBean> response16) {


                                                        if (response16.body().getStatus().equals("1")) {

                                                            dialog2.dismiss();
                                                            dialog.dismiss();

                                                            Dialog dialog1 = new Dialog(SubCat3.this);
                                                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                            dialog1.setCancelable(true);
                                                            dialog1.setContentView(R.layout.pending_order_dialog);
                                                            dialog1.show();

                                                            TextView code = dialog1.findViewById(R.id.code);
                                                            TextView type13 = dialog1.findViewById(R.id.type);
                                                            TextView status = dialog1.findViewById(R.id.status);
                                                            TextView price = dialog1.findViewById(R.id.price);
                                                            TextView paid = dialog1.findViewById(R.id.paid);

                                                            TextView bill = dialog1.findViewById(R.id.bill);
                                                            TextView balance = dialog1.findViewById(R.id.balance);

                                                            Button ok = dialog1.findViewById(R.id.ok);
                                                            Button cancel = dialog1.findViewById(R.id.cancel);

                                                            cancel.setOnClickListener(v19 -> dialog1.dismiss());

                                                            Data item2 = response16.body().getData();

                                                            TextView text = dialog1.findViewById(R.id.text);

                                                            if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                                cancel.setVisibility(View.VISIBLE);
                                                                ok.setVisibility(View.VISIBLE);
                                                                text.setText("Update this order?");
                                                            } else {
                                                                cancel.setVisibility(View.GONE);
                                                                ok.setVisibility(View.GONE);
                                                                text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                            }

                                                            ok.setOnClickListener(v18 -> {


                                                                Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                                call2.enqueue(new Callback<scratchCardBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<scratchCardBean> call33, Response<scratchCardBean> response15) {

                                                                        Toast.makeText(SubCat3.this, response15.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                        dialog1.dismiss();

                                                                        loadPerks();

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<scratchCardBean> call33, Throwable t) {

                                                                    }
                                                                });


                                                            });


                                                            status.setText(item2.getStatus());

                                                            switch (item2.getText()) {
                                                                case "perks":
                                                                    type13.setText("ORDER NO. - " + item2.getId());
                                                                    code.setText("Item - " + item2.getCode());
                                                                    type13.setTextColor(Color.parseColor("#009688"));

                                                                    price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                    float pr = Float.parseFloat(item2.getPrice());
                                                                    float pa = Float.parseFloat(item2.getCashValue());

                                                                    paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);

                                                                    break;
                                                                case "cash":
                                                                    if (item2.getTableName().equals("")) {
                                                                        type13.setText("ORDER NO. - " + item2.getId());
                                                                    } else {
                                                                        type13.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item2.getClient());
                                                                    type13.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item2.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c = Float.parseFloat(item2.getCashRewards());
                                                                        float s = Float.parseFloat(item2.getScratchAmount());
                                                                        float t = Float.parseFloat(item2.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                                case "scratch":
                                                                    if (item2.getTableName().equals("")) {
                                                                        type13.setText("ORDER NO. - " + item2.getId());
                                                                    } else {
                                                                        type13.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                    }
                                                                    code.setText("Shop - " + item2.getClient());
                                                                    type13.setTextColor(Color.parseColor("#689F38"));

                                                                    price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                    paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                    //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                    //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                    if (item2.getStatus().equals("pending")) {

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                    } else {

                                                                        float c = Float.parseFloat(item2.getCashRewards());
                                                                        float s = Float.parseFloat(item2.getScratchAmount());
                                                                        float t = Float.parseFloat(item2.getBillAmount());

                                                                        bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                        balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                    }

                                                                    paid.setVisibility(View.VISIBLE);
                                                                    price.setVisibility(View.VISIBLE);
                                                                    break;
                                                            }


                                                        } else {
                                                            amo = "0";
                                                            tab = "";
                                                            scr = a;

                                                            cid = item.getId();

                                                            dialog2.dismiss();

                                                            pho = phone;
                                                            tex = "";

                                                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                            File newdir = new File(dir);
                                                            try {
                                                                newdir.mkdirs();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }


                                                            String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                            file = new File(fil);
                                                            try {
                                                                file.createNewFile();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                            startActivityForResult(getpic, 2);

                                                        }


                                                    }

                                                    @Override
                                                    public void onFailure(Call<pendingOrderBean> call24, Throwable t) {

                                                    }
                                                });


                                            } else {
                                                Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                            }

                                        });


                                    }


                                });


                                ta.setOnClickListener(v17 -> {

                                    dialog4.dismiss();


                                    take = "yes";

                                    Dialog dialog2 = new Dialog(SubCat3.this);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setCancelable(true);
                                    dialog2.setContentView(R.layout.amount_dialog);
                                    dialog2.show();


                                    EditText am = dialog2.findViewById(R.id.name);
                                    Button sub = dialog2.findViewById(R.id.submit);

                                    sub.setOnClickListener(v16 -> {

                                        String a = am.getText().toString();
                                        String c = item.getCashValue();

                                        float aa = 0, cc = 0;

                                        try {

                                            aa = Float.parseFloat(a);
                                            cc = Float.parseFloat(c);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (aa > 0 && aa <= cc) {


                                            Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                            call1.enqueue(new Callback<pendingOrderBean>() {
                                                @Override
                                                public void onResponse(Call<pendingOrderBean> call23, Response<pendingOrderBean> response14) {


                                                    if (response14.body().getStatus().equals("1")) {

                                                        dialog2.dismiss();
                                                        dialog.dismiss();

                                                        Dialog dialog1 = new Dialog(SubCat3.this);
                                                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog1.setCancelable(true);
                                                        dialog1.setContentView(R.layout.pending_order_dialog);
                                                        dialog1.show();

                                                        TextView code = dialog1.findViewById(R.id.code);
                                                        TextView type12 = dialog1.findViewById(R.id.type);
                                                        TextView status = dialog1.findViewById(R.id.status);
                                                        TextView price = dialog1.findViewById(R.id.price);
                                                        TextView paid = dialog1.findViewById(R.id.paid);

                                                        TextView bill = dialog1.findViewById(R.id.bill);
                                                        TextView balance = dialog1.findViewById(R.id.balance);

                                                        Button ok = dialog1.findViewById(R.id.ok);
                                                        Button cancel = dialog1.findViewById(R.id.cancel);

                                                        cancel.setOnClickListener(v15 -> dialog1.dismiss());

                                                        Data item2 = response14.body().getData();

                                                        TextView text = dialog1.findViewById(R.id.text);

                                                        if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                            cancel.setVisibility(View.VISIBLE);
                                                            ok.setVisibility(View.VISIBLE);
                                                            text.setText("Update this order?");
                                                        } else {
                                                            cancel.setVisibility(View.GONE);
                                                            ok.setVisibility(View.GONE);
                                                            text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                        }

                                                        ok.setOnClickListener(v14 -> {


                                                            Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call32, Response<scratchCardBean> response13) {

                                                                    Toast.makeText(SubCat3.this, response13.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call32, Throwable t) {

                                                                }
                                                            });


                                                        });


                                                        status.setText(item2.getStatus());

                                                        switch (item2.getText()) {
                                                            case "perks":
                                                                type12.setText("ORDER NO. - " + item2.getId());
                                                                code.setText("Item - " + item2.getCode());
                                                                type12.setTextColor(Color.parseColor("#009688"));

                                                                price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                float pr = Float.parseFloat(item2.getPrice());
                                                                float pa = Float.parseFloat(item2.getCashValue());

                                                                paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);

                                                                break;
                                                            case "cash":
                                                                if (item2.getTableName().equals("")) {
                                                                    type12.setText("ORDER NO. - " + item2.getId());
                                                                } else {
                                                                    type12.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item2.getClient());
                                                                type12.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item2.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c = Float.parseFloat(item2.getCashRewards());
                                                                    float s = Float.parseFloat(item2.getScratchAmount());
                                                                    float t = Float.parseFloat(item2.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                            case "scratch":
                                                                if (item2.getTableName().equals("")) {
                                                                    type12.setText("ORDER NO. - " + item2.getId());
                                                                } else {
                                                                    type12.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                }
                                                                code.setText("Shop - " + item2.getClient());
                                                                type12.setTextColor(Color.parseColor("#689F38"));

                                                                price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                if (item2.getStatus().equals("pending")) {

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                } else {

                                                                    float c = Float.parseFloat(item2.getCashRewards());
                                                                    float s = Float.parseFloat(item2.getScratchAmount());
                                                                    float t = Float.parseFloat(item2.getBillAmount());

                                                                    bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                    balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                                }

                                                                paid.setVisibility(View.VISIBLE);
                                                                price.setVisibility(View.VISIBLE);
                                                                break;
                                                        }


                                                    } else {
                                                        amo = "0";
                                                        tab = "";
                                                        scr = a;

                                                        cid = item.getId();

                                                        dialog2.dismiss();

                                                        pho = phone;
                                                        tex = "";

                                                        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                        File newdir = new File(dir);
                                                        try {
                                                            newdir.mkdirs();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                        String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                        file = new File(fil);
                                                        try {
                                                            file.createNewFile();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                        Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                        getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                        getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                        startActivityForResult(getpic, 2);

                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<pendingOrderBean> call23, Throwable t) {

                                                }
                                            });


                                        } else {
                                            Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                        }

                                    });


                                });

                            }
                            else {
                                take = "no";

                                Dialog dialog2 = new Dialog(SubCat3.this);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setCancelable(true);
                                dialog2.setContentView(R.layout.amount_dialog);
                                dialog2.show();


                                EditText am = dialog2.findViewById(R.id.name);
                                Button sub = dialog2.findViewById(R.id.submit);

                                sub.setOnClickListener(v13 -> {

                                    String a = am.getText().toString();
                                    String c = item.getCashValue();

                                    float aa = 0, cc = 0;

                                    try {

                                        aa = Float.parseFloat(a);
                                        cc = Float.parseFloat(c);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (aa > 0 && aa <= cc) {


                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid"), client, "");

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call22, Response<pendingOrderBean> response12) {


                                                if (response12.body().getStatus().equals("1")) {

                                                    dialog2.dismiss();
                                                    dialog.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type1 = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(v12 -> dialog1.dismiss());

                                                    Data item2 = response12.body().getData();

                                                    TextView text = dialog1.findViewById(R.id.text);

                                                    if (item2.getDeviceId().equals(SharePreferenceUtils.getInstance().getString("userid"))) {
                                                        cancel.setVisibility(View.VISIBLE);
                                                        ok.setVisibility(View.VISIBLE);
                                                        text.setText("Update this order?");
                                                    } else {
                                                        cancel.setVisibility(View.GONE);
                                                        ok.setVisibility(View.GONE);
                                                        text.setText("If you wish to split the bill, then transfer your scratch cards/ cash rewards to that user who has made the order.");
                                                    }

                                                    ok.setOnClickListener(v1 -> {


                                                        Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId(), "0", a, item.getId());
                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                            @Override
                                                            public void onResponse(Call<scratchCardBean> call3, Response<scratchCardBean> response1) {

                                                                Toast.makeText(SubCat3.this, response1.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                dialog1.dismiss();

                                                                loadPerks();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<scratchCardBean> call3, Throwable t) {

                                                            }
                                                        });


                                                    });


                                                    status.setText(item2.getStatus());

                                                    switch (item2.getText()) {
                                                        case "perks":
                                                            type1.setText("ORDER NO. - " + item2.getId());
                                                            code.setText("Item - " + item2.getCode());
                                                            type1.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item2.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item2.getPrice());
                                                            float pa = Float.parseFloat(item2.getCashValue());

                                                            paid.setText("Pending benefits - " + (pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            if (item2.getTableName().equals("")) {
                                                                type1.setText("ORDER NO. - " + item2.getId());
                                                            } else {
                                                                type1.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item2.getClient());
                                                            type1.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item2.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c = Float.parseFloat(item2.getCashRewards());
                                                                float s = Float.parseFloat(item2.getScratchAmount());
                                                                float t = Float.parseFloat(item2.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            if (item2.getTableName().equals("")) {
                                                                type1.setText("ORDER NO. - " + item2.getId());
                                                            } else {
                                                                type1.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                            }
                                                            code.setText("Shop - " + item2.getClient());
                                                            type1.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item2.getStatus().equals("pending")) {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            } else {

                                                                float c = Float.parseFloat(item2.getCashRewards());
                                                                float s = Float.parseFloat(item2.getScratchAmount());
                                                                float t = Float.parseFloat(item2.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + (t - (c + s))));


                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                } else {
                                                    amo = "0";
                                                    tab = "";
                                                    scr = a;

                                                    cid = item.getId();

                                                    dialog2.dismiss();

                                                    pho = phone;
                                                    tex = "";

                                                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                                                    File newdir = new File(dir);
                                                    try {
                                                        newdir.mkdirs();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }


                                                    String fil = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                                                    file = new File(fil);
                                                    try {
                                                        file.createNewFile();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    uri = FileProvider.getUriForFile(SubCat3.this, BuildConfig.APPLICATION_ID + ".provider", file);

                                                    Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                                    getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    startActivityForResult(getpic, 2);

                                                }


                                            }

                                            @Override
                                            public void onFailure(Call<pendingOrderBean> call22, Throwable t) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

                                });


                            }



                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<tablebean> call, Throwable t) {
                            bar.setVisibility(View.GONE);
                        }
                    });*/


                });


            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView text;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }


}
