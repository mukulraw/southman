package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Steam extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    SteamAdapter adapter;

    List<String>list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.steam , container , false);

        list = new ArrayList<>();

        adapter = new SteamAdapter(getContext() , list);

        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);







        return view;
    }


    public class SteamAdapter extends RecyclerView.Adapter<SteamAdapter.MyViewHolder>{

        Context context;

        List<String>list = new ArrayList<>();

        public SteamAdapter(Context context , List<String>list){

            this.context = context;
            this.list = list;


        }


        @NonNull
        @Override
        public SteamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.steam_list_model , viewGroup , false);
            return new SteamAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SteamAdapter.MyViewHolder myViewHolder, int i) {

            //String item = list.get(i);
            myViewHolder.play.setText("");


            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" , myViewHolder.imageView , options);



        }

        public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();
        }



        @Override
        public int getItemCount() {
            return 16;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            Button play;

            ImageView imageView;

            TextView textView;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                play = itemView.findViewById(R.id.play);

                imageView = itemView.findViewById(R.id.image);

                textView = itemView.findViewById(R.id.text);

               /* Intent i = new Intent(context , Videoplayer.class);
                context.startActivity(i);*/
            }
        }
    }

}
