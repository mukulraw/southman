package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import java.util.ArrayList;
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

    Toolbar toolbar;

    ViewPager pager;

    SmartTabLayout tabLayout;

    PagerAdapter adapter;

    ImageView search, notification, reward, perks, request;

    //TextView profile, order, wishlist, setting, logout, name, edit;

    RoundedImageView roundedImageView;

    Button play, video, shop;

    TextView location;

    String lname;

    TextView count;


    BroadcastReceiver singleReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //drawer = findViewById(R.id.drawer);
        location = findViewById(R.id.location);

        count = findViewById(R.id.count);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);


        notification = findViewById(R.id.notification);
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
        roundedImageView = findViewById(R.id.imageView1);

        ada();

        lname = SharePreferenceUtils.getInstance().getString("lname");

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Notification.class);
                startActivity(i);
            }
        });

        singleReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals("count")) {
                    count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(singleReceiver,
                new IntentFilter("count"));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Search.class);
                startActivity(i);
            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.request_dialog);
                dialog.show();


                EditText name = dialog.findViewById(R.id.name);
                EditText phone = dialog.findViewById(R.id.phone);
                Button submit = dialog.findViewById(R.id.submit);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                    }
                });


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
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Winners.class);
                startActivity(intent);
            }
        });

        perks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Perks.class);
                startActivity(intent);
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

            }
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


                            if (response.body().getData().size() > 0) {

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


    public void ada() {

        notification.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
        reward.setVisibility(View.VISIBLE);
        request.setVisibility(View.VISIBLE);

        adapter = new PagerAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(adapter);
        tabLayout.setViewPager(pager);

    }


    public class PagerAdapter extends FragmentStatePagerAdapter {

        String[] titles = {
                "Play",
                "Videos",
                "Shop"
        };

        public PagerAdapter(FragmentManager fm, int list) {
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

        Context context;
        List<Datum> list = new ArrayList<>();
        Dialog dialog;

        public LocationAdapter(Context context, List<Datum> list, Dialog dialog) {
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


            viewHolder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharePreferenceUtils.getInstance().saveString("location", item.getId());
                    SharePreferenceUtils.getInstance().saveString("lname", item.getName());
                    lname = item.getName();
                    location.setText(item.getName());
                    dialog.dismiss();
                    ada();

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
                text = itemView.findViewById(R.id.textView2);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }
}
