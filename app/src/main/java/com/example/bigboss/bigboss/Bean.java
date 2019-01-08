package com.example.bigboss.bigboss;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Bean extends Application {


    public String baseurl = "http://ec2-13-126-246-74.ap-south-1.compute.amazonaws.com/";




    @Override
    public void onCreate() {
        super.onCreate();

        FontsOverride.setDefaultFont(this  , "MONOSPACE" , "calibri.ttf");


        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

    }
}
