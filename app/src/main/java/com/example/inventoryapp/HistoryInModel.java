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

import org.w3c.dom.Text;

import java.util.ArrayList;


public class HistoryInModel extends ArrayAdapter<String> {
    //Declarasi variable
    private final Activity context;
    private ArrayList<String> vNama_Barang;
    private ArrayList<String> vJumlah_Masuk;
    private ArrayList<String> vFoto;


    public HistoryInModel(Activity context, ArrayList<String> Nama_Barang,
                          ArrayList<String> Jumlah_Masuk)
    {
        super(context, R.layout.recycle_item_inout,Nama_Barang);
        this.context    = context;
        this.vNama_Barang  = Nama_Barang;
        this.vJumlah_Masuk = Jumlah_Masuk;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.recycle_item_inout, null, true);

        //Declarasi komponen
//        TextView TVin = rowView.findViewById(R.id.TVin);
//        TextView TVitem = rowView.findViewById(R.id.item);
        TextView nama       = rowView.findViewById(R.id.namaitem);
        TextView stok        =  rowView.findViewById(R.id.TVjmlitem);


        //Set Parameter Value sesuai widget textview
        nama.setText(vNama_Barang.get(position));
        stok.setText(vJumlah_Masuk.get(position));

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}
