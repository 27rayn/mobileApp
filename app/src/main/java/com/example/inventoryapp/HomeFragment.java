package com.example.inventoryapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    TextView TVName, TVday, TVdate;
    String username;
    SharedPreferences sp;
    ImageButton opencam;
    Button registeritems, stockin, stockout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);



        Bundle extra = getActivity().getIntent().getExtras();
        opencam = view.findViewById(R.id.opencam);
        TVday = view.findViewById(R.id.TVday);
        TVdate = view.findViewById(R.id.Date_Infostock);
        TVName = view.findViewById(R.id.TVName);
        registeritems = view.findViewById(R.id.registeritems);
        stockin = view.findViewById(R.id.Stockin);
        stockout = view.findViewById(R.id.Stockout);

        //GetUsername From Database
        sp = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        username = sp.getString("nama", null);
        TVName.setText(username);

//        if (extra != null) {
//
//            tvname = extra.getString("ETSU1");
//            TVName.setText(tvname);
//        }


        day();
        nowdate();

        opencam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), scan.class);
                startActivity(intent);
            }
        });

                registeritems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAdditems;
                toAdditems = new Intent(getActivity(),AddItems.class);
                startActivity(toAdditems);
            }
        });

        stockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStockIn;
                toStockIn = new Intent(getContext(), StockInUpdate.class);
                startActivity(toStockIn);
            }
        });


        stockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStockOut;
                toStockOut = new Intent(getContext(), StockOutUpdate.class);
                startActivity(toStockOut);
            }
        });

        return view;
    }

    private void nowdate(){
        Calendar tgl = Calendar.getInstance();
        String curDate = DateFormat.getDateInstance().format(tgl.getTime());
        TVdate.setText(curDate);
    }

    private void day() {
        Calendar kalender = Calendar.getInstance();
        int jam = kalender.get(Calendar.HOUR_OF_DAY);

        if (jam >= 0 && jam < 12) {
            TVday.setText("Good Morning,");
        } else if (jam >= 12 && jam < 18) {
            TVday.setText("Good Afternoon,");
        } else if (jam >= 18 && jam < 24) {
            TVday.setText("Good Evening,");
        }
    }
}