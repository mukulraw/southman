package com.example.bigboss.bigboss;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;

    Toolbar toolbar;

    ViewPager pager;

    TabLayout tabLayout;

    PagerAdapter adapter;

    ImageView search, notification;

    TextView profile, order, wishlist, setting, logout, name, edit;

    RoundedImageView roundedImageView;

    Button play , video , shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Play"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Shop"));

        adapter = new PagerAdapter(getSupportFragmentManager() , 3);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        tabLayout.getTabAt(0).setText("Play");
        tabLayout.getTabAt(1).setText("Videos");
        tabLayout.getTabAt(2).setText("Shop");

        notification = findViewById(R.id.notification);

        search = findViewById(R.id.search);

        profile = findViewById(R.id.profile);

        order = findViewById(R.id.order);

        wishlist = findViewById(R.id.wish);

        edit = findViewById(R.id.edit);

        name = findViewById(R.id.name);

        setting = findViewById(R.id.setting);

        logout = findViewById(R.id.logout);

        roundedImageView = findViewById(R.id.imageView1);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Notification.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Search.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, MyProfile.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, MyOrder.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, WishList.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, Setting.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });



        play = findViewById(R.id.play);

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














    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(FragmentManager fm, int list) {
            super(fm);
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
}
