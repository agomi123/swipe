package com.example.swipe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.swipe.Models.Product;
import com.example.swipe.R;

import java.util.ArrayList;
import java.util.Locale;

public class ProductListAdapter extends ArrayAdapter<Product> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Product> animalNamesList;
    private ArrayList<Product> arraylist;
    public ProductListAdapter(@NonNull Context context, ArrayList<Product> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
        animalNamesList=courseModelArrayList;
        this.arraylist = new ArrayList<Product>();
        this.arraylist.addAll(animalNamesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        Product item = getItem(position);
        TextView name = listitemView.findViewById(R.id.proname);
        TextView price = listitemView.findViewById(R.id.proprice);
        ImageView courseIV = listitemView.findViewById(R.id.img);

        name.setText(item.getProduct_name());
        price.setText("Only ₹ "+ item.getPrice());
       // price.setText(String.valueOf(item.getPrice()));
        if(item.getImg().equals(""))
            courseIV.setImageResource(R.drawable.img);
        else
            Glide.with(getContext()).load(item.getImg()).into(courseIV);

        return listitemView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        animalNamesList.clear();
        if (charText.length() == 0) {
            animalNamesList.addAll(arraylist);
        } else {
            for (Product wp : arraylist) {
                if (wp.getProduct_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    animalNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
