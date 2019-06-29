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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.ShopTillPOJO.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Onedaysale extends Fragment {


    private RecyclerView grid;

    private GridLayoutManager manager;

    private OnedayAdapter adapter;

    private String catid;

    ProgressBar bar;

    private List<Datum>list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.till , container , false);

        catid = Objects.requireNonNull(getArguments()).getString("catid");

        list = new ArrayList<>();

        grid = vi.findViewById(R.id.grid);

        adapter = new OnedayAdapter(getContext() , list);

        manager = new GridLayoutManager(getContext() , 3);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return vi;


    }



    public class OnedayAdapter extends RecyclerView.Adapter<OnedayAdapter.MyViewHolder>{


        final Context context;
         List<Datum> list;



        OnedayAdapter(Context context, List<Datum> list){

            this.context  = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.till_list_model , viewGroup , false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OnedayAdapter.MyViewHolder myViewHolder, int i) {


            final Datum item = list.get(i);
            myViewHolder.name.setText(item.getSubcatName());


            /*DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage("" ,myViewHolder. imageView , options);
*/



          myViewHolder.itemView.setOnClickListener(v -> {


              Intent i1 = new Intent(context , MeansCategory.class);
              i1.putExtra("id" , item.getId());
              context.startActivity(i1);



          });


        }

       public void setgrid(List<Datum>list){
          this.list = list;
          notifyDataSetChanged();

       }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            final ImageView imageView;
            final TextView name;


            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                imageView = itemView.findViewById(R.id.tshirt);


            }
        }
    }




}
