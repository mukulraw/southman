package com.sc.bigboss.bigboss;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Bean extends MultiDexApplication {
    private static Context context;

    public final String baseurl = "http://mrtecks.com/";


    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        FontsOverride.setDefaultFont(this  , "MONOSPACE" , "calibri.ttf");


        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

    }
}
