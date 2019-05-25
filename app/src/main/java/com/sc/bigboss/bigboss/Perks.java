package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.app.SharedElementCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.ScratchTextView;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.Datum;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.winnersPOJO.winnersBean;
import com.tarek360.instacapture.Instacapture;
import com.tarek360.instacapture.listener.SimpleScreenCapturingListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Perks extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;

    TextView perks, cash;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    CardAdapter adapter;
    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perks);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        perks = findViewById(R.id.perks);
        cash = findViewById(R.id.cash);
        grid = findViewById(R.id.grid);
        history = findViewById(R.id.history);

        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("Perks");


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perks.this , History.class);
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("asdsad", android_id);

        Call<getPerksBean> call = cr.getPerks(android_id);
        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {

                if (response.body().getStatus().equals("1")) {
                    cash.setText(response.body().getData().get(0).getCashRewards());
                    perks.setText(response.body().getData().get(0).getPerks());

                    progress.setVisibility(View.VISIBLE);

                    Call<scratchCardBean> call1 = cr.getScratchCards(response.body().getData().get(0).getId());

                    call1.enqueue(new Callback<scratchCardBean>() {
                        @Override
                        public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {

                            adapter = new CardAdapter(Perks.this , response1.body().getData());
                            manager = new GridLayoutManager(Perks.this , 2);
                            grid.setAdapter(adapter);
                            grid.setLayoutManager(manager);

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<scratchCardBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        List<Datum> list = new ArrayList<>();
        Context context;

        public CardAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.scratch_list_item , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.scratch_dialog);
                    dialog.show();

                    ScratchTextView scratch = dialog.findViewById(R.id.scratch);
                    Button share = dialog.findViewById(R.id.share);

                    scratch.setText(item.getCashValue());

                    scratch.setRevealListener(new ScratchTextView.IRevealListener() {
                        @Override
                        public void onRevealed(ScratchTextView tv) {

                            share.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {

                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Dialog dialog1 = new Dialog(context);
                            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog1.setCancelable(false);
                            dialog1.setContentView(R.layout.share_dialog);
                            dialog.dismiss();
                            dialog1.show();


                            TextView nnn = dialog1.findViewById(R.id.name);

                            nnn.setText("Attention " + SharePreferenceUtils.getInstance().getString("name"));


                            TextView device = dialog1.findViewById(R.id.device);
                            TextView code = dialog1.findViewById(R.id.code);
                            TextView cancel = dialog1.findViewById(R.id.cancel);
                            TextView proceed = dialog1.findViewById(R.id.proceed);
                            ProgressBar bar = dialog1.findViewById(R.id.progress);

                            String android_id = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);


                            device.setText("Device - " + android_id);
                            code.setText("Code - " + item.getText());

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });


                            proceed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Instacapture.INSTANCE.capture(Perks.this , new SimpleScreenCapturingListener() {
                                        @Override
                                        public void onCaptureComplete(Bitmap bitmap) {
                                            //Your code here..

                                            bar.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) getApplicationContext();

                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseurl)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                            Call<scratchCardBean> call2 = cr.redeem(item.getId() , SharePreferenceUtils.getInstance().getString("userid") , item.getCashValue() , item.getText() , item.getClient());

                                            call2.enqueue(new Callback<scratchCardBean>() {
                                                @Override
                                                public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response) {

                                                    if (response.body().getStatus().equals("1"))
                                                    {

                                                        progress.setVisibility(View.VISIBLE);

                                                        Call<scratchCardBean> call1 = cr.getScratchCards(SharePreferenceUtils.getInstance().getString("userid"));

                                                        call1.enqueue(new Callback<scratchCardBean>() {
                                                            @Override
                                                            public void onResponse(Call<scratchCardBean> call, Response<scratchCardBean> response1) {


                                                                adapter = new CardAdapter(Perks.this , response1.body().getData());
                                                                manager = new GridLayoutManager(Perks.this , 2);
                                                                grid.setAdapter(adapter);
                                                                grid.setLayoutManager(manager);

                                                                progress.setVisibility(View.GONE);
                                                            }

                                                            @Override
                                                            public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                                progress.setVisibility(View.GONE);
                                                            }
                                                        });

                                                        /*Intent sendIntent = new Intent("android.intent.action.SEND");
                                                        //File f=new File("path to the file");
                                                        //Uri uri = Uri.fromFile(file);
                                                        sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.ContactPicker"));
                                                        sendIntent.setType("image");
                                                        sendIntent.putExtra(Intent.EXTRA_STREAM , getImageUri(context , bitmap));
                                                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(item.getPhone())+"@s.whatsapp.net");
                                                        sendIntent.putExtra(Intent.EXTRA_TEXT,"");
                                                        startActivity(sendIntent);
*/


                                                        Toast.makeText(Perks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                        dialog1.dismiss();

                                                    }

                                                    bar.setVisibility(View.GONE);

                                                }

                                                @Override
                                                public void onFailure(Call<scratchCardBean> call, Throwable t) {
                                                    bar.setVisibility(View.GONE);
                                                }
                                            });




                                        }
                                    });

                                }
                            });



                        }
                    });


                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, String.valueOf(System.currentTimeMillis()), null);
        return Uri.parse(path);
    }

}
