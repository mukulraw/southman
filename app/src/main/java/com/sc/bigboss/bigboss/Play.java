package com.sc.bigboss.bigboss;

import android.content.ContentUris;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sc.bigboss.bigboss.getPlayPOJO.getPlayBean;
import com.sc.bigboss.bigboss.registerPlayPOJO.registerPlayBean;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

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

    private FloatingActionButton submit;

    private ViewPager pager;

    private CircleIndicator indicator;

    private ImageAddapter adapter;

    private TextView textTimer;
    private TextView imageTimer;

    private TextView name;
    private TextView color;
    private TextView price;
    private TextView size;
    private TextView proof;
    private TextView brand;
    private TextView nagtiable;

    private EditText email;
    private EditText phone;

    private ProgressBar bar;

    String id;

    private String playId;

    private String imm;
    private String base;

    private ConstraintLayout regLayout;
    private View imgLayout;

    private LinearLayout changeImage;

    private ImageView image;

    private File f1;

    private String video;
    private String diff;
    private String sku;

    // List<ProductInfo>list;

    private String tttt;

    private Uri uri;

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


        submit.setOnClickListener(v -> {


            String e = email.getText().toString();
            String p = phone.getText().toString();

            if (e.length() > 0) {
                if (p.length() == 10) {


                    bar.setVisibility(View.VISIBLE);


                    MultipartBody.Part body = null;

                    try {

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


                    Call<registerPlayBean> call = cr.registerPlay(playId, e, p, android_id, getLocalIpAddress(), SharePreferenceUtils.getInstance().getString("token") , sku , body);

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
                                i.putExtra("image", base + "southman/admin2/upload/products/" + imm);
                                i.putExtra("title", name.getText().toString());
                                i.putExtra("price", price.getText().toString());
                                i.putExtra("brand", brand.getText().toString());
                                i.putExtra("color", color.getText().toString());
                                i.putExtra("size", size.getText().toString());
                                i.putExtra("negotiable", proof.getText().toString());
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


        });


        changeImage.setOnClickListener(v -> {


            final CharSequence[] items = {"Take Photo from Camera",
                    "Choose from Gallery",
                    "Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(items, (dialog, item) -> {
                if (items[item].equals("Take Photo from Camera")) {
                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                    File newdir = new File(dir);
                    try {
                        newdir.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                    f1 = new File(file);
                    try {
                        f1.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", f1);

                    Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(getpic, 1);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();


        });


        return view;
    }

    class ImageAddapter extends FragmentStatePagerAdapter {


        // List<ProductInfo>list = new ArrayList<>();
        List<String> im;

        ImageAddapter(FragmentManager fm, List<String> im) {
            super(fm);
            this.im = im;

            //this.list = list;
        }

        @Override
        public Fragment getItem(int i) {


            String url = im.get(i);

            Image1 frag = new Image1();
            Bundle b = new Bundle();
            b.putString("url", base + "southman/admin2/upload/products/" + url);
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return im.size();
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume

        reload();

    }

    private String getLocalIpAddress() {
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

    private CountDownTimer timer;

    private void startTextTimer() {
        timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
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

    private void startImageTimer() {
        timer = new CountDownTimer((long) Float.parseFloat(diff) * 1000, 1000) {
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

    private void reload() {
        regLayout.setVisibility(View.INVISIBLE);
        imgLayout.setVisibility(View.INVISIBLE);

        bar.setVisibility(View.VISIBLE);

        //Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        base = "http://mrtecks.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getPlayBean> call = cr.getPlay(SharePreferenceUtils.getInstance().getString("location"));
        call.enqueue(new Callback<getPlayBean>() {
            @Override
            public void onResponse(Call<getPlayBean> call, Response<getPlayBean> response) {


                if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
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

                        sku = response.body().getData().get(0).getData().getSku();

                        adapter = new ImageAddapter(getChildFragmentManager(), response.body().getData().get(0).getData().getImage());

                        pager.setAdapter(adapter);

                        indicator.setViewPager(pager);

                        name.setText(response.body().getData().get(0).getData().getName());
                        price.setText(response.body().getData().get(0).getData().getPrice());
                        brand.setText(response.body().getData().get(0).getData().getBrand());
                        color.setText(response.body().getData().get(0).getData().getColor());
                        size.setText(response.body().getData().get(0).getData().getSize());
                        nagtiable.setText(response.body().getData().get(0).getData().getNegotiable());


                        String det = response.body().getData().get(0).getData().getDetails();


                        proof.setText(Html.fromHtml(det.trim()).toString().trim());

                        Log.d("dddddd" , String.valueOf(Html.fromHtml(det)));

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

    private static void handleP(SpannableStringBuilder text) {
        int len = text.length();

        if (len >= 1 && text.charAt(len - 1) == '\n') {
            if (len >= 2 && text.charAt(len - 2) == '\n') {
                return;
            }
            text.append("\n");
            return;
        }

        if (len != 0) {

            text.append("\n\n");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            String ypath = getPath(getContext(), uri);
            f1 = new File(ypath);

            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, filePath, null, null, null);
            Objects.requireNonNull(cursor).moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();

            image.setImageBitmap(bitmap);
        }
        else if (requestCode == 1 && resultCode == RESULT_OK) {

            image.setImageURI(uri);
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

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            timer.cancel();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public static class MyCustomDialogFragment extends DialogFragment {


        ViewPager pager;
        TextView desc;
        ProgressBar bar;
        String base;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.play_dialog, container, false);

            pager = v.findViewById(R.id.viewPager);
            desc = v.findViewById(R.id.details);
            bar = v.findViewById(R.id.progressBar4);

            // Do all the stuff to initialize your custom view


            bar.setVisibility(View.VISIBLE);

            //Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

            base = "http://mrtecks.com/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<getPlayBean> call = cr.getPlay(SharePreferenceUtils.getInstance().getString("location"));
            call.enqueue(new Callback<getPlayBean>() {
                @Override
                public void onResponse(Call<getPlayBean> call, Response<getPlayBean> response) {


                    if (Objects.requireNonNull(response.body()).getStatus().equals("1")) {
                        //playId = response.body().getData().get(0).getId();


                        String status = response.body().getData().get(0).getStatus();

                        if (status.equals("soon")) {


                        } else if (status.equals("register")) {
                            //imm = response.body().getData().get(0).getData().getImage().get(0);

                            //sku = response.body().getData().get(0).getData().getSku();

                            ImageAddapter adapter = new ImageAddapter(getChildFragmentManager(), response.body().getData().get(0).getData().getImage());

                            pager.setAdapter(adapter);

                            //indicator.setViewPager(pager);



                            String det = response.body().getData().get(0).getData().getDetails();


                            desc.setText(Html.fromHtml(det.trim()).toString().trim());

                            Log.d("dddddd" , String.valueOf(Html.fromHtml(det)));

                            //textTimer.setText("Time till registration ");


                        }


                    }


                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<getPlayBean> call, Throwable t) {

                    bar.setVisibility(View.GONE);

                }
            });


            return v;
        }


        class ImageAddapter extends FragmentStatePagerAdapter {


            // List<ProductInfo>list = new ArrayList<>();
            List<String> im;

            ImageAddapter(FragmentManager fm, List<String> im) {
                super(fm);
                this.im = im;

                //this.list = list;
            }

            @Override
            public Fragment getItem(int i) {


                String url = im.get(i);

                Image1 frag = new Image1();
                Bundle b = new Bundle();
                b.putString("url", base + "southman/admin2/upload/products/" + url);
                frag.setArguments(b);
                return frag;
            }

            @Override
            public int getCount() {
                return im.size();
            }
        }


        public static class Image1 extends Fragment {


            ImageView image;
            String url;

            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.image1 , container , false);

                assert getArguments() != null;
                url = getArguments().getString("url");

                image = view.findViewById(R.id.image);

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(url , image , options);



                return view;
            }
        }

    }


}
