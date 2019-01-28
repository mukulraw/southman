package com.example.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bigboss.bigboss.matchingPOJO.BottomWear;
import com.example.bigboss.bigboss.matchingPOJO.Data;
import com.example.bigboss.bigboss.matchingPOJO.TopWear;
import com.example.bigboss.bigboss.matchingPOJO.matchingBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MeansWear extends Fragment {


    LinearLayout top, bottom;

    ImageView pant , shirt;

    String catId;

    String tid, bid , ttitle , btitle;

    ImageView tclick , bclick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.meanswear, container, false);

        catId = getArguments().getString("Catid");

        top = view.findViewById(R.id.top);
        bottom = view.findViewById(R.id.bottom);
        tclick = view.findViewById(R.id.tclick);
        bclick = view.findViewById(R.id.bclick);


        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.topdialog);
                dialog.setCancelable(true);
                dialog.show();


                final RecyclerView grid = dialog.findViewById(R.id.grid);
                final ProgressBar progress = dialog.findViewById(R.id.progress);
                final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);

                Bean b = (Bean)getContext().getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                Call<matchingBean> call = cr.getMatchingData(catId);

                call.enqueue(new Callback<matchingBean>() {
                    @Override
                    public void onResponse(Call<matchingBean> call, Response<matchingBean> response) {

                        Log.d("count" , String.valueOf(response.body().getData().getTopWear().size()));

                        MeansAdapter adapter = new MeansAdapter(getContext() , response.body().getData().getTopWear() , tclick , shirt , dialog , tid , ttitle);
                        grid.setLayoutManager(manager);
                        grid.setAdapter(adapter);

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<matchingBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });



            }
        });


        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.topdialog);
                dialog.setCancelable(true);
                dialog.show();


                final RecyclerView grid = dialog.findViewById(R.id.grid);
                final ProgressBar progress = dialog.findViewById(R.id.progress);
                final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);

                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean)getContext().getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                Call<matchingBean> call = cr.getMatchingData(catId);

                call.enqueue(new Callback<matchingBean>() {
                    @Override
                    public void onResponse(Call<matchingBean> call, Response<matchingBean> response) {

                        Log.d("count" , String.valueOf(response.body().getData().getTopWear().size()));

                        BottomAdapter adapter = new BottomAdapter(getContext() , response.body().getData().getBottomWear() , bclick , pant , dialog , bid , btitle);
                        grid.setLayoutManager(manager);
                        grid.setAdapter(adapter);

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<matchingBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });




            }
        });


        shirt = view.findViewById(R.id.shirt);
        pant = view.findViewById(R.id.pant);
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext(), SingleProduct.class);
                i.putExtra("id"  , tid);
                i.putExtra("text"  , ttitle);
                startActivity(i);

            }
        });


        pant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext(), SingleProduct.class);
                i.putExtra("id"  , bid);
                i.putExtra("text"  , btitle);
                startActivity(i);

            }
        });


        return view;
    }


    public class MeansAdapter extends RecyclerView.Adapter<MeansAdapter.MyViewHolder> {

        Context context;
        ImageView tclick , tview;
        Dialog dialog;
        String tid , ttitle;
        List<List<TopWear>> list = new ArrayList<>();

        // List<String> list = new ArrayList<>();

        MeansAdapter(Context context, List<List<TopWear>> list, ImageView tclick, ImageView tview, Dialog dialog, String tid , String ttitle) {

            this.context = context;
            this.list = list;
            this.tclick = tclick;
            this.tview = tview;
            this.dialog = dialog;
            this.tid = tid;
            this.ttitle = ttitle;

        }


        @NonNull
        @Override
        public MeansAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.means_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MeansAdapter.MyViewHolder myViewHolder, int i) {

            final TopWear item = list.get(i).get(0);

            final Bitmap[] lBitmap = new Bitmap[1];

            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();


            loader.loadImage(item.getProductImage(), options, new ImageLoadingListener() {
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

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tview.setImageBitmap(lBitmap[0]);
                    tclick.setImageBitmap(lBitmap[0]);

                    tid = item.getId();
                    ttitle = item.getProductTitle();

                    dialog.dismiss();

                }
            });



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

            TextView name;

            ImageView image;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.tshirt);
            }
        }
    }

    public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.MyViewHolder> {

        Context context;
        ImageView tclick , tview;
        Dialog dialog;
        String tid , ttitle;
        List<List<BottomWear>> list = new ArrayList<>();

        // List<String> list = new ArrayList<>();

        BottomAdapter(Context context, List<List<BottomWear>> list, ImageView tclick, ImageView tview, Dialog dialog, String tid , String ttitle) {

            this.context = context;
            this.list = list;
            this.tclick = tclick;
            this.tview = tview;
            this.dialog = dialog;
            this.tid = tid;
            this.ttitle = ttitle;

        }


        @NonNull
        @Override
        public BottomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.means_list_model, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final BottomAdapter.MyViewHolder myViewHolder, int i) {

            final BottomWear item = list.get(i).get(0);

            final Bitmap[] lBitmap = new Bitmap[1];

            DisplayImageOptions options = new DisplayImageOptions.Builder().
                    cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();


            loader.loadImage(item.getProductImage(), options, new ImageLoadingListener() {
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

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tview.setImageBitmap(lBitmap[0]);
                    tclick.setImageBitmap(lBitmap[0]);

                    tid = item.getId();
                    ttitle = item.getProductTitle();

                    dialog.dismiss();

                }
            });



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

            TextView name;

            ImageView image;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                image = itemView.findViewById(R.id.tshirt);
            }
        }
    }


}
