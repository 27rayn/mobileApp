package com.example.inventoryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemsFragment extends Fragment {

    ListView recyclerbarang;
    ArrayList<String>array_nama, array_perusahaan, array_foto, array_stok;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerbarang = view.findViewById(R.id.recyclebarang);


        ImageButton additems = view.findViewById(R.id.additems);

        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddItems;
                toAddItems = new Intent(getActivity(), AddItems.class);
                startActivity(toAddItems);
            }
        });

//        recyclerbarang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), SettingsFragment.class);
//                startActivity(intent);
//            }
//        });

        getData();

        return view;
    }

    void initializeArray(){
        array_nama      = new ArrayList<String>();
        array_perusahaan       = new ArrayList<String>();
        array_foto   = new ArrayList<String>();
        array_stok     = new ArrayList<String>();

        array_nama.clear();
        array_perusahaan.clear();
        array_foto.clear();
        array_stok.clear();
    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("https://tkjb2019.com/mobile/api_kelompok_2/sm/getDatabarang.php")
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
                                    array_nama.add(jo.getString("nama"));
                                    array_perusahaan.add(jo.getString("perusahaan"));
                                    array_foto.add(jo.getString("foto"));
                                    array_stok.add(jo.getString("stok"));
                                }

                               final ItemsModel adapter = new ItemsModel(getActivity(), array_nama, array_perusahaan, array_foto, array_stok);
                                recyclerbarang.setAdapter(adapter);


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
