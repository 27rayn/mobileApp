package com.example.inventoryapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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


public class HistoryStockOutFragment extends Fragment {

    private Context mContext;
    ListView listhistory;
    ArrayList<String> array_nama_barang,array_jumlahkeluar;
    FragmentManager fragmentManager;
    TextView emptypage;
    ImageView imageempty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_stock_out, container, false);

        emptypage = view.findViewById(R.id.emptypage);
        imageempty = view.findViewById(R.id.imageempty);

        listhistory = view.findViewById(R.id.stock_historyout);


        getDataKeluar();
        return view;
    }

    void initializeArray(){
        array_nama_barang      = new ArrayList<String>();
        array_jumlahkeluar     = new ArrayList<String>();

        array_nama_barang.clear();
        array_jumlahkeluar.clear();
    }

    public void getDataKeluar(){
        initializeArray();
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/getbarangkeluar.php")
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
                                    array_nama_barang.add(jo.getString("nama_barang_keluar"));
                                    array_jumlahkeluar.add(jo.getString("jumlah_keluar"));
                                }
                                HistoryOutModel adapter = new HistoryOutModel(getActivity(), array_nama_barang, array_jumlahkeluar);
                                listhistory.setAdapter(adapter);

                                imageempty.setVisibility(View.GONE);
                                emptypage.setVisibility(View.GONE);

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