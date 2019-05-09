package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.getPlayPOJO.getPlayBean;
import com.sc.bigboss.bigboss.playDataPOJO.User;
import com.sc.bigboss.bigboss.playDataPOJO.playDataBean;
import com.sc.bigboss.bigboss.registerPlayPOJO.registerPlayBean;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Playitem extends AppCompatActivity {

    RecyclerView grid;

    GridLayoutManager manager;

    PlayitemAdapter adapter;

    Toolbar toolbar;

    Button quit;

    TextView name, color, price, size, chances, proof, brand, nagtiable, totalprice, timer, one, two, three, four, five, six, seven, eight, nine, ten, delete, ok;

    EditText totaltext;

    Handler handler;

    ImageView image;

    float current = 0;

    int count = 0;

    TextView bname;
    CircleImageView uimage;

    String userId, playId;

    List<User> list;

    int chanc = 3;

    String wid , wimage , wname = "South Man. No one played, this product is yours";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playitem);

        list = new ArrayList<>();

        userId = getIntent().getStringExtra("userid");
        playId = getIntent().getStringExtra("playId");

        toolbar = findViewById(R.id.toolbar);
        uimage = findViewById(R.id.image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        adapter = new PlayitemAdapter(this, list);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        grid = findViewById(R.id.grid);

        image = findViewById(R.id.watch);

        quit = findViewById(R.id.quit);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog dialog = new Dialog(Playitem.this);
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
        });


        name = findViewById(R.id.name);

        brand = findViewById(R.id.brand);

        color = findViewById(R.id.color);

        price = findViewById(R.id.price);

        size = findViewById(R.id.size);

        nagtiable = findViewById(R.id.nagtiable);

        proof = findViewById(R.id.waterproof);

        timer = findViewById(R.id.timer);

        totalprice = findViewById(R.id.bprice);
        bname = findViewById(R.id.bname);

        totaltext = findViewById(R.id.totaltext);

        one = findViewById(R.id.one);

        two = findViewById(R.id.two);

        three = findViewById(R.id.three);

        four = findViewById(R.id.four);

        five = findViewById(R.id.five);

        six = findViewById(R.id.six);

        seven = findViewById(R.id.seven);

        eight = findViewById(R.id.eight);

        nine = findViewById(R.id.nine);

        ten = findViewById(R.id.ten);

        delete = findViewById(R.id.delete);

        ok = findViewById(R.id.ok);

        chances = findViewById(R.id.chances);


        chances.setText("You have " + String.valueOf(chanc) + " chances left");


        String im = getIntent().getStringExtra("image");

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(im, image, options);


        name.setText(getIntent().getStringExtra("title"));
        price.setText(getIntent().getStringExtra("price"));

        current = 0;

        brand.setText(getIntent().getStringExtra("brand"));
        color.setText(getIntent().getStringExtra("color"));
        size.setText(getIntent().getStringExtra("size"));
        nagtiable.setText(getIntent().getStringExtra("negotiable"));


        totaltext.setInputType(InputType.TYPE_CLASS_TEXT);
        totaltext.setTextIsSelectable(false);

        totaltext.setCursorVisible(false);
        totaltext.setFocusableInTouchMode(false);
        totaltext.setFocusable(false);

        final InputConnection ic = totaltext.onCreateInputConnection(new EditorInfo());


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "1";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "2";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String value = "3";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String value = "4";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    String value = "5";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "6";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "7";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "8";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "9";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String value = "0";
                    ic.commitText(value, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    CharSequence selectedText = ic.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        // no selection, so delete previous character
                        ic.deleteSurroundingText(1, 0);
                    } else {
                        // delete the selection
                        ic.commitText("", 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chanc > 0) {
                    String tt = totaltext.getText().toString();

                    String pp = price.getText().toString();

                    if (tt.length() > 0) {
                        float ttt = Float.parseFloat(tt);

                        if (ttt > current && ttt < Float.parseFloat(pp)) {


                            Bean b = (Bean) getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<registerPlayBean> call = cr.playBid(playId, userId, tt);


                            call.enqueue(new Callback<registerPlayBean>() {
                                @Override
                                public void onResponse(Call<registerPlayBean> call, Response<registerPlayBean> response) {

                                    if (response.body().getStatus().equals("1")) {

                                        chanc = Integer.parseInt(response.body().getMessage());
                                        Toast.makeText(Playitem.this, "Bid placed successfully", Toast.LENGTH_SHORT).show();

                                        chances.setText("You have " + String.valueOf(chanc) + " chances left");

                                        totaltext.setText("");

                                    } else {
                                        Toast.makeText(Playitem.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onFailure(Call<registerPlayBean> call, Throwable t) {

                                }
                            });


                        } else {
                            Toast.makeText(Playitem.this, "The bid amount must be between " + String.valueOf(current) + " and " + price.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(Playitem.this, "You don't have any chances left", Toast.LENGTH_SHORT).show();
                }


            }
        });


        setRepeat();

        resetTimer();

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

                        try {

                            if (Float.parseFloat(response.body().getBids().get(0).getBid()) != current) {


                                bname.setText(response.body().getBids().get(0).getName() + " will own it for Rs. ");
                                totalprice.setText(response.body().getBids().get(0).getBid());

                                wname = response.body().getBids().get(0).getName();
                                wimage = response.body().getBids().get(0).getImage();

                                current = Float.parseFloat(response.body().getBids().get(0).getBid());

                                wid = response.body().getBids().get(0).getUserId();
                                //reset timer

                                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                                ImageLoader loader = ImageLoader.getInstance();
                                loader.displayImage(response.body().getBids().get(0).getImage() , uimage , options);

                                resetTimer();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    CountDownTimer timer2;


    void resetTimer() {

        try {
            timer2.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }


        timer2 = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText(getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

            }

            @Override
            public void onFinish() {


                ok.setClickable(false);
                ok.setEnabled(false);


                endPlay();

                //submitTest();
                //finish();
                Toast.makeText(Playitem.this, "Play finished", Toast.LENGTH_SHORT).show();

            }
        };


        timer2.start();

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


    void endPlay()
    {


        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<registerPlayBean> call = cr.endPlay(playId , wid);
        call.enqueue(new Callback<registerPlayBean>() {
            @Override
            public void onResponse(Call<registerPlayBean> call, Response<registerPlayBean> response) {


                Dialog dialog = new Dialog(Playitem.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.end_play_dialog);
                dialog.show();

                TextView cong = dialog.findViewById(R.id.textView20);
                CircularImageView iimm = dialog.findViewById(R.id.imageView4);

                cong.setText("Congratulations " + wname);
                // show dialog

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(wimage , iimm , options);

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        finish();

                    }
                });

            }

            @Override
            public void onFailure(Call<registerPlayBean> call, Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(Playitem.this);
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
}
