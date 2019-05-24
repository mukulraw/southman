package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.bigboss.bigboss.locationPOJO.Datum;
import com.sc.bigboss.bigboss.locationPOJO.locationBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Location extends AppCompatActivity {


    Spinner spinner;

    List<String> listt = new ArrayList<>();
    List<String> lid = new ArrayList<>();


    String li;

    Button submit;


    String sel = "";

    ProgressBar progress;


    Toolbar toolbar;

    RecyclerView grid;
    GridLayoutManager manager;
    locAdapter adapter;

    List<Datum> list;

    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        cd = new ConnectionDetector(Location.this);

        listt = new ArrayList<>();
        lid = new ArrayList<>();

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        adapter = new locAdapter(this, list);

        grid.setLayoutManager(manager);

        grid.setAdapter(adapter);

        spinner = findViewById(R.id.spinner);

        progress = findViewById(R.id.progress);

        if (cd.isConnectingToInternet()){
            Bean b = (Bean) getApplicationContext();

            progress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            String android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);



            String userid = SharePreferenceUtils.getInstance().getString("userid");


            if (userid.length() > 0)
            {

                Call<locationBean> call = cr.getLocations();

                call.enqueue(new Callback<locationBean>() {
                    @Override
                    public void onResponse(Call<locationBean> call, Response<locationBean> response) {

                        if (Objects.equals(response.body().getStatus(), "1")) {

                            adapter.setgrid(response.body().getData());

                        } else {

                            Toast.makeText(Location.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        progress.setVisibility(View.GONE);




                    }

                    @Override
                    public void onFailure(Call<locationBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            }
            else
            {


                Dialog dialog = new Dialog(Location.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.register_dialog);
                dialog.show();

                EditText name = dialog.findViewById(R.id.name);
                Button submit = dialog.findViewById(R.id.submit);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String n = name.getText().toString();

                        if (n.length() > 0)
                        {

                            Call<scratchCardBean> call1 = cr.register(android_id , n , SharePreferenceUtils.getInstance().getString("token"));

                            call1.enqueue(new Callback<scratchCardBean>() {
                                @Override
                                public void onResponse(Call<scratchCardBean> call2, Response<scratchCardBean> response2) {

                                    dialog.dismiss();

                                    SharePreferenceUtils.getInstance().saveString("userid" , response2.body().getMessage());


                                    Call<locationBean> call = cr.getLocations();

                                    call.enqueue(new Callback<locationBean>() {
                                        @Override
                                        public void onResponse(Call<locationBean> call, Response<locationBean> response) {

                                            if (Objects.equals(response.body().getStatus(), "1")) {

                                                adapter.setgrid(response.body().getData());

                                            } else {

                                                Toast.makeText(Location.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                            progress.setVisibility(View.GONE);




                                        }

                                        @Override
                                        public void onFailure(Call<locationBean> call, Throwable t) {
                                            progress.setVisibility(View.GONE);
                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Call<scratchCardBean> call, Throwable t) {



                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(Location.this, "Invalid name", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }









        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }




        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    sel = lid.get(position - 1);
                    submit.setClickable(true);
                    submit.setEnabled(true);
                }
                else
                {
                    submit.setClickable(false);
                    submit.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
       /* submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (sel.length() > 0) {
                        SharePreferenceUtils.getInstance().saveString("location", sel);
                        Intent i = new Intent(Location.this, MainActivity.class);
                        i.putExtra("lname", spinner.getSelectedItem().toString());
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Location.this, "Please choose a location", Toast.LENGTH_SHORT).show();
                    }

            }
        });*/
    }

    public class locAdapter extends RecyclerView.Adapter<locAdapter.My> {

        Context context;

        List<Datum> list = new ArrayList<>();

        public locAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;

        }


        @NonNull
        @Override
        public locAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.loc_list_model, viewGroup, false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull locAdapter.My my, final int i) {

            Datum item = list.get(i);

            my.lo.setText(item.getName());


            my.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SharePreferenceUtils.getInstance().saveString("location", list.get(i).getId());
                    SharePreferenceUtils.getInstance().saveString("lname", list.get(i).getName());
                    Intent ii = new Intent(Location.this, MainActivity.class);
                    //ii.putExtra("lname", list.get(i).getName());
                    startActivity(ii);
                    finish();


                }
            });


        }


        public void setgrid(List<Datum> list) {


            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class My extends RecyclerView.ViewHolder {

            TextView lo;
            RadioButton radioButton;

            public My(@NonNull View itemView) {
                super(itemView);

                lo = itemView.findViewById(R.id.lo);
                radioButton = itemView.findViewById(R.id.radio);
            }
        }
    }
}
