package com.sc.bigboss.bigboss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Two extends Fragment {

    private Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.two, container, false);

        next = view.findViewById(R.id.next);

        next.setOnClickListener(v -> {


            Intent i = new Intent(getContext() , Three.class);
            startActivity(i);
        });
        return view;
    }
}
