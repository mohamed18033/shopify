package com.example.shopifygp.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopifygp.Database.OrderItemModel;
import com.example.shopifygp.R;
import com.example.shopifygp.ViewHolder.OrderItemViewHolder;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private List<OrderItemModel> mOrderItems;

    public OrderItemAdapter(List<OrderItemModel> orderItems) {
        mOrderItems = orderItems;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_card, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        OrderItemModel orderItem = mOrderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return mOrderItems.size();
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        mOrderItems = orderItems;
        notifyDataSetChanged();
    }




}
