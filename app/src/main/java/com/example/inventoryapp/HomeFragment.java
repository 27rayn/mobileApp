package com.example.inventoryapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    TextView TVName, TVday, TVdate, TVstockinnum, TVstockoutnum, TVstocknum;
    String username, stockinnum, stockoutnum, stocknum;
    SharedPreferences sp,sum;
    ImageButton opencam;
    Button registeritems, stockin, stockout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);



        opencam = view.findViewById(R.id.opencam);
        TVday = view.findViewById(R.id.TVday);
        TVdate = view.findViewById(R.id.Date_Infostock);
        TVName = view.findViewById(R.id.TVName);
        registeritems = view.findViewById(R.id.registeritems);
        stockin = view.findViewById(R.id.Stockin);
        stockout = view.findViewById(R.id.Stockout);
        TVstockinnum = view.findViewById(R.id.TVStockInNum);
        TVstockoutnum = view.findViewById(R.id.TVStockOutNum);
        TVstocknum = view.findViewById(R.id.TVStockNum);



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

        getCountStock();
        getSumIndata();
        getSumOutdata();

        return view;
    }

    public void getCountStock(){
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/countbarang.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status) {
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon", "" + ja);
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    stocknum = (jo.getString("COUNT(id_barang)"));
                                }
                                TVstocknum.setText(stocknum);

                            } else {
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void getSumIndata(){
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/insumdashboard.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status) {
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon", "" + ja);
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    stockinnum = (jo.getString("SUM(jumlah_masuk)"));
                                }
                                TVstockinnum.setText(stockinnum);

                            } else {
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void getSumOutdata(){
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/outsumdashboard.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status) {
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon", "" + ja);
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    stockoutnum = (jo.getString("SUM(jumlah_keluar)"));
                                }
                                TVstockoutnum.setText(stockoutnum);

                            } else {
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
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