package com.example.bigboss.bigboss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;

public class page4 extends Fragment {


    ZoomageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page4 , container , false);

        imageView = view.findViewById(R.id.watch);

        return view;
    }
}
