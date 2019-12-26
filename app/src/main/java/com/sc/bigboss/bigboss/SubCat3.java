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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.sc.bigboss.bigboss.createOrderPOJO.createOrderBean;
import com.sc.bigboss.bigboss.getPerksPOJO.Datum;
import com.sc.bigboss.bigboss.getPerksPOJO.Order;
import com.sc.bigboss.bigboss.getPerksPOJO.Scratch;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.pendingOrderPOJO.Data;
import com.sc.bigboss.bigboss.pendingOrderPOJO.pendingOrderBean;

import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.usersPOJO.usersBean;
import com.sc.bigboss.bigboss.voucherHistoryPOJO.voucherHistoryBean;
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
    private String id;
    private ImageView search;
    private ImageView home;
    private String catName;
    private String base;
    private String client;
    private ImageView notification;
    private ImageView perks2;
    private String bann;
    private String p;
    private TextView title;

    TabLayout tabs;
    ViewPager pager;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat4);



        toolbar = findViewById(R.id.toolbar);

        tabs = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(v -> finish());



        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);
        title = findViewById(R.id.title);

        notification.setOnClickListener(v -> {


            Intent i = new Intent(SubCat3.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(SubCat3.this, Perks.class);
            startActivity(intent);
        });


        title.setText(getIntent().getStringExtra("text"));
        catName = getIntent().getStringExtra("text");
        client = getIntent().getStringExtra("client");
        bann = getIntent().getStringExtra("banner");

        Log.d("catname", getIntent().getStringExtra("id"));





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


        FragStateAdapter adapter = new FragStateAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


        count = findViewById(R.id.count);

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



    }

    private BroadcastReceiver singleReceiver;
    private TextView count;




    @Override
    protected void onResume() {
        super.onResume();


        count.setText(String.valueOf(SharePreferenceUtils.getInstance().getInteger("count")));


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(singleReceiver);

    }


    class FragStateAdapter extends FragmentStatePagerAdapter
    {

        String[] ttt = new String[]{
                "REWARDS",
                "PUBLIC"
        };

        FragStateAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return ttt[position];
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0)
            {
                payment frag = new payment();
                Bundle b = new Bundle();
                b.putString("text" , catName);
                b.putString("client" , client);
                b.putString("banner" , bann);
                b.putString("id" , id);
                frag.setArguments(b);
                return frag;
            }
            else
            {
                share frag = new share();
                Bundle b = new Bundle();
                b.putString("text" , catName);
                b.putString("client" , client);
                b.putString("banner" , bann);
                b.putString("id" , id);
                frag.setArguments(b);
                return frag;
            }


        }

        @Override
        public int getCount() {
            return 2;
        }
    }



}
