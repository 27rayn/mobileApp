package com.example.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class HistoryStockInFragment extends Fragment {

    private Context mContext;
    ListView listhistory;
    ArrayList<String>array_nama_barang,array_jumlahmasuk;
    FragmentManager fragmentManager;
    TextView emptypage, tvrec;
    ImageView imageempty;
    String stockinnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_stock_in, container, false);

        listhistory = view.findViewById(R.id.stock_recyclerview);
        emptypage = view.findViewById(R.id.emptypage);
        imageempty = view.findViewById(R.id.imageempty);
        tvrec = view.findViewById(R.id.tvrec);
        getDataMasuk();



        return view;
    }
    void initializeArray(){
        array_nama_barang      = new ArrayList<String>();
        array_jumlahmasuk     = new ArrayList<String>();

        array_nama_barang.clear();
        array_jumlahmasuk.clear();
    }


    public void getDataMasuk(){
        initializeArray();
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/getbarangmasuk.php")
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
                                    array_nama_barang.add(jo.getString("nama_barang"));
                                    array_jumlahmasuk.add(jo.getString("jumlah_masuk"));

                                }
                                HistoryInModel adapter = new HistoryInModel(getActivity(), array_nama_barang, array_jumlahmasuk);
                                listhistory.setAdapter(adapter);
                                tvrec.setVisibility(View.VISIBLE);
                                emptypage.setVisibility(View.INVISIBLE);


                            } else {
                                imageempty.setVisibility(View.VISIBLE);
                                emptypage.setVisibility(View.VISIBLE);
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



}