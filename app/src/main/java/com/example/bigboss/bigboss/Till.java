package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Till extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    TillAddapter adapter;

    List<String>list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.till , container ,false);

       // list = new ArrayList<>();

        adapter = new TillAddapter(getContext());

        grid = vi.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 3);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return vi;


    }

    public class TillAddapter extends RecyclerView.Adapter<TillAddapter.MyViewHolder>{


        Context context;
       // List<String>list = new ArrayList<>();



        public TillAddapter(Context context ){

            this.context  = context;
            //this.list = list;
        }

        @NonNull
        @Override
        public TillAddapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.till_list_model , viewGroup , false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TillAddapter.MyViewHolder myViewHolder, int i) {


          /*  String item = list.get(i);
            myViewHolder.name.setText("");


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" ,myViewHolder. imageView , options);
*/


        }

    /*   public void setgrid(List<String>list){
          this.list = list;
          notifyDataSetChanged();

       }*/

        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView name;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                imageView = itemView.findViewById(R.id.tshirt);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent(context , MeansCategory.class);
                        context.startActivity(i);
                    }
                });
            }
        }
    }
}
