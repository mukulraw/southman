package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.usersPOJO.usersBean;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Perks extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progress;

    private TextView perks;
    private TextView cash;

    private TextView name;
    private TextView phone;

private Button update;

    private TextView history;

    private Button transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perks);


        toolbar = findViewById(R.id.toolbar);
        perks = findViewById(R.id.perks);
        cash = findViewById(R.id.cash);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        update = findViewById(R.id.update);

        history = findViewById(R.id.history);
        transfer = findViewById(R.id.transfer);

        progress = findViewById(R.id.progress);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setTitle("Profile");


        update.setOnClickListener(v -> {

            Dialog dialog = new Dialog(Perks.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.register_dialog);
            dialog.show();

            EditText name = dialog.findViewById(R.id.name);
            EditText phone = dialog.findViewById(R.id.phone);
            Button submit = dialog.findViewById(R.id.submit);


            submit.setOnClickListener(v12 -> {

                String n = name.getText().toString();
                String p = phone.getText().toString();

                if (n.length() > 0) {

                    if (p.length() == 10) {

                        Bean b = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        String android_id = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                        Call<scratchCardBean> call1 = cr.register(android_id, n, SharePreferenceUtils.getInstance().getString("token"), p);

                        call1.enqueue(new Callback<scratchCardBean>() {
                            @Override
                            public void onResponse(Call<scratchCardBean> call2, Response<scratchCardBean> response2) {

                                dialog.dismiss();

                                SharePreferenceUtils.getInstance().saveString("userid", response2.body().getMessage());
                                SharePreferenceUtils.getInstance().saveString("name", n);
                                SharePreferenceUtils.getInstance().saveString("phone", p);

                                onResume();

                            }

                            @Override
                            public void onFailure(Call<scratchCardBean> call, Throwable t) {


                            }
                        });
                    } else {
                        Toast.makeText(Perks.this, "Invalid phone", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(Perks.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            });


        });

        history.setOnClickListener(v -> {
            Intent intent = new Intent(Perks.this, History.class);
            startActivity(intent);
        });


        transfer.setOnClickListener(v -> {


            Dialog dialog = new Dialog(Perks.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.transfer_layout);
            dialog.show();


            List<String> ids = new ArrayList<>();
            List<String> names = new ArrayList<>();
            final String[] id = new String[1];

            SearchableSpinner spinner = dialog.findViewById(R.id.spinner);
            EditText amount = dialog.findViewById(R.id.amount);
            Button submit = dialog.findViewById(R.id.submit);

            spinner.setTitle("Select user");
            spinner.setPositiveButton("OK");

            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


            Call<usersBean> call = cr.getUsers();
            call.enqueue(new Callback<usersBean>() {
                @Override
                public void onResponse(Call<usersBean> call, Response<usersBean> response) {


                    for (int i = 0; i < response.body().getData().size(); i++) {
                        ids.add(response.body().getData().get(i).getId());
                        names.add(response.body().getData().get(i).getName() + "_" + response.body().getData().get(i).getDeviceId());
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Perks.this,
                            R.layout.spinner_item, names);

                    spinner.setAdapter(adapter);


                }

                @Override
                public void onFailure(Call<usersBean> call, Throwable t) {

                }
            });


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {

                    id[0] = ids.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




            submit.setOnClickListener(v1 -> {

                String a = amount.getText().toString();
                String c = cash.getText().toString();

                float aa = 0, cc = 0;

                try {

                    aa = Float.parseFloat(a);
                    cc = Float.parseFloat(c);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (aa > 0 && aa <= cc) {


                    Call<usersBean> call1 = cr.transfer(SharePreferenceUtils.getInstance().getString("userid"), id[0], String.valueOf(aa));

                    call1.enqueue(new Callback<usersBean>() {
                        @Override
                        public void onResponse(Call<usersBean> call22, Response<usersBean> response) {

                            dialog.dismiss();
                            Toast.makeText(Perks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            onResume();
                        }

                        @Override
                        public void onFailure(Call<usersBean> call22, Throwable t) {

                        }
                    });


                } else {
                    Toast.makeText(Perks.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }

            });



        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);

        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        name.setText(SharePreferenceUtils.getInstance().getString("name") + "_" + android_id);
        phone.setText(SharePreferenceUtils.getInstance().getString("phone"));


        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


        Log.d("asdsad", android_id);

        Call<getPerksBean> call = cr.getPerks(SharePreferenceUtils.getInstance().getString("userid"));
        call.enqueue(new Callback<getPerksBean>() {
            @Override
            public void onResponse(Call<getPerksBean> call, Response<getPerksBean> response) {

                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
                    cash.setText(response.body().getData().get(0).getCashRewards());
                    perks.setText(response.body().getData().get(0).getPerks());



                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPerksBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, String.valueOf(System.currentTimeMillis()), null);
        return Uri.parse(path);
    }

}
