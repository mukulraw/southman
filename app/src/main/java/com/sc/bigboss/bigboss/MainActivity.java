package com.sc.bigboss.bigboss;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sc.bigboss.bigboss.bannerPOJO.bannerBean;
import com.sc.bigboss.bigboss.locationPOJO.Datum;
import com.sc.bigboss.bigboss.locationPOJO.locationBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sc.bigboss.bigboss.qrPOJO.Redeem;
import com.sc.bigboss.bigboss.qrPOJO.Voucher;
import com.sc.bigboss.bigboss.qrPOJO.qrBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.xuexiang.xqrcode.XQRCode;

import java.util.List;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    //DrawerLayout drawer;

    private Toolbar toolbar;

    private ViewPager pager;

    private SmartTabLayout tabLayout;

    private PagerAdapter adapter;

    private ImageView search;
    private ImageView notification;
    private ImageView reward;
    private ImageView perks;
    private ImageView request;
    private ImageView scan;

    //TextView profile, order, wishlist, setting, logout, name, edit;

    private RoundedImageView roundedImageView;

    ProgressBar progress;

    Button play, video, shop;

    private TextView location;

    private String lname;

    private TextView count;


    private BroadcastReceiver singleReceiver;

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //drawer = findViewById(R.id.drawer);
        location = findViewById(R.id.location);

        count = findViewById(R.id.count);

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);


        notification = findViewById(R.id.notification);
        scan = findViewById(R.id.scan);
        reward = findViewById(R.id.reward);
        perks = findViewById(R.id.perks);
        request = findViewById(R.id.request);

        search = findViewById(R.id.search);

        //profile = findViewById(R.id.profile);

        /*order = findViewById(R.id.order);

        wishlist = findViewById(R.id.wish);
*/
        //edit = findViewById(R.id.edit);

        // name = findViewById(R.id.name);

  /*      setting = findViewById(R.id.setting);

        logout = findViewById(R.id.logout);
*/


        ada();

        lname = SharePreferenceUtils.getInstance().getString("lname");

        notification.setOnClickListener(v -> {


            Intent i = new Intent(MainActivity.this, Notification.class);
            startActivity(i);
        });

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Objects.requireNonNull(intent.getAction()).equals("count")) {
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        search.setOnClickListener(v -> {


            Intent i = new Intent(MainActivity.this, Search.class);
            startActivity(i);
        });


        request.setOnClickListener(v -> {


            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.request_dialog);
            dialog.show();


            EditText name = dialog.findViewById(R.id.name);
            EditText phone = dialog.findViewById(R.id.phone);
            Button submit = dialog.findViewById(R.id.submit);


            submit.setOnClickListener(v1 -> {

                String n = name.getText().toString();
                String p = phone.getText().toString();

                if (n.length() > 0) {

                    if (p.length() == 10) {
                        Bean b = (Bean) getApplicationContext();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<scratchCardBean> call1 = cr.request(SharePreferenceUtils.getInstance().getString("userid"), n, p);

                        call1.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call2, Response<scratchCardBean> response2) {

                                Toast.makeText(MainActivity.this, response2.body().getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {

                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid phone", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "Invalid details", Toast.LENGTH_SHORT).show();
                }

            });


        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(XQRCode.ACTION_DEFAULT_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);



            }
        });


     /*   profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, MyProfile.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });*/


  /*      order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, MyOrder.class);
                startActivity(i);
                //drawer.closeDrawer(GravityCompat.START);

            }
        });
*/

  /*      wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, WishList.class);
                startActivity(i);
                //drawer.closeDrawer(GravityCompat.START);

            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Setting.class);
                startActivity(i);
                //drawer.closeDrawer(GravityCompat.START);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

*/

       /* play = findViewById(R.id.play);

        video = findViewById(R.id.video);

        shop = findViewById(R.id.shop);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

*/
        reward.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, Winners.class);
            startActivity(intent);
        });

        perks.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, Perks.class);
            startActivity(intent);
        });


        location.setOnClickListener(v -> {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.location_layoout);
            dialog.setCancelable(true);
            dialog.show();


            final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
            ProgressBar progress = dialog.findViewById(R.id.progressBar);

            progress = findViewById(R.id.progress);


            Bean b = (Bean) getApplicationContext();

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<locationBean> call = cr.getLocations();

            Log.d("location", lname);

            final ProgressBar finalProgress = progress;
            call.enqueue(new Callback<locationBean>() {
                @Override
                public void onResponse(Call<locationBean> call, Response<locationBean> response) {

                    LocationAdapter adapter = new LocationAdapter(MainActivity.this, response.body().getData(), dialog);
                    GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 1);
                    grid.setAdapter(adapter);
                    grid.setLayoutManager(manager);

                    finalProgress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<locationBean> call, Throwable t) {
                    finalProgress.setVisibility(View.GONE);
                }
            });

        });


        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                Log.d("pageaa", String.valueOf(i));


                if (i == 2) {

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                    Call<bannerBean> call1 = cr.getBanners();

                    call1.enqueue(new Callback<bannerBean>() {
                        @Override
                        public void onResponse(Call<bannerBean> call, Response<bannerBean> response) {


                            if (Objects.requireNonNull(response.body()).getData().size() > 0) {

                                Dialog dialog = new Dialog(MainActivity.this  , android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.popup_dialog);
                                dialog.show();

                                ImageView im = dialog.findViewById(R.id.image);

                                Glide.with(MainActivity.this).load(response.body().getData().get(0).getImage()).into(im);


                            }


                        }

                        @Override
                        public void onFailure(Call<bannerBean> call, Throwable t) {

                        }
                    });

                    search.setVisibility(View.GONE);
                    request.setVisibility(View.GONE);
                    reward.setVisibility(View.GONE);

                } else if (i == 1) {
                    search.setVisibility(View.GONE);
                    request.setVisibility(View.GONE);
                    reward.setVisibility(View.GONE);
                } else {
                    notification.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    reward.setVisibility(View.VISIBLE);
                    request.setVisibility(View.VISIBLE);




                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }


    private void ada() {

        notification.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
        reward.setVisibility(View.VISIBLE);
        request.setVisibility(View.VISIBLE);

        adapter = new PagerAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(adapter);
        tabLayout.setViewPager(pager);

    }


    class PagerAdapter extends FragmentStatePagerAdapter {

        final String[] titles = {
                "Play",
                "Videos",
                "Shop"
        };

        PagerAdapter(FragmentManager fm, int list) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0) {

                return new Play();
            } else if (i == 1) {
                return new Videos();
            } else if (i == 2) {

                return new Shop();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));

        location.setText(lname);

    }

    class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

        final Context context;
        List<Datum> list;
        final Dialog dialog;

        LocationAdapter(Context context, List<Datum> list, Dialog dialog) {
            this.context = context;
            this.list = list;
            this.dialog = dialog;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.location_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            final Datum item = list.get(i);

            viewHolder.text.setText(item.getName());


            viewHolder.text.setOnClickListener(v -> {

                SharePreferenceUtils.getInstance().saveString("location", item.getId());
                SharePreferenceUtils.getInstance().saveString("lname", item.getName());
                lname = item.getName();
                location.setText(item.getName());
                dialog.dismiss();
                ada();

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
                text = itemView.findViewById(R.id.textView2);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("Asdasd","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result

            Bundle bundle = data.getExtras();

            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);

                    Log.d("qr result" , result);

                    Bean b = (Bean) getApplicationContext();

                    progress.setVisibility(View.VISIBLE);

                    String base = b.baseurl;
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<qrBean> call = cr.getQR(result);

                    call.enqueue(new Callback<qrBean>() {
                        @Override
                        public void onResponse(Call<qrBean> call, Response<qrBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                Redeem item2 = response.body().getRedeem();

                                Intent i1 = new Intent(MainActivity.this, SubCat3.class);
                                i1.putExtra("id", item2.getId());
                                i1.putExtra("text", item2.getText());
                                i1.putExtra("catname", item2.getCatId());
                                i1.putExtra("client", item2.getClientId());
                                i1.putExtra("banner", base + "southman/admin2/upload/sub_cat/" + item2.getImageUrl());
                                startActivity(i1);

                                /*Dialog dialog = new Dialog(MainActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.scan_layout);
                                dialog.show();

                                TextView tit = dialog.findViewById(R.id.textView38);
                                Button vs = dialog.findViewById(R.id.button9);
                                Button rs = dialog.findViewById(R.id.button7);


                                tit.setText(response.body().getRedeem().getClientName());

                                Voucher item1 = response.body().getVoucher();
                                Redeem item2 = response.body().getRedeem();

                                vs.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();

                                        Intent i1 = new Intent(MainActivity.this, SubCat2.class);
                                        i1.putExtra("id", item1.getId());
                                        i1.putExtra("text", item1.getText());
                                        i1.putExtra("catname", item1.getCatId());
                                        i1.putExtra("client", item1.getClientId());
                                        startActivity(i1);

                                    }
                                });

                                rs.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();

                                        Intent i1 = new Intent(MainActivity.this, SubCat3.class);
                                        i1.putExtra("id", item2.getId());
                                        i1.putExtra("text", item2.getText());
                                        i1.putExtra("catname", item2.getCatId());
                                        i1.putExtra("client", item2.getClientId());
                                        i1.putExtra("banner", base + "southman/admin2/upload/sub_cat/" + item2.getImageUrl());
                                        startActivity(i1);

                                    }
                                });*/
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }




                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<qrBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
                }
            }




            //progress.setVisibility(View.VISIBLE);








        }
    }

}
