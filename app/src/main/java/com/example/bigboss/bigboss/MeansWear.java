package com.example.bigboss.bigboss;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MeansWear extends Fragment {


    LinearLayout top, bottom;

    ImageView pant , shirt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.meanswear, container, false);

        top = view.findViewById(R.id.top);
        bottom = view.findViewById(R.id.bottom);


        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.topdialog);
                dialog.setCancelable(true);
                dialog.show();


                RecyclerView grid = dialog.findViewById(R.id.grid);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
                MeansAdapter adapter = new MeansAdapter(getContext());
                grid.setLayoutManager(manager);
                grid.setAdapter(adapter);

            }
        });


        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.bottomdialog);
                dialog.setCancelable(true);
                dialog.show();


                RecyclerView grid = dialog.findViewById(R.id.grid);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
                BottomAdapter adapter = new BottomAdapter(getContext());
                grid.setLayoutManager(manager);
                grid.setAdapter(adapter);

            }
        });


        shirt = view.findViewById(R.id.shirt);
        pant = view.findViewById(R.id.pant);
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext(), SingleProduct.class);
                startActivity(i);

            }
        });


        pant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext(), SingleProduct.class);
                startActivity(i);

            }
        });


        return view;
    }


    public class MeansAdapter extends RecyclerView.Adapter<MeansAdapter.MyViewHolder> {

        Context context;

        // List<String> list = new ArrayList<>();

        public MeansAdapter(Context context) {

            this.context = context;

            //  this.list = list;
        }


        @NonNull
        @Override
        public MeansAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.means_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MeansAdapter.MyViewHolder myViewHolder, int i) {


           /* String item = list.get(i);

            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" ,myViewHolder. image , options);*/

        }

        /* public void setgrid(List<String> list) {

             this.list = list;
             notifyDataSetChanged();
         }
 */
        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;

            ImageView image;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.image);
            }
        }
    }


}
