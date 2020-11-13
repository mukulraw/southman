package com.sc.bigboss.bigboss;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    ImageView home , videos , bid , history;
    TextView hometitle , videostitle , bidtitle , historytitle;

    int current = 1;

    Toolbar toolbar;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = findViewById(R.id.toolbar4);

        home = findViewById(R.id.imageView10);
        videos = findViewById(R.id.imageView11);
        bid = findViewById(R.id.imageView13);
        history = findViewById(R.id.imageView14);

        hometitle = findViewById(R.id.textView53);
        videostitle = findViewById(R.id.textView54);
        bidtitle = findViewById(R.id.textView55);
        historytitle = findViewById(R.id.textView56);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setTitle("Cuddapah");

        current = 1;
        select(current);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(1);
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(2);
            }
        });

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(3);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(4);
            }
        });



        hometitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(1);
            }
        });

        videostitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(2);
            }
        });

        bidtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(3);
            }
        });

        historytitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(4);
            }
        });


    }

    void select(int number)
    {
        if (number == 1)
        {
            current = 1;
            home.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            videos.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bid.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            history.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            hometitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            videostitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bidtitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            historytitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            FragmentManager fm = getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }

            FragmentTransaction ft = fm.beginTransaction();
            Shop frag1 = new Shop();
            ft.replace(R.id.replace , frag1);
            //ft.addToBackStack(null);
            ft.commit();

        }
        else if (number == 2)
        {
            current = 2;
            home.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videos.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            bid.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            history.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            hometitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videostitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            bidtitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            historytitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            FragmentManager fm = getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }

            FragmentTransaction ft = fm.beginTransaction();
            //Videos frag1 = new Videos();


            MeansWear2 till = new MeansWear2();

            Bundle b = new Bundle();

            b.putString("Catid" , "6");
            b.putString("type" , "women");

            till.setArguments(b);


            ft.replace(R.id.replace , till);
            //ft.addToBackStack(null);
            ft.commit();

        }
        else if (number == 3)
        {
            current = 3;
            home.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videos.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bid.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            history.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            hometitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videostitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bidtitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            historytitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            FragmentManager fm = getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }

            FragmentTransaction ft = fm.beginTransaction();
            Play frag1 = new Play();
            ft.replace(R.id.replace , frag1);
            //ft.addToBackStack(null);
            ft.commit();

        }
        else
        {
            current = 4;
            home.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videos.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bid.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            history.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

            hometitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            videostitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            bidtitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            historytitle.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }
    }

}