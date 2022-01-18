package com.example.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Random;

public class AddItems extends AppCompatActivity {

    Button generate, saveaddeditem;
    EditText itemsss,stockitem,supplieret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemsss = findViewById(R.id.itemssss);
        stockitem = findViewById(R.id.stockitem);
        supplieret = findViewById(R.id.supplieret);
        generate = findViewById(R.id.generate);
        saveaddeditem = findViewById(R.id.saveaaddeditem);

        itemsss.addTextChangedListener(addItemsWatcher);
        stockitem.addTextChangedListener(addItemsWatcher);
        supplieret.addTextChangedListener(addItemsWatcher);

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
            String itemsInput = itemsss.getText().toString().trim();
            String stockInput = stockitem.getText().toString().trim();
            String supplierInput = supplieret.getText().toString().trim();

            saveaddeditem.setEnabled
                    (!itemsInput.isEmpty() && !stockInput.isEmpty() && !supplierInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

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
                generate.setText(Integer.toString(val));
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