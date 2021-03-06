package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.createOrderPOJO.createOrderBean;
import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.usersPOJO.usersBean;
import com.sc.bigboss.bigboss.voucherHistoryPOJO.voucherHistoryBean;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class payment extends Fragment implements PaymentStatusListener {

    private RecyclerView grid;

    private GridLayoutManager manager;

    private CardAdapter adapter;

    private List<Scratch> list;

    private ProgressBar bar;

    private String id;

    private TextView minimum;

    private ConnectionDetector cd;

    private String catName;
    private String base;
    private String client;

    private LinearLayout linear;

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

    float min = 0;

    Button createOrder;
    TextView currentOrder;
    LinearLayout hide;

    boolean orderCreated = false;


    String baa, ordercontrrol;

    TextView billAmount, tid, status, cashdiscount, scratchcard, bill, balance;


    Button confirmandpay, deleteorder;

    String oid, ttiidd, tbill;

    private static final int TEZ_REQUEST_CODE = 123;

    TextView lcoation;
    String lat, lng;


    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment, container, false);


        cd = new ConnectionDetector(getActivity());


        linear = view.findViewById(R.id.linear);

        billAmount = view.findViewById(R.id.bill_amount);
        tid = view.findViewById(R.id.tid);
        status = view.findViewById(R.id.status);
        cashdiscount = view.findViewById(R.id.cash_discount);
        scratchcard = view.findViewById(R.id.scratch_card);
        bill = view.findViewById(R.id.bill);
        balance = view.findViewById(R.id.balance);
        confirmandpay = view.findViewById(R.id.ok);
        deleteorder = view.findViewById(R.id.cancel);
        minimum = view.findViewById(R.id.textView46);
        lcoation = view.findViewById(R.id.textView37);

        banner = view.findViewById(R.id.banner);

        perks = view.findViewById(R.id.perks);
        upload = view.findViewById(R.id.upload);

        createOrder = view.findViewById(R.id.create);
        currentOrder = view.findViewById(R.id.current);
        hide = view.findViewById(R.id.hide);


        catName = getArguments().getString("text");
        client = getArguments().getString("client");
        bann = getArguments().getString("banner");

        Log.d("catname", getArguments().getString("id"));

        grid = view.findViewById(R.id.grid);

        list = new ArrayList<>();

        adapter = new CardAdapter(getActivity(), list);

        manager = new GridLayoutManager(getActivity(), 3);

        /*manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                return Integer.parseInt(adapter.getSpace(i));

            }
        });*/

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        bar = view.findViewById(R.id.progress);

        id = getArguments().getString("id");

        Glide.with(this).load(bann).into(banner);

        if (cd.isConnectingToInternet()) {

            //bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getActivity().getApplicationContext();

            base = b.baseurl;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        deleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog = new Dialog(getActivity());
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

                        Bean b = (Bean) getActivity().getApplicationContext();


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

                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

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


        lcoation.setOnClickListener(new View.OnClickListener() {
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


        confirmandpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                float tb = Float.parseFloat(tbill);

                if (tb > 0) {

                    Dialog dialog = new Dialog(getActivity());
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

                            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                                    .with(getActivity())
                                    .setPayeeVpa("southman@sbi")
                                    .setPayeeName("South Man")
                                    .setTransactionId(ttiidd)
                                    .setTransactionRefId(ttiidd)
                                    //.setPayeeMerchantCode("BCR2DN6T6WEP3JDV")
                                    .setDescription("Payment Store Order")
                                    .setAmount(String.valueOf(tbill))
                                    .build();

                            Dialog dialog1 = new Dialog(getActivity());
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
                                    easyUpiPayment.setPaymentStatusListener(payment.this);

                                }
                            });

                            googlepay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(payment.this);

                                }
                            });


                            bhim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(payment.this);

                                }
                            });


                            paytm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog1.dismiss();

                                    easyUpiPayment.setDefaultPaymentApp(PaymentApp.PAYTM);
                                    easyUpiPayment.startPayment();
                                    easyUpiPayment.setPaymentStatusListener(payment.this);

                                }
                            });


                        }
                    });

                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                            Intent intent = new Intent(getActivity(), StatusActivity4.class);
                            intent.putExtra("id", oid);
                            intent.putExtra("pid", client);
                            intent.putExtra("sta", "success");
                            intent.putExtra("amount", baa);
                            intent.putExtra("tid", ttiidd);
                            startActivity(intent);


                        }
                    });

                } else {

                    Dialog dialog = new Dialog(getActivity());
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

                            Intent intent = new Intent(getActivity(), StatusActivity6.class);
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


        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ordercontrrol.equals("customer")) {
                    if (!SharePreferenceUtils.getInstance().getBoolean("createOrder")) {
                        Dialog dialog2 = new Dialog(getActivity());
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

                                Dialog dialog3 = new Dialog(getActivity());
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

                                            float aaa = Float.parseFloat(aa);
                                            if (aaa > min) {
                                                pbar.setVisibility(View.VISIBLE);

                                                Bean b = (Bean) getActivity().getApplicationContext();


                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.baseurl)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                                Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, String.valueOf(aaa), String.valueOf(System.currentTimeMillis()));
                                                call.enqueue(new Callback<createOrderBean>() {
                                                    @Override
                                                    public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                        if (response.body().getStatus().equals("1")) {
                                                            orderCreated = true;
                                                            dialog3.dismiss();
                                                            onResume();
                                                        }

                                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                        pbar.setVisibility(View.GONE);

                                                    }

                                                    @Override
                                                    public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                        pbar.setVisibility(View.GONE);
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });


                            }
                        });


                    } else {

                        Dialog dialog3 = new Dialog(getActivity());
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

                                    float aaa = Float.parseFloat(aa);

                                    if (aaa > min) {
                                        pbar.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getActivity().getApplicationContext();


                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<createOrderBean> call = cr.createOrder(SharePreferenceUtils.getInstance().getString("userid"), client, String.valueOf(aaa), String.valueOf(System.currentTimeMillis()));
                                        call.enqueue(new Callback<createOrderBean>() {
                                            @Override
                                            public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                                if (response.body().getStatus().equals("1")) {
                                                    orderCreated = true;
                                                    dialog3.dismiss();
                                                    onResume();
                                                }

                                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                pbar.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                pbar.setVisibility(View.GONE);
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
                } else {

                    if (!SharePreferenceUtils.getInstance().getBoolean("createOrder2")) {
                        Dialog dialog2 = new Dialog(getActivity());
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.bill_amount_proceed_dialog2);
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
                                    SharePreferenceUtils.getInstance().saveBoolean("createOrder2", true);
                                }

                                bar.setVisibility(View.VISIBLE);

                                Bean b = (Bean) getActivity().getApplicationContext();


                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<createOrderBean> call = cr.requestOrder(SharePreferenceUtils.getInstance().getString("userid"), client, String.valueOf(System.currentTimeMillis()));
                                call.enqueue(new Callback<createOrderBean>() {
                                    @Override
                                    public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                        if (response.body().getStatus().equals("1")) {
                                            orderCreated = true;

                                            onResume();
                                        }

                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        bar.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<createOrderBean> call, Throwable t) {
                                        bar.setVisibility(View.GONE);
                                    }
                                });


                            }
                        });


                    } else {

                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();


                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<createOrderBean> call = cr.requestOrder(SharePreferenceUtils.getInstance().getString("userid"), client, String.valueOf(System.currentTimeMillis()));
                        call.enqueue(new Callback<createOrderBean>() {
                            @Override
                            public void onResponse(Call<createOrderBean> call, Response<createOrderBean> response) {

                                if (response.body().getStatus().equals("1")) {
                                    orderCreated = true;
                                    onResume();
                                }

                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    }

                }


            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float pe1 = Float.parseFloat(p);

                if (pe1 > 0) {

                    if (!orderCreated) {

                        Dialog dialog1 = new Dialog(getActivity());
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
                                    Dialog dialog2 = new Dialog(getActivity());
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

                                            Dialog dialog3 = new Dialog(getActivity());
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

                                                        Bean b = (Bean) getActivity().getApplicationContext();


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

                                                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                pbar.setVisibility(View.GONE);

                                                            }

                                                            @Override
                                                            public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                                pbar.setVisibility(View.GONE);
                                                            }
                                                        });

                                                    } else {
                                                        Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                    }


                                                }
                                            });


                                        }
                                    });


                                } else {

                                    Dialog dialog3 = new Dialog(getActivity());
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

                                                Bean b = (Bean) getActivity().getApplicationContext();


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

                                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                        pbar.setVisibility(View.GONE);

                                                    }

                                                    @Override
                                                    public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                        pbar.setVisibility(View.GONE);
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });

                                }

                            }
                        });

                    } else {

                        Dialog dialog = new Dialog(getActivity());
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

                        if (pe <= ba) {
                            ul = pe;
                        } else {
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

                                if (da.length() > 0) {
                                    float dd = Float.parseFloat(da);


                                    if (dd > 0 && dd <= ul) {


                                        pbar.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getActivity().getApplicationContext();


                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                        Call<scratchCardBean> call = cr.updateOrder(oid, da, "0");

                                        call.enqueue(new Callback<scratchCardBean>() {
                                            @Override
                                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                loadPerks();

                                                pbar.setVisibility(View.GONE);

                                                dialog.dismiss();

                                            }

                                            @Override
                                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                pbar.setVisibility(View.GONE);
                                            }
                                        });


                                    } else {
                                        Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


                    }


                } else {
                    Toast.makeText(getActivity(), "You don't have enough cash rewards", Toast.LENGTH_SHORT).show();
                }


            }
        });


        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    loadPerks();

                }

            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (orderCreated) {
                    if (hide.getVisibility() == View.GONE) {
                        hide.setVisibility(View.VISIBLE);
                        currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_down_chevron, 0);
                    } else {
                        hide.setVisibility(View.GONE);
                        currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrowleft1, 0);
                    }
                }


            }
        });

        return view;
    }

    private BroadcastReceiver singleReceiver;


    private void loadPerks() {

        bar.setVisibility(View.VISIBLE);

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

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
                    perks.setText("Cash reward remaining : " + response.body().getData().get(0).getCashRewards());
                    p = response.body().getData().get(0).getCashRewards();

                }

                ordercontrrol = response.body().getClient().getPercentage();

                if (ordercontrrol.equals("customer")) {
                    createOrder.setText("ENTER FINAL BILL");
                } else {
                    createOrder.setText("REQUEST FINAL BILL");
                }

                if (response.body().getClient().getLatitude().equals("") || response.body().getClient().getLatitude() == null) {
                    lcoation.setVisibility(View.GONE);
                } else {
                    lcoation.setVisibility(View.VISIBLE);
                    lat = response.body().getClient().getLatitude();
                    lng = response.body().getClient().getLongitude();
                }

                if (response.body().getClient().getMinimunBill().equals("0") || response.body().getClient().getMinimunBill() == null) {
                    min = 0;
                } else {
                    min = Float.parseFloat(response.body().getClient().getMinimunBill());
                }

                if (min > 0) {
                    minimum.setText("Minimum bill amount is \u20B9 " + (min + 1));
                    minimum.setVisibility(View.VISIBLE);
                } else {
                    minimum.setVisibility(View.GONE);
                }

                if (Objects.requireNonNull(response.body()).getScratch().size() > 0) {
                    adapter = new CardAdapter(getActivity(), response.body().getScratch());
                    manager = new GridLayoutManager(getActivity(), 2);
                    grid.setAdapter(adapter);
                    grid.setLayoutManager(manager);
                    linear.setVisibility(View.GONE);

                } else {
                    adapter = new CardAdapter(getActivity(), response.body().getScratch());
                    manager = new GridLayoutManager(getActivity(), 2);
                    grid.setAdapter(adapter);
                    grid.setLayoutManager(manager);
                    linear.setVisibility(View.VISIBLE);
                }


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

                    baa = "\u20B9 " + String.valueOf(nb) + " <strike>\u20B9 " + item.getAmount() + "</strike>";

                    billAmount.setText(Html.fromHtml("\u20B9 " + String.valueOf(nb) + " <strike>\u20B9 " + item.getAmount() + "</strike>"));

                    createOrder.setVisibility(View.GONE);
                    billAmount.setVisibility(View.VISIBLE);
                    hide.setVisibility(View.VISIBLE);

                    currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_down_chevron, 0);

                    tid.setText("TXN ID - " + item.getTxn());
                    status.setText(item.getStatus());
                    cashdiscount.setText("Cash Discount - \u20B9 " + item.getRed());
                    scratchcard.setText("Scratch Discount - \u20B9 " + item.getBlue());
                    bill.setText("Total Bill - \u20B9 " + item.getAmount());
                    balance.setText("Balance Pay - \u20B9 " + String.valueOf(nb));

                    if (item.getMode().equals("CASH") || tb == 0) {
                        deleteorder.setVisibility(View.GONE);
                        confirmandpay.setVisibility(View.GONE);
                    } else {
                        deleteorder.setVisibility(View.VISIBLE);
                        confirmandpay.setVisibility(View.VISIBLE);
                    }

                } else {

                    currentOrder.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrowleft1, 0);

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
    public void onResume() {
        super.onResume();

        loadPerks();


    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Log.d("TransactionDetails", transactionDetails.toString());
    }

    @Override
    public void onTransactionSuccess() {
        Log.d("TransactionSuccess", "TransactionSuccess");
        Intent intent = new Intent(getActivity(), StatusActivity3.class);
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
        Intent intent = new Intent(getActivity(), StatusActivity3.class);
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
        Toast.makeText(getContext(), "App Not Found", Toast.LENGTH_SHORT).show();
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

            if (item.getScratch().equals("1")) {
                holder.enable.setVisibility(View.VISIBLE);
                holder.disable.setVisibility(View.GONE);
            } else {
                holder.enable.setVisibility(View.GONE);
                holder.disable.setVisibility(View.VISIBLE);
            }

            holder.text2.setText("You have got \u20B9 " + item.getCashValue());

            holder.expiry.setText("will expire on " + item.getExpiry());

            holder.itemView.setOnClickListener(v -> {


                if (item.getScratch().equals("1")) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.scratch_dialog2);
                    dialog.show();

                    TextView scratch = dialog.findViewById(R.id.scratch);
                    TextView title = dialog.findViewById(R.id.title);
                    TextView date = dialog.findViewById(R.id.date);
                    Button share = dialog.findViewById(R.id.share);
                    Button transfer = dialog.findViewById(R.id.transfer);
                    Button publicshare = dialog.findViewById(R.id.publicshare);
                    ProgressBar bar = dialog.findViewById(R.id.progress);


                    title.setText(item.getText());
                    date.setText(item.getCreated());

                    publicshare.setOnClickListener(new View.OnClickListener() {
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

                                    dialog.dismiss();
                                    dialog1.dismiss();

                                }
                            });

                            shareNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    bar.setVisibility(View.VISIBLE);

                                    Bean b = (Bean) context.getApplicationContext();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.baseurl)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                    Call<scratchCardBean> call = cr.sharePublic(client, SharePreferenceUtils.getInstance().getString("userid"), "SHARE", item.getId());

                                    call.enqueue(new Callback<scratchCardBean>() {
                                        @Override
                                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                            dialog1.dismiss();

                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            loadPerks();

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

                    transfer.setOnClickListener(v127 -> transfer.setOnClickListener(v126 -> {

                        dialog.dismiss();

                        Dialog dialog14 = new Dialog(getActivity());
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

                        Bean b = (Bean) context.getApplicationContext();

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
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
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        onResume();
                                    }

                                    @Override
                                    public void onFailure(Call<usersBean> call29, Throwable t) {

                                    }
                                });


                            } else {
                                Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_SHORT).show();
                            }

                        });


                    }));

                    scratch.setText("You have got \u20B9 " + item.getCashValue());

                    share.setOnClickListener(v124 -> {

                        dialog.dismiss();

                        if (!orderCreated) {

                            Dialog dialog1 = new Dialog(context);
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
                                        Dialog dialog2 = new Dialog(context);
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

                                                Dialog dialog3 = new Dialog(context);
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

                                                            Bean b = (Bean) context.getApplicationContext();


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

                                                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    pbar.setVisibility(View.GONE);

                                                                }

                                                                @Override
                                                                public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                                    pbar.setVisibility(View.GONE);
                                                                }
                                                            });

                                                        } else {
                                                            Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                        }


                                                    }
                                                });


                                            }
                                        });


                                    } else {

                                        Dialog dialog3 = new Dialog(context);
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

                                                    Bean b = (Bean) getActivity().getApplicationContext();


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

                                                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            pbar.setVisibility(View.GONE);

                                                        }

                                                        @Override
                                                        public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                            pbar.setVisibility(View.GONE);
                                                        }
                                                    });

                                                } else {
                                                    Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });

                                    }

                                }
                            });

                        } else {

                            Dialog dialog5 = new Dialog(context);
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

                            if (pe <= ba) {
                                ul = pe;
                            } else {
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

                                    if (da.length() > 0) {
                                        float dd = Float.parseFloat(da);


                                        if (dd > 0 && dd <= ul) {


                                            pbar.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) context.getApplicationContext();


                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseurl)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                            Call<scratchCardBean> call = cr.updateOrder2(oid, "0", da, item.getId());

                                            call.enqueue(new Callback<scratchCardBean>() {
                                                @Override
                                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                    loadPerks();

                                                    pbar.setVisibility(View.GONE);

                                                    dialog5.dismiss();

                                                }

                                                @Override
                                                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                    pbar.setVisibility(View.GONE);
                                                }
                                            });


                                        } else {
                                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });


                        }


                    });


                } else {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.scratch_dialog);
                    dialog.show();

                    ScratchView scratch2 = dialog.findViewById(R.id.scratch2);

                    TextView scratch = dialog.findViewById(R.id.scratch);
                    Button share = dialog.findViewById(R.id.share);
                    Button transfer = dialog.findViewById(R.id.transfer);
                    Button publicshare = dialog.findViewById(R.id.publicshare);
                    ProgressBar bar = dialog.findViewById(R.id.progress);

                    scratch2.setStrokeWidth(15);

                    publicshare.setOnClickListener(new View.OnClickListener() {
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

                                    dialog.dismiss();
                                    dialog1.dismiss();

                                }
                            });

                            shareNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    bar.setVisibility(View.VISIBLE);

                                    Bean b = (Bean) context.getApplicationContext();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.baseurl)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                    Call<scratchCardBean> call = cr.sharePublic(client, SharePreferenceUtils.getInstance().getString("userid"), "SHARE", item.getId());

                                    call.enqueue(new Callback<scratchCardBean>() {
                                        @Override
                                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                            dialog1.dismiss();

                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            loadPerks();

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

                    transfer.setOnClickListener(v127 -> transfer.setOnClickListener(v126 -> {

                        dialog.dismiss();

                        Dialog dialog14 = new Dialog(context);
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

                        Bean b = (Bean) context.getApplicationContext();

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


                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
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
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        onResume();
                                    }

                                    @Override
                                    public void onFailure(Call<usersBean> call29, Throwable t) {

                                    }
                                });


                            } else {
                                Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show();
                            }

                        });


                    }));


                    scratch.setText("You have got \u20B9 " + item.getCashValue());


                    scratch2.setRevealListener(new ScratchView.IRevealListener() {
                        @Override
                        public void onRevealed(ScratchView tv) {


                            bar.setVisibility(View.VISIBLE);


                            Bean b = (Bean) context.getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<voucherHistoryBean> call = cr.updateScratch(item.getId());

                            call.enqueue(new Callback<voucherHistoryBean>() {
                                @Override
                                public void onResponse(Call<voucherHistoryBean> call, Response<voucherHistoryBean> response) {

                                    share.setVisibility(View.VISIBLE);
                                    transfer.setVisibility(View.VISIBLE);
                                    publicshare.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);

                                    onResume();

                                }

                                @Override
                                public void onFailure(Call<voucherHistoryBean> call, Throwable t) {
                                    bar.setVisibility(View.GONE);
                                }
                            });


                        }

                        @Override
                        public void onRevealPercentChangedListener(ScratchView stv, float percent) {

                        }
                    });

                    share.setOnClickListener(v124 -> {

                        dialog.dismiss();

                        if (!orderCreated) {

                            Dialog dialog1 = new Dialog(context);
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
                                        Dialog dialog2 = new Dialog(context);
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

                                                Dialog dialog3 = new Dialog(context);
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

                                                            Bean b = (Bean) context.getApplicationContext();


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

                                                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                                    pbar.setVisibility(View.GONE);

                                                                }

                                                                @Override
                                                                public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                                    pbar.setVisibility(View.GONE);
                                                                }
                                                            });

                                                        } else {
                                                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                        }


                                                    }
                                                });


                                            }
                                        });


                                    } else {

                                        Dialog dialog3 = new Dialog(context);
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

                                                    Bean b = (Bean) context.getApplicationContext();


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

                                                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            pbar.setVisibility(View.GONE);

                                                        }

                                                        @Override
                                                        public void onFailure(Call<createOrderBean> call, Throwable t) {
                                                            pbar.setVisibility(View.GONE);
                                                        }
                                                    });

                                                } else {
                                                    Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });

                                    }

                                }
                            });

                        } else {

                            Dialog dialog5 = new Dialog(context);
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

                            if (pe <= ba) {
                                ul = pe;
                            } else {
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

                                    if (da.length() > 0) {
                                        float dd = Float.parseFloat(da);


                                        if (dd > 0 && dd <= ul) {


                                            pbar.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) context.getApplicationContext();


                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseurl)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                            Call<scratchCardBean> call = cr.updateOrder2(oid, "0", da, item.getId());

                                            call.enqueue(new Callback<scratchCardBean>() {
                                                @Override
                                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                    loadPerks();

                                                    pbar.setVisibility(View.GONE);

                                                    dialog5.dismiss();

                                                }

                                                @Override
                                                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                    pbar.setVisibility(View.GONE);
                                                }
                                            });


                                        } else {
                                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                                    }


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


    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(singleReceiver);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE && resultCode == RESULT_OK) {
            // Process based on the data in response.


            String res = data.getStringExtra("Status");

            Log.d("result", res);

            if (res.equals("SUCCESS")) {
                Intent intent = new Intent(getActivity(), StatusActivity3.class);
                intent.putExtra("id", oid);
                intent.putExtra("pid", client);
                intent.putExtra("sta", "success");
                intent.putExtra("amount", baa);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), StatusActivity3.class);
                intent.putExtra("id", oid);
                intent.putExtra("pid", client);
                intent.putExtra("amount", baa);
                intent.putExtra("sta", "failure");
                startActivity(intent);
            }


        }
    }

}
