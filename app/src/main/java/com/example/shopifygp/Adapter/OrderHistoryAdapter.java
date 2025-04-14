package com.example.shopifygp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.Model.OrderModel;
import com.example.shopifygp.R;
import com.example.shopifygp.ViewHolder.OrderHistoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends FirebaseRecyclerAdapter<OrderModel, OrderHistoryViewHolder> {
    private DatabaseReference ordersRef;
    FirebaseDatabase database;
    public OrderHistoryAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
        database = FirebaseDatabase.getInstance("https://wagba-ed603-default-rtdb.europe-west1.firebasedatabase.app");
        ordersRef = database.getReference("Orders");
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position, @NonNull OrderModel order) {
        holder.orderIdTextView.setText("#" + order.getOrderID());
        holder.deliveryTimeTextView.setText(order.getDeliveryTime());
        holder.deliveryGateTextView.setText(order.getDeliveryGate());
        holder.orderPriceTextView.setText(order.getPrice());
        holder.orderStatusTextView.setText(order.getOrderStatus());
        List<String> orderSummary = new ArrayList<>();
        for (OrderItemModel orderItem : order.getOrderItems()) {
            orderSummary.add("x" + orderItem.getOrderItemCount() + " " + orderItem.getOrderItemName() + "\n" + orderItem.getOrderItemTotalPrice() + "$");
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(holder.itemView.getContext(), android.R.layout.simple_list_item_1, orderSummary);
        holder.orderItemsListView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_card, parent, false);
        return new OrderHistoryViewHolder(view);
    }
}
