package com.example.shopifygp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.R;

public class ViewHolderItem extends RecyclerView.ViewHolder{
    public ImageView itemImage;
    public TextView itemName;
    public TextView itemDescription;
    public TextView itemPrice;

    public  ViewHolderItem(@NonNull View itemView) {
        super(itemView);

        itemName = (TextView) itemView.findViewById(R.id.itemname);
        itemDescription = (TextView) itemView.findViewById(R.id.itemdescrip);
        itemPrice = (TextView) itemView.findViewById(R.id.foodprice);
        itemImage = (ImageView) itemView.findViewById(R.id.itemimg);
    }
}
