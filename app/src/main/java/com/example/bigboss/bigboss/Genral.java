package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Genral extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    GenralAdapter adapter;

    List<String>list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.genral , container , false);

        list = new ArrayList<>();

        adapter = new GenralAdapter(getContext() , list);

        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);






        return view;
    }

    public class GenralAdapter extends RecyclerView.Adapter<GenralAdapter.MyViewHolder>{

        Context context;
        List<String>list = new ArrayList<>();

        public GenralAdapter(Context context , List<String>list){

            this.context = context;

            this.list = list;

        }


        @NonNull
        @Override
        public GenralAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.genral_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GenralAdapter.MyViewHolder myViewHolder, int i) {





        }

       public void setgrid(List<String> list){

            this.list = list;
            notifyDataSetChanged();
        }



        @Override
        public int getItemCount() {
            return 16;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

               /* itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context , Videoplayer.class);
                        context.startActivity(i);

                    }
                });*/
            }
        }
    }

}
