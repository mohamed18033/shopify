package com.example.shopifygp.ViewHolder;


import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.R;

public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView orderIdTextView;
    public TextView orderPriceTextView;
    public TextView deliveryTimeTextView;
    public TextView deliveryGateTextView;
    public TextView orderStatusTextView;
    public ListView orderItemsListView;


    public OrderHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        orderIdTextView = itemView.findViewById(R.id.order_id);
        orderPriceTextView = itemView.findViewById(R.id.order_history_price);
        deliveryTimeTextView = itemView.findViewById(R.id.order_history_delivery_time);
        deliveryGateTextView = itemView.findViewById(R.id.order_history_delivery_gate);
        orderItemsListView = itemView.findViewById(R.id.order_history_list_view);
        orderStatusTextView = itemView.findViewById(R.id.order_status);
    }
}
