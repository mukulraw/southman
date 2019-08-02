package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sc.bigboss.bigboss.matchPOJO.Datum;
import com.sc.bigboss.bigboss.matchPOJO.matchBean;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MeansWear extends Fragment {


    private LinearLayout top;
    private LinearLayout bottom;

    private ImageView pant;
    private ImageView shirt;

    private String catId;
    private String base;
    private String type;

    private String tid;
    private String bid;
    private String ttitle;
    private String btitle;

    private ImageView tclick;
    private ImageView bclick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.meanswear, container, false);

        catId = Objects.requireNonNull(getArguments()).getString("Catid");
        type = getArguments().getString("type");

        top = view.findViewById(R.id.top);

        bottom = view.findViewById(R.id.bottom);

        tclick = view.findViewById(R.id.tclick);

        bclick = view.findViewById(R.id.bclick);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        base = b.baseurl;

        top.setOnClickListener(v -> {


            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.topdialog);
            dialog.setCancelable(true);
            dialog.show();


            final RecyclerView grid = dialog.findViewById(R.id.grid);
            final ProgressBar progress = dialog.findViewById(R.id.progress);
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);

            Bean b12 = (Bean) getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b12.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<matchBean> call = cr.getMatch(type , "top" , SharePreferenceUtils.getInstance().getString("location"));

            call.enqueue(new Callback<matchBean>() {
                @Override
                public void onResponse(Call<matchBean> call, Response<matchBean> response) {


                    MeansAdapter adapter = new MeansAdapter(getContext(), response.body().getData(), tclick, shirt, dialog);
                    grid.setLayoutManager(manager);
                    grid.setAdapter(adapter);

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<matchBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });


        });


        bottom.setOnClickListener(v -> {


            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.topdialog);
            dialog.setCancelable(true);
            dialog.show();


            final RecyclerView grid = dialog.findViewById(R.id.grid);
            final ProgressBar progress = dialog.findViewById(R.id.progress);
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);

            progress.setVisibility(View.VISIBLE);

            Bean b1 = (Bean) getContext().getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b1.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            Call<matchBean> call = cr.getMatch(type , "bottom" , SharePreferenceUtils.getInstance().getString("location"));

            Log.d("catId", catId);

            call.enqueue(new Callback<matchBean>() {
                @Override
                public void onResponse(Call<matchBean> call, Response<matchBean> response) {

                    Log.d("count", String.valueOf(response.body().getData()));

                    BottomAdapter adapter = new BottomAdapter(getContext(), response.body().getData(), bclick, pant, dialog);

                    grid.setLayoutManager(manager);

                    grid.setAdapter(adapter);

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<matchBean> call, Throwable t) {

                    progress.setVisibility(View.GONE);

                }
            });


        });


        shirt = view.findViewById(R.id.shirt);

        pant = view.findViewById(R.id.pant);

        shirt.setOnClickListener(v -> {

            if (tid != null) {

                Intent i = new Intent(getContext(), SingleProduct2.class);
                i.putExtra("id", tid);
                i.putExtra("text", ttitle);
                startActivity(i);

            } else {
                Toast.makeText(getContext(), "Please choose the Top Wear", Toast.LENGTH_SHORT).show();
            }


        });


        pant.setOnClickListener(v -> {

            //  Log.d("id" , bid);
            // Log.d("text" , btitle);

            if (bid != null) {

                Intent i = new Intent(getContext(), SingleProduct2.class);
                i.putExtra("id", bid);
                i.putExtra("text", btitle);
                startActivity(i);

            } else {
                Toast.makeText(getContext(), "Please choose the Bottom Wear", Toast.LENGTH_SHORT).show();
            }


        });


        return view;
    }


    public class MeansAdapter extends RecyclerView.Adapter<MeansAdapter.MyViewHolder> {

        final Context context;
        final ImageView tclick;
        final ImageView tview;
        final Dialog dialog;
        //String tid , ttitle;
        List<Datum> list;

        // List<String> list = new ArrayList<>();

        MeansAdapter(Context context, List<Datum> list, ImageView tclick, ImageView tview, Dialog dialog) {

            this.context = context;
            this.list = list;
            this.tclick = tclick;
            this.tview = tview;
            this.dialog = dialog;

        }


        @NonNull
        @Override
        public MeansAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.means_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MeansAdapter.MyViewHolder myViewHolder, int i) {


            try {
                final Datum item = list.get(i);

                final Bitmap[] lBitmap = new Bitmap[1];

                DisplayImageOptions options = new DisplayImageOptions.Builder().
                        cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

                ImageLoader loader = ImageLoader.getInstance();


                loader.loadImage(base + "southman/admin2/upload/products/" + item.getProductImage(), options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        myViewHolder.image.setImageBitmap(loadedImage);
                        lBitmap[0] = loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                myViewHolder.name.setText(item.getProductTitle());

                myViewHolder.itemView.setOnClickListener(v -> {

                    tview.setImageBitmap(lBitmap[0]);
                    tclick.setImageBitmap(lBitmap[0]);

                    tid = item.getPid();
                    ttitle = item.getProductTitle();

                    dialog.dismiss();

                });


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /* public void setgrid(List<String> list) {

             this.list = list;
             notifyDataSetChanged();
         }
 */
        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final TextView name;

            final ImageView image;


            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.tshirt);
            }
        }
    }

    public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.MyViewHolder> {

        final Context context;
        final ImageView tclick;
        final ImageView tview;
        final Dialog dialog;
        String tid, ttitle;
        List<Datum> list;

        // List<String> list = new ArrayList<>();

        BottomAdapter(Context context, List<Datum> list, ImageView tclick, ImageView tview, Dialog dialog) {

            this.context = context;
            this.list = list;
            this.tclick = tclick;
            this.tview = tview;
            this.dialog = dialog;

        }


        @NonNull
        @Override
        public BottomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.means_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final BottomAdapter.MyViewHolder myViewHolder, int i) {


            try {

                final Datum item = list.get(i);

                final Bitmap[] lBitmap = new Bitmap[1];

                DisplayImageOptions options = new DisplayImageOptions.Builder().
                        cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

                ImageLoader loader = ImageLoader.getInstance();


                loader.loadImage(base + "southman/admin2/upload/products/" + item.getProductImage(), options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        myViewHolder.image.setImageBitmap(loadedImage);
                        lBitmap[0] = loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                myViewHolder.name.setText(item.getProductTitle());

                myViewHolder.itemView.setOnClickListener(v -> {

                    tview.setImageBitmap(lBitmap[0]);
                    tclick.setImageBitmap(lBitmap[0]);

                    bid = item.getPid();
                    btitle = item.getProductTitle();

                    dialog.dismiss();

                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /* public void setgrid(List<String> list) {

             this.list = list;
             notifyDataSetChanged();
         }
 */
        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            final TextView name;

            final ImageView image;


            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.tshirt);
            }
        }
    }


}
