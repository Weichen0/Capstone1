package com.example.capstone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class ListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] factors;
    private final Integer[] img;

    public ListAdapter(@NonNull Activity context, String[] factors, Integer[] img) {
        super(context, R.layout.layout, factors);

        this.context = context;
        this.factors = factors;
        this.img = img;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout, null, true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        imageView.setImageResource(img[position]);


        return rowView;
    }
}
