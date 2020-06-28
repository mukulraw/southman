package com.sc.bigboss.bigboss;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class paymentnew extends Fragment {

    RecyclerView grid;
    GridAdapter adapter;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paymentnew , container , false);

        grid = view.findViewById(R.id.grid);

        adapter = new GridAdapter(getContext());
        manager = new GridLayoutManager(getContext() , 2);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


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
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

}
