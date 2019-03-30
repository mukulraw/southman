package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sc.bigboss.bigboss.playDataPOJO.User;
import com.sc.bigboss.bigboss.playDataPOJO.playDataBean;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AdActivity extends YouTubeBaseActivity  implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer player;

    String url , diff;
    TextView timerr , nnn;

    GridLayoutManager manager;

Button quit;

    RecyclerView grid;

    List<User> list;

    PlayitemAdapter adapter;

    String userId , playId , name , phone , image , title , price , brand , color , size , negotiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad2);

        list = new ArrayList<>();


        url = getIntent().getStringExtra("url");
        diff = getIntent().getStringExtra("diff");
        userId = getIntent().getStringExtra("userid");
        playId = getIntent().getStringExtra("playId");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        price = getIntent().getStringExtra("price");
        brand = getIntent().getStringExtra("brand");
        color = getIntent().getStringExtra("color");
        size = getIntent().getStringExtra("size");
        negotiable = getIntent().getStringExtra("negotiable");

        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        quit = findViewById(R.id.button);
        nnn = findViewById(R.id.textView21);
        timerr = findViewById(R.id.textView18);

        adapter = new PlayitemAdapter(this, list);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        grid = findViewById(R.id.grid);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);


        youTubePlayerView.initialize("AIzaSyBJuWOg3svNvIVR4qt0q1GDsETF6SrUExQ", this);

        nnn.setText(name);

        startImageTimer();


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(AdActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quit_dialog_layout);
                dialog.show();

                Button ookk = dialog.findViewById(R.id.button2);
                Button canc = dialog.findViewById(R.id.button4);

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ookk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });


            }
        });

        setRepeat();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {

            this.player = youTubePlayer;

            try {

                //youTubePlayer.loadVideo("GqbNL4XGT4U");

                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

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

    void startImageTimer() {
        CountDownTimer timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerr.setText("Play begins in " + getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

            }

            @Override
            public void onFinish() {

                Intent i = new Intent(AdActivity.this , Playitem.class);
                //Intent i = new Intent(getContext(), Playitem.class);

                i.putExtra("userid", userId);
                i.putExtra("name", name);
                i.putExtra("phone", phone);
                i.putExtra("playId", playId);
                i.putExtra("image", image);
                i.putExtra("title", title);
                i.putExtra("price", price);
                i.putExtra("brand", brand);
                i.putExtra("color", color);
                i.putExtra("size", size);
                i.putExtra("negotiable", negotiable);

                startActivity(i);
                finish();

            }
        };
        timer.start();
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    @Override
    public void onBackPressed() {
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(AdActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quit_dialog_layout);
                dialog.show();

                Button ookk = dialog.findViewById(R.id.button2);
                Button canc = dialog.findViewById(R.id.button4);

                canc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                ookk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });


            }
        });
    }

    public class PlayitemAdapter extends RecyclerView.Adapter<PlayitemAdapter.MyViewHolder> {

        Context context;

        List<User> list = new ArrayList<>();
        // List<String>list = new ArrayList<>();

        public PlayitemAdapter(Context context, List<User> list) {

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public PlayitemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.playitem_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayitemAdapter.MyViewHolder myViewHolder, int i) {

            User item = list.get(i);

            myViewHolder.textView.setText(item.getName() + " joined to play");

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage(), myViewHolder.image, options);

        }

        public void setgrid(List<User> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            CircleImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.text);
                image = itemView.findViewById(R.id.image);


            }
        }
    }

    void setRepeat() {
        final Handler handler = new Handler();
// Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<playDataBean> call = cr.getPlayData(playId);

                call.enqueue(new Callback<playDataBean>() {
                    @Override
                    public void onResponse(Call<playDataBean> call, Response<playDataBean> response) {

                        adapter.setgrid(response.body().getUsers());

                    }

                    @Override
                    public void onFailure(Call<playDataBean> call, Throwable t) {

                    }
                });

                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 1000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }


}
