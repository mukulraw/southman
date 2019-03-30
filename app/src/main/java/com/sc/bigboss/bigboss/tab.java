package com.sc.bigboss.bigboss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class tab extends AppCompatActivity {

    RecyclerView grid;
    LinearLayoutManager manager;

    TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        adapter = new TabAdapter(this);

        grid = findViewById(R.id.grid);
        manager = new LinearLayoutManager(this  , LinearLayoutManager.HORIZONTAL, false);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

    }

    public class TabAdapter extends RecyclerView.Adapter<TabAdapter.Myviewholder>{


        Context context;

        public TabAdapter(Context context){
            this.context = context;
        }

        @NonNull
        @Override
        public TabAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.tab_list_model , viewGroup , false);

            return new Myviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TabAdapter.Myviewholder myviewholder, int i) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class Myviewholder extends RecyclerView.ViewHolder{

            public Myviewholder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
