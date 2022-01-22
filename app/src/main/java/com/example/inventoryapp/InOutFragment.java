package com.example.inventoryapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class InOutFragment extends Fragment {

    private Context mContext;
    ListView listhistory;
    ArrayList<String>array_nama_barang,array_jumlahmasuk;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_out, container, false);

        listhistory = view.findViewById(R.id.stock_recyclerview);


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


                            } else {
                                Toast.makeText(getActivity(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

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