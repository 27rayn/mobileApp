package com.example.inventoryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.Random;

public class AddItems extends AppCompatActivity {

    Button generate, saveaddeditem;
    EditText barcode,itemsss,stockitem,supplieret;
    String id, nama, perusahaan, stok;
    ProgressDialogAddItems customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemsss = findViewById(R.id.itemssss);
        stockitem = findViewById(R.id.stockitem);
        supplieret = findViewById(R.id.supplieret);
        generate = findViewById(R.id.generate);
        saveaddeditem = findViewById(R.id.saveaaddeditem);
        barcode = findViewById(R.id.barcode);

        generate.addTextChangedListener(addItemsWatcher);
        itemsss.addTextChangedListener(addItemsWatcher);
        stockitem.addTextChangedListener(addItemsWatcher);
        supplieret.addTextChangedListener(addItemsWatcher);

        customProgressDialog = new ProgressDialogAddItems(AddItems.this);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ImageButton backbutton = findViewById(R.id.backtoolbar);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    private TextWatcher addItemsWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            id = barcode.getText().toString().trim();
            nama = itemsss.getText().toString().trim();
            stok  = stockitem.getText().toString().trim();
            perusahaan = supplieret.getText().toString().trim();

            saveaddeditem.setEnabled
                    (!id.isEmpty() && !nama.isEmpty() && !perusahaan.isEmpty() && !stok.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            saveaddeditem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    kirimData();
                }
            });
        }
        void kirimData(){
            AndroidNetworking.post("https://tkjb2019.com/mobile/api_kelompok_2/sm/addDataBarang.php")
                    .addBodyParameter("id_barang", id)
                    .addBodyParameter("nama",""+nama)
                    .addBodyParameter("perusahaan",""+perusahaan)
                    .addBodyParameter("stok",""+stok)
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
                                Toast.makeText(AddItems.this, ""+pesan, Toast.LENGTH_SHORT).show();
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
                                    new AlertDialog.Builder(AddItems.this)
                                            .setMessage("Invalid data")
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
    };

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        Button BTNgeneratebarcode = dialog.findViewById(R.id.BTNgeneratebarcode);
        Button BTNmanualinput = dialog.findViewById(R.id.BTNmanualinput);
        Button closebottomsheet = dialog.findViewById(R.id.closebottomsheet);

        BTNgeneratebarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int val = random.nextInt(1000000000);
                barcode.setText(Integer.toString(val));
                dialog.dismiss();
            }
        });

        BTNmanualinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        closebottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}