package com.example.bigboss.bigboss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.TillCategory3POJO.ProductInfo;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.example.bigboss.bigboss.getPlayPOJO.getPlayBean;
import com.example.bigboss.bigboss.registerPlayPOJO.registerPlayBean;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Play extends Fragment {

    Button submit;

    AutoScrollViewPager pager;

    CircleIndicator indicator;

    ImageAddapter adapter;

    TextView name, color, price, size, proof, brand, nagtiable;

    EditText email, phone;

    ProgressBar bar;

    String id;

    String playId;

    String imm;

    // List<ProductInfo>list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.play, container, false);

        //id = getArguments().getString("id");

        // list = new ArrayList<>();

        submit = view.findViewById(R.id.submit);

        bar = view.findViewById(R.id.progress);



        pager = (AutoScrollViewPager) view.findViewById(R.id.pager);

        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(2000);

        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        indicator = view.findViewById(R.id.indicator);





        name = view.findViewById(R.id.namee);

        brand = view.findViewById(R.id.brand);

        color = view.findViewById(R.id.color);

        price = view.findViewById(R.id.price);

        size = view.findViewById(R.id.size);

        nagtiable = view.findViewById(R.id.nagtiable);

        proof = view.findViewById(R.id.waterproof);

        email = view.findViewById(R.id.email);

        phone = view.findViewById(R.id.phone);

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getPlayBean> call = cr.getPlay();
        call.enqueue(new Callback<getPlayBean>() {
            @Override
            public void onResponse(Call<getPlayBean> call, Response<getPlayBean> response) {

                playId = response.body().getData().get(0).getId();

                imm = response.body().getData().get(0).getData().getImage().get(0);

                adapter = new ImageAddapter(getChildFragmentManager() , response.body().getData().get(0).getData().getImage());

                pager.setAdapter(adapter);

                indicator.setViewPager(pager);

                name.setText(response.body().getData().get(0).getData().getName());
                price.setText(response.body().getData().get(0).getData().getPrice());
                brand.setText(response.body().getData().get(0).getData().getBrand());
                color.setText(response.body().getData().get(0).getData().getColor());
                size.setText(response.body().getData().get(0).getData().getSize());
                nagtiable.setText(response.body().getData().get(0).getData().getNegotiable());
                //proof.setText(response.body().getProductInfo().get(0).get());


                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String e = email.getText().toString();
                String p = phone.getText().toString();

                if (e.length() > 0)
                {
                    if (p.length() == 10)
                    {


                        bar.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getContext().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                        Log.d("Android","Android ID : "+android_id);
                        Log.d("Android","Android ID : "+getLocalIpAddress());
                        Log.d("Android","Android ID : " + SharePreferenceUtils.getInstance().getString("token"));


                        Call<registerPlayBean> call = cr.registerPlay(playId , e , p , android_id , getLocalIpAddress()  , SharePreferenceUtils.getInstance().getString("token"));

                        call.enqueue(new Callback<registerPlayBean>() {
                            @Override
                            public void onResponse(Call<registerPlayBean> call, Response<registerPlayBean> response) {

                                if (response.body().getStatus().equals("1"))
                                {

                                    Intent i = new Intent(getContext(), Playitem.class);

                                    i.putExtra("userid" , response.body().getData().getUserId());
                                    i.putExtra("name" , response.body().getData().getName());
                                    i.putExtra("phone" , response.body().getData().getPhone());
                                    i.putExtra("playId" , response.body().getData().getPlayId());
                                    i.putExtra("image" , imm);
                                    i.putExtra("title" , name.getText().toString());
                                    i.putExtra("price" , price.getText().toString());
                                    i.putExtra("brand" , brand.getText().toString());
                                    i.putExtra("color" , color.getText().toString());
                                    i.putExtra("size" , size.getText().toString());
                                    i.putExtra("negotiable" , nagtiable.getText().toString());

                                    startActivity(i);

                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<registerPlayBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Invalid Phone", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
                }



            }
        });



        return view;
    }

    public class ImageAddapter extends FragmentStatePagerAdapter {


        // List<ProductInfo>list = new ArrayList<>();
        List<String> im = new ArrayList<>();

        public ImageAddapter(FragmentManager fm , List<String> im) {
            super(fm);
            this.im = im;

            //this.list = list;
        }

        @Override
        public Fragment getItem(int i) {


            String url = im.get(i);

            Image1 frag = new Image1();
            Bundle b = new Bundle();
            b.putString("url" , url);
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return im.size();
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
           /* indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));*/
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        pager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        pager.startAutoScroll();
    }

    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

}
