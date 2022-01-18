package com.example.inventoryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class StockOutUpdate extends AppCompatActivity {

    private ListView listitemstockout;
    private ItemsModel adapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText updateStockOut;
    private Button applystockout, cancelstockout;
    private TextView TVitemsnamepopup, TVstockoutpopup;


    ImageButton backtoolbarstockout;
    ArrayList<String> nama=new ArrayList<>();
    ArrayList<String>perusahaan=new ArrayList<>();
    ArrayList<String>foto=new ArrayList<>();
    ArrayList<String>stok=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_out_update);

        listitemstockout = findViewById(R.id.listitemstockin);
        backtoolbarstockout = findViewById(R.id.backtoolbarstockin);

        backtoolbarstockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getDataBarang();
    }
    public void getDataBarang(){
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
//                                    JSONObject jo = ja.getJSONObject(i);
                                    nama.add(ja.getJSONObject(i).getString("nama"));
                                    perusahaan.add(ja.getJSONObject(i).getString("perusahaan"));
                                    foto.add(ja.getJSONObject(i).getString("foto"));
                                    stok.add(ja.getJSONObject(i).getString("stok"));
                                    adapter = new ItemsModel(StockOutUpdate.this, nama, perusahaan, foto, stok);
                                    listitemstockout.setAdapter(adapter);
                                }

//                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
//                                ItemsModel adapter = new ItemsModel(getActivity(), array_nama,array_foto,array_stok);
//                                //Set adapter to list
//                                itemsList.setAdapter(adapter);


                                //edit and delete
                                listitemstockout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik", "" + nama.get(position));
                                        Toast.makeText(StockOutUpdate.this, nama.get(position), Toast.LENGTH_SHORT).show();
                                        createUpdateStockDialog();
                                        TVitemsnamepopup.setText(nama.get(position));

//                                        Setelah proses koneksi keserver selesai, maka aplikasi akan
//                                        berpindah class
//                                        DataActivity.class dan membawa/mengirim data -data
//                                        hasil query dari server.
//                                        Intent i = new Intent(getActivity(), SettingsFragment.class);
//                                        i.putExtra("nama", nama.get(position));
//                                        i.putExtra("perusahaan", perusahaan.get(position));
//                                        i.putExtra("foto", foto.get(position));
//                                        i.putExtra("stok", stok.get(position));
//                                        startActivity(i);
                                    }
                                });

                            } else {
                                Toast.makeText(StockOutUpdate.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

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

    public void createUpdateStockDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final  View stockUpdatePopupView = getLayoutInflater().inflate(R.layout.popupstockout, null);

        TVstockoutpopup = stockUpdatePopupView.findViewById(R.id.TVstockinpopup);
        TVitemsnamepopup = stockUpdatePopupView.findViewById(R.id.TVitemsnamepopup);
        updateStockOut = stockUpdatePopupView.findViewById(R.id.inputstockquantity);
        applystockout = stockUpdatePopupView.findViewById(R.id.ApplyStockOut);
        cancelstockout = stockUpdatePopupView.findViewById(R.id.CancelStockOut);

        updateStockOut.setBackgroundResource(R.color.white);

        cancelstockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(stockUpdatePopupView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.corneralertdialog));
        dialog.show();
    }

}