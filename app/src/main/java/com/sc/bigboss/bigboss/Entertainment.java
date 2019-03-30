package com.sc.bigboss.bigboss;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class Entertainment extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    EntainmentAdapeter adapter;

    List<String>list;

    ProgressBar bar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.entertainment , container , false);

       // list = new ArrayList<>();

        adapter = new EntainmentAdapeter(getContext());

        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);


        bar = view.findViewById(R.id.progress);


        return view;
    }

    public class EntainmentAdapeter extends RecyclerView.Adapter<EntainmentAdapeter.MyViewHolder>{

        Context context;

        //List<String>list;

        public EntainmentAdapeter(Context context){

            this.context = context;
            //this.list = list;
        }


        @NonNull
        @Override
        public EntainmentAdapeter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.enter_list_model , viewGroup , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EntainmentAdapeter.MyViewHolder myViewHolder, int i) {


           /* String item = list.get(i);
            myViewHolder.text.setText("");


            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" ,myViewHolder. image , options);

*/

        }

       /* public void setgrid(List<String>list){

            this.list = list;
            notifyDataSetChanged();
        }

*/
        @Override
        public int getItemCount() {
            return 18;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView text;

            ImageView image;

            Button play;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                play = itemView.findViewById(R.id.play);

                image = itemView.findViewById(R.id.image);

                text = itemView.findViewById(R.id.text);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent(context , Videoplayer.class);
                        context.startActivity(i);
                    }
                });
            }
        }
    }
}
