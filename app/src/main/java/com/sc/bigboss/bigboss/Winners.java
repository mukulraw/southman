package com.sc.bigboss.bigboss;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.bigboss.bigboss.winnersPOJO.Datum;
import com.sc.bigboss.bigboss.winnersPOJO.winnersBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Winners extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView grid;
    private ProgressBar progress;
    private GridLayoutManager manager;
    private List<Datum> list;
    private WinnersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progress);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Winners");

        adapter = new WinnersAdapter(this , list);

        manager = new GridLayoutManager(this , 1);
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Call<winnersBean> call = cr.getWiners();
        call.enqueue(new Callback<winnersBean>() {
            @Override
            public void onResponse(Call<winnersBean> call, Response<winnersBean> response) {

                Log.d("respomse" , String.valueOf(Objects.requireNonNull(response.body()).getData().size()));

                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<winnersBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class WinnersAdapter extends RecyclerView.Adapter<WinnersAdapter.ViewHolder>
    {
        final Context context;
        List<Datum> list;

        WinnersAdapter(Context context, List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.winner_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.title.setText(item.getPid());
            holder.date.setText(item.getTime());
            holder.name.setText(item.getUsername());
            holder.price.setText(Html.fromHtml("\u20B9" + item.getWprice() + "  <strike>\u20B9" + item.getPrice() + "</strike>"));

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getUserimage() , holder.image , options);



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            final TextView title;
            final TextView price;
            final TextView name;
            final TextView date;
            final CircleImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.textView23);
                price = itemView.findViewById(R.id.textView24);
                name = itemView.findViewById(R.id.textView25);
                date = itemView.findViewById(R.id.textView26);
                image = itemView.findViewById(R.id.imageView8);

            }
        }
    }

}
