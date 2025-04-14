package com.example.shopifygp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    TextView categoryNameTextView;
    TextView countTextView;
    TextView priceTextView;

    public OrderItemViewHolder(View itemView) {
        super(itemView);
        categoryNameTextView = itemView.findViewById(R.id.itemname);
        countTextView = itemView.findViewById(R.id.item_count);
        priceTextView = itemView.findViewById(R.id.price);
    }

    public void bind(OrderItemModel orderItem) {
        categoryNameTextView.setText(orderItem.getOrderItemName());
        countTextView.setText(String.valueOf(orderItem.getOrderItemCount()));
        priceTextView.setText(String.valueOf(orderItem.getOrderItemTotalPrice()));
    }
}

