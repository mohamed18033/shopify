package com.example.shopifygp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView categoryImage;
    public TextView categoryName;
    public TextView categoryDescription;


    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage =itemView.findViewById(R.id.restaurantimg);
        categoryName =itemView.findViewById(R.id.restaurantname);
        categoryDescription =itemView.findViewById(R.id.restaurantcategory);

    }
}
