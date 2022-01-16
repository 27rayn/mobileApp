package com.example.inventoryapp;

import android.app.ProgressDialog;
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

    private ListView recyclerbarang;
    private ItemsModel adapter;
    ArrayList<String>nama=new ArrayList<>();
    ArrayList<String>perusahaan=new ArrayList<>();
    ArrayList<String>foto=new ArrayList<>();
    ArrayList<String>stok=new ArrayList<>();

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
        nama      = new ArrayList<String>();
        perusahaan       = new ArrayList<String>();
        foto   = new ArrayList<String>();
        stok     = new ArrayList<String>();

        nama.clear();
        perusahaan.clear();
        foto.clear();
        stok.clear();
    }

    public void getData(){
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
                                    nama.add(jo.getString("nama"));
                                    perusahaan.add(jo.getString("perusahaan"));
                                    foto.add(jo.getString("foto"));
                                    stok.add(jo.getString("stok"));
                                }

                                ItemsModel adapter = new ItemsModel(getActivity(), nama, perusahaan, foto, stok);
                                recyclerbarang.setAdapter(adapter);

//                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
//                                ItemsModel adapter = new ItemsModel(getActivity(), array_nama,array_foto,array_stok);
//                                //Set adapter to list
//                                itemsList.setAdapter(adapter);

                                //edit and delete
//                                recyclerbarang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        Log.d("TestKlik", "" + nama.get(position));
//                                        Toast.makeText(getActivity(), nama.get(position), Toast.LENGTH_SHORT).show();
//
////                                        Setelah proses koneksi keserver selesai, maka aplikasi akan
////                                        berpindah class
////                                        DataActivity.class dan membawa/mengirim data -data
////                                        hasil query dari server.
//                                        Intent i = new Intent(getActivity(), StockInUpdate.class);
//                                        i.putExtra("nama", nama.get(position));
//                                        i.putExtra("perusahaan", perusahaan.get(position));
//                                        i.putExtra("foto", foto.get(position));
//                                        i.putExtra("stok", stok.get(position));
//                                        startActivity(i);
//                                    }
//                                });

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
