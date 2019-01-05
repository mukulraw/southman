package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Playitem extends AppCompatActivity {

    RecyclerView grid;

    GridLayoutManager manager;

    PlayitemAdapter adapter;

    Toolbar toolbar;

    Button quit;

    TextView name , color  , price , size , chances, proof , brand , nagtiable , totalprice , totaltext , timer ,one , two , three , four , five , six , seven , eight , nine , ten , delete , ok;

    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playitem);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        list = new ArrayList<>();

        adapter = new PlayitemAdapter(this , list);

        manager = new GridLayoutManager(getApplicationContext() , 1);

        grid = findViewById(R.id.grid);
        quit = findViewById(R.id.quit);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Playitem.this , MainActivity.class);
                startActivity(i);
            }
        });



        name = findViewById(R.id.namee);

        brand = findViewById(R.id.brand);

        color = findViewById(R.id.color);

        price = findViewById(R.id.price);

        size = findViewById(R.id.size);

        nagtiable = findViewById(R.id.nagtiable);

        proof = findViewById(R.id.waterproof);

        timer = findViewById(R.id.timer);

        totalprice = findViewById(R.id.totalprices);

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


    }

    public class PlayitemAdapter extends RecyclerView.Adapter<PlayitemAdapter.MyViewHolder>{

        Context context;

        List<String>list = new ArrayList<>();

        public PlayitemAdapter(Context context , List<String>list){

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public PlayitemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.playitem_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayitemAdapter.MyViewHolder myViewHolder, int i) {


            String item = list.get(i);

            myViewHolder.textView.setText("");


        }

        public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();;
        }

        @Override
        public int getItemCount() {
            return 5;
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
