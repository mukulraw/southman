package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.ScratchTextView;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.pendingOrderPOJO.Data;
import com.sc.bigboss.bigboss.pendingOrderPOJO.pendingOrderBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.Datum;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import com.sc.bigboss.bigboss.subCat3POJO.subCat3Bean;
import com.tarek360.instacapture.Instacapture;
import com.tarek360.instacapture.listener.SimpleScreenCapturingListener;

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

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    CardAdapter adapter;

    List<Datum> list;

    ProgressBar bar;

    String id;

    TextView title;

    ImageView search, home;

    ConnectionDetector cd;

    String catName, base, client;

    LinearLayout linear;

    ImageView notification, perks2;

    Uri uri;
    File file;

    String ph, co, bann;

    TextView perks;

    String p;

    String pho = "", tex = "";

    String phone;

    Button upload;

    ImageView banner;

    String tab = "", amo = "0", scr = "0", cid = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat3);

        cd = new ConnectionDetector(SubCat3.this);

        toolbar = findViewById(R.id.toolbar);
        linear = findViewById(R.id.linear);

        banner = findViewById(R.id.banner);

        perks = findViewById(R.id.perks);
        upload = findViewById(R.id.upload);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SubCat3.this, Notification.class);
                startActivity(i);
            }
        });

        perks2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubCat3.this, Perks.class);
                startActivity(intent);
            }
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SubCat3.this, Search.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubCat3.this, MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
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


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                            for (int i = 0; i < response.body().getData().size(); i++) {
                                names.add(response.body().getData().get(i));
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SubCat3.this,
                                    R.layout.spinner_item, names);

                            spinner.setAdapter(adapter);


                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    tab = names.get(position);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String a = amount.getText().toString();
                                    String c = p;

                                    float aa = Float.parseFloat(a);
                                    float cc = Float.parseFloat(c);

                                    if (aa > 0 && aa <= cc) {





                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid") , client);

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call, Response<pendingOrderBean> response) {


                                                if (response.body().getStatus().equals("1"))
                                                {

                                                    dialog.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            dialog1.dismiss();

                                                        }
                                                    });

                                                    Data item = response.body().getData();


                                                    ok.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {





                                                            Call<scratchCardBean> call2 = cr.updateOrder(item.getId() , a , "0");
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                                    Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call, Throwable t) {

                                                                }
                                                            });





                                                        }
                                                    });




                                                    status.setText(item.getStatus());

                                                    switch (item.getText()) {
                                                        case "perks":
                                                            type.setText("ORDER NO. - " + item.getId());
                                                            code.setText("Item - " + item.getCode());
                                                            type.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item.getPrice());
                                                            float pa = Float.parseFloat(item.getCashValue());

                                                            paid.setText("Pending benefits - " + String.valueOf(pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            code.setText("Shop - " + item.getClient());
                                                            type.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending"))
                                                            {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            }
                                                            else
                                                            {

                                                                float c = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            code.setText("Shop - " + item.getClient());
                                                            type.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending"))
                                                            {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            }
                                                            else
                                                            {

                                                                float c = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                }
                                                else
                                                {
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
                                            public void onFailure(Call<pendingOrderBean> call, Throwable t) {

                                            }
                                        });









                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

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

                            sub.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String a = am.getText().toString();
                                    String c = p;

                                    float aa = Float.parseFloat(a);
                                    float cc = Float.parseFloat(c);

                                    if (aa > 0 && aa <= cc) {



                                        Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid") , client);

                                        call1.enqueue(new Callback<pendingOrderBean>() {
                                            @Override
                                            public void onResponse(Call<pendingOrderBean> call, Response<pendingOrderBean> response) {


                                                if (response.body().getStatus().equals("1"))
                                                {

                                                    dialog2.dismiss();

                                                    Dialog dialog1 = new Dialog(SubCat3.this);
                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog1.setCancelable(true);
                                                    dialog1.setContentView(R.layout.pending_order_dialog);
                                                    dialog1.show();

                                                    TextView code = dialog1.findViewById(R.id.code);
                                                    TextView type = dialog1.findViewById(R.id.type);
                                                    TextView status = dialog1.findViewById(R.id.status);
                                                    TextView price = dialog1.findViewById(R.id.price);
                                                    TextView paid = dialog1.findViewById(R.id.paid);

                                                    TextView bill = dialog1.findViewById(R.id.bill);
                                                    TextView balance = dialog1.findViewById(R.id.balance);

                                                    Button ok = dialog1.findViewById(R.id.ok);
                                                    Button cancel = dialog1.findViewById(R.id.cancel);

                                                    cancel.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            dialog1.dismiss();

                                                        }
                                                    });

                                                    Data item = response.body().getData();


                                                    ok.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {





                                                            Call<scratchCardBean> call2 = cr.updateOrder(item.getId() , a , "0");
                                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                                @Override
                                                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                                    Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    dialog1.dismiss();

                                                                    loadPerks();

                                                                }

                                                                @Override
                                                                public void onFailure(Call<scratchCardBean> call, Throwable t) {

                                                                }
                                                            });





                                                        }
                                                    });




                                                    status.setText(item.getStatus());

                                                    switch (item.getText()) {
                                                        case "perks":
                                                            type.setText("ORDER NO. - " + item.getId());
                                                            code.setText("Item - " + item.getCode());
                                                            type.setTextColor(Color.parseColor("#009688"));

                                                            price.setText("Benefits - " + item.getPrice() + " credits");

                                                            float pr = Float.parseFloat(item.getPrice());
                                                            float pa = Float.parseFloat(item.getCashValue());

                                                            paid.setText("Pending benefits - " + String.valueOf(pr - pa) + " credits");

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);

                                                            break;
                                                        case "cash":
                                                            type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            code.setText("Shop - " + item.getClient());
                                                            type.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending"))
                                                            {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            }
                                                            else
                                                            {

                                                                float c = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                        case "scratch":
                                                            type.setText("ORDER NO. - " + item.getId() + " (Table - " + item.getTableName() + ")");
                                                            code.setText("Shop - " + item.getClient());
                                                            type.setTextColor(Color.parseColor("#689F38"));

                                                            price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item.getCashRewards()));
                                                            paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                            //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                            //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                            if (item.getStatus().equals("pending"))
                                                            {

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                            }
                                                            else
                                                            {

                                                                float c = Float.parseFloat(item.getCashRewards());
                                                                float s = Float.parseFloat(item.getScratchAmount());
                                                                float t = Float.parseFloat(item.getBillAmount());

                                                                bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item.getBillAmount()));
                                                                balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                            }

                                                            paid.setVisibility(View.VISIBLE);
                                                            price.setVisibility(View.VISIBLE);
                                                            break;
                                                    }


                                                }
                                                else
                                                {
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
                                            public void onFailure(Call<pendingOrderBean> call, Throwable t) {

                                            }
                                        });








                                    } else {
                                        Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                    }

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
        });

        count = findViewById(R.id.count);

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals("count")) {
                    loadPerks();
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));


    }

    BroadcastReceiver singleReceiver;
    TextView count;

    public class MAdapter extends RecyclerView.Adapter<MAdapter.MyViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();


        public MAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;


        }


        @NonNull
        @Override
        public MAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.category_list_model, viewGroup, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MAdapter.MyViewHolder myViewHolder, int i) {


            final Datum item = list.get(i);

            //  myViewHolder.name.setText(item.getSubcatName());


/*
            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(base + "bigboss/admin2/upload/sub_cat1/" + item.getImageUrl(), myViewHolder.imageView, options);
*/

//            Glide.with(context).load(base + "bigboss/admin2/upload/sub_cat1/" + item.getImageUrl()).into(myViewHolder.imageView);


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    /*if (catName.equals("redeem store"))
                    {
                        Intent i = new Intent(context, ProductList3.class);
                        i.putExtra("id", item.getId());
                        i.putExtra("text", item.getSubcatName());
                        i.putExtra("catname", catName);
                        i.putExtra("phone", item.getPhone());
                        i.putExtra("client", client);
                        context.startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(context, ProductList2.class);
                        i.putExtra("id", item.getId());
                        i.putExtra("text", item.getSubcatName());
                        i.putExtra("catname", catName);
                        i.putExtra("phone", item.getPhone());
                        i.putExtra("client", client);
                        context.startActivity(i);
                    }*/


                }
            });

        }

        public void setgrid(List<Datum> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            // TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.tshirt);

                //name = itemView.findViewById(R.id.name);


            }
        }
    }

    void loadPerks() {

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

        Call<getPerksBean> call = cr.getPerks(android_id);


        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {

                if (response.body().getStatus().equals("1")) {
                    perks.setText("Cash reward remaining : " + response.body().getData().get(0).getCashRewards());
                    p = response.body().getData().get(0).getCashRewards();

                }

                Call<scratchCardBean> call1 = cr.getScratchCards(response.body().getData().get(0).getId(), client);

                call1.enqueue(new Callback<scratchCardBean>() {
                    @Override
                    public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                        if (response1.body().getData().size() > 0) {
                            adapter = new CardAdapter(SubCat3.this, response1.body().getData());
                            manager = new GridLayoutManager(SubCat3.this, 2);
                            grid.setAdapter(adapter);
                            grid.setLayoutManager(manager);
                            linear.setVisibility(View.GONE);

                        } else {
                            adapter = new CardAdapter(SubCat3.this, response1.body().getData());
                            manager = new GridLayoutManager(SubCat3.this, 2);
                            grid.setAdapter(adapter);
                            grid.setLayoutManager(manager);
                            linear.setVisibility(View.VISIBLE);
                        }


                        bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<scratchCardBean> call, Throwable t) {
                        bar.setVisibility(View.GONE);
                    }
                });

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
            View view = inflater.inflate(R.layout.scratch_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.text.setText(item.getText());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.scratch_dialog);
                    dialog.show();

                    ScratchTextView scratch = dialog.findViewById(R.id.scratch);
                    Button share = dialog.findViewById(R.id.share);

                    scratch.setText("You have got Rs." + item.getCashValue());

                    scratch.setRevealListener(new ScratchTextView.IRevealListener() {
                        @Override
                        public void onRevealed(ScratchTextView tv) {

                            share.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {

                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            dialog.dismiss();


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


                                        for (int i = 0; i < response.body().getData().size(); i++) {
                                            names.add(response.body().getData().get(i));
                                        }


                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SubCat3.this,
                                                R.layout.spinner_item, names);

                                        spinner.setAdapter(adapter);


                                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                tab = names.get(position);

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {



                                                String a = amount.getText().toString();
                                                String c = item.getCashValue();

                                                float aa = Float.parseFloat(a);
                                                float cc = Float.parseFloat(c);

                                                if (aa > 0 && aa <= cc) {


                                                    Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid") , client);

                                                    call1.enqueue(new Callback<pendingOrderBean>() {
                                                        @Override
                                                        public void onResponse(Call<pendingOrderBean> call, Response<pendingOrderBean> response) {


                                                            if (response.body().getStatus().equals("1"))
                                                            {

                                                                dialog.dismiss();

                                                                Dialog dialog1 = new Dialog(SubCat3.this);
                                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setCancelable(true);
                                                                dialog1.setContentView(R.layout.pending_order_dialog);
                                                                dialog1.show();

                                                                TextView code = dialog1.findViewById(R.id.code);
                                                                TextView type = dialog1.findViewById(R.id.type);
                                                                TextView status = dialog1.findViewById(R.id.status);
                                                                TextView price = dialog1.findViewById(R.id.price);
                                                                TextView paid = dialog1.findViewById(R.id.paid);

                                                                TextView bill = dialog1.findViewById(R.id.bill);
                                                                TextView balance = dialog1.findViewById(R.id.balance);

                                                                Button ok = dialog1.findViewById(R.id.ok);
                                                                Button cancel = dialog1.findViewById(R.id.cancel);

                                                                cancel.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                        dialog1.dismiss();

                                                                    }
                                                                });

                                                                Data item2 = response.body().getData();


                                                                ok.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {





                                                                        Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId() ,  "0" , a , item.getId());
                                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                                            @Override
                                                                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                                dialog1.dismiss();

                                                                                loadPerks();

                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<scratchCardBean> call, Throwable t) {

                                                                            }
                                                                        });





                                                                    }
                                                                });




                                                                status.setText(item2.getStatus());

                                                                switch (item2.getText()) {
                                                                    case "perks":
                                                                        type.setText("ORDER NO. - " + item2.getId());
                                                                        code.setText("Item - " + item2.getCode());
                                                                        type.setTextColor(Color.parseColor("#009688"));

                                                                        price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                        float pr = Float.parseFloat(item2.getPrice());
                                                                        float pa = Float.parseFloat(item2.getCashValue());

                                                                        paid.setText("Pending benefits - " + String.valueOf(pr - pa) + " credits");

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);

                                                                        break;
                                                                    case "cash":
                                                                        type.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending"))
                                                                        {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        }
                                                                        else
                                                                        {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                    case "scratch":
                                                                        type.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending"))
                                                                        {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        }
                                                                        else
                                                                        {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                }


                                                            }
                                                            else
                                                            {
                                                                amo = "0";
                                                                scr = a;
                                                                cid = item.getId();

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
                                                                startActivityForResult(getpic, 2);

                                                            }


                                                        }

                                                        @Override
                                                        public void onFailure(Call<pendingOrderBean> call, Throwable t) {

                                                        }
                                                    });




                                                } else {
                                                    Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                                }

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

                                        sub.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String a = am.getText().toString();
                                                String c = item.getCashValue();

                                                float aa = Float.parseFloat(a);
                                                float cc = Float.parseFloat(c);

                                                if (aa > 0 && aa <= cc) {




                                                    Call<pendingOrderBean> call1 = cr.getPending(SharePreferenceUtils.getInstance().getString("userid") , client);

                                                    call1.enqueue(new Callback<pendingOrderBean>() {
                                                        @Override
                                                        public void onResponse(Call<pendingOrderBean> call, Response<pendingOrderBean> response) {


                                                            if (response.body().getStatus().equals("1"))
                                                            {

                                                                dialog2.dismiss();
                                                                dialog.dismiss();

                                                                Dialog dialog1 = new Dialog(SubCat3.this);
                                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setCancelable(true);
                                                                dialog1.setContentView(R.layout.pending_order_dialog);
                                                                dialog1.show();

                                                                TextView code = dialog1.findViewById(R.id.code);
                                                                TextView type = dialog1.findViewById(R.id.type);
                                                                TextView status = dialog1.findViewById(R.id.status);
                                                                TextView price = dialog1.findViewById(R.id.price);
                                                                TextView paid = dialog1.findViewById(R.id.paid);

                                                                TextView bill = dialog1.findViewById(R.id.bill);
                                                                TextView balance = dialog1.findViewById(R.id.balance);

                                                                Button ok = dialog1.findViewById(R.id.ok);
                                                                Button cancel = dialog1.findViewById(R.id.cancel);

                                                                cancel.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                        dialog1.dismiss();

                                                                    }
                                                                });

                                                                Data item2 = response.body().getData();


                                                                ok.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {





                                                                        Call<scratchCardBean> call2 = cr.updateOrder2(item2.getId() ,  "0" , a , item.getId());
                                                                        call2.enqueue(new Callback<scratchCardBean>() {
                                                                            @Override
                                                                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                                dialog1.dismiss();

                                                                                loadPerks();

                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<scratchCardBean> call, Throwable t) {

                                                                            }
                                                                        });





                                                                    }
                                                                });




                                                                status.setText(item2.getStatus());

                                                                switch (item2.getText()) {
                                                                    case "perks":
                                                                        type.setText("ORDER NO. - " + item2.getId());
                                                                        code.setText("Item - " + item2.getCode());
                                                                        type.setTextColor(Color.parseColor("#009688"));

                                                                        price.setText("Benefits - " + item2.getPrice() + " credits");

                                                                        float pr = Float.parseFloat(item2.getPrice());
                                                                        float pa = Float.parseFloat(item2.getCashValue());

                                                                        paid.setText("Pending benefits - " + String.valueOf(pr - pa) + " credits");

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);

                                                                        break;
                                                                    case "cash":
                                                                        type.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending"))
                                                                        {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        }
                                                                        else
                                                                        {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                    case "scratch":
                                                                        type.setText("ORDER NO. - " + item2.getId() + " (Table - " + item2.getTableName() + ")");
                                                                        code.setText("Shop - " + item2.getClient());
                                                                        type.setTextColor(Color.parseColor("#689F38"));

                                                                        price.setText(Html.fromHtml("<font color=#000000>Cash discount</font> - Rs." + item2.getCashRewards()));
                                                                        paid.setText(Html.fromHtml("<font color=#000000>Scratch discount</font> - Rs." + item2.getScratchAmount()));

//                    float pr1 = Float.parseFloat(item.getPrice());
                                                                        //                  float pa1 = Float.parseFloat(item.getCashValue());

                                                                        //                holder.paid.setText("Balance pay - Rs." + String.valueOf(pr1 - pa1));

                                                                        if (item2.getStatus().equals("pending"))
                                                                        {

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - unverified"));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - unverified"));

                                                                        }
                                                                        else
                                                                        {

                                                                            float c = Float.parseFloat(item2.getCashRewards());
                                                                            float s = Float.parseFloat(item2.getScratchAmount());
                                                                            float t = Float.parseFloat(item2.getBillAmount());

                                                                            bill.setText(Html.fromHtml("<font color=#000000>Total bill</font> - Rs." + item2.getBillAmount()));
                                                                            balance.setText(Html.fromHtml("<font color=#000000>Balance pay</font> - Rs." + String.valueOf(t - (c + s))));



                                                                        }

                                                                        paid.setVisibility(View.VISIBLE);
                                                                        price.setVisibility(View.VISIBLE);
                                                                        break;
                                                                }


                                                            }
                                                            else
                                                            {
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
                                                        public void onFailure(Call<pendingOrderBean> call, Throwable t) {

                                                        }
                                                    });








                                                } else {
                                                    Toast.makeText(SubCat3.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                                }

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
                    });


                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView text;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                PackageInfo info = getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            File file2 = null;

            try {
                file2 = new Compressor(SubCat3.this).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                body = MultipartBody.Part.createFormData("bill", file2.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }


            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            bar.setVisibility(View.VISIBLE);


            Call<scratchCardBean> call = cr.buyCash(
                    SharePreferenceUtils.getInstance().getString("userid"),
                    client,
                    amo,
                    "cash",
                    tab,
                    amo,
                    scr,
                    body
            );


            call.enqueue(new Callback<scratchCardBean>() {
                @Override
                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    loadPerks();

                    bar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                    bar.setVisibility(View.GONE);
                }
            });


        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                PackageInfo info = getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            File file2 = null;

            try {
                file2 = new Compressor(SubCat3.this).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                body = MultipartBody.Part.createFormData("bill", file2.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }


            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            bar.setVisibility(View.VISIBLE);


            Call<scratchCardBean> call = cr.redeem(
                    cid,
                    SharePreferenceUtils.getInstance().getString("userid"),
                    scr,
                    client,
                    "scratch",
                    tab,
                    scr,
                    body
            );


            call.enqueue(new Callback<scratchCardBean>() {
                @Override
                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    loadPerks();

                    bar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                    bar.setVisibility(View.GONE);
                }
            });


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }



}
