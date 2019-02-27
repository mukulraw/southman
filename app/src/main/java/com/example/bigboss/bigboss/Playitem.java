package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.playDataPOJO.User;
import com.example.bigboss.bigboss.playDataPOJO.playDataBean;
import com.example.bigboss.bigboss.registerPlayPOJO.registerPlayBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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

    float current = 0;

    int count = 0;

    TextView bname;
    ImageView image;

    String userId, playId;

    List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playitem);

        list = new ArrayList<>();

        userId = getIntent().getStringExtra("userid");
        playId = getIntent().getStringExtra("playId");

        toolbar = findViewById(R.id.toolbar);
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


                Intent i = new Intent(Playitem.this, MainActivity.class);
                startActivity(i);

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


        String im = getIntent().getStringExtra("image");

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(im, image, options);


        name.setText(getIntent().getStringExtra("title"));
        price.setText(getIntent().getStringExtra("price"));

        current = Float.parseFloat(getIntent().getStringExtra("price"));

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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
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
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tt = totaltext.getText().toString();

                if (tt.length() > 0)
                {
                    float ttt = Float.parseFloat(tt);

                    if (ttt > current)
                    {


                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<registerPlayBean> call = cr.playBid(playId , userId , tt);


                        call.enqueue(new Callback<registerPlayBean>() {
                            @Override
                            public void onResponse(Call<registerPlayBean> call, Response<registerPlayBean> response) {

                                Toast.makeText(Playitem.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(Call<registerPlayBean> call, Throwable t) {

                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(Playitem.this, "The bid amount must be greater than " + String.valueOf(current), Toast.LENGTH_SHORT).show();
                    }

                }




            }
        });


        setRepeat();



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

                            bname.setText(response.body().getBids().get(0).getName() + " will own it for Rs. ");
                            totalprice.setText(response.body().getBids().get(0).getBid());

                            current = Float.parseFloat(response.body().getBids().get(0).getBid());

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

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.text);


            }
        }
    }
}
