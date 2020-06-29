package com.sc.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class paymentnew extends Fragment {

    RecyclerView grid;
    GridAdapter adapter;
    GridLayoutManager manager;
    FloatingActionButton next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paymentnew , container , false);

        grid = view.findViewById(R.id.grid);
        next = view.findViewById(R.id.floatingActionButton2);

        adapter = new GridAdapter(getContext());
        manager = new GridLayoutManager(getContext() , 2);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext() , Summary.class);
                startActivity(intent);

            }
        });

        return view;
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>
    {
        Context context;

        public GridAdapter(Context context)
        {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.rewards_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

}
