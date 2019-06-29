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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sc.bigboss.bigboss.matchByIdPOJO.matchByIdBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SingleProduct2 extends AppCompatActivity {

    private Toolbar toolbar;

    private Button order;

    private TextView name;
    private TextView brand;
    private TextView color;
    private TextView size;
    private TextView negitable;
    private TextView price;
    private static TextView title;
    private TextView details;
    private TextView cod;

    private ViewPager imageView;

    private CircleIndicator indicator;

    private ProgressBar bar;

    private static String id;

    private ImageView search;
    private ImageView home;

    private String ph;
    private String co;

    private String catName;
    private String base;

    private LinearLayout negotitle;

    private ImageView notification;
    private ImageView perks2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product2);
        id = getIntent().getStringExtra("id");
        catName = getIntent().getStringExtra("catname");


        toolbar = findViewById(R.id.toolbar);
        negotitle = findViewById(R.id.negotitle);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);


        toolbar.setNavigationOnClickListener(v -> finish());
        title = findViewById(R.id.title);

        notification = findViewById(R.id.notification);
        perks2 = findViewById(R.id.perks2);


        notification.setOnClickListener(v -> {


            Intent i = new Intent(SingleProduct2.this, Notification.class);
            startActivity(i);
        });

        perks2.setOnClickListener(view -> {

            Intent intent = new Intent(SingleProduct2.this , Perks.class);
            startActivity(intent);
        });

        indicator = findViewById(R.id.indicator);

        details = findViewById(R.id.text);

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

        search.setOnClickListener(v -> {


            Intent i = new Intent(SingleProduct2.this, Search.class);
            startActivity(i);
        });


        home.setOnClickListener(v -> {

            Intent i = new Intent(SingleProduct2.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
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

        Call<matchByIdBean> call = cr.getMatchById(id);

        call.enqueue(new Callback<matchByIdBean>() {
            @Override
            public void onResponse(Call<matchByIdBean> call, Response<matchByIdBean> response) {

                name.setText(Objects.requireNonNull(response.body()).getData().getProductTitle());

                brand.setText(response.body().getData().getBrand());

                color.setText(response.body().getData().getColor());

                size.setText(response.body().getData().getSize());
                cod.setText(response.body().getData().getProductCode());

                if (response.body().getData().getDiscountPrice() != null)
                {
                    price.setText(Html.fromHtml("\u20B9" + response.body().getData().getDiscountPrice() + "  <strike>\u20B9" + response.body().getData().getPrice() + "</strike>"));
                }
                else
                {
                    price.setText("\u20B9" + response.body().getData().getPrice());
                }

                //price.setText("\u20B9" + response.body().getProductInfo().get(0).getPrice());


                if (response.body().getData().getNegotiable().equals("no")) {

                    negitable.setText("No");

                    negitable.setTextColor(Color.RED);


                } else {
                    negitable.setText("Yes");

                    negitable.setTextColor(Color.parseColor("#4CAF50"));

                }

                /*if (catName.equals("shop by shop"))
                {
                    negotitle.setVisibility(View.VISIBLE);
                }
                else
                {
                    negotitle.setVisibility(View.GONE);
                }*/

                if (response.body().getData().getWhatsappOrderNow().equals("yes")) {
                    order.setVisibility(View.VISIBLE);
                } else {
                    order.setVisibility(View.GONE);
                }

                ph = String.valueOf(response.body().getData().getPhoneNumber());

                co = String.valueOf(response.body().getData().getProductCode());

                details.setText(Html.fromHtml(response.body().getData().getSubTitle()).toString().trim());



                ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager(), response.body().getData().getThumb());

                imageView.setAdapter(adapter);

                indicator.setViewPager(imageView);



                bar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<matchByIdBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


        imageView.setOnClickListener(v -> {

            Intent i = new Intent(SingleProduct2.this, WristWatch.class);
            i.putExtra("id", id);
            i.putExtra("text", title.getText().toString());
            startActivity(i);

        });


        order.setOnClickListener(v -> {

            final Dialog dialog = new Dialog(SingleProduct2.this);

            dialog.setContentView(R.layout.dialog);

            dialog.setCancelable(true);

            dialog.show();

            TextView code = dialog.findViewById(R.id.code);

            TextView mobile = dialog.findViewById(R.id.mobile);

            Button watshp = dialog.findViewById(R.id.whatsapp);

            Button call1 = dialog.findViewById(R.id.call);

            mobile.setText(ph);

            code.setText(co);

            watshp.setOnClickListener(v12 -> {



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


            });

            call1.setOnClickListener(v1 -> {

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


            });
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
        }
        return app_installed;
    }

    class ViewAdapter extends FragmentStatePagerAdapter {

        List<String> tlist;

        ViewAdapter(FragmentManager fm, List<String> tlist) {
            super(fm);
            this.tlist = tlist;
        }

        @Override
        public Fragment getItem(int i) {


            Page2 frag = new Page2();
            Bundle b = new Bundle();
            b.putString("url" , base + "southman/admin2/upload/products/" + tlist.get(i));

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

            url = Objects.requireNonNull(getArguments()).getString("url");

            imageView = view.findViewById(R.id.watch);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(url , imageView , options);

            imageView.setOnClickListener(v -> {

                Intent i = new Intent(getContext(), WristWatch2.class);
                i.putExtra("id", id);
                i.putExtra("text", title.getText().toString());
                startActivity(i);

            });

            return view;
        }
    }


}
