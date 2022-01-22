package com.example.inventoryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

public class StockInUpdate extends AppCompatActivity {

    private ListView listitemstockin;
    private ItemsModel adapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText updateStockIn;
    private Button applystockin, cancelstockin;
    private TextView TVitemsnamepopup, TVstockinpopup;

    CustomProgressDialog customProgressDialog;

    String id_barang,nama_barang,jumlah_masuk,tanggal_masuk;
    Button BTNincrement, BTNdecrement;

    ProgressDialog progressDialog;
    ImageButton backtoolbarstockin;
    ArrayList<String> nama=new ArrayList<>();
    ArrayList<String>perusahaan=new ArrayList<>();
    ArrayList<String>foto=new ArrayList<>();
    ArrayList<String>stok=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_in_update);

        listitemstockin = findViewById(R.id.listitemstockin);
        backtoolbarstockin = findViewById(R.id.backtoolbarstockin);

        customProgressDialog = new CustomProgressDialog(StockInUpdate.this);

        backtoolbarstockin.setOnClickListener(new View.OnClickListener() {
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
                                    adapter = new ItemsModel(StockInUpdate.this, nama, perusahaan, foto, stok);
                                    listitemstockin.setAdapter(adapter);
                                }

//                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
//                                ItemsModel adapter = new ItemsModel(getActivity(), array_nama,array_foto,array_stok);
//                                //Set adapter to list
//                                itemsList.setAdapter(adapter);


                                //edit and delete
                                listitemstockin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik", "" + nama.get(position));
                                        Toast.makeText(StockInUpdate.this, nama.get(position), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(StockInUpdate.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

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
        final  View stockUpdatePopupView = getLayoutInflater().inflate(R.layout.popupstockin, null);

        TVstockinpopup = stockUpdatePopupView.findViewById(R.id.TVstockinpopup);
        TVitemsnamepopup = stockUpdatePopupView.findViewById(R.id.TVitemsnamepopup);
        updateStockIn = stockUpdatePopupView.findViewById(R.id.inputstockquantity);
        applystockin = stockUpdatePopupView.findViewById(R.id.ApplyStockIn);
        cancelstockin = stockUpdatePopupView.findViewById(R.id.CancelStockIn);

        applystockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customProgressDialog.show();

                jumlah_masuk = updateStockIn.getText().toString();
                nama_barang = TVitemsnamepopup.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });

        cancelstockin.setOnClickListener(new View.OnClickListener() {
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

    void validasiData(){
        if(jumlah_masuk.equals("")) {
            customProgressDialog.dismiss();
            Toast.makeText(StockInUpdate.this, "Sorry your input is empty, we cannot update your items", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("https://tkjb2019.com/mobile/api_kelompok_2/sm/barangmasuk.php")
                .addBodyParameter("nama_barang",""+nama_barang)
                .addBodyParameter("jumlah_masuk",""+jumlah_masuk)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        customProgressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(StockInUpdate.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
//                                Intent toHome;
//                                toHome = new Intent(StockInUpdate.this, MainActivity.class);
//                                startActivity(toHome);
//                                new androidx.appcompat.app.AlertDialog.Builder(Login.this)
//                                        .setMessage("Login Success !")
////                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
////                                            @Override
////                                            public void onClick(DialogInterface dialog, int which) {
////                                                //Intent i = getIntent();
////                                                //setResult(RESULT_CANCELED,i);
////                                                //add_mahasiswa.this.finish();
////
////                                            }
////                                        })
//                                        .setCancelable(false)
//                                        .show();


                            }
                            else{
                                new AlertDialog.Builder(StockInUpdate.this)
                                        .setMessage("Invalid email and password")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = getIntent();
                                                //setResult(RESULT_CANCELED,i);
                                                //add_mahasiswa.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }

}