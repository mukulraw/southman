package com.sc.bigboss.bigboss;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

import com.sc.bigboss.bigboss.PlaySliderPOJO.PlayBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SingleProduct extends AppCompatActivity {

    Toolbar toolbar;

    Button order;

    TextView name;
    TextView brand;
    TextView color;
    TextView size;
    TextView negitable;
    TextView price;
    static TextView title;
    WebView details;
    TextView cod;

    ViewPager imageView;

    CircleIndicator indicator;

    ProgressBar bar;

    static String id;

    ImageView search , home;

    String ph, co;

    String catName , base;

    LinearLayout negotitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        id = getIntent().getStringExtra("id");
        catName = getIntent().getStringExtra("catname");


        toolbar = findViewById(R.id.toolbar);
        negotitle = findViewById(R.id.negotitle);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        title = findViewById(R.id.title);

        indicator = findViewById(R.id.indicator);

        details = findViewById(R.id.text);

        details.setVerticalScrollBarEnabled(false);
        details.setHorizontalScrollBarEnabled(false);

details.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

        title.setText(getIntent().getStringExtra("text"));

        name = findViewById(R.id.name);

        brand = findViewById(R.id.brand);

        color = findViewById(R.id.color);

        size = findViewById(R.id.size);

        price = findViewById(R.id.price);

        negitable = findViewById(R.id.nagtiable);

        imageView = findViewById(R.id.image);

        order = findViewById(R.id.order);

        search = findViewById(R.id.search);
        home = findViewById(R.id.home);
        cod = findViewById(R.id.code);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SingleProduct.this, Search.class);
                startActivity(i);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SingleProduct.this, MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });


        bar = findViewById(R.id.progress);

        bar.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        base = b.baseurl;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<PlayBean> call = cr.play(id, SharePreferenceUtils.getInstance().getString("location"));

        call.enqueue(new Callback<PlayBean>() {
            @Override
            public void onResponse(Call<PlayBean> call, Response<PlayBean> response) {

                name.setText(response.body().getProductInfo().get(0).getProductTitle());

                brand.setText(response.body().getProductInfo().get(0).getBrand());

                color.setText(response.body().getProductInfo().get(0).getColor());

                size.setText(response.body().getProductInfo().get(0).getSize());
                cod.setText(response.body().getProductInfo().get(0).getProductCode());

/*                if (response.body().getProductInfo().get(0).getDiscountPrice() != null && !catName.equals("shop by shop"))
                {*/
                    price.setText(Html.fromHtml("\u20B9" + response.body().getProductInfo().get(0).getDiscountPrice() + "  <strike>\u20B9" + response.body().getProductInfo().get(0).getPrice() + "</strike>"));
               /* }
                else
                {
                    price.setText("\u20B9" + response.body().getProductInfo().get(0).getPrice());
                }*/

                //price.setText("\u20B9" + response.body().getProductInfo().get(0).getPrice());


                if (response.body().getProductInfo().get(0).getNegotiable().equals("no")) {

                    negitable.setText("No");

                    negitable.setTextColor(Color.RED);


                } else {
                    negitable.setText("Yes");

                    negitable.setTextColor(Color.parseColor("#4CAF50"));

                }


                    negotitle.setVisibility(View.GONE);


                if (response.body().getProductInfo().get(0).getWhatsappOrderNow().equals("yes")) {
                    order.setVisibility(View.VISIBLE);
                } else {
                    order.setVisibility(View.GONE);
                }

                ph = String.valueOf(response.body().getProductInfo().get(0).getPhoneNumber());

                co = String.valueOf(response.body().getProductInfo().get(0).getProductCode());

                details.loadData(response.body().getProductInfo().get(0).getProductDetail() , "text/html", "UTF-8");

                //details.setText(Html.fromHtml(response.body().getProductInfo().get(0).getProductDetail()));



                ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager(), response.body().getThumb().get(0));

                imageView.setAdapter(adapter);

                indicator.setViewPager(imageView);



                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<PlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SingleProduct.this, WristWatch.class);
                i.putExtra("id", id);
                i.putExtra("text", title.getText().toString());
                startActivity(i);

            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SingleProduct.this);

                dialog.setContentView(R.layout.dialog);

                dialog.setCancelable(true);

                dialog.show();

                TextView code = dialog.findViewById(R.id.code);

                TextView mobile = dialog.findViewById(R.id.mobile);

                Button watshp = dialog.findViewById(R.id.whatsapp);

                Button call = dialog.findViewById(R.id.call);

                mobile.setText(ph);

                code.setText(co);

                watshp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



/*

                        try {

                            Uri uri = Uri.parse("smsto:" + ph);
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            sendIntent.setPackage("com.whatsapp");
                            startActivity(sendIntent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
*/


                        openWhatsApp();

                        dialog.dismiss();

                       // String formattedNumber = Util.formatPhone(ph);
                       /* try{
                            Intent sendIntent =new Intent("android.intent.action.MAIN");
                            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.setType("text/plain");
                            sendIntent.putExtra(Intent.EXTRA_TEXT,"dstfdsg");
                            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("917503381028") +"@s.whatsapp.net");
                            sendIntent.setPackage("com.whatsapp");
                            SingleProduct.this.startActivity(sendIntent);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(SingleProduct.this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                        }
*/



/*

                        String url = "https://api.whatsapp.com/send?phone="+ph;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
*/


                    }
                });

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /* try {


                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse(ph));
                            startActivity(i);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }*/




                        try {

                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ph));
                            startActivity(intent);
                            dialog.dismiss();


                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }
                });
            }
        });

    }


    private void openWhatsApp() {



        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=91" + ph + "&text=Product Code : " + co));
        startActivity(browserIntent);

/*

        String smsNumber = "ph";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(ph) + "@s.whatsapp.net");//phone number without "+" prefix
           // sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(ph) + "Product code : " + co);//phone number without "+" prefix

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }*/
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public class ViewAdapter extends FragmentStatePagerAdapter {

        List<String> tlist = new ArrayList<>();

        public ViewAdapter(FragmentManager fm, List<String> tlist) {
            super(fm);
            this.tlist = tlist;
        }

        @Override
        public Fragment getItem(int i) {


            Page2 frag = new Page2();
            Bundle b = new Bundle();
            b.putString("url" , base + "bigboss/admin2/upload/products/" + tlist.get(i));

            frag.setArguments(b);
            return frag;

        }

        @Override
        public int getCount() {
            return tlist.size();
        }
    }

    public static class Page2 extends Fragment {

        ImageView imageView;

        String url;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.page1 , container , false);

            url = getArguments().getString("url");

            imageView = view.findViewById(R.id.watch);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(url , imageView , options);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getContext(), WristWatch.class);
                    i.putExtra("id", id);
                    i.putExtra("text", title.getText().toString());
                    startActivity(i);

                }
            });

            return view;
        }
    }

}
