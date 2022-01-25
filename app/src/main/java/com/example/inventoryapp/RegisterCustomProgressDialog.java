package com.example.inventoryapp;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class RegisterCustomProgressDialog extends Dialog {

    public RegisterCustomProgressDialog(@NonNull Context context){
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER_HORIZONTAL;

        getWindow().setAttributes(params);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        setContentView(R.layout.loading_layout_register);
    }


}
