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

import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
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


    String baa;

    TextView billAmount , tid , status , cashdiscount , scratchcard , bill , balance;


    Button confirmandpay , deleteorder;

    String oid , ttiidd , tbill;

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

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


        deleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog = new Dialog(SubCat3.this);
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

                        bar.setVisibility(View.VISIBLE);

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

                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                                bar.setVisibility(View.GONE);

                                loadPerks();

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



        confirmandpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                float tb = Float.parseFloat(tbill);

                if (tb > 0)
                {

                    Dialog dialog = new Dialog(SubCat3.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.payment_mode_dialog);
                    dialog.show();

                    Button gpay = dialog.findViewById(R.id.button7);
                    Button cash = dialog.findViewById(R.id.button9);

                    gpay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();



                            try {


                                Uri uri = new Uri.Builder()
                                        .scheme("upi")
                                        .authority("pay")
                                        .appendQueryParameter("pa", "southman@sbi")
                                        .appendQueryParameter("pn", "South Man")
                                        .appendQueryParameter("mc", "BCR2DN6T6WEP3JDV")
                                        .appendQueryParameter("tr", ttiidd)
                                        .appendQueryParameter("tn", "Redeem Store Order")
                                        .appendQueryParameter("am", String.valueOf(tbill))
                                        .appendQueryParameter("cu", "INR")
                                        .appendQueryParameter("url", "https://southman.in")
                                        .build();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(uri);
                                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                                startActivityForResult(intent, TEZ_REQUEST_CODE);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(SubCat3.this, "You don't have Google Pay app installed", Toast.LENGTH_SHORT).show();
                            }





                        }
                    });

                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                            Intent intent = new Intent(SubCat3.this , StatusActivity4.class);
                            intent.putExtra("id" , oid);
                            intent.putExtra("pid" , id);
                            intent.putExtra("sta" , "success");
                            intent.putExtra("amount" , baa);
                            startActivity(intent);


                        }
                    });

                }
                else
                {

                    Dialog dialog = new Dialog(SubCat3.this);
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

                            Intent intent = new Intent(SubCat3.this , StatusActivity3.class);
                            intent.putExtra("id" , oid);
                            intent.putExtra("pid" , id);
                            intent.putExtra("sta" , "success");
                            intent.putExtra("amount" , baa);
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

                    Dialog dialog = new Dialog(SubCat3.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.add_cash_discount_dialog);
                    dialog.show();


                    TextView tag = dialog.findViewById(R.id.textView39);
                    TextView limit = dialog.findViewById(R.id.textView40);
                    EditText damount = dialog.findViewById(R.id.editText2);
                    Button update = dialog.findViewById(R.id.button7);
                    Button cancel = dialog.findViewById(R.id.button9);
                    ProgressBar pbar = dialog.findViewById(R.id.progressBar7);

                    tag.setText("Enter discount amount to add in Order #" + ttiidd);


                    float ba = Float.parseFloat(tbill);
                    float pe = Float.parseFloat(p);


                    float ul;

                    if (pe <= ba)
                    {
                        ul = pe;
                    }
                    else
                    {
                        ul = ba;
                    }

                    limit.setText("Tip: Enter amount in between 1 to " + String.valueOf(ul));

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });



                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            String da = damount.getText().toString();

                            if (da.length() > 0)
                            {
                                float dd = Float.parseFloat(da);


                                if (dd > 0 && dd <= ul)
                                {


                                    pbar.setVisibility(View.VISIBLE);

                                    Bean b = (Bean) getApplicationContext();


                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.baseurl)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                    Call<scratchCardBean> call = cr.updateOrder(oid , da , "0");

                                    call.enqueue(new Callback<scratchCardBean>() {
                                        @Override
                                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                            Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            loadPerks();

                                            pbar.setVisibility(View.GONE);

                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                            pbar.setVisibility(View.GONE);
                                        }
                                    });


                                }
                                else
                                {
                                    Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });


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
                        currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0 ,0 ,R.drawable.ic_down_chevron , 0);
                    } else {
                        hide.setVisibility(View.GONE);
                        currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0 ,0 ,R.drawable.arrowleft , 0);
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

                    oid = item.getId();
                    ttiidd = item.getTxn();


                    float ca = Float.parseFloat(item.getCash());
                    float sc = Float.parseFloat(item.getScratch());
                    float tb = Float.parseFloat(item.getAmount());

                    float nb = tb - (ca + sc);

                    tbill = String.valueOf(nb);

                    baa = "\u20B9 " + String.valueOf(nb) + " <strike>\u20B9 " + item.getAmount() + "</strike>";

                    billAmount.setText(Html.fromHtml("\u20B9 " + String.valueOf(nb) + " <strike>\u20B9 " + item.getAmount() + "</strike>"));

                    createOrder.setVisibility(View.GONE);
                    billAmount.setVisibility(View.VISIBLE);
                    hide.setVisibility(View.VISIBLE);

                    currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0 ,0 ,R.drawable.ic_down_chevron , 0);

                    tid.setText("TXN ID - " + item.getTxn());
                    status.setText(item.getStatus());
                    cashdiscount.setText("Cash Discount - \u20B9 " + item.getCash());
                    scratchcard.setText("Scratch Discount - \u20B9 " + item.getScratch());
                    bill.setText("Total Bill - \u20B9 " + item.getAmount());
                    balance.setText("Balance Pay - \u20B9 " + String.valueOf(nb));

                    if (item.getMode().equals("CASH"))
                    {
                        deleteorder.setVisibility(View.GONE);
                        confirmandpay.setVisibility(View.GONE);
                    }
                    else
                    {
                        deleteorder.setVisibility(View.VISIBLE);
                        confirmandpay.setVisibility(View.VISIBLE);
                    }

                }
                else
                {

                    currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0 ,0 ,R.drawable.arrowleft , 0);

                    orderCreated = false;

                    oid = "";
                    ttiidd = "";
                    tbill = "0";
                    baa = "";
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

                scratch.setStrokeWidth(15);

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

                    }
                    else {

                        Dialog dialog5 = new Dialog(SubCat3.this);
                        dialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog5.setCancelable(false);
                        dialog5.setContentView(R.layout.add_cash_discount_dialog);
                        dialog5.show();


                        TextView tag = dialog5.findViewById(R.id.textView39);
                        TextView limit = dialog5.findViewById(R.id.textView40);
                        EditText damount = dialog5.findViewById(R.id.editText2);
                        Button update = dialog5.findViewById(R.id.button7);
                        Button cancel = dialog5.findViewById(R.id.button9);
                        ProgressBar pbar = dialog5.findViewById(R.id.progressBar7);

                        tag.setText("Enter discount amount to add in Order #" + ttiidd);

                        String c = item.getCashValue();

                        float ba = Float.parseFloat(tbill);
                        float pe = Float.parseFloat(c);


                        float ul;

                        if (pe <= ba)
                        {
                            ul = pe;
                        }
                        else
                        {
                            ul = ba;
                        }

                        limit.setText("Tip: Enter amount in between 1 to " + String.valueOf(ul));

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog5.dismiss();

                            }
                        });



                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                String da = damount.getText().toString();

                                if (da.length() > 0)
                                {
                                    float dd = Float.parseFloat(da);


                                    if (dd > 0 && dd <= ul)
                                    {


                                        pbar.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getApplicationContext();


                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                        Call<scratchCardBean> call = cr.updateOrder2(oid , "0" , da , item.getId());

                                        call.enqueue(new Callback<scratchCardBean>() {
                                            @Override
                                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                Toast.makeText(SubCat3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                loadPerks();

                                                pbar.setVisibility(View.GONE);

                                                dialog5.dismiss();

                                            }

                                            @Override
                                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                pbar.setVisibility(View.GONE);
                                            }
                                        });


                                    }
                                    else
                                    {
                                        Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(SubCat3.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                }



                            }
                        });


                    }










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
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE && resultCode == RESULT_OK) {
            // Process based on the data in response.



            String res = data.getStringExtra("Status");

            Log.d("result", res);

            if (res.equals("SUCCESS"))
            {
                Intent intent = new Intent(SubCat3.this , StatusActivity3.class);
                intent.putExtra("id" , oid);
                intent.putExtra("pid" , id);
                intent.putExtra("sta" , "success");
                intent.putExtra("amount" , baa);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(SubCat3.this , StatusActivity3.class);
                intent.putExtra("id" , oid);
                intent.putExtra("pid" , id);
                intent.putExtra("amount" , baa);
                intent.putExtra("sta" , "failure");
                startActivity(intent);
            }




        }
    }

}
