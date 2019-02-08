package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.net.MalformedURLException;
import java.net.URL;

public class Videoplayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    Toolbar toolbar;

    YouTubePlayerView youTubePlayerView;

    ImageView back;

    String id;

    ProgressBar bar;

    TextView ingr;

    Button order;

    String is;
    String ph , co;
    String url , des;

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
        // rec = findViewById(R.id.receipe);
        ingr = findViewById(R.id.ingredients);

        ingr.setText(des);

        order = findViewById(R.id.order);

        youTubePlayerView = findViewById(R.id.videoplayer);



        youTubePlayerView.initialize("AIzaSyBJuWOg3svNvIVR4qt0q1GDsETF6SrUExQ", this);




       /* youTubePlayerView.initialize("AIzaSyBJuWOg3svNvIVR4qt0q1GDsETF6SrUExQ", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(extractYoutubeVideoId(url));

                Log.d("iidd" , url);

                //  https://www.youtube.com/watch?v=G0Hx6uN2AJE

                //youTubePlayer.play();


              //  youTubePlayer.cueVideo(extractYoutubeVideoId(url));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
*/

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

                        /*Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(sendIntent, ""));
                        startActivity(sendIntent);
*/

                        try {

                            Uri uri = Uri.parse("smsto:" + ph);
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            sendIntent.setPackage("com.whatsapp");
                            startActivity(sendIntent);

                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse(ph));
                            startActivity(i);


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


        if (!b)
        {

            this.player = youTubePlayer;

            try {

                //youTubePlayer.loadVideo("GqbNL4XGT4U");
                youTubePlayer.loadVideo(extractYoutubeId(url));
            } catch (Exception e) {
                e.printStackTrace();
            }

                    /*youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

                    youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                        @Override
                        public void onPlaying() {
                            loadingPopup.setVisibility(View.GONE);


                        }

                        @Override
                        public void onPaused() {

                        }

                        @Override
                        public void onStopped() {

                        }

                        @Override
                        public void onBuffering(boolean b) {

                        }

                        @Override
                        public void onSeekTo(int i) {

                        }
                    });

                    youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {

                        }

                        @Override
                        public void onLoaded(String s) {
                            //loadingPopup.setVisibility(View.GONE);
                            //loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {

                        }

                        @Override
                        public void onVideoEnded() {

                            youTubePlayer.release();

                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {

                        }
                    });
*/
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


}
