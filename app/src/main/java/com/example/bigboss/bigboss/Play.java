package com.example.bigboss.bigboss;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigboss.bigboss.TillCategory3POJO.ProductInfo;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.example.bigboss.bigboss.getPlayPOJO.getPlayBean;
import com.example.bigboss.bigboss.registerPlayPOJO.registerPlayBean;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;

public class Play extends Fragment {

    FloatingActionButton submit;

    AutoScrollViewPager pager;

    CircleIndicator indicator;

    ImageAddapter adapter;

    TextView textTimer, imageTimer;

    TextView name, color, price, size, proof, brand, nagtiable;

    EditText email, phone;

    ProgressBar bar;

    String id;

    String playId;

    String imm;

    ConstraintLayout regLayout;
    View imgLayout;

    LinearLayout changeImage;

    ImageView image;

    String video, diff;

    // List<ProductInfo>list;

    String tttt;

    Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.play, container, false);

        //id = getArguments().getString("id");

        // list = new ArrayList<>();

        submit = view.findViewById(R.id.submit);

        bar = view.findViewById(R.id.progress);

        changeImage = view.findViewById(R.id.changeImage);
        image = view.findViewById(R.id.image);

        regLayout = view.findViewById(R.id.constraintLayout);
        imgLayout = view.findViewById(R.id.play_status);

        textTimer = view.findViewById(R.id.textView13);
        imageTimer = view.findViewById(R.id.textView5);


        pager = view.findViewById(R.id.pager);

        pager.addOnPageChangeListener(new MyOnPageChangeListener());

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

        /*bar.setVisibility(View.VISIBLE);

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


                if (response.body().getStatus().equals("1")) {
                    playId = response.body().getData().get(0).getId();


                    String status = response.body().getData().get(0).getStatus();
                    diff = response.body().getData().get(0).getDiff();
                    video = response.body().getData().get(0).getVideo();


                    if (status.equals("soon")) {

                        regLayout.setVisibility(View.INVISIBLE);
                        imgLayout.setVisibility(View.VISIBLE);
                        //imageTimer.setText("Play begins in ");

                        startImageTimer();


                    } else if (status.equals("register")) {
                        imm = response.body().getData().get(0).getData().getImage().get(0);

                        adapter = new ImageAddapter(getChildFragmentManager(), response.body().getData().get(0).getData().getImage());

                        pager.setAdapter(adapter);

                        indicator.setViewPager(pager);

                        name.setText(response.body().getData().get(0).getData().getName());
                        price.setText(response.body().getData().get(0).getData().getPrice());
                        brand.setText(response.body().getData().get(0).getData().getBrand());
                        color.setText(response.body().getData().get(0).getData().getColor());
                        size.setText(response.body().getData().get(0).getData().getSize());
                        nagtiable.setText(response.body().getData().get(0).getData().getNegotiable());
                        //proof.setText(response.body().getProductInfo().get(0).get());


                        regLayout.setVisibility(View.VISIBLE);
                        imgLayout.setVisibility(View.INVISIBLE);
                        //textTimer.setText("Time till registration ");

                        startTextTimer();

                    } else {
                        regLayout.setVisibility(View.INVISIBLE);
                        imgLayout.setVisibility(View.VISIBLE);
                        imageTimer.setText("Play has ended");
                    }


                } else {

                    regLayout.setVisibility(View.INVISIBLE);
                    imgLayout.setVisibility(View.VISIBLE);
                    imageTimer.setText("No play found");

                }


                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });*/


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String e = email.getText().toString();
                String p = phone.getText().toString();

                if (e.length() > 0) {
                    if (p.length() == 10) {


                        bar.setVisibility(View.VISIBLE);


                        MultipartBody.Part body = null;

                        try {

                            String ypath = getPath(getActivity(), uri);
                            File f1 = new File(ypath);

                            RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                            body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


                        }catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }

                        Bean b = (Bean) getContext().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        String android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                        Log.d("Android", "Android ID : " + android_id);
                        Log.d("Android", "Android ID : " + getLocalIpAddress());
                        Log.d("Android", "Android ID : " + SharePreferenceUtils.getInstance().getString("token"));


                        Call<registerPlayBean> call = cr.registerPlay(playId, e, p, android_id, getLocalIpAddress(), SharePreferenceUtils.getInstance().getString("token") , body);

                        call.enqueue(new Callback<registerPlayBean>() {
                            @Override
                            public void onResponse(Call<registerPlayBean> call, Response<registerPlayBean> response) {

                                if (response.body().getStatus().equals("1")) {

                                    Intent i = new Intent(getContext(), AdActivity.class);
                                    //Intent i = new Intent(getContext(), Playitem.class);

                                    i.putExtra("userid", response.body().getData().getUserId());
                                    i.putExtra("name", response.body().getData().getName());
                                    i.putExtra("phone", response.body().getData().getPhone());
                                    i.putExtra("playId", response.body().getData().getPlayId());
                                    i.putExtra("image", imm);
                                    i.putExtra("title", name.getText().toString());
                                    i.putExtra("price", price.getText().toString());
                                    i.putExtra("brand", brand.getText().toString());
                                    i.putExtra("color", color.getText().toString());
                                    i.putExtra("size", size.getText().toString());
                                    i.putExtra("negotiable", nagtiable.getText().toString());
                                    i.putExtra("url", video);
                                    i.putExtra("diff", tttt);

                                    startActivity(i);

                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                bar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<registerPlayBean> call, Throwable t) {
                                bar.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), "Invalid Phone", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
                }


            }
        });


        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });


        return view;
    }

    public class ImageAddapter extends FragmentStatePagerAdapter {


        // List<ProductInfo>list = new ArrayList<>();
        List<String> im = new ArrayList<>();

        public ImageAddapter(FragmentManager fm, List<String> im) {
            super(fm);
            this.im = im;

            //this.list = list;
        }

        @Override
        public Fragment getItem(int i) {


            String url = im.get(i);

            Image1 frag = new Image1();
            Bundle b = new Bundle();
            b.putString("url", url);
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

        reload();

    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
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


    void startTextTimer() {
        CountDownTimer timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textTimer.setText("Time till registration " + getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

                tttt = String.valueOf(millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {


                reload();

                //submitTest();
                //finish();
                //Toast.makeText(Test.this, "Test completed", Toast.LENGTH_SHORT).show();

            }
        };
        timer.start();
    }

    void startImageTimer() {
        CountDownTimer timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                imageTimer.setText("Play begins in " + getDurationString(Integer.parseInt(String.valueOf(millisUntilFinished / 1000))));

            }

            @Override
            public void onFinish() {


                reload();
                //submitTest();
                //finish();
                //Toast.makeText(Test.this, "Test completed", Toast.LENGTH_SHORT).show();

            }
        };
        timer.start();
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    void reload() {
        regLayout.setVisibility(View.INVISIBLE);
        imgLayout.setVisibility(View.INVISIBLE);

        bar.setVisibility(View.VISIBLE);

        //Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-13-126-246-74.ap-south-1.compute.amazonaws.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getPlayBean> call = cr.getPlay(SharePreferenceUtils.getInstance().getString("location"));
        call.enqueue(new Callback<getPlayBean>() {
            @Override
            public void onResponse(Call<getPlayBean> call, Response<getPlayBean> response) {


                if (response.body().getStatus().equals("1")) {
                    playId = response.body().getData().get(0).getId();


                    String status = response.body().getData().get(0).getStatus();
                    diff = response.body().getData().get(0).getDiff();
                    video = response.body().getData().get(0).getVideo();


                    if (status.equals("soon")) {

                        regLayout.setVisibility(View.INVISIBLE);
                        imgLayout.setVisibility(View.VISIBLE);
                        //imageTimer.setText("Play begins in ");

                        startImageTimer();


                    } else if (status.equals("register")) {
                        imm = response.body().getData().get(0).getData().getImage().get(0);

                        adapter = new ImageAddapter(getChildFragmentManager(), response.body().getData().get(0).getData().getImage());

                        pager.setAdapter(adapter);

                        indicator.setViewPager(pager);

                        name.setText(response.body().getData().get(0).getData().getName());
                        price.setText(response.body().getData().get(0).getData().getPrice());
                        brand.setText(response.body().getData().get(0).getData().getBrand());
                        color.setText(response.body().getData().get(0).getData().getColor());
                        size.setText(response.body().getData().get(0).getData().getSize());
                        nagtiable.setText(response.body().getData().get(0).getData().getNegotiable());
                        //proof.setText(response.body().getProductInfo().get(0).get());


                        regLayout.setVisibility(View.VISIBLE);
                        imgLayout.setVisibility(View.INVISIBLE);
                        //textTimer.setText("Time till registration ");

                        startTextTimer();

                    } else {
                        regLayout.setVisibility(View.INVISIBLE);
                        imgLayout.setVisibility(View.VISIBLE);
                        imageTimer.setText("Play has ended");
                    }


                } else {

                    regLayout.setVisibility(View.INVISIBLE);
                    imgLayout.setVisibility(View.VISIBLE);
                    imageTimer.setText("No play found");

                }


                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getPlayBean> call, Throwable t) {

                bar.setVisibility(View.GONE);

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(uri, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();

            image.setImageBitmap(bitmap);
        }
        else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
        }



    }

    private static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = true;

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
