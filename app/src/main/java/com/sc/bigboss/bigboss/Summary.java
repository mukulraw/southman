package com.sc.bigboss.bigboss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class Summary extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    GridAdapter adapter;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        toolbar = findViewById(R.id.toolbar5);
        grid = findViewById(R.id.grid);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("ORDER ID #455467873132458");

        adapter = new GridAdapter(Summary.this);
        manager = new GridLayoutManager(Summary.this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


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
            View view = inflater.inflate(R.layout.redeem_rewards_list_model , parent , false);
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