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
import android.widget.ImageView;
import android.widget.TextView;

public class Onedaysale extends Fragment {


    RecyclerView grid;

    GridLayoutManager manager;

    OnedayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.oneday , container , false);

        grid = vi.findViewById(R.id.grid);

        adapter = new OnedayAdapter(getContext());

        manager = new GridLayoutManager(getContext() , 3);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return vi;
    }



    public class OnedayAdapter extends RecyclerView.Adapter<OnedayAdapter.MyViewHolder>{


        Context context;
        // List<String>list = new ArrayList<>();



        public OnedayAdapter(Context context ){

            this.context  = context;
            //this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.one_list_model , viewGroup , false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OnedayAdapter.MyViewHolder myViewHolder, int i) {


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
