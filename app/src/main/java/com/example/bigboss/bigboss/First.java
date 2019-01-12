package com.example.bigboss.bigboss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class First extends Fragment {


    TextView next;

ViewPager pager;

public void setData(ViewPager pager)
{
    this.pager = pager;
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first , container , false);

        next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pager.setCurrentItem(1);


                /*FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                First f = new First();
                ft.replace(R.id.replace, f);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

                ft.commit();
*/

            }
        });
        return view;
    }
}
