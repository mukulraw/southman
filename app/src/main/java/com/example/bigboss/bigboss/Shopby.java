package com.example.bigboss.bigboss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Shopby extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    ShopByAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shopby, container, false);

        adapter = new ShopByAdapter(getContext());


        manager = new GridLayoutManager(getContext() , 3);
        grid = view.findViewById(R.id.grid);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return view;
    }


    public class ShopByAdapter extends RecyclerView.Adapter<ShopByAdapter.MyViewHolder> {

        Context context;

        public ShopByAdapter(Context context){

            this.context = context;
        }


        @NonNull
        @Override
        public ShopByAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.shopby_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopByAdapter.MyViewHolder myViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
