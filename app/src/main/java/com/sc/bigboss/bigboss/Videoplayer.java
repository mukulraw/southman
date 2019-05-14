package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.net.MalformedURLException;
import java.net.URL;

public class Videoplayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    Toolbar toolbar;

    YouTubePlayerView youTubePlayerView;

    ImageView back;

    String id;

    ProgressBar bar;

    TextView ingr;

    Button order;

    String is;

    String ph, co;

    String url, des;

    YouTubePlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        id = getIntent().getStringExtra("id");

        is = getIntent().getStringExtra("is");

        ph = getIntent().getStringExtra("ph");

        url = getIntent().getStringExtra("videourl");

        des = getIntent().getStringExtra("des");

        co = getIntent().getStringExtra("code");

        toolbar = findViewById(R.id.toolbar);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        bar = findViewById(R.id.progress);

        ingr = findViewById(R.id.ingredients);

        des = des.replace("\n" , "");
        des = des.replace("\t" , "");

        ingr.setText(Html.fromHtml(des));

        order = findViewById(R.id.order);

        youTubePlayerView = findViewById(R.id.videoplayer);

        youTubePlayerView.initialize("AIzaSyBJuWOg3svNvIVR4qt0q1GDsETF6SrUExQ", this);

        if (is.equals("yes")) {

            order.setVisibility(View.VISIBLE);
        } else {

            order.setVisibility(View.GONE);
        }

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Videoplayer.this);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                mobile.setText(ph);
                code.setText(co);

                watshp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {

                            openWhatsApp();
                            dialog.dismiss();

                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {

                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ph));
                            startActivity(intent);
                            dialog.dismiss();


                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }
                });
            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


        if (!b) {

            this.player = youTubePlayer;

            try {

                //youTubePlayer.loadVideo("GqbNL4XGT4U");
                youTubePlayer.loadVideo(extractYoutubeId(url));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }


    private void openWhatsApp() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=91" + ph + "&text=Product Code : " + co));
        startActivity(browserIntent);
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}
