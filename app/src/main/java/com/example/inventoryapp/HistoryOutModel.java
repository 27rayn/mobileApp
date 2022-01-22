package com.example.inventoryapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HistoryOutModel extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vNama_Barang_Keluar;
    private ArrayList<String> vJumlah_Keluar;

    public HistoryOutModel(Activity context, ArrayList<String> Nama_Barang_Keluar,
                           ArrayList<String> Jumlah_Keluar)
    {
        super(context, R.layout.recycle_item_out,Nama_Barang_Keluar);
        this.context    = context;
        this.vNama_Barang_Keluar  = Nama_Barang_Keluar;
        this.vJumlah_Keluar = Jumlah_Keluar;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.recycle_item_out, null, true);

        //Declarasi komponen
//        TextView TVin = rowView.findViewById(R.id.TVin);
//        TextView TVitem = rowView.findViewById(R.id.item);

        TextView namakeluar = rowView.findViewById(R.id.namaitemout);
        TextView stokout = rowView.findViewById(R.id.TVjmlitemout);



        //Set Parameter Value sesuai widget textview;
        namakeluar.setText(vNama_Barang_Keluar.get(position));
        stokout.setText(vJumlah_Keluar.get(position));

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}
