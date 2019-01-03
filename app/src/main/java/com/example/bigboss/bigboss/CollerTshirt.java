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
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CollerTshirt extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    CollerAdapter adapeter;

    List<String>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coller_tshirt);


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

        adapeter = new CollerAdapter(this , list);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(this , 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapeter);

    }

    public class CollerAdapter extends RecyclerView.Adapter<CollerAdapter.MyViewHolder>{

        Context context;

        List<String>list = new ArrayList<>();

        public CollerAdapter(Context context , List<String>list){

            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public CollerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.coller_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CollerAdapter.MyViewHolder myViewHolder, int i) {



            String item = list.get(i);
            myViewHolder.name.setText("");
            myViewHolder.brand.setText("");
            myViewHolder.size.setText("");
            myViewHolder.prices.setText("");
            myViewHolder.color.setText("");
            myViewHolder.negtiable.setText("");


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" ,myViewHolder. image , options);

        }

       public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name , brand , size , prices , color , negtiable;

            ImageView image;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                brand = itemView.findViewById(R.id.brand);
                size = itemView.findViewById(R.id.size);
                prices = itemView.findViewById(R.id.price);
                color = itemView.findViewById(R.id.color);
                negtiable = itemView.findViewById(R.id.nagtiable);
                image = itemView.findViewById(R.id.image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context ,SingleProduct.class );
                        context.startActivity(i);
                    }
                });
            }
        }
    }




}
