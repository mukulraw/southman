package com.example.bigboss.bigboss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WishList extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    WishAdapter adapter;

    List<String>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

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

        toolbar.setTitle("My WishList");

        list = new ArrayList<>();

        adapter = new WishAdapter(this , list);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);















    }


    public class WishAdapter extends RecyclerView.Adapter<WishAdapter.MyViewHolder>{


        Context context;

        List<String>list = new ArrayList<>();

        public WishAdapter(Context context , List<String>list){

            this.context = context;
            this.list = list;


        }

        @NonNull
        @Override
        public WishAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.wish_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WishAdapter.MyViewHolder myViewHolder, int i) {

/*

            String item = list.get(i);

            myViewHolder.name.setText("");
            myViewHolder.color.setText("");
            myViewHolder.price.setText("");
            myViewHolder.brand.setText("");
            myViewHolder.size.setText("");
            myViewHolder.negitable.setText("");


*/


        }



        public void  setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();
        }



        @Override
        public int getItemCount() {
            return 5;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView color , name, price , negitable , size , brand;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                color = itemView.findViewById(R.id.color);
                name = itemView.findViewById(R.id.name);
                price = itemView.findViewById(R.id.price);
                negitable = itemView.findViewById(R.id.nagtiable);
                size = itemView.findViewById(R.id.size);
                brand = itemView.findViewById(R.id.brand);


            }
        }
    }
}
