package com.sc.bigboss.bigboss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class Page1 extends Fragment {

    private ImageView imageView;

    private String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page1 , container , false);

        url = Objects.requireNonNull(getArguments()).getString("url");

        imageView = view.findViewById(R.id.watch);

/*
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(url , imageView , options);
*/


        Glide.with(Objects.requireNonNull(getActivity())).load(url).into(imageView);


        return view;
    }
}
